package com.laba.OrderService.entity;

import com.laba.OrderService.enums.OrderState;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Table(name = "orders")
@Entity
@Getter
@Setter
public class Order extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;


	private String orderNumber;
	private Date orderDate;
	private String orderDescription;
	private Double totalAmount;

//	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//	private Set<OrderProduct> orderProducts = new HashSet<>();
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	private Users users;

	private Long userId;

	@Enumerated(EnumType.STRING)
	private OrderState orderState;

}
