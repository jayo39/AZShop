package com.azeroth.project.repository;

import com.azeroth.project.domain.AddressDomain;

import java.util.List;

public interface AddressRepository {
    int save(AddressDomain addressDomain);

    int delete(Long id);

    List<AddressDomain> getAddressByUser(Long user_id);
}
