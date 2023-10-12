package com.food.service.impl;

import com.food.dto.UserFileDto;
import com.food.service.UserFileService;
import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserFileServiceImpl implements UserFileService {
    @Value("${minio.bucket-name}")
    private String bucketName;
    private final MinioClient client;

    public UserFileServiceImpl(MinioClient client) {
        this.client = client;
    }

    @Override
    public List<UserFileDto> getListObjects() {
        var list = new ArrayList<UserFileDto>();
        try {
            var result = client.listObjects(ListObjectsArgs.builder().bucket(bucketName).recursive(true).build());

            for (Result<Item> item : result) {
                var dto = UserFileDto.builder().filename(item.get().objectName()).size(item.get().size()).userId(item.get().objectName()).filename(item.get().objectName()).lastName(item.get().objectName()).build();
                list.add(dto);
            }

            log.info("list objects {}", list.size());
        } catch (Exception e) {
            log.error("Happened error when get list objects from minio: ", e);
            return null;
        }
        return list;
    }


    @Override
    public InputStream getObjects(String fileName) {
        InputStream stream;
        try {
            stream = client.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
            log.info("get objects: {}", fileName);

        } catch (Exception e) {
            log.error("Happened error when get list objects from minio: ", e);
            return null;
        }

        return stream;
    }

    @Override
    public UserFileDto uploadFile(UserFileDto dto) {
        System.out.println(dto);

        return null;
    }
}
