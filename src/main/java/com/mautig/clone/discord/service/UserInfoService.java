package com.mautig.clone.discord.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.mautig.clone.discord.entity.UserInfo;

public interface UserInfoService extends UserDetailsService {
    UserInfo addUser(UserInfo userInfo);

    boolean existsByEmail(String email);
}
