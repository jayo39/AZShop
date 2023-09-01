package com.azeroth.project.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDomain {
    private String maincode;
    private String subcode;
    private String mainname;
    private String subname;
}
