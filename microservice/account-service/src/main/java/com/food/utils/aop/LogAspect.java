package com.food.utils.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.dto.LogDto;
import com.food.service.LogService;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class LogAspect {
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private LogService service;

    @Pointcut("@annotation(com.food.utils.aop.Log)")
    public void logAnnotation() {

    }

    @Around(value = "logAnnotation() && @annotation(log)")
    public Object createIslemLog(ProceedingJoinPoint joinPoint, Log log) throws Throwable {
        Object result = null;
        LogDto dto=new LogDto();

        Class<? extends Object> sinif = joinPoint.getTarget().getClass();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method metot = signature.getMethod();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();


        dto.setService(sinif.getName() + ": " + metot.getName());
        //dto.setMethod(HttpUtil.getMethod(request));
        //dto.setPath(HttpUtil.getPath(request));
        dto.setCreateDate(new Date());
        dto.setUsername("log.user");

        ///// Before Method Execution /////
        result = joinPoint.proceed();
        ///// After Method Execution /////

        ResponseEntity<?> response = (ResponseEntity<?>) result;
        if (ObjectUtils.isNotEmpty(response)) {
            Object body = response.getBody();
            var status=(long) response.getStatusCodeValue();
            var json=convertObjectToJson(body);
            dto.setStatus(status);
            dto.setBody(json);
        }

        service.getLogService().sendLog(dto);
        return result;
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (ObjectUtils.isEmpty(object)) {
            return null;
        }
        return mapper.writeValueAsString(object);
    }
}
