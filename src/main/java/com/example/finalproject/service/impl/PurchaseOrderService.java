package com.example.finalproject.service.impl;

import com.example.finalproject.exception.NotFoundException;
import com.example.finalproject.exception.PurchaseFailureException;
import com.example.finalproject.exception.QuantityNotAvailableException;
import com.example.finalproject.model.*;
import com.example.finalproject.model.Enum.OrderStatus;
import com.example.finalproject.repository.*;
import com.example.finalproject.service.IPurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService implements IPurchaseOrderService {

    @Autowired
    private PurchaseOrderRepo purchaseOrderRepo;

    @Autowired
    private PurchaseItemRepo purchaseItemRepo;

    @Autowired
    private AdvertisementRepo advertisementRepo;

    @Autowired
    private BuyerRepo buyerRepo;

    @Autowired
    private BatchRepo batchRepo;
    @Autowired
    private SectionRepo sectionRepo;

    @Override
    public BigDecimal createPurchaseOrder(PurchaseOrder purchaseOrder) {
        buyerRepo.findById(purchaseOrder.getBuyer().getBuyerCode()).orElseThrow(() -> new NotFoundException("Buyer not found"));

        BigDecimal totalPrice = new BigDecimal(0);
        LocalDate dateTime = LocalDate.of(purchaseOrder.getDateTime().getYear(), purchaseOrder.getDateTime().getMonth(),
                purchaseOrder.getDateTime().getDayOfMonth());
        List<PurchaseItem> purchaseItemList = purchaseOrder.getPurchaseItems();

        // TODO: Validar se o mesmo produto é inserido duas vezes na lista (advertisementList)

        for (int i = 0; i < purchaseItemList.size(); i++) {
            // Set advertisement
            Long id = purchaseItemList.get(i).getAdvertisement().getAdvertisementId();
            Advertisement advertisement = advertisementRepo.findById(id).orElseThrow(() -> new NotFoundException("Advertisement not found"));
            purchaseItemList.get(i).setAdvertisement(advertisement);

            // Set purchaseItem
            int totalQuantity = advertisement.getBatches().stream()
                    .filter(b -> ChronoUnit.WEEKS.between(dateTime, b.getDueDate()) >= 3)
                    .mapToInt(Batch::getProductQuantity).sum();

            if (totalQuantity < purchaseItemList.get(i).getQuantity()) {
                throw new QuantityNotAvailableException("Quantity not available for product: " + advertisement.getName() + ". Available quantity: " + totalQuantity);
            }

            purchaseItemList.get(i).setPrice(advertisement.getPrice().multiply(BigDecimal.valueOf(purchaseItemList.get(i).getQuantity())));
            totalPrice = totalPrice.add(advertisement.getPrice().multiply(BigDecimal.valueOf(purchaseItemList.get(i).getQuantity())));
        }
        purchaseOrderRepo.save(purchaseOrder);
        purchaseItemList.forEach(i -> i.setPurchaseOrder(purchaseOrder));
        purchaseItemRepo.saveAll(purchaseItemList);
        return totalPrice;
    }

    @Override
    public List<Advertisement> findAllAdvertisementsByPurchase(Long purchaseCode) {
        PurchaseOrder purchaseOrder = (purchaseOrderRepo.findById(purchaseCode).orElseThrow(() -> new NotFoundException("Purchase order not found")));
        purchaseOrder.getPurchaseItems().forEach(i -> i.getAdvertisement().setPrice(i.getPrice().divide(BigDecimal.valueOf(i.getQuantity()))));
        return purchaseOrder.getPurchaseItems().stream().map(PurchaseItem::getAdvertisement).collect(Collectors.toList());
    }

    @Override
    public PurchaseOrder updatePurchaseStatus(Long purchaseCode) {
        PurchaseOrder purchaseOrder = (purchaseOrderRepo.findById(purchaseCode).orElseThrow(() -> new NotFoundException("Purchase order not found")));
        if (purchaseOrder.getOrderStatus().equals(OrderStatus.FINALIZADO))
            throw new PurchaseFailureException("The purchase order is already finished");
        LocalDate dateTime = LocalDate.of(purchaseOrder.getDateTime().getYear(), purchaseOrder.getDateTime().getMonth(),
                purchaseOrder.getDateTime().getDayOfMonth());

        List<PurchaseItem> purchaseItemList = purchaseOrder.getPurchaseItems();
        for (PurchaseItem purchaseItem : purchaseItemList) {
            // definir quantidade
            int boughtQuantity = purchaseItem.getQuantity();

            // pegar as batches e ordena-las
            List<Batch> batchList = purchaseItem.getAdvertisement().getBatches().stream()
                    .filter(b -> ChronoUnit.WEEKS.between(dateTime, b.getDueDate()) >= 3)
                    .sorted(Comparator.comparing(Batch::getDueDate))
                    .collect(Collectors.toList());

            // (lancar excecao quantidade indisponivel)
            int totalQuantity = batchList.stream().mapToInt(Batch::getProductQuantity).sum();
            if (totalQuantity < boughtQuantity) {
                purchaseOrder.setOrderStatus(OrderStatus.INDISPONIVEL);
                purchaseOrderRepo.save(purchaseOrder);
                throw new PurchaseFailureException("Your cart product quantity: " + purchaseItem.getAdvertisement().getName() + " has expired. Available quantity: " + totalQuantity);
            }

            // definir verificar as quantidades por batch  diminuir volume e quantidade
            for (Batch batch : batchList) {
                Section section = batch.getInboundOrder().getSection();
                Float sectionVolume = section.getAccumulatedVolume();
                int quantityBatch = batch.getProductQuantity();
                int quantityDiff = boughtQuantity - quantityBatch;
                if (quantityDiff >= 0) {
                    section.setAccumulatedVolume(sectionVolume - batch.getVolume());
                    sectionRepo.save(section);

                    boughtQuantity -= quantityBatch;
                    batch.setProductQuantity(0);
                    batch.setVolume(0F);
                    batchRepo.save(batch);
                } else {
                    Float advertisementVolume = batch.getUnitVolume() * boughtQuantity;
                    section.setAccumulatedVolume(sectionVolume - advertisementVolume);
                    sectionRepo.save(section);

                    batch.setProductQuantity(Math.abs(quantityDiff));
                    batch.setVolume(batch.getVolume() - advertisementVolume);
                    batchRepo.save(batch);
                    break;
                }
            }
        }

        purchaseOrder.setOrderStatus(OrderStatus.FINALIZADO);
        // atualizar o response body do controller
        return purchaseOrderRepo.save(purchaseOrder);
    }
}
