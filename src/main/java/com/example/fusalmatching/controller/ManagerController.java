package com.example.fusalmatching.controller;

import com.example.fusalmatching.config.jwt.JwtToken;
import com.example.fusalmatching.dto.request.LoginRequestDto;
import com.example.fusalmatching.dto.request.ManagerSignRequestDto;
import com.example.fusalmatching.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/managers")
@RestController
public class ManagerController {

    private final ManagerService managerService;


    @PostMapping("/new")
    public void createManager(@RequestBody ManagerSignRequestDto managerSignRequestDto) throws Exception {
        managerService.createManager(managerSignRequestDto);
    }

    @PostMapping("/login")
    public JwtToken login(@RequestBody LoginRequestDto loginRequestDto) {
        String id = loginRequestDto.getId();
        String password = loginRequestDto.getPassword();
        return managerService.login(id, password);
    }

    @PostMapping("/test")
    public void test() {
        System.out.println("==========================>>>>>>>>>>>>> 인증 성공");
    }
}
