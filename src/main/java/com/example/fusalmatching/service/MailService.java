package com.example.fusalmatching.service;

import com.example.fusalmatching.config.MailConfig;
import com.example.fusalmatching.domain.Team;
import com.example.fusalmatching.dto.request.MailDto;
import com.example.fusalmatching.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.InvalidPropertiesFormatException;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class MailService {

    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final TeamRepository teamRepository;

    public String sendMail(MailDto mailDto) throws MessagingException, InvalidPropertiesFormatException {
        MailConfig MailConfig = new MailConfig(javaMailSender);

        Optional<Team> optional = teamRepository.findById(mailDto.getId());

        Team team = optional.get();

        if(Objects.equals(team.getEmail(), mailDto.getEmail())) {

            MailConfig.setTo(mailDto.getEmail());
            MailConfig.setSubject("광주풋살 이메일 인증번호");

            String htmlContent = "<h3> 다음의 인증번호를 입력해 주세요 </h3><br>";

            StringBuilder number = getRandomNum();

            MailConfig.setText(htmlContent + " ==> " + number, true);
            MailConfig.send();

            return passwordEncoder.encode(number);
        } else {
            return "이메일 정보를 다시 확인해주세요";
        }
    }

    public StringBuilder getRandomNum() {
        Random random = new Random();

        StringBuilder randomNum = new StringBuilder();

        for(int i = 0 ; i < 6 ; i++){
            randomNum.append(String.valueOf(random.nextInt(10)));

        }

        return randomNum;

    }


}
