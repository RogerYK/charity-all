package com.github.rogeryk.charity.server.core.aop.login;

import com.github.rogeryk.charity.server.db.domain.User;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@Component
public class InjectUser {

    @Pointcut("execution(public * com.github.rogeryk.charity.server.web.controller.*.*(@LoginedUser (*), ..)) ")
    public void point(){}


    @Around("point()")
    public Object injectUser(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object user =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MethodSignature s = (MethodSignature) proceedingJoinPoint.getSignature();
        Class<?>[] paramTypes = s.getMethod().getParameterTypes();
        Object[] args = proceedingJoinPoint.getArgs();
        if (user instanceof User) {
            if (paramTypes[0].equals(User.class)) {
                args[0] = user;
            } else if (paramTypes[0].equals(Long.class)) {
                args[0] = ((User) user).getId();
            }
        } else {
            args[0] = null;
        }
        return proceedingJoinPoint.proceed(args);
    }
}
