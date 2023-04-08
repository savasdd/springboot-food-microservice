package com.food.service;

import com.food.dto.UserDto;
import com.food.dto.UserRolDto;
import com.food.service.impl.AuthServiceImpl;
import com.food.utils.kyce.KeycloakTokenResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AuthService {
    public KeycloakTokenResponse getToken(UserDto dto) throws Exception;

    public KeycloakTokenResponse refreshToken(String token);

    public List<UserRolDto> getUserRoles();
}
