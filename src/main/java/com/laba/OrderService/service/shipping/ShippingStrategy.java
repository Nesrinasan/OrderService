package com.laba.OrderService.service.shipping;

public interface ShippingStrategy {
    double calculate(double weight);
}
