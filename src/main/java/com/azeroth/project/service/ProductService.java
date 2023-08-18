package com.azeroth.project.service;

import com.azeroth.project.domain.ProductDomain;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    // 상품 등록 (첨부 이미지 있는 경우)
    int addProduct(ProductDomain productDomain, MultipartFile files);

    // 메인 페이지에 상품 보이기
    List<ProductDomain> listByPagination(Integer page, Model model);

    // 카테고리별 상품 가져오기
    List<ProductDomain> listByCategory(String maincode, String subcode);

    // 검색으로 상품 가져오기
    List<ProductDomain> listBySearch(String searchedValue);

    // 상품 상세 정보
    ProductDomain findById(Long id);

    // 상품 삭제
    int delete(Long id, String originalImage);

    // 상품 수정
    int update(Integer isDelete, String originalImage, ProductDomain productDomain, MultipartFile file);

    int modifyAmount(Long product_id, Long amount);

}
