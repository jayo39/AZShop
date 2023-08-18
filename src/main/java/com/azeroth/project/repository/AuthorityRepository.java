package com.azeroth.project.repository;

import com.azeroth.project.domain.AuthorityDomain;

public interface AuthorityRepository {
    // get Role info using role id
    AuthorityDomain findById(Long role_id);

}
