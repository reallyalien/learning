package com.ot.session.controller;

import com.ot.session.model.AuthenticationRequest;
import com.ot.session.model.UserDto;
import com.ot.session.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(value = "/login", produces = {"text/plain;charset=UTF-8"})
    public String login(AuthenticationRequest request) {
        UserDto userDto = authenticationService.authentication(request);
        return userDto.getFullname() + "登录成功";
    }
}
