package com.azeroth.project.service;

import com.azeroth.project.domain.CategoryDomain;

import java.util.List;

public interface CategoryService {
    List<CategoryDomain> findAllMain();

    List<CategoryDomain> findAllSub();

    List<CategoryDomain> findAll();

}
