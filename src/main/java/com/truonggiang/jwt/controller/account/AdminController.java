package com.truonggiang.jwt.controller.account;

import com.truonggiang.jwt.model.dto.UserInfoDTO;
import com.truonggiang.jwt.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@Scope("prototype")
public class AdminController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/alluser", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserInfoDTO> getAllListUser() {

        List<UserInfoDTO> userInfoDTOS = userInfoService.getAllUser();
        return userInfoDTOS;

    }

    @RequestMapping(value = "/helloadmin", method = RequestMethod.GET)
    public ResponseEntity<String> testingAdmin () {

        return ResponseEntity.ok("admin testing ok!!");

    }
}
