package com.azeroth.project.service;

import com.azeroth.project.domain.*;
import com.azeroth.project.repository.AddressRepository;
import com.azeroth.project.repository.AuthorityRepository;
import com.azeroth.project.repository.SalesRepository;
import com.azeroth.project.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Value("${app.upload.path}")
    private String uploadDir;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;
    private AuthorityRepository authRepository;
    private AddressRepository addressRepository;
    private SalesRepository salesRepository;

    @Autowired
    public UserServiceImpl(SqlSession sqlSession) {
        userRepository = sqlSession.getMapper(UserRepository.class);
        authRepository = sqlSession.getMapper(AuthorityRepository.class);
        addressRepository = sqlSession.getMapper(AddressRepository.class);
        salesRepository = sqlSession.getMapper(SalesRepository.class);
    }

    @Override
    public UserDomain findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean isExist(String username) {
        UserDomain user = userRepository.findByUsername(username);
        return user != null;
    }

    @Override
    public boolean isExistEmail(String email) {
        UserDomain user = userRepository.findByEmail(email);
        return user != null;
    }

    @Override
    public List<UserDomain> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public UserDomain findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public int register(UserDomain user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setU_status("USE");
        return userRepository.register(user);
    }

    @Override
    public int resetPassword(UserDomain user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.resetPassword(user);
    }

    @Override
    public AuthorityDomain selectRoleById(Long id) {
        UserDomain user = userRepository.findById(id);
        return authRepository.findById(user.getAuthority_id());
    }

    @Override
    public int saveAddress(AddressDomain addressDomain) {
        return addressRepository.save(addressDomain);
    }

    @Override
    public int addSale(SalesDomain salesDomain) {
        return salesRepository.save(salesDomain);
    }

    @Override
    public List<SalesDomain> getSalesByUsername(String username) {
        return salesRepository.getSalesByUsername(username);
    }

    @Override
    public int delete(UserDomain user) {
        return userRepository.delete(user);
    }

    @Override
    public int updateLog(UserDomain userDomain) {
        return userRepository.updateLog(userDomain);
    }

    @Override
    public int update(UserDomain userDomain) {
        return userRepository.update(userDomain);
    }

    @Override
    public int deleteAddress(Long id) {
        return addressRepository.delete(id);
    }

    @Override
    public QryAddressList getAddressByUser(Long user_id) {
        QryAddressList list = new QryAddressList();
        List<AddressDomain> addresses = addressRepository.getAddressByUser(user_id);
        list.setList(addresses);
        list.setStatus("OK");
        return list;
    }
}
