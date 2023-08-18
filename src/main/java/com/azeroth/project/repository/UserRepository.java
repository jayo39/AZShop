package com.azeroth.project.repository;

import com.azeroth.project.domain.UserDomain;

import java.util.List;

public interface UserRepository {
    // get User using user id
    UserDomain findById(Long id);

    // get User using username
    UserDomain findByUsername(String username);

    // get User using email
    UserDomain findByEmail(String email);

    List<UserDomain> getAllUsers();

    // register new user
    int register(UserDomain user);

    // edit user info
    int update(UserDomain user);

    int resetPassword(UserDomain user);

    int updateLog(UserDomain user);

    int delete(UserDomain user);
}
