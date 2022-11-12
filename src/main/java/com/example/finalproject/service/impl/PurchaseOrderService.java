package com.example.finalproject.service.impl;

import com.example.finalproject.exception.NotFoundException;
import com.example.finalproject.exception.QuantityNotAvailableException;
import com.example.finalproject.model.Advertisement;
import com.example.finalproject.model.Batch;
import com.example.finalproject.model.PurchaseItem;
import com.example.finalproject.model.PurchaseOrder;
import com.example.finalproject.repository.AdvertisementRepo;
import com.example.finalproject.repository.BuyerRepo;
import com.example.finalproject.repository.PurchaseItemRepo;
import com.example.finalproject.repository.PurchaseOrderRepo;
import com.example.finalproject.service.IPurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
            double totalQuantity = advertisement.getBatches().stream()
                    .filter(b -> ChronoUnit.WEEKS.between(dateTime, b.getDueDate()) >= 3)
                    .mapToDouble(Batch::getProductQuantity).sum();

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
        purchaseOrder.getPurchaseItems().forEach(i-> i.getAdvertisement().setPrice(i.getPrice().divide(BigDecimal.valueOf(i.getQuantity()))));
        return purchaseOrder.getPurchaseItems().stream().map(PurchaseItem::getAdvertisement).collect(Collectors.toList());
    }

    // TODO: alterar método para puxar advertisements dos items
    @Override
    public PurchaseOrder updatePurchaseStatus(Long purchaseCode) {
        PurchaseOrder purchaseOrder = (purchaseOrderRepo.findById(purchaseCode).orElseThrow(() -> new NotFoundException("Purchase order not found")));
//        List<Advertisement> advertisements = purchaseOrder.getAdvertisements();
//        // atualizar volume
//        for (int i = 0; i < advertisements.size(); i++) {
//            List<Batch> batchList = advertisements.get(i).getBatches();
////            List<Integer> quantities = batchList.stream().map(Batch::getProductQuantity).collect(Collectors.toList());
//
//            for (int j = 0; j < batchList.size(); j++){
//                Double unitVolumeFunction = batchList.get(j).getUnitVolume();
//                System.out.println("UNIT VOLUME FUNCTION === -> " + unitVolumeFunction);
//            }
//
//
//            Float sectionVolume = advertisements.get(i).getBatches().get(i).getInboundOrder().getSection().getVolume();
//            System.out.println("SECTION VOLUME === -> " + sectionVolume);
//        }
//        // atualizar quantity
//
//
//
//        // atualizar OrderStatus
//        purchaseOrder.setOrderStatus(OrderStatus.FINALIZADO);
//
//        // atualizar o response body
        return purchaseOrder;
    }
}
