package com.laba.OrderService.service.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laba.OrderService.dto.kafka.UpdateProductStopDto;
import com.laba.OrderService.entity.Order;
import com.laba.OrderService.enums.OrderState;
import com.laba.OrderService.service.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductConsumer {

    private final ObjectMapper objectMapper;
    private final OrderService orderService;


    public ProductConsumer(ObjectMapper objectMapper, OrderService orderService) {
        this.objectMapper = objectMapper;
        this.orderService = orderService;
    }

    @KafkaListener(topics = "${topic.stock.update}", groupId = "productUpdate")
    @RetryableTopic(
            attempts = "2",
            dltStrategy = DltStrategy.FAIL_ON_ERROR)
    public void listen(String message) {
        try {
            UpdateProductStopDto updateProductStopDto = objectMapper.readValue(message, UpdateProductStopDto.class);
            String orderNumber = updateProductStopDto.orderNumber();
            Order order = orderService.findOrderByOrderNumber(orderNumber);
            order.setOrderState(OrderState.CREATED);
            orderService.saveOrder(order);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }catch (Exception e){
           log.error("order update sırasında bir hata oluştu.");

        }
    }

    @KafkaListener(topics = "${topic.stock.update_fail}", groupId = "productUpdate")
    @RetryableTopic(
            attempts = "2",
            dltStrategy = DltStrategy.FAIL_ON_ERROR)
    public void listenfailUpdateStoc(String orderNumber) {
        try {

            Order order = orderService.findOrderByOrderNumber(orderNumber);
            order.setOrderState(OrderState.CANCELED);
            orderService.saveOrder(order);

        } catch (Exception e){
            log.error("order update sırasında bir hata oluştu.");

        }
    }

    @DltHandler
    public void handleDltPayment(@Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        System.out.println();

    }
}
