package com.azeroth.project.service;

import com.azeroth.project.domain.QryResult;
import com.azeroth.project.domain.QryReviewList;
import com.azeroth.project.domain.ReviewDomain;
import com.azeroth.project.domain.UserDomain;
import com.azeroth.project.repository.ReviewRepository;
import com.azeroth.project.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{

    private ReviewRepository reviewRepository;

    private UserRepository userRepository;

    @Autowired
    public ReviewServiceImpl(SqlSession sqlSession) {
        reviewRepository = sqlSession.getMapper(ReviewRepository.class);
        userRepository = sqlSession.getMapper(UserRepository.class);
    }

    @Override
    public QryReviewList list(Long product_id) {
        QryReviewList list = new QryReviewList();
        List<ReviewDomain> reviews = reviewRepository.findByProductId(product_id);
        list.setCount(reviews.size());
        list.setList(reviews);
        list.setStatus("OK");
        return list;
    }

    @Override
    public QryResult save(Long user_id, Long product_id, String content, Double rating) {
        UserDomain user = userRepository.findById(user_id);
        ReviewDomain review = ReviewDomain.builder()
                .user(user)
                .user_id(user_id)
                .product_id(product_id)
                .content(content)
                .rating(rating)
                .build();
        reviewRepository.save(review);
        return QryResult.builder()
                .count(1)
                .status("OK")
                .build();
    }

    @Override
    public QryResult delete(Long id, Long product_id) {
        int result = 0;
        if (id == null) {
            result = reviewRepository.deleteByProduct(product_id);
        } else {
            result = reviewRepository.deleteById(id);
        }
        String status = "FAIL";
        if (result == 1) status = "OK";
        return QryResult.builder().count(result).status(status).build();
    }
}
