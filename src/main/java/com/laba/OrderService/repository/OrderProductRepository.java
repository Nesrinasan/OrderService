package com.laba.OrderService.repository;

import com.laba.OrderService.entity.Order;
import com.laba.OrderService.entity.OrderProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderProductRepository extends CrudRepository<OrderProduct, Long> {
//
//    List<OrderProduct> findAllByOrder(Order order);
//
//
    @Query("SELECT po FROM OrderProduct po WHERE po.order.id = :orderId")
    List<OrderProduct> findProductOrdersByOrderId(Long orderId);


    List<OrderProduct> findAllByOrder(Order order);


}
