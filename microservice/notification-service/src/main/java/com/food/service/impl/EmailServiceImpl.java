package com.food.service.impl;

import com.food.config.MailConfig;
import com.food.dto.MailDto;
import com.food.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final MailConfig mailConfig;

    public EmailServiceImpl(JavaMailSender mailSender, MailConfig mailConfig) {
        this.mailSender = mailSender;
        this.mailConfig = mailConfig;
        System.setProperty("mail.mime.charset", "utf8");
    }

    @Override
    public Boolean sendMail(MailDto dto) {
        try {
            String from = mailConfig.getFrom();
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String encodedSubject = new String(dto.getSubject().getBytes("UTF-8"), "UTF-8");
            String encodedBody = new String(dto.getBody().getBytes("UTF-8"), "UTF-8");
            message.setSubject(encodedSubject, "UTF-8");
            message.setContent(encodedBody, "text/html; charset=UTF-8");
            helper.setFrom(from);
            helper.setTo(dto.getTo());
            mailSender.send(message);

            return true;
        } catch (Exception e) {
            log.error("Mail Exception {}", e.getMessage());
            return false;
        }

    }

    @Override
    public Boolean sendMailTemplate(MailDto dto, SimpleMailMessage template, String... templateArgs) {
        String text = String.format(template.getText(), templateArgs);
        dto.setBody(text);
        return sendMail(dto);
    }

    @Override
    public Boolean sendMailAttachment(MailDto dto) {
        try {
            String from = mailConfig.getFrom();
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(dto.getTo());
            helper.setSubject(dto.getSubject());
            helper.setText(dto.getBody());

            FileSystemResource file = new FileSystemResource(new File(dto.getPath()));
            helper.addAttachment("Invoice", file);
            mailSender.send(message);

            return true;
        } catch (Exception e) {
            log.error("Mail Exception {}", e.getMessage());
            return false;
        }
    }
}
