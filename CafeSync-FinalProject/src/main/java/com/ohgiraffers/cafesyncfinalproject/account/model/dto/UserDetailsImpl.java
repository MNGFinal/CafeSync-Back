package com.ohgiraffers.cafesyncfinalproject.account.model.dto;

import com.ohgiraffers.cafesyncfinalproject.account.model.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getAuthority() == null) {
            return Collections.singleton(new SimpleGrantedAuthority("ROLE_GUEST")); // 기본 권한 설정
        }

        // ✅ Role Enum을 문자열 권한으로 변환
        String roleName;
        if (user.getAuthority() == Role.ADMIN) {
            roleName = "ROLE_ADMIN";
        } else if (user.getAuthority() == Role.USER) {
            roleName = "ROLE_USER";
        } else {
            roleName = "ROLE_GUEST"; // 예외적인 경우 기본값 설정
        }

        return Collections.singleton(new SimpleGrantedAuthority(roleName));
    }

    @Override
    public String getPassword() {
        return user.getUserPass();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
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
