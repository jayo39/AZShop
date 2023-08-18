package com.azeroth.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartProduct {
    private Long id;
    private Long amount;
    private Long product_id;
    private Long price;
    private Long stock;
    private String p_img;
    private String p_name;
}
