package com.azeroth.project.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDomain {

    private Long id;
    private String p_name;
    private String main_cate;
    private String sub_cate;
    private String p_img;
    private String detail;
    private Long price;
    private Long stock;
    private Long p_rank;
}
