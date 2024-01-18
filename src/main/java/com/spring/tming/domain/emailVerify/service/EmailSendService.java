package com.spring.tming.domain.emailVerify.service;

import com.spring.tming.domain.emailVerify.dto.request.EmailCheckReq;
import com.spring.tming.domain.emailVerify.dto.request.EmailReq;
import com.spring.tming.domain.emailVerify.dto.response.EmailAuthRes;
import com.spring.tming.domain.emailVerify.dto.response.EmailRes;
import com.spring.tming.global.exception.GlobalException;
import com.spring.tming.global.meta.ResultCode;
import com.spring.tming.global.redis.RedisUtil;
import com.spring.tming.global.validator.EmailCheckValidator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendService {
    private final JavaMailSender mailSender;
    private final RedisUtil redisUtil;

    @Value("${spring.mail.username}")
    private String email;

    private static final String TITLE = "Tming 회원가입인증 이메일입니다.";

    // 이메일을 어디서 보내는지, 어디로 보내는지, 인증 번호를 HTML 형식으로 어떻게 보내는지 작성.
    public EmailRes trialEmail(EmailReq emailReq) {
        String email = emailReq.getEmail();
        EmailCheckValidator.validateEmail(email);
        String authNumber = makeRandomCapital();
        sendEmail(email, authNumber);
        // EmailRes 객체 생성 및 반환
        return new EmailRes();
    }

    public EmailAuthRes verifyAuthNumber(EmailCheckReq emailCheckReq) {
        String email = emailCheckReq.getEmail();
        String authNumber = emailCheckReq.getAuthNumber();

        // 레디스에서 저장된 인증번호 가져오기
        String storedAuthNumber = (String) redisUtil.get(email);

        // 입력받은 인증번호와 레디스에 저장된 인증번호 비교
        return EmailAuthRes.builder()
                .success(storedAuthNumber != null && storedAuthNumber.equals(authNumber))
                .build();
    }

    private void sendEmail(String to, String authNumber) {
        mailPass(to, TITLE, authNumber);
        // 레디스에 인증번호 저장
        try {
            redisUtil.set(email, authNumber, 30);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(ResultCode.REDIS_CONNECTION_FAIL);
        }
    }

    // 인증번호를 담은 이메일을 전송함.
    private void mailPass(String toMail, String title, String code) {
        try {
            MimeMessage message = createMessage(toMail, title, code);
            mailSender.send(message);
        } catch (MessagingException e) {
            // 이메일 서버에 연결할 수 없거나, 잘못된 이메일 주소를 사용하거나, 인증 오류가 발생하는 등 오류가 있는 경우 MessagingException이 발생.
            e.printStackTrace(); // e.printStackTrace()는 예외를 기본 오류 스트림에 출력하는 메서드.
            throw new GlobalException(ResultCode.EMAIL_SEND_ERROR);
        }
    }

    private MimeMessage createMessage(String to, String subject, String code)
            throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(email);
        message.addRecipients(RecipientType.TO, to);
        message.setSubject(subject, StandardCharsets.UTF_8.name());
        message.setContent(code, ContentType.TEXT_HTML.getMimeType());

        return message;
    }

    // 임의의 6자리 숫자 반환 -> 6자리 랜덤 대문자를 반환하는 걸로 수정.
    public String makeRandomCapital() {
        Random r = new Random();
        StringBuilder randomCapital = new StringBuilder(); // 문자열 연산에 적합한 StringBuilder를 사용
        for (int i = 0; i < 6; i++) {
            char ch = (char) ('A' + r.nextInt(26)); // 'A'에서 'Z' 사이의 문자를 랜덤으로 선택.
            randomCapital.append(ch); // 랜덤 생성 대문자를 직접 문자열 연결 대신 효율성 높은 StringBuilder에 추가.
        }
        return randomCapital.toString();
    }
}
