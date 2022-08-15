package com.boss.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@MappedSuperclass  //繼承類要加這個
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @JsonIgnore
    @CreatedDate
    @Column(name = "create_time")
    private Date createTime;

    @JsonIgnore
    @LastModifiedDate
    private Date updateTime;

    @JsonIgnore
    @CreatedBy
    private Long createUser;

    @JsonIgnore
    @LastModifiedBy
    private Long updateUser;

}
