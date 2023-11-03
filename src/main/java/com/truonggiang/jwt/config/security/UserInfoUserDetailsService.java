package com.truonggiang.jwt.config.security;

import com.truonggiang.jwt.model.entity.UserInfoEntity;
import com.truonggiang.jwt.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.truonggiang.jwt.common.constant.UserInfoConst.USER_NOT_FOUND;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserInfoEntity> user = userInfoRepository.findByUsername(username);
        return user.map(UserInfoUserDetails::new)
                .orElseThrow(
                        () ->
                                new UsernameNotFoundException
                                        (String.format(USER_NOT_FOUND, username)));

    }
}
