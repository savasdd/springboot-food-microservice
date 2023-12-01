package com.food.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.food.dto.AuthorityDto;
import com.food.utils.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static java.util.Objects.isNull;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    @Override
    public AuthorityDto getAuthority(String token) {
        try {
            DecodedJWT decodedJWT = decodeToken(token);
            JsonObject payloadAsJson = decodeTokenPayloadToJsonObject(decodedJWT);//User info

            var parse = JsonUtil.fromJson(payloadAsJson, AuthorityDto.class);
            parse.setRoles(parse.getRoles());
            return parse;
        } catch (Exception e) {
            log.error("Token has not been parsed ", e.getMessage());
        }
        return null;
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
}
