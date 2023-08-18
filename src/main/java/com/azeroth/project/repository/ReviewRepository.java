package com.azeroth.project.repository;

import com.azeroth.project.domain.ReviewDomain;

import java.util.List;

public interface ReviewRepository {

    // 특정 상품의 리뷰들 가져오기
    List<ReviewDomain> findByProductId(Long product_id);

    int save(ReviewDomain reviewDomain);

    int deleteById(Long id);

    int deleteByProduct(Long product_id);
}
