package com.example.capstone.service;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String from;

    @Value("${spring.mail.password}")
    private String password;

    /**
     * 이메일로 인증번호를 전송하는 메서드
     * @param toEmail 수신자 이메일 주소
     * @return 생성된 인증번호 (6자리)
     */
    public String sendVerificationCode(String toEmail) {
        String code = generateCode(); // 인증번호 생성

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("[WithMe] 이메일 인증 코드");
            message.setText("인증번호: " + code);
            Transport.send(message);
        } catch (Exception e) {
            // 로그에 에러 메시지를 명확히 출력
            System.err.println("[이메일 전송 실패] " + e.getClass().getSimpleName() + ": " + e.getMessage());

            // 예외를 감싸서 다시 던지기
            throw new IllegalStateException("이메일 전송 중 오류가 발생했습니다.", e);
        }

        return code;
    }

    /**
     * 6자리 난수 인증번호 생성
     */
    private String generateCode() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }
}
