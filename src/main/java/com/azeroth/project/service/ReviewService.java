package com.azeroth.project.service;

import com.azeroth.project.domain.QryResult;
import com.azeroth.project.domain.QryReviewList;

public interface ReviewService {

    QryReviewList list(Long product_id);

    QryResult save(Long user_id, Long product_id, String content, Double rating);

    QryResult delete(Long id, Long product_id);
}
