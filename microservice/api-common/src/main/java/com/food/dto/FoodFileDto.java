package com.food.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodFileDto extends GenericDto {

    private Long foodId;
    private String filename;
    private String fileType;
    private Long size;
    @JsonIgnore
    private MultipartFile fileData;
}
