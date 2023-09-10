package com.food.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFileDto extends GenericDto {

    private String userId;
    private String firstName;
    private String lastName;
    private String fileData;
}
