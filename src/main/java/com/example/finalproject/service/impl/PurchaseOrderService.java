package com.example.finalproject.service.impl;

import com.example.finalproject.model.Enum.OrderStatus;
import com.example.finalproject.exception.NotFoundException;
import com.example.finalproject.exception.QuantityNotAvailableException;
import com.example.finalproject.model.Advertisement;
import com.example.finalproject.model.Batch;
import com.example.finalproject.model.PurchaseOrder;
import com.example.finalproject.repository.AdvertisementRepo;
import com.example.finalproject.repository.PurchaseOrderRepo;
import com.example.finalproject.service.IPurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseOrderService implements IPurchaseOrderService {

    @Autowired
    private PurchaseOrderRepo purchaseOrderRepo;

    @Autowired
    private AdvertisementRepo advertisementRepo;

    @Override
    public BigDecimal createPurchaseOrder(PurchaseOrder purchaseOrder, List<Integer> quantity) {
        List<Advertisement> advertisementList = getAdvertisementList(purchaseOrder);
        purchaseOrder.setAdvertisements(advertisementList);

        // TODO: Validar se o mesmo produto é inserido duas vezes na lista (advertisementList)
        BigDecimal totalPrice = new BigDecimal(0);
        LocalDate dateTime = LocalDate.of(purchaseOrder.getDateTime().getYear(), purchaseOrder.getDateTime().getMonth(),
                purchaseOrder.getDateTime().getDayOfMonth());

        for (int i = 0; i < advertisementList.size(); i++) {
            double totalQuantity = advertisementList.get(i).getBatches().stream()
                    .filter(b -> ChronoUnit.WEEKS.between(dateTime, b.getDueDate()) >= 3)
                    .mapToDouble(Batch::getProductQuantity).sum();

            if (totalQuantity < quantity.get(i)) {
                throw new QuantityNotAvailableException("Quantity not available for product: " + advertisementList.get(i).getName() + ". Available quantity: " + totalQuantity);
            }
            totalPrice = totalPrice.add(advertisementList.get(i).getPrice().multiply(BigDecimal.valueOf(quantity.get(i))));
        }
        purchaseOrderRepo.save(purchaseOrder);
        return totalPrice;
    }
    
    @Override
    public List<Advertisement> findAllAdvertisementsByPurchase(Long purchaseCode) {
        PurchaseOrder purchaseOrder = (purchaseOrderRepo.findById(purchaseCode).orElseThrow(() -> new NotFoundException("Purchase order not found")));
        return purchaseOrder.getAdvertisements();
    }
    
    @Override
    public PurchaseOrder updatePurchaseStatus(Long purchaseCode) {
        PurchaseOrder purchaseOrder = (purchaseOrderRepo.findById(purchaseCode).orElseThrow(() -> new NotFoundException("Purchase order not found")));
        purchaseOrder.setOrderStatus(OrderStatus.FINALIZADO);
        return purchaseOrder;
    }


    private List<Advertisement> getAdvertisementList(PurchaseOrder purchaseOrder) {
        List<Advertisement> advertisementList = new ArrayList<>();
        for (int i = 0; i < purchaseOrder.getAdvertisements().size(); i++) {
            Long id = purchaseOrder.getAdvertisements().get(i).getAdvertisementId();
            Advertisement advertisement = advertisementRepo.findById(id).orElseThrow(() -> new NotFoundException("AdvertisementId [" + id + "] not found"));
            advertisementList.add(advertisement);
        }
        return advertisementList;
    }
}
