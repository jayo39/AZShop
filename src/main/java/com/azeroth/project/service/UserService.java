package com.azeroth.project.service;


import com.azeroth.project.domain.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    // get User using username
    UserDomain findByUsername(String username);

    // check if user exists (registered)
    boolean isExist(String username);

    // check if email exists (registered)
    boolean isExistEmail(String email);

    // register new user
    int register(UserDomain user);

    int resetPassword(UserDomain user);

    // get the user role using user id
    AuthorityDomain selectRoleById(Long id);

    int saveAddress(AddressDomain addressDomain);

    QryAddressList getAddressByUser(Long user_id);

    int update(UserDomain userDomain);

    int deleteAddress(Long id);

    int delete(UserDomain user);

    UserDomain findByEmail(String email);

    List<UserDomain> getAllUsers();

    int updateLog(UserDomain userDomain);

    int addSale(SalesDomain salesDomain);

    List<SalesDomain> getSalesByUsername(String username);

}
