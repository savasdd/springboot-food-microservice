package com.food.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodFileDto extends GenericDto {

    private String foodId;
    private String filename;
    private Long size;
    private MultipartFile fileData;
}
