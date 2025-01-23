package com.mautig.clone.discord.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mautig.clone.discord.entity.UserInfo;
import com.mautig.clone.discord.mapper.UserInfoMapper;
import com.mautig.clone.discord.model.AuthRequest;
import com.mautig.clone.discord.model.AuthResponse;
import com.mautig.clone.discord.model.UserInfoDetails;
import com.mautig.clone.discord.service.JwtService;
import com.mautig.clone.discord.service.UserInfoService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserInfoService userInfoService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserInfoMapper userInfoMapper;

    public AuthController(
            UserInfoService userInfoService,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            UserInfoMapper userInfoMapper) {
        this.userInfoService = userInfoService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userInfoMapper = userInfoMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserInfo userInfo) {
        if (userInfoService.existsByEmail(userInfo.getEmail())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        var userInfoDto = userInfoMapper.toUserInfoDto(userInfoService.addUser(userInfo));
        return ResponseEntity.status(HttpStatus.CREATED).body(userInfoDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            UserInfoDetails userInfo = (UserInfoDetails) authentication.getPrincipal();
            return ResponseEntity
                    .ok(AuthResponse.builder()
                            .token(jwtService.generateToken(userInfo.getUsername()))
                            .build());
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
