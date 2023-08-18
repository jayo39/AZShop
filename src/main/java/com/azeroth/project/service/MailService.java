package com.azeroth.project.service;

import com.azeroth.project.domain.MailDomain;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class MailService {

    private JavaMailSender javaMailSender;

    public String mailSend(MailDomain mailDomain) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDomain.getAddress());
        message.setSubject(mailDomain.getTITLE());
        String code = generateCode();
        String content = mailDomain.getMESSAGE() + "\n" + code;
        message.setText(content);
        javaMailSender.send(message);
        return code;
    }

    public String generateCode() {
        StringBuilder code = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random();
        for(int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            code.append(randomChar);
        }

        return code.toString();
    }
}
