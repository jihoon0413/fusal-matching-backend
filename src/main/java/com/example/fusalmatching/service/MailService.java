package com.example.fusalmatching.service;

import com.example.fusalmatching.config.MailConfig;
import com.example.fusalmatching.dto.request.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class MailService {

    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;

    public String sendMail(MailDto mailDto) throws MessagingException {
        MailConfig MailConfig = new MailConfig(javaMailSender);

        MailConfig.setTo(mailDto.getEmail());
        MailConfig.setSubject("광주풋살 이메일 인증번호");

        String htmlContent = "<h3> 다음의 인증번호를 입력해 주세요 </h3><br>";

        StringBuilder number = getRandomNum();

        MailConfig.setText(htmlContent + " ==> " + number , true);
        MailConfig.send();

        return passwordEncoder.encode(number);
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
