package com.food.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends GenericDto {

    private String userId;
    private String firstName;
    private String lastName;
    private String fileData;
}
