package com.azeroth.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDomain {
    private Long id;
    private Long user_id;
    private String name;
    private String address;
    private String address_detail;
    private String postcode;
}
