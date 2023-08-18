package com.azeroth.project.config;

import com.azeroth.project.domain.AuthorityDomain;
import com.azeroth.project.domain.UserDomain;
import com.azeroth.project.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private UserService userService;

    private UserDomain user;

    public void setUser(UserDomain user) {
        this.user = user;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public PrincipalDetails(UserDomain user) {
        this.user = user;
    }

    public UserDomain getUser() {
        return user;
    }

    // get Roles from current User
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collect = new ArrayList<>();

        AuthorityDomain authority = userService.selectRoleById(user.getId());

        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return authority.getName();
            }

            // thymeleaf
            @Override
            public String toString() {
                return authority.getName();
            }
        });

        return collect;
    }

    public void updateLogDate(UserDomain userDomain) {
        userService.updateLog(userDomain);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
