package com.spring.tming.domain.emailVerify.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {

    @Value("${spring.mail.transport.protocol}")
    private String springMailTransportProtocol;

    @Value("${spring.mail.smtp.auth}")
    private String springMailSmtpAuth;

    @Value("${spring.mail.smtp.socketFactory.class}")
    private String springMailSmtpSocketFactoryClass;

    @Value("${spring.mail.smtp.starttls.enable}")
    private String springMailSmtpStarttlsEnable;

    @Value("${spring.mail.debug}")
    private String springMailDebug;

    @Value("${spring.mail.smtp.ssl.trust}")
    private String springMailSmtpSslTrust;

    @Value("${spring.mail.smtp.ssl.protocols}")
    private String springMailSmtpSslProtocols;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("spring.mail.transport.protocol", springMailTransportProtocol);
        javaMailProperties.put("spring.mail.smtp.auth", springMailSmtpAuth);
        javaMailProperties.put(
                "spring.mail.smtp.socketFactory.class", springMailSmtpSocketFactoryClass);
        javaMailProperties.put("spring.mail.smtp.starttls.enable", springMailSmtpStarttlsEnable);
        javaMailProperties.put("spring.mail.debug", springMailDebug);
        javaMailProperties.put("spring.mail.smtp.ssl.trust", springMailSmtpSslTrust);
        javaMailProperties.put("spring.mail.smtp.ssl.protocols", springMailSmtpSslProtocols);

        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;
    }
}
