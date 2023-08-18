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
    private String maincode;        // 메인카테고리 코드
    private String subcode;         // 서브카테고리 코드
    private String mainname;        // 메인카테고리 이름
    private String subname;         // 서브카테고리 이름
}
