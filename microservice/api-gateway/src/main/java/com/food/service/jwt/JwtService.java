package com.food.service.jwt;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public interface JwtService {

    List<SimpleGrantedAuthority> getRoles(String token);
}
