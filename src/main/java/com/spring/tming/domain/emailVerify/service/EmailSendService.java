package com.spring.tming.domain.emailVerify.service;

import com.spring.tming.domain.emailVerify.dto.request.EmailCheckReq;
import com.spring.tming.domain.emailVerify.dto.request.EmailReq;
import com.spring.tming.domain.emailVerify.dto.response.EmailRes;
import com.spring.tming.global.exception.GlobalException;
import com.spring.tming.global.meta.ResultCode;
import com.spring.tming.global.validator.EmailCheckValidator;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendService {
    private final StringRedisTemplate redisTemplate;
    private final JavaMailSender mailSender;
    private static final String SET_FROM = "${spring.mail.username}";
    private static final String TITLE = "Tming 회원가입인증 이메일입니다.";
    private static final String CONTENT_BEFORE_AUTHNUMBER =
            "Tming 서비스를 이용해주셔서 감사합니다." + "<br><br>" + "인증번호는 ";
    private static final String CONTENT_AFTER_AUTHNUMBER = "입니다." + "<br>" + "인증번호를 정확히 입력해주세요.";

    // 이메일을 어디서 보내는지, 어디로 보내는지, 인증 번호를 HTML 형식으로 어떻게 보내는지 작성.
    public EmailRes trialEmail(EmailReq emailReq) {
        String email = emailReq.getEmail();
        EmailCheckValidator.validateEmail(email);
        System.out.println("이메일 인증 이메일: " + email);
        String authNumber = makeRandomCapital();
        sendEmail(email, authNumber);
        // EmailRes 객체 생성 및 반환
        return new EmailRes();
    }

    public void verifyAuthNumber(EmailCheckReq emailCheckReq) {
        String email = emailCheckReq.getEmail();
        String authNumber = emailCheckReq.getAuthNumber();
    }

    private void sendEmail(String email, String authNumber) {
        String toMail = email; // 인증번호를 받을 이메일 주소를 입력 받음.
        final String content = CONTENT_BEFORE_AUTHNUMBER + authNumber + CONTENT_AFTER_AUTHNUMBER;
        mailPass(SET_FROM, toMail, TITLE, content);
        try {
            // 레디스에 인증번호 저장
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(email, authNumber, 30, TimeUnit.MINUTES); // 30분 동안만 저장
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
            throw new GlobalException(ResultCode.REDIS_CONNECTION_FAIL);
        }
    }

    // 인증번호를 담은 이메일을 전송함.
    private void mailPass(String setFrom, String toMail, String title, String content) {
        MimeMessage message =
                mailSender.createMimeMessage(); // JavaMailSender 객체를 사용하여 MimeMessage 객체를 생성함.
        try {
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "utf-8"); // 이메일 메시지와 관련된 설정을 수행.
            // true를 전달하여 multipart 형식의 메시지를 지원하고, "utf-8"을 전달하여 문자 인코딩을 설정.
            helper.setFrom(setFrom); // email-config에 설정한, 인증 메일 발송용 이메일 주소
            helper.setTo(toMail); // 이메일의 수신자 주소 설정
            helper.setSubject(title); // 이메일 제목
            helper.setText(content, true); // 이메일의 내용 설정. 두 번째 매개 변수에 true를 설정하여 HTML 형식으로 지정.
            mailSender.send(message);
        } catch (MessagingException e) {
            // 이메일 서버에 연결할 수 없거나, 잘못된 이메일 주소를 사용하거나, 인증 오류가 발생하는 등 오류가 있는 경우 MessagingException이 발생.
            e.printStackTrace(); // e.printStackTrace()는 예외를 기본 오류 스트림에 출력하는 메서드.
            throw new GlobalException(ResultCode.EMAIL_SEND_ERROR);
        }
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
