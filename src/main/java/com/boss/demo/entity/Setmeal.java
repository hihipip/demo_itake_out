package com.boss.demo.entity;


import com.boss.demo.validate.IsMobile;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Data
@Table(name = "tb_setmeal")
public class Setmeal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;

    @Column(name = "category_id")
    private long categoryId;

    @NotBlank(message = "請填寫名稱")
    private String name;

    @Min(value = 1)
    private int price;

    private int status;

    @NotBlank(message = "請上傳圖片")
    private String image;

    @NotBlank(message = "請填寫描述")
    private String description;

}
