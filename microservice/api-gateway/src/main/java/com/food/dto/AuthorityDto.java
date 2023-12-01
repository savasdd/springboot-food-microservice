package com.food.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Getter
@Setter
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorityDto implements Serializable {

    @JsonProperty("exp")
    private Double exp;
    @JsonProperty("iat")
    private Double iat;
    @JsonProperty("jti")
    private String jti;
    @JsonProperty("iss")
    private String iss;
    @JsonProperty("aud")
    private Object aud;
    @JsonProperty("sub")
    private String sub;
    @JsonProperty("typ")
    private String typ;
    @JsonProperty("azp")
    private String azp;
    @JsonProperty("session_state")
    private String session_state;
    @JsonProperty("acr")
    private String acr;
    @JsonProperty("realm_access")
    private JsonObject realm_access;
    @JsonProperty("resource_access")
    private JsonObject resource_access;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("sid")
    private String sid;
    @JsonProperty("email_verified")
    private String email_verified;
    @JsonProperty("name")
    private String name;
    @JsonProperty("preferred_username")
    private String preferred_username;
    @JsonProperty("given_name")
    private String given_name;
    @JsonProperty("family_name")
    private String family_name;
    @JsonProperty("email")
    private String email;
    private List<SimpleGrantedAuthority> roles;

    public List<SimpleGrantedAuthority> getRoles() {
        JsonArray resourceAccess = realm_access.getAsJsonArray("roles");

        return resourceAccess != null ? StreamSupport.stream(resourceAccess.spliterator(), false)
                .map(JsonElement::getAsString)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()) : null;
    }

}
