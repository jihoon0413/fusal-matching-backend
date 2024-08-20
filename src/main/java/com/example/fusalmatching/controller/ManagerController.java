package com.example.fusalmatching.controller;

import com.example.fusalmatching.config.jwt.JwtToken;
import com.example.fusalmatching.dto.request.LoginRequestDto;
import com.example.fusalmatching.dto.request.ManagerSignRequestDto;
import com.example.fusalmatching.dto.response.ManagerResponseDto;
import com.example.fusalmatching.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/managers")
@RestController
public class ManagerController {

    private final ManagerService managerService;


    @GetMapping
    public ManagerResponseDto getMyPage(@RequestParam String id) {
        return managerService.getMyPage(id);
    }

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
