package com.laba.OrderService.service;

@FunctionalInterface
public interface ReplaceFunction {

    String replace(String template, String name, String orderNumber);

}
