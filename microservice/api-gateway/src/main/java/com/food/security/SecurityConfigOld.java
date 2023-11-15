//package com.food.security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.Arrays;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//@RequiredArgsConstructor
//public class SecurityConfigOld {
//
//    private final JwtAuthConverter jwtAuthConverter;
//
//    private static final String[] AUTH_WHITELIST = {
//            "/v2/api-docs",
//            "/swagger-resources",
//            "/swagger-resources/**",
//            "/configuration/ui",
//            "/configuration/security",
//            "/swagger-ui.html",
//            "/webjars/**",
//            "/v3/api-docs/**",
//            "/swagger-ui/**",
//            "/configuration/**",
//            "/v3/api-docs/**",
//            "/swagger-ui/**",
//            "/v2/api-docs/**",
//            "/swagger-resources/**"
//    };
//
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:3000"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.addAllowedHeader("*");
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable();
//        http.authorizeRequests()
//                //.antMatchers(HttpMethod.GET, "/api/private").hasRole("USER")
//                .antMatchers("/api/auth/**").permitAll()
////                .antMatchers(HttpMethod.POST, "/api/auth/getToken").permitAll()
////                .antMatchers(HttpMethod.POST, "/api/auth/getRefreshToken").permitAll()
//                .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
//                .antMatchers(AUTH_WHITELIST).permitAll()
//                .anyRequest().authenticated();
//        http.oauth2ResourceServer()
//                .jwt()
//                .jwtAuthenticationConverter(jwtAuthConverter);
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        return http.build();
//    }
//
//
//}