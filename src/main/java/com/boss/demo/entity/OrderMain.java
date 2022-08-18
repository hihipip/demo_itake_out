package com.boss.demo.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_order_main")
@Data
public class OrderMain extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String orderSn;
    private int status;
    private long consumerId;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date orderTime;
    private int amount;

    private String remark;
    @NotBlank(message = "請填寫電話")
    private String phone;
    @NotBlank(message = "請填寫地址")
    private String address;
    @NotBlank(message = "請填寫姓名")
    private String name;

    @Transient
    List<OrderDetail> orderDetails;

}
