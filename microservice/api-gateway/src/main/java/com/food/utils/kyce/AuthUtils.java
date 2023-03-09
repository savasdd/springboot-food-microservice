package com.food.utils.kyce;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.food.dto.UserDto;
import com.food.dto.UserRolDto;
import com.food.utils.GatewayUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.ToString;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Objects.isNull;

@Component
@ToString
public class AuthUtils {

    @Value("${keycloak.resource}")
    private String clientId;

    public UserDto getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //Principal principal = (Principal) auth.getPrincipal();
        return new UserDto(auth.getName(), null);
    }

    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext()).map(e -> e.getAuthentication())
                .map(Authentication::getName);
    }


    public List<UserRolDto> getUserRol(String token) throws Exception {
        String tkn = token != null ? token : getTokenFromAuth();
        List<SimpleGrantedAuthority> list = getAutRoles(tkn);
        var roles = list.stream().map(m -> {
            return new UserRolDto(m.getAuthority().trim());
        }).collect(Collectors.toList());
        return roles;
    }


    private List<SimpleGrantedAuthority> getAutRoles(String value) throws Exception {
        DecodedJWT decodedJWT = decodeToken(value);
        JsonObject payloadAsJson = decodeTokenPayloadToJsonObject(decodedJWT);//User info
        JsonObject resourceAccess = payloadAsJson.getAsJsonObject("realm_access");
        if (resourceAccess != null) {
            return StreamSupport.stream(resourceAccess.getAsJsonArray("roles").spliterator(), false)
                    .map(JsonElement::getAsString)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    private DecodedJWT decodeToken(String value) throws Exception {
        if (isNull(value)) {
            throw new Exception("Token has not been provided");
        }
        return JWT.decode(value);
    }

    private JsonObject decodeTokenPayloadToJsonObject(DecodedJWT decodedJWT) throws Exception {
        try {
            String payloadAsString = decodedJWT.getPayload();
            return new Gson().fromJson(
                    new String(Base64.getDecoder().decode(payloadAsString), StandardCharsets.UTF_8),
                    JsonObject.class);
        } catch (RuntimeException exception) {
            throw new Exception("Invalid JWT or JSON format of each of the jwt parts", exception);
        }
    }

    public Boolean validateRol(String value) throws Exception {
        List<SimpleGrantedAuthority> list = getAutRoles(value);
        String[] roles = {GatewayUtils.ADMIN_ROLU, GatewayUtils.USER_ROLU};

        if (list.size() > 0) {
            Long count = list.stream().filter(c -> Arrays.asList(roles).contains(c.getAuthority().trim())).count();
            if (count > 0)
                return true;
        }
        return false;
    }

    private String getTokenFromAuth() {
        String token = null;
        try {
            Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            JSONObject obj = new JSONObject(mapper.writeValueAsString(object));
            token = obj.has("tokenValue") ? obj.get("tokenValue").toString() : null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return token;
    }



}
