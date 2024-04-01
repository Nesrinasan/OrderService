
package com.laba.OrderService.dto;

import lombok.Builder;

@Builder
public record ProductCountUpdateRequestDto(Long id, int numberOfProduct) {

}
