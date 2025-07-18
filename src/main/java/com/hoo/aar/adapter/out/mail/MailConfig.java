package com.hoo.aar.adapter.out.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    JavaMailSender mailSender(
            @Value("${spring.mail.host}") String host,
            @Value("${spring.mail.port}") Integer port,
            @Value("${spring.mail.username}") String username,
            @Value("${spring.mail.password}") String password,
            @Value("${spring.mail.properties.mail.smtp.auth}") Boolean auth,
            @Value("${spring.mail.properties.mail.smtp.starttls.enable}") Boolean enable,
            @Value("${spring.mail.properties.mail.smtp.starttls.required}") Boolean required,
            @Value("${spring.mail.properties.mail.smtp.connectiontimeout}") Integer connectionTimeout,
            @Value("${spring.mail.properties.mail.smtp.timeout}") Integer timeout,
            @Value("${spring.mail.properties.mail.smtp.writetimeout}") Integer writeTimeout
            ) {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", auth);
        properties.put("mail.smtp.starttls.enable", enable);
        properties.put("mail.smtp.starttls.required", required);
        properties.put("mail.smtp.connectiontimeout", connectionTimeout);
        properties.put("mail.smtp.timeout", timeout);
        properties.put("mail.smtp.writetimeout", writeTimeout);

        mailSender.setJavaMailProperties(properties);

        return mailSender;
    }

}
