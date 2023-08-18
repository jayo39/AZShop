package com.azeroth.project.service;


import com.azeroth.project.domain.UserTotal;
import com.azeroth.project.repository.SalesRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private SalesRepository salesRepository;

    @Autowired
    public AdminServiceImpl(SqlSession sqlSession) {
        salesRepository = sqlSession.getMapper(SalesRepository.class);
    }


    @Override
    public List<UserTotal> getUserTotal() {
        return salesRepository.getUserTotal();
    }
}
