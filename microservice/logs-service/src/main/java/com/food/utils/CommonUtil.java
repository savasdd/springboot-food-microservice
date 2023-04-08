package com.food.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class CommonUtil {

    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress != null)
            return ipAddress;
        else return request.getRemoteAddr();
    }

    public static HttpServletRequest getHttpServletRequest() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(attributes)) {
            return ((ServletRequestAttributes) attributes).getRequest();
        }
        return null;
    }

    public static String getHeaderValue(String headerName) {
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        if (Objects.nonNull(httpServletRequest.getHeader(headerName)))
            return httpServletRequest.getHeader(headerName);

        return null;
    }
}
