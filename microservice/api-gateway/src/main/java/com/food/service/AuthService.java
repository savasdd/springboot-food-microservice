package com.food.service;

import com.food.service.impl.AuthServiceImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthServiceImpl authService;
}
