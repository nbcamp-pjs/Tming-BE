package com.spring.tming.domain.emailVerify.service;

import com.spring.tming.global.exception.GlobalException;
import com.spring.tming.global.meta.ResultCode;
import java.util.Random;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSendService {
    @Autowired private JavaMailSender mailSender;
    private int authNumber;

    // 임의의 6자리 양수를 반환.
    public void makeRandomNumber() {
        Random r = new Random();
        StringBuilder randomNumber = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            randomNumber.append(r.nextInt(10));
        }

        authNumber = Integer.parseInt(randomNumber.toString());
    }

    // 이메일을 어디서 보내는지, 어디로 보내는지, 인증 번호를 HTML 형식으로 어떻게 보내는지 작성.
    public String joinEmail(String email) {
        makeRandomNumber();
        String setFrom = "jangd6995@gmail.com";
        String toMail = email; // 인증번호를 받을 이메일 주소를 입력 받음.
        String title = "Tming 회원가입인증 이메일입니다.";
        String content =
                "Tming 서비스를 이용해주셔서 감사합니다."
                        + "<br><br>"
                        + "인증번호는 "
                        + authNumber
                        + "입니다."
                        + "<br>"
                        + "인증번호를 정확히 입력해주세요.";
        mailSend(setFrom, toMail, title, content);
        return Integer.toString(authNumber);
    }

    // 인증번호를 담은 이메일을 전송함.
    public void mailSend(String setFrom, String toMail, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new GlobalException(ResultCode.EMAIL_SEND_ERROR);
        }
    }
}
