package com.boss.demo.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_dish")
@Data
public class Dish extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;
    @NotBlank(message = "請填寫名稱")
    private String name;

    @Min(value = 1)
    private int price;

    private long category_id;

    @NotBlank(message = "請上傳圖片")
    private String image;

    @NotBlank(message = "請填寫描述")
    private String description;

    private int status;

    private int sort;




}
