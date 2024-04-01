package com.laba.OrderService.entity;

import jakarta.persistence.*;

@Table
@Entity
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id")
//    private Product product;

    private Long productId;

    //@ManyToOne(cascade = CascadeType.PERSIST)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    private String orderProductDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getOrderProductDesc() {
        return orderProductDesc;
    }

    public void setOrderProductDesc(String orderProductDesc) {
        this.orderProductDesc = orderProductDesc;
    }
}
