
package com.laba.OrderService.repository;

import com.laba.OrderService.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

        Order findByOrderNumber(String orderNumber);



}
