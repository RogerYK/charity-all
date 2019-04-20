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

//    @Before("point()")
//    public void injectUser(JoinPoint joinPoint) {
//        Object[] args = joinPoint.getArgs();
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        args[0] = user;
//    }

    @Around("point()")
    public Object injectUser(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("inject");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Object[] args = proceedingJoinPoint.getArgs();
        args[0] = user;
        log.info("aspect"+"potion");
        return proceedingJoinPoint.proceed(args);
    }
}
