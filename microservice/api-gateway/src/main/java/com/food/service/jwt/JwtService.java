package com.food.service.jwt;

import com.food.dto.AuthorityDto;

public interface JwtService {

    AuthorityDto getRoles(String token);
}
