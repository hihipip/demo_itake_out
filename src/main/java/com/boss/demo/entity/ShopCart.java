package com.boss.demo.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_shopping_cart")
@Data
public class ShopCart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;

    private String name;
    private String image;
    private long userId;
    private long dishId;
    private long setmealId;
    private String dishFlavor;
    @Min(value = 1)
    private int number;
    private int amount;



}
