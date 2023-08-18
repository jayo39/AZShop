package com.azeroth.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesDomain {
    private Long id;
    private String u_name;
    private String phone;
    private Long p_id;
    private Long amount;
    private String address;
    private String address_detail;
    private String postcode;
    private String deliveryreq;
    private String tracknum;
    private Long total;
    private LocalDateTime regdate;
}
