package com.boss.demo.entity;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "tb_consumer")
@Data
public class Consumer extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;

    @NotBlank(message = "請填寫姓名")
    private String name;

    @NotBlank(message = "請填寫電話")
    private String phone;

    private String password;

    @NotBlank(message = "請填寫頭像")
    private String avatar;

    private int status;

    @Transient
    private String smsCode;


}
