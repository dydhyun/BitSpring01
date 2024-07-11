package com.bit.springboard.common;

import org.aspectj.lang.ProceedingJoinPoint;

public class Around {
    public Object aroundLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        System.out.println("[로그] 로직 처리 전 실행");

        Object returnObj = proceedingJoinPoint.proceed();
        return returnObj;
    }
}
