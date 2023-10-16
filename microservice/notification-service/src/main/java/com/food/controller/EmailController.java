package com.food.controller;

import com.food.dto.MailDto;
import com.food.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class EmailController {

    private final EmailService service;

    public EmailController(EmailService service) {
        this.service = service;
    }

    @Operation(description = "Mail send")
    @PostMapping(value = "/send")
    public ResponseEntity<Boolean> sendMail(@RequestBody MailDto dto) {
        return ResponseEntity.ok(service.sendMail(dto));
    }
}
