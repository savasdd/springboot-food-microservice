package com.food.service.jwt;

import com.food.dto.AuthorityDto;

public interface JwtService {

    AuthorityDto getAuthority(String token);
}
