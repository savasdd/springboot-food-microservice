package com.food.service.impl;

import com.food.config.MinioConfig;
import com.food.dto.FoodFileDto;
import com.food.minio.MinioUtil;
import com.food.service.FoodFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class FoodFileServiceImpl implements FoodFileService {
    private final MinioUtil minioUtil;
    private final MinioConfig minioProperties;

    public FoodFileServiceImpl(MinioUtil minioUtil, MinioConfig minioProperties) {
        this.minioUtil = minioUtil;
        this.minioProperties = minioProperties;
    }

    @Override
    public List<FoodFileDto> getListObjects(String foodId) {
        var result = minioUtil.listObjects(minioProperties.getBucketName());
        var list = StreamSupport.stream(result.spliterator(), true).map(val -> {
            try {
                return FoodFileDto.builder().filename(val.get().objectName()).size(val.get().size()).foodId(val.get().objectName()).build();

            } catch (Exception e) {
                log.error("Happened error when get list objects from minio: ", e);
                return FoodFileDto.builder().build();
            }
        }).toList();
        return list;
    }


    @Override
    public InputStream getObjects(String fileName) {
        var result = minioUtil.getObject(minioProperties.getBucketName(), fileName);
        return result;
    }

    @Override
    public FoodFileDto uploadFile(FoodFileDto dto) {
        System.out.println(dto);
        System.out.println(dto.getFileData().getOriginalFilename());
        System.out.println(dto.getFileData().getSize());

        return null;
    }
}
