package com.azeroth.project.repository;

import com.azeroth.project.domain.CategoryDomain;

import java.util.List;

public interface CategoryRepository {
    // 메인 카테고리 모두 선택
    List<CategoryDomain> findAllMain();

    // 서브 카테고리 모두 선택
    List<CategoryDomain> findAllSub();

    // 모두 선택
    List<CategoryDomain> findAll();
}
