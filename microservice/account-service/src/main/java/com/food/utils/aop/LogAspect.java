package com.food.utils.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@Aspect
@Component
public class LogAspect {
    @Autowired
    ObjectMapper mapper;
    @Pointcut("@annotation(com.food.utils.aop.Log)")
    public void logAnnotation() {

    }

    @Around(value = "logAnnotation() && @annotation(log)")
    public Object createIslemLog(ProceedingJoinPoint joinPoint, Log log) throws Throwable {
        Object result = null;

        Class<? extends Object> sinif = joinPoint.getTarget().getClass();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method metot = signature.getMethod();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

//        dto.setServis(sinif.getName() + " => " + metot.getName());
//        dto.setMetot(HttpUtil.getMethod(request));
//        dto.setPath(HttpUtil.getPath(request));
//        dto.setCreateDate(new Date());
//        dto.setKullaniciAdi(util.getUser().getKullaniciAdi());
        // log.info(HttpUtil.getHeaderMap(request, false));

        ///// Before Method Execution /////
        result = joinPoint.proceed();
        ///// After Method Execution /////

        ResponseEntity<?> response = (ResponseEntity<?>) result;
        if (ObjectUtils.isNotEmpty(response)) {
            Object body = response.getBody();
            var status=(long) response.getStatusCodeValue();
            var json=convertObjectToJson(body);
            System.out.println(status);
            System.out.println(json);
        }

        return result;
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (ObjectUtils.isEmpty(object)) {
            return null;
        }
        return mapper.writeValueAsString(object);
    }
}
