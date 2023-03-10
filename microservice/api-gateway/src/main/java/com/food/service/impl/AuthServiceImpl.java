package com.food.service.impl;

import com.food.dto.UserDto;
import com.food.dto.UserRolDto;
import com.food.utils.kyce.AuthUtils;
import com.food.utils.kyce.KeycloakAuthClient;
import com.food.utils.kyce.KeycloakTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthServiceImpl {

    private final AuthUtils utils;
    private final KeycloakAuthClient client;

    public KeycloakTokenResponse getToken(UserDto dto) throws Exception {
        AccessTokenResponse response = client.authenticateApi(dto);
        log.info("Generate Token: " + dto.getUsername());
        return new KeycloakTokenResponse(response.getToken(), response.getExpiresIn(), response.getTokenType(), utils.getUserRol(response.getToken()), response.getRefreshToken(), response.getRefreshExpiresIn());

//        if (client.isLoginUser(dto.getUsername())) {
//            log.info("Generate Token: " + dto.getUsername());
//            return new KeycloakTokenResponse(response.getToken(), response.getExpiresIn(), response.getTokenType(), utils.getUserRol(response.getToken()), response.getRefreshToken(), response.getRefreshExpiresIn());
//        } else
//            throw new Exception("Kullanıcı Giriş Yetkisi Yok!");
    }

    public KeycloakTokenResponse refreshToken(String token) {
        try {
            AccessTokenResponse response = client.refreshToken(token);
            log.info("Generate Refresh Token");
            return new KeycloakTokenResponse(response.getToken(), response.getExpiresIn(), response.getTokenType(), utils.getUserRol(response.getToken()), response.getRefreshToken(), response.getRefreshExpiresIn());

        } catch (Exception e) {
            log.error("Token Hatası");
            e.printStackTrace();
            return null;
        }
    }

    public List<UserRolDto> getUserRoles() throws Exception {
        List<RoleRepresentation> list = client.getUserRoles(utils.getCurrentAuditor().get());
        var rolList = list.stream().map(m -> new UserRolDto(m.getName())).collect(Collectors.toList());
        log.info("Get User Roles");
        return rolList.size() > 0 ? rolList : null;
    }

}
