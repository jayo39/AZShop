package com.azeroth.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartDomain {
    private Long id;
    private Long user_id;
    private Long product_id;
    private Long amount;
    private LocalDateTime regdate;
}
