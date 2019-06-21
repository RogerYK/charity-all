package com.github.rogeryk.charity.aop.login;

import com.github.rogeryk.charity.domain.User;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@Component
public class InjectUser {

    @Pointcut("execution(public * com.github.rogeryk.charity.controller.*.*(@LoginedUser (*), ..)) ")
    public void point(){}


    @Around("point()")
    public Object injectUser(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("inject");
        Object user =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Object[] args = proceedingJoinPoint.getArgs();
        if (user instanceof User) {
            args[0] = ((User) user).getId();
        } else {
            args[0] = null;
        }
        log.info("aspect"+"potion");
        return proceedingJoinPoint.proceed(args);
    }
}
