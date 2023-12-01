package com.food.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.event.LogEvent;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

@Aspect
@Component
public class MongoLogAspect {
    private final ObjectMapper mapper;

    public MongoLogAspect(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Pointcut("@annotation(com.food.aop.MongoLog)")
    public void logAnnotation() {
    }

    @Around(value = "logAnnotation() && @annotation(log)")
    public Object createIslemLog(ProceedingJoinPoint joinPoint, MongoLog log) throws Throwable {
        Object result = null;
        LogEvent dto = new LogEvent();

        Class<? extends Object> sinif = joinPoint.getTarget().getClass();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method metot = signature.getMethod();
        MongoLog annotation = metot.getAnnotation(MongoLog.class);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        dto.setUsername("log.user");
        dto.setStatus(annotation != null ? Integer.valueOf(annotation.status()) : null);
        //dto.setMethod(metot.getName());

        ///// Before Method Execution /////
        result = joinPoint.proceed();
        ///// After Method Execution /////

        Object response = (Object) result;
        if (ObjectUtils.isNotEmpty(response)) {
            dto.setBody(List.of(convertObjectToJson(response)));
        }

        ///service.producerLog(dto);
        return result;
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (ObjectUtils.isEmpty(object)) {
            return null;
        }
        return mapper.writeValueAsString(object);
    }
}
