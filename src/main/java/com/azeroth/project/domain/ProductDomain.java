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

    private Long id;            //PK
    private String p_name;      // 상품명
    private String main_cate;   // 메인 카테고리
    private String sub_cate;    // 서브 카테고리
    private String p_img;       // 상품이미지
    private String detail;      // 상품상세설명
    private Long price;         // 가격
    private Long stock;         // 재고
    private Long p_rank;        // 판매량
}
