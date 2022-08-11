package com.boss.demo.entity;

import com.boss.demo.validate.IsMobile;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name="tb_member")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "請填寫姓名")
    private String name;

    @IsMobile
    private String phone;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private int sexual;

    private int role;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8") //返回时间类型
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    @CreatedDate
    private Date createTime;

    @LastModifiedDate
    private Date updateTime;

    @CreatedBy
    private Long createUser;

    @LastModifiedBy
    private Long updateUser;



}
