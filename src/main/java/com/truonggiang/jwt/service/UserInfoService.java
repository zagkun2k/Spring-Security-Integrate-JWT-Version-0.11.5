package com.truonggiang.jwt.service;

import com.truonggiang.jwt.model.dto.UserInfoDTO;
import com.truonggiang.jwt.model.entity.UserInfoEntity;

import java.util.List;

public interface UserInfoService {

    List<UserInfoDTO> getAllUser();
    UserInfoDTO getUserById(Long Id);

    void addUser(UserInfoEntity entity);
}
