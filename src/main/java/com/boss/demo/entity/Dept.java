package com.boss.demo.entity;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "tb_dept")
@Data
public class Dept extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;

    @NotBlank(message = "請填寫部門名稱")
    private String name;



}
