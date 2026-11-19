package com.probity.echawdi.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.config.WithDatabase;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class DatabaseRouterAspect {

    private final TransactionalHelper transactionalHelper;

    @Around("@annotation(withDatabase)")
    public Object proceed(ProceedingJoinPoint proceedingJoinPoint, WithDatabase withDatabase) throws Throwable {
        try {
            DatabaseContextHolder.setCtx(withDatabase.value());
            return this.transactionalHelper.runWithTransaction(proceedingJoinPoint::proceed);
        } finally {
            DatabaseContextHolder.restoreCtx();
        }
    }
}