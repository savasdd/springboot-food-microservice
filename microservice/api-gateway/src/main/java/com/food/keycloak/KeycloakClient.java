package com.food.keycloak;

import com.food.config.keycloak.KeycloakConfig;
import com.food.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakClient {

    private final KeycloakConfig config;

    public AccessTokenResponse authenticate(UserDto dto) {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("client_id", config.getResource());
        paramMap.add("client_secret", config.getSecret());
        paramMap.add("username", dto.getUsername().trim());
        paramMap.add("password", dto.getPassword().trim());
        paramMap.add("grant_type", OAuth2Constants.PASSWORD);

        return getHeaderResponse(paramMap);
    }


    public AccessTokenResponse refreshToken(String token) {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("client_id", config.getResource());
        paramMap.add("client_secret", config.getSecret());
        paramMap.add("refresh_token", token);
        paramMap.add("grant_type", OAuth2Constants.REFRESH_TOKEN);

        return getHeaderResponse(paramMap);
    }

    private AccessTokenResponse getHeaderResponse(MultiValueMap<String, String> paramMap) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(paramMap, headers);

        final RestTemplate restTemplate = new RestTemplate();
        return getAccessTokenResponse(entity, restTemplate);
    }

    private AccessTokenResponse getAccessTokenResponse(HttpEntity<MultiValueMap<String, String>> entity, RestTemplate restTemplate) {
        ResponseEntity<AccessTokenResponse> response = restTemplate.exchange(config.getUrl(), HttpMethod.POST, entity, AccessTokenResponse.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Failed to authenticate");
            throw new RuntimeException("Failed to authenticate");
        }

        log.info("Authentication success");
        return response.getBody();
    }


    public RealmResource initClient() {
        RealmResource resource = KeycloakBuilder.builder()
                .serverUrl(config.getHost())
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .realm(config.getRealm())
                .clientId(config.getResource())
                .clientSecret(config.getSecret())
                .build().realm(config.getRealm());
        return resource;
    }


}
