package com.azeroth.project.service;

import com.azeroth.project.domain.CategoryDomain;
import com.azeroth.project.repository.CategoryRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(SqlSession sqlSession) {
        categoryRepository = sqlSession.getMapper(CategoryRepository.class);
    }

    // 카테고리 전부 가져오기
    @Override
    public List<CategoryDomain> findAllMain() {
        return categoryRepository.findAllMain();
    }

    @Override
    public List<CategoryDomain> findAllSub() {
        return categoryRepository.findAllSub();
    }

    @Override
    public List<CategoryDomain> findAll() {
        return categoryRepository.findAll();
    }
}
