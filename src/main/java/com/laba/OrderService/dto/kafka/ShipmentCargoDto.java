package com.laba.OrderService.dto.kafka;

import java.io.Serializable;

public record ShipmentCargoDto(String orderNumber, long productId) implements Serializable {

}
