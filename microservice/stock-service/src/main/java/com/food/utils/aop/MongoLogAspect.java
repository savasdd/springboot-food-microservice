package com.food.utils.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.dto.LogStock;
import com.food.service.LogService;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class MongoLogAspect {
    private final ObjectMapper mapper;

    private final LogService logService;

    public MongoLogAspect(ObjectMapper mapper, LogService logService) {
        this.mapper = mapper;
        this.logService = logService;
    }

    @Pointcut("@annotation(com.food.utils.aop.MongoLog)")
    public void logAnnotation() {

    }

    @Around(value = "logAnnotation() && @annotation(log)")
    public Object createIslemLog(ProceedingJoinPoint joinPoint, MongoLog log) throws Throwable {
        Object result = null;
        LogStock dto=new LogStock();

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

        logService.sendLog(dto);
        return result;
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (ObjectUtils.isEmpty(object)) {
            return null;
        }
        return mapper.writeValueAsString(object);
    }
}
