package com.food.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Objects.isNull;

@Service
public class JwtServiceImpl implements JwtService {

    @Override
    public List<SimpleGrantedAuthority> getRoles(String token) {
        return null;
    }

    private List<SimpleGrantedAuthority> getAutRoles(String value) {
        try {
            DecodedJWT decodedJWT = decodeToken(value);
            JsonObject payloadAsJson = decodeTokenPayloadToJsonObject(decodedJWT);//User info
            JsonObject resourceAccess = payloadAsJson.getAsJsonObject("realm_access");
            if (resourceAccess != null) {
                return StreamSupport.stream(resourceAccess.getAsJsonArray("roles").spliterator(), false)
                        .map(JsonElement::getAsString)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
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
