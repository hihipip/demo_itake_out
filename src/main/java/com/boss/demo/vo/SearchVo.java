package com.boss.demo.vo;

import lombok.Data;

/**
 * 搜尋類
 */
@Data
public class SearchVo {
    private String sortBy="";
    private String sortDir="asc";
    private String searchName="";
    private String startDate="";
    private String endDate="";
    private int page=0;
}
