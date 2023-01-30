package com.example.aop;

import com.example.aop.entity.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Aspect
@Component
public class DecodeAOP {
    @Pointcut("@annotation(com.example.aop.annotation.Decode)")
    private void enableDecode() {}

    @Before("enableDecode()")
    public void before(JoinPoint joinPoint) throws UnsupportedEncodingException {
        Object[] args = joinPoint.getArgs();

        for(Object obj : args) {
            if(obj instanceof User) {
                User user = User.class.cast(obj);
                String encodeEmail = user.getEmail();
                String email = new String(Base64.getDecoder().decode(encodeEmail), "UTF-8");
                user.setEmail(email);
            }
        }
    }

    @AfterReturning(value = "enableDecode()", returning = "returnObj")
    public void afterReturn(JoinPoint joinPoint, Object returnObj) {
        if(returnObj instanceof User) {
            User user = User.class.cast(returnObj);
            String decodeEmail = user.getEmail();
            String email = Base64.getEncoder().encodeToString(decodeEmail.getBytes());
            user.setEmail(email);
        }
    }
}
