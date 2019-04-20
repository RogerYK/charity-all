package com.github.rogeryk.charity.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransactionAdvice {

    @Pointcut("execution(public * com.github.rogeryk.charity.service.*.*(..))" +
            "&& @annotation(org.springframework.transaction.annotation.Transactional)")
    public void transactionService(){}

    //对于Transaction中由于冲突失败的方法进行重试，三次后再抛出失败
    @Around("transactionService()")
    public Object daAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        int cnt = 0;
        Object ans = null;

        while (true) {
            try {
                ans =  proceedingJoinPoint.proceed();
            } catch (ObjectOptimisticLockingFailureException e) {
                if (cnt > 3) {
                    throw e;
                } else {
                    cnt++;
                    continue;
                }
            } catch (Throwable throwable) {
                throw throwable;
            }
            break;
        }

        return ans;
    }
}

