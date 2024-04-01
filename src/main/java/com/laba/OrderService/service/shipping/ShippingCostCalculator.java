package com.laba.OrderService.service.shipping;

public class ShippingCostCalculator {
    private ShippingStrategy strategy;

    public ShippingCostCalculator(ShippingStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculateCost(double weight) {
        return strategy.calculate(weight);
    }
}
