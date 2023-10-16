package com.food.service.impl;

import com.food.config.MailConfig;
import com.food.dto.MailDto;
import com.food.enums.EPaymentType;
import com.food.event.OrderEvent;
import com.food.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    private final ApplicationContext applicationContext;
    private final JavaMailSender mailSender;
    private final MailConfig mailConfig;

    public EmailServiceImpl(ApplicationContext applicationContext, JavaMailSender mailSender, MailConfig mailConfig) {
        this.applicationContext = applicationContext;
        this.mailSender = mailSender;
        this.mailConfig = mailConfig;
        System.setProperty("mail.mime.charset", "utf8");
    }

    @Override
    public void confirm(OrderEvent event) {
        String to = "svsdd22@gmail.com";
        String subject = "Ödeme Bilgisi";
        if (event.getStatus().equals(EPaymentType.ACCEPT)) {
            sendMail(MailDto.builder().to(to).subject(subject).foodName(event.getFoodName()).foodCount(event.getStockCount()).foodPrice(event.getAmount()).foodStatus(event.getStatus().name()).build());
        }
    }

    @Override
    public Boolean sendMail(MailDto dto) {
        try {
            Map<String, Object> props = new HashMap<String, Object>();
            props.put("from", mailConfig.getFrom());
            props.put("to", dto.getTo());
            props.put("urun", dto.getFoodName());
            props.put("adet", dto.getFoodCount());
            props.put("fiyat", dto.getFoodPrice());
            props.put("durum", dto.getFoodStatus());
            String html = configSpringTemplate(props);
            dto.setBody(html);

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


    private String configSpringTemplate(Map<String, Object> props) {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("classpath:/template/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resolver.setApplicationContext(applicationContext);

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(resolver);

        Context context = new Context();
        context.setVariables(props);
        final String template = "mail-template";
        String html = templateEngine.process(template, context);

        return html;
    }
}
