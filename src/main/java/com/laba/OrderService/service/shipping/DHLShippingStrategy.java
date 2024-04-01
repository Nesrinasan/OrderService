package com.laba.OrderService.service.shipping;

public class DHLShippingStrategy implements ShippingStrategy {
    @Override
    public double calculate(double weight) {
        return weight * 1.8 + 10;
    }
}
