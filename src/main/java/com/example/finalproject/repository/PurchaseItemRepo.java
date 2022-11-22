package com.example.finalproject.repository;

import com.example.finalproject.model.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseItemRepo extends JpaRepository<PurchaseItem, Long> {

    @Query(value = "SELECT DISTINCT(pi.item_code), pi.price,pi.quantity,pi.advertisement_code,pi.purchase_code \n" +
            " FROM purchase_item pi\n" +
            " INNER JOIN purchase_order po ON pi.purchase_code = po.purchase_code \n" +
            " INNER JOIN batch b ON pi.advertisement_code = b.advertisement_code \n" +
            " INNER JOIN inbound_order io ON b.order_code  = io.order_code \n" +
            " INNER JOIN section s ON io.section_code = s.section_code \n" +
            " WHERE po.order_status = \"FINALIZADO\" \n" +
            " AND s.warehouse_code = ?1", nativeQuery = true)
    List<PurchaseItem> findAllByWarehouseCode(Long warehouseCode);

    @Query(value = "SELECT DISTINCT(pi.item_code), pi.price,pi.quantity,pi.advertisement_code,pi.purchase_code \n" +
            " FROM purchase_item pi\n" +
            " INNER JOIN purchase_order po ON pi.purchase_code = po.purchase_code \n" +
            " INNER JOIN batch b ON pi.advertisement_code = b.advertisement_code \n" +
            " INNER JOIN inbound_order io ON b.order_code  = io.order_code \n" +
            " INNER JOIN section s ON io.section_code = s.section_code \n" +
            " WHERE po.order_status = \"FINALIZADO\" \n" +
            " AND po.date_time BETWEEN ?2 AND ?3\n" +
            " AND s.warehouse_code = ?1", nativeQuery = true)
    List<PurchaseItem> findAllByWarehouseInitialDateAndFinalDate(Long warehouseCode, LocalDateTime initialDate, LocalDateTime finalDate);

}