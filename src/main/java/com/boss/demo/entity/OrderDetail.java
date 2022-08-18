package com.boss.demo.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "tb_order_detail")
@Data
public class OrderDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;

    private long orderId;

    private String name;
    private String image;
    private long dishId;
    private long setmealId;
    private String dishFlavor;

    @Min(value = 1)
    private int number;
    private int amount;



}
