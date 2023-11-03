package com.truonggiang.jwt.controller.auth;

import com.truonggiang.jwt.common.utils.UserInfoMapper;
import com.truonggiang.jwt.config.security.jwt.model.AuthRequest;
import com.truonggiang.jwt.config.security.jwt.service.JwtService;
import com.truonggiang.jwt.model.dto.UserInfoDTO;
import com.truonggiang.jwt.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope("prototype")
@RequestMapping("/api/v1/authentication")
public class AuthController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<String> addUser(@RequestBody UserInfoDTO dto) {

        service.addUser(UserInfoMapper.UserInfoDTOtoEntity(dto));
        return ResponseEntity.ok("add worked!");

    }


    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<String> authAccount(@RequestBody AuthRequest requestAuthDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestAuthDto.getUsername(), requestAuthDto.getPassword()));



        if (authentication.isAuthenticated()) {
            String jwt = jwtService.generateToken(requestAuthDto.getUsername());
            return new ResponseEntity<>(jwtService.generateToken(requestAuthDto.getUsername()), HttpStatus.OK);
        }
       return new ResponseEntity<>(new AuthenticationCredentialsNotFoundException("Invalid username info").toString(), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ResponseEntity<String> authAccount() {


        return new ResponseEntity<>("Welcome auth", HttpStatus.OK);
    }
}
