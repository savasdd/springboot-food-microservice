package com.food.utils.kyce;

import com.food.dto.UserDto;
import com.food.utils.GatewayUtils;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Component
public class KeycloakAuthClient {
    @Value("${keycloak.resource}")
    String clientId;

    @Value("${keycloak.realm}")
    String realm;
    @Value("${keycloak.credentials.secret}")
    String clientSecret;

    @Value("${keycloak.host}")
    String baseUrl;

    @Value("${keycloak.auth-server-url}")
    String authUrl;

    public AccessTokenResponse authenticate(UserDto dto) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(baseUrl)
                .grantType(OAuth2Constants.PASSWORD)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(dto.getUsername().split("@")[0])
                .password(dto.getPassword())
                .build();
        AccessTokenResponse resp = keycloak.tokenManager().getAccessToken();
        log.info("Authentication success");
        return resp;
    }

    public AccessTokenResponse refreshToken(String token) {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("client_id", clientId);
        paramMap.add("client_secret", clientSecret);
        paramMap.add("refresh_token", token);
        paramMap.add("grant_type", OAuth2Constants.REFRESH_TOKEN);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(paramMap, headers);

        log.info("Try to authenticate");
        final RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AccessTokenResponse> response = restTemplate.exchange(authUrl, HttpMethod.POST, entity, AccessTokenResponse.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Failed to authenticate");
            throw new RuntimeException("Failed to authenticate");
        }

        log.info("Authentication success");
        return response.getBody();
    }

    public Boolean isLoginUser(String username) {
        Boolean isLogin = false;
        RealmResource resource = initResource();
        var mainSubGroup = getKycGroup(resource);
        var userGroup = getKycUserGroup(resource, username);

        if (!mainSubGroup.isEmpty())
            for (GroupRepresentation g : mainSubGroup) {
                var sas = userGroup.stream().map(m1 -> m1.getName()).collect(Collectors.toList()).contains(g.getName());
                if (sas)
                    isLogin = true;
            }

        log.info("Auth Login: " + isLogin);
        return isLogin;
    }


    private RealmResource initResource() {
        RealmResource resource = KeycloakBuilder.builder()
                .serverUrl(baseUrl)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build().realm(realm);
        return resource;
    }

    public UserRepresentation getKycUser(String username) {
        RealmResource resource = initResource();
        List<UserRepresentation> userList = resource.users().search(username.split("@")[0]);
        return userList.size() > 0 ? userList.get(0) : null;
    }

    public List<RoleRepresentation> getUserRoles(String username) {
        RealmResource resource = initResource();
        List<UserRepresentation> userList = resource.users().search(username);
        String userId = userList.size() > 0 ? userList.get(0).getId() : null;
        List<RoleRepresentation> list = resource.users().get(userId).roles().realmLevel().listEffective();
        return list;
    }

    public List<GroupRepresentation> getKycGroup(RealmResource resc) {
        RealmResource resource = resc != null ? resc : initResource();
        List<List<GroupRepresentation>> group = resource.groups().groups().stream().filter(f -> f.getName().trim().equals(GatewayUtils.KEYC_GROUP)).map(m -> m.getSubGroups()).collect(Collectors.toList());
        return group.size() > 0 ? group.get(0) : new ArrayList<>();
    }

    public List<GroupRepresentation> getKycUserGroup(RealmResource resc, String username) {
        RealmResource resource = resc != null ? resc : initResource();
        String userId = getKycUser(username).getId();
        List<GroupRepresentation> group = resource.users().get(userId).groups();
        return group.size() > 0 ? group : new ArrayList<>();
    }

}
