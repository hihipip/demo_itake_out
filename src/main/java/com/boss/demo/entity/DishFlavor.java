package com.boss.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_dish_flavor")
@Data
@NoArgsConstructor
public class DishFlavor extends BaseEntity {

    public DishFlavor(String name,String description){
        this.name = name;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "請填寫名稱")
    private String name;

    private long dish_id;

    private String description;



}
