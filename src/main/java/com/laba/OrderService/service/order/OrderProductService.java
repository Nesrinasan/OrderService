package com.laba.OrderService.service.order;

import com.laba.OrderService.dto.ProductCountUpdateRequestDto;
import com.laba.OrderService.dto.kafka.CreateOrderDto;
import com.laba.OrderService.entity.Order;
import com.laba.OrderService.entity.OrderProduct;
import com.laba.OrderService.enums.OrderState;
import com.laba.OrderService.repository.OrderProductRepository;
import com.laba.OrderService.resttemplate.ProductClient;
import com.laba.OrderService.service.kafka.KafkaORderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final ProductClient productClient;
    private final KafkaORderService kafkaProductService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveOrderProduct(List<Long> productIdList, Order order) {
        productIdList
                .stream()
                .map(productClient::getProductInfoDto)
                .forEach(product -> {
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setOrder(order);
                    orderProduct.setProductId(product.id());
                    orderProduct.setOrderState(OrderState.SUSPEND);
                    orderProductRepository.save(orderProduct);
                    kafkaProductService.sendMessageKafka(new CreateOrderDto(order.getOrderDescription() , order.getOrderNumber(), order.getUserId(), product.id()));

                    int numberOfProduct = product.numberOfProduct();
                    ProductCountUpdateRequestDto updateRequestDto = ProductCountUpdateRequestDto.builder().id(product.id()).numberOfProduct(--numberOfProduct).build();

                 //   productClient.updateProductCount(updateRequestDto);
                    System.out.println(order);

                });
    }


    public List<OrderProduct> findAllByOrders(Order order){
        return orderProductRepository.findAllByOrder(order);
    }

}
