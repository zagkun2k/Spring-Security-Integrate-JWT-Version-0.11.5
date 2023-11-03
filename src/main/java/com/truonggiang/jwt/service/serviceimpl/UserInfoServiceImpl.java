package com.truonggiang.jwt.service.serviceimpl;

import com.truonggiang.jwt.common.utils.UserInfoMapper;
import com.truonggiang.jwt.model.dto.UserInfoDTO;
import com.truonggiang.jwt.model.entity.UserInfoEntity;
import com.truonggiang.jwt.repository.UserInfoRepository;
import com.truonggiang.jwt.service.UserInfoService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public List<UserInfoDTO> getAllUser() {

        List<UserInfoDTO> userInfoDTOS = new ArrayList<>();
        for (UserInfoEntity entity : userInfoRepository.findAll()) {

            userInfoDTOS.add(UserInfoMapper.UserInfoEntityToDTO(entity));
        }
        return userInfoDTOS;

    }

    @Override
    public UserInfoDTO getUserById(Long Id) {

        return UserInfoMapper
                .UserInfoEntityToDTO(
                        userInfoRepository.findById(Id).get()
                );

    }

    public void addUser(UserInfoEntity entity) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        userInfoRepository.save(entity);

    }

}
