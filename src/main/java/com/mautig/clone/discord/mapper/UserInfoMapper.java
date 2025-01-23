package com.mautig.clone.discord.mapper;

import org.mapstruct.Mapper;
import com.mautig.clone.discord.entity.UserInfo;
import com.mautig.clone.discord.model.UserInfoDto;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {
    public UserInfoDto toUserInfoDto(UserInfo userInfo);
}
