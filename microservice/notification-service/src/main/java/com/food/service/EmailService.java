package com.food.service;

import com.food.dto.MailDto;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    Boolean sendMail(MailDto dto);

    Boolean sendMailTemplate(MailDto dto, SimpleMailMessage template, String... templateArgs);

    Boolean sendMailAttachment(MailDto dto);
}
