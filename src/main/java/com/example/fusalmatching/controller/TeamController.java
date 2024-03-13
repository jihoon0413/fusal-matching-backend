package com.example.fusalmatching.controller;

import com.example.fusalmatching.config.jwt.JwtToken;
import com.example.fusalmatching.dto.request.*;
import com.example.fusalmatching.dto.response.TeamResponseDto;
import com.example.fusalmatching.dto.response.TeamReviewResponseDto;
import com.example.fusalmatching.service.MailService;
import com.example.fusalmatching.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/teams")
@RestController
public class TeamController {

    private final TeamService teamService;
    private final MailService mailService;

    @GetMapping
    public TeamResponseDto getMyPage(@RequestParam String id) {

        return teamService.getMyPage(id);
    }

    @GetMapping("/reviews")
    public List<TeamReviewResponseDto> getReviews(@RequestParam String id) {
        return teamService.getReviewList(id);
    }

    @PostMapping("/new")
    public void createTeam(@RequestBody TeamSignRequestDto teamSignDto) throws Exception {
        teamService.createTeam(teamSignDto);
    }

    @PostMapping("/modifyProfile")
    public void modifyTeam(@RequestBody TeamModifyProfileRequestDto teamSignDto) {
        teamService.modifyTeam(teamSignDto);
    }

    @PostMapping("/check-id")
    public boolean checkId(@RequestBody CheckRequestDto id) {
        return this.teamService.checkId(id);
    }

    @PostMapping("/check-name")
    public boolean checkName(@RequestBody CheckRequestDto id) {
        return this.teamService.checkName(id);
    }

    @PostMapping("/login")
    public JwtToken login(@RequestBody LoginRequestDto loginRequestDto) {
        String id = loginRequestDto.getId();
        String password = loginRequestDto.getPassword();

        return teamService.login(id,password);
    }

    @PostMapping("/send-email")
    public String sendEmail(@RequestBody MailDto mailDto) throws MessagingException, InvalidPropertiesFormatException {
        return mailService.sendMail(mailDto);
    }

    @PostMapping("/check-num")
    public boolean checkRandomNum(@RequestBody CheckRandomNumDto randomNum) {
        return teamService.checkNum(randomNum);
    }

    @PostMapping("/test")
    public void test() {
        System.out.println("==========================>>>>>>>>>>>>> 인증 성공");
    }




}
