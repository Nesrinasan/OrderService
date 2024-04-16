package com.laba.OrderService.service.order;

import com.laba.OrderService.dto.OrderRequestDto;
import com.laba.OrderService.dto.ProductInfoResponseDto;
import com.laba.OrderService.dto.UserInfoResponseDto;
import com.laba.OrderService.entity.Order;
import com.laba.OrderService.entity.OrderProduct;
import com.laba.OrderService.enums.OrderState;
import com.laba.OrderService.exception.BusinessException;
import com.laba.OrderService.exception.GeneralException;
import com.laba.OrderService.feign.UserFeignClient;
import com.laba.OrderService.repository.OrderRepository;
import com.laba.OrderService.resttemplate.ProductClient;
import com.laba.OrderService.resttemplate.UserClient;
import com.laba.OrderService.resttemplate.UserRestClient;
import com.laba.OrderService.service.mail.MailService;
import com.laba.OrderService.service.shipping.DHLShippingStrategy;
import com.laba.OrderService.service.shipping.FedExShippingStrategy;
import com.laba.OrderService.service.shipping.ShippingCostCalculator;
import com.laba.OrderService.service.shipping.UPSShippingStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

   // private final UserClient userClient;
    private final UserFeignClient userClient;
   // private final UserRestClient userClient;

    private final MailService mailService;

    private final OrderProductService orderProductService;

    private final ProductClient productClient;
//
//    private final ReportService reportService;


    public OrderService(OrderRepository orderRepository, UserFeignClient userClient, MailService mailService, OrderProductService orderProductService, ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.userClient = userClient;
        this.mailService = mailService;
        this.orderProductService = orderProductService;
        this.productClient = productClient;
    }

    public void test() {
        try {
            FileReader fileReader = new FileReader("");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Transactional
    public void save(OrderRequestDto orderRequestDto) {

        List<Long> productIdList = orderRequestDto.getProductIdList();
        String orderDescription = orderRequestDto.getOrderDescription();
        Long userId = orderRequestDto.getUserId();

        Order order = new Order();
        order.setOrderDescription(orderDescription);
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderState(OrderState.SUSPEND);

        UserInfoResponseDto userDto = userClient.getInfo(userId);
        order.setUserId(userDto.id());

    //    orderProductService.saveOrderProduct(productIdList, order);

        mailService.sendMailUser(order, userDto);

        getCargoOffer(order, userDto);

        System.out.println(order.getTotalAmount());
    }

    public void getCargoOffer(Order order, UserInfoResponseDto users) {

        Optional<Order> repositorySpringJpById = orderRepository.findById(order.getId());

        Order orders = repositorySpringJpById.orElseThrow(GeneralException::new);

        List<OrderProduct> orderProductList = orderProductService.findAllByOrders(orders);

        int totalWeigth = 0;
        for (OrderProduct orderProduct : orderProductList) {

            Long productId = orderProduct.getProductId();
            ProductInfoResponseDto productInfoDto = productClient.getProductInfoDto(productId);

            int weight = productInfoDto.weigth();
            totalWeigth += weight;

        }
        ShippingCostCalculator calculator = null;
        if (users.premium()) {
            calculator = new ShippingCostCalculator(new UPSShippingStrategy());
            System.out.println("UPS Shipping Cost: " + calculator.calculateCost(totalWeigth));
            order.setTotalAmount(calculator.calculateCost(totalWeigth));
            return;
        }

        if (totalWeigth > 200) {
            throw new BusinessException("ürün ağırlığı fazla. farklı bir kargo seçeneği ile ilerleyin");
        }

        calculate(order, totalWeigth);

    }

    private void calculate(Order order, int totalWeigth) {
        ShippingCostCalculator calculator;
        if(totalWeigth > 0 && totalWeigth <= 100){
            calculator = new ShippingCostCalculator(new FedExShippingStrategy());
            order.setTotalAmount(calculator.calculateCost(totalWeigth));

        }else if(totalWeigth > 100 && totalWeigth < 200){
            calculator = new ShippingCostCalculator(new DHLShippingStrategy());
            order.setTotalAmount(calculator.calculateCost(totalWeigth));

        }
    }


    public String retunOorderByOrderId(Long orderId, Long userId) {
        //order ön işlemler

        try {
            Order order = orderRepository.findById(orderId).get();
            UserInfoResponseDto userDto = userClient.getInfo(userId);
            getCargoOffer(order, userDto);
        } catch (Exception e) {
            getDefaultCargo();
            return "Kargo iadesi için ücretsiz kargo kampanyasından faydalanamazsınız. minimum tutar geçerli değil ";

        }
        return "işleminiz başarılı";

    }

    private void getDefaultCargo() {

    }

    public void deleteOrderByOrderNumberCascade(Long orderID) {
        Order order = orderRepository.findById(orderID).get();
        orderRepository.delete(order);

    }

    public Order findOrderByOrderNumber(String orderNumber){
        return orderRepository.findByOrderNumber(orderNumber);
    }

    public void saveOrder(Order order){
        orderRepository.save(order);
    }
}
