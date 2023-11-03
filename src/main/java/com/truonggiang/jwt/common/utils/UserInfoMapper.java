package com.truonggiang.jwt.common.utils;

import com.truonggiang.jwt.model.dto.UserInfoDTO;
import com.truonggiang.jwt.model.entity.UserInfoEntity;
import org.modelmapper.ModelMapper;

public class UserInfoMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static UserInfoEntity UserInfoDTOtoEntity(UserInfoDTO dto) {

        return mapper.map(dto, UserInfoEntity.class);

    }

    public static UserInfoDTO UserInfoEntityToDTO(UserInfoEntity entity) {

        return mapper.map(entity, UserInfoDTO.class);

    }
}
