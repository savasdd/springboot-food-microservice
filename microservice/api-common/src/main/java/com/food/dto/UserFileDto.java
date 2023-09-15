package com.food.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFileDto extends GenericDto {

    private String userId;
    private String firstName;
    private String lastName;
    @SuppressWarnings("java:S1948")
    private MultipartFile fileData;
    private String filename;
    private Long size;
}
