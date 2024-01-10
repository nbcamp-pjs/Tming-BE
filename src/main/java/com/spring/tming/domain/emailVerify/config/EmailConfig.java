package com.spring.tming.domain.emailVerify.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {

    @Value("${mail.transport.protocol}")
    private String mailTransportProtocol;

    @Value("${mail.smtp.auth}")
    private String mailSmtpAuth;

    @Value("${mail.smtp.socketFactory.class}")
    private String mailSmtpSocketFactoryClass;

    @Value("${mail.smtp.starttls.enable}")
    private String mailSmtpStarttlsEnable;

    @Value("${mail.debug}")
    private String mailDebug;

    @Value("${mail.smtp.ssl.trust}")
    private String mailSmtpSslTrust;

    @Value("${mail.smtp.ssl.protocols}")
    private String mailSmtpSslProtocols;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.transport.protocol", mailTransportProtocol);
        javaMailProperties.put("mail.smtp.auth", mailSmtpAuth);
        javaMailProperties.put("mail.smtp.socketFactory.class", mailSmtpSocketFactoryClass);
        javaMailProperties.put("mail.smtp.starttls.enable", mailSmtpStarttlsEnable);
        javaMailProperties.put("mail.debug", mailDebug);
        javaMailProperties.put("mail.smtp.ssl.trust", mailSmtpSslTrust);
        javaMailProperties.put("mail.smtp.ssl.protocols", mailSmtpSslProtocols);

        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;
    }
}
