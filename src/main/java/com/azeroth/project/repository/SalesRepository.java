package com.azeroth.project.repository;

import com.azeroth.project.domain.SalesDomain;
import com.azeroth.project.domain.UserTotal;

import java.util.List;

public interface SalesRepository {
    int save(SalesDomain salesDomain);
    List<SalesDomain> getSalesByUsername(String username);

    List<UserTotal> getUserTotal();
}
