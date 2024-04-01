package com.laba.OrderService.service.order;

import org.springframework.stereotype.Service;

@Service
public class OrderService2 {
//
//    private final OrderRepository orderRepository;
//
//    private final UserService userService;
//
//    private final MailService mailService;
//
//    private final ReportService reportService;
//
//    private final OrderProductService productOrderService;
//
//    public OrderService2(OrderRepository orderRepository, ProductService productService, OrderProductRepository orderProductRepository, UserService userService, MailService mailService, ReportService reportService, OrderProductService productOrderService) {
//        this.orderRepository = orderRepository;
//        this.userService = userService;
//        this.mailService = mailService;
//        this.reportService = reportService;
//        this.productOrderService = productOrderService;
//    }
//
//    public void test() {
//        try {
//            FileReader fileReader = new FileReader("");
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//    @Transactional
//    public void save(OrderRequestDto orderRequestDto) {
//
//        List<Long> productIdList = orderRequestDto.getProductIdList();
//        String orderDescription = orderRequestDto.getOrderDescription();
//        Long userId = orderRequestDto.getUserId();
//
//        Order order = new Order();
//        order.setOrderDescription(orderDescription);
//        order.setOrderNumber(UUID.randomUUID().toString());
//        Optional<Users> userById = userService.findUserById(userId);
//        Users users = userById.get();
//        order.setUsers(users);
//
//        productOrderService.saveORderProduct(productIdList, order);
//
//        reportService.createOrderReport2(order.getId(), userId.toString());
//
//        mailService.sendMailUser(order, users);
//
//        getCargoOffer(order, users);
//
//        System.out.println(order.getTotalAmount());
//    }
//
//    public void getCargoOffer(Order order, Users users) {
//        Users users1 = new Users();
//        users1.equals(users);
//        System.out.println(users1);
//
//        Optional<Order> repositorySpringJpById = orderRepositorySpringJp.findById(order.getId());
//
//        Order orders = repositorySpringJpById.orElseThrow(GeneralException::new);
//
//        List<OrderProduct> orderProductList = productOrderService.findAllByOrder(orders);
//
//        int totalWeigth = 0;
//        for (OrderProduct orderProduct : orderProductList) {
//            Product product = orderProduct.getProduct();
//            int weight = product.getWeight();
//            totalWeigth += weight;
//
//        }
//        ShippingCostCalculator calculator = null;
//        if (users.isPremium()) {
//            calculator = new ShippingCostCalculator(new UPSShippingStrategy());
//            System.out.println("UPS Shipping Cost: " + calculator.calculateCost(totalWeigth));
//            order.setTotalAmount(calculator.calculateCost(totalWeigth));
//            return;
//        }
//
//        if (totalWeigth > 200) {
//            throw new BusinessException("ürün ağırlığı fazla. farklı bir kargo seçeneği ile ilerleyin");
//        }
//
//        calculate(order, totalWeigth);
//
//    }
//
//    private void calculate(Order order, int totalWeigth) {
//        ShippingCostCalculator calculator;
//        if(totalWeigth > 0 && totalWeigth <= 100){
//            calculator = new ShippingCostCalculator(new FedExShippingStrategy());
//            order.setTotalAmount(calculator.calculateCost(totalWeigth));
//
//        }else if(totalWeigth > 100 && totalWeigth < 200){
//            calculator = new ShippingCostCalculator(new DHLShippingStrategy());
//            order.setTotalAmount(calculator.calculateCost(totalWeigth));
//
//        }
//    }
//
//    public void deleteOrderByOrderNumberCascade(Long orderID) {
//        Order order = orderRepositorySpringJp.findById(orderID).get();
//        orderRepositorySpringJp.delete(order);
//
//    }
//
//    public String retunOorderByOrderId(Long orderId, Long userId) {
//        //order ön işlemler
//
//        try {
//            Order order = orderRepositorySpringJp.findById(orderId).get();
//            Optional<Users> userById = userService.findUserById(userId);
//            getCargoOffer(order, userById.get());
//        } catch (Exception e) {
//            getDefaultCargo();
//            return "Kargo iadesi için ücretsiz kargo kampanyasından faydalanamazsınız. minimum tutar geçerli değil ";
//
//        }
//        return "işleminiz başarılı";
//
//    }
//
//    private void getDefaultCargo() {
//
//    }
}
