package com.boss.demo.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_setmeal_dish")
@Data
public class SetmealDish extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;

    private Long setmealId;

    private Long dishId;

    @NotBlank(message = "請填寫名稱")
    private String name;

    @Min(value = 1)
    private int price;


    @Min(value = 1)
    private int copies;




}
