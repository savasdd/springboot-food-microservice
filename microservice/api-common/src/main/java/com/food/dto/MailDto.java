package com.food.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailDto extends GenericDto {

    private String to;
    private String subject;
    private String body;
    private String from;
    private String path;
}
