package com.bit.springboard.common;

import org.aspectj.lang.JoinPoint;

public class AfterThrowing {
    public void afterThrowingLog(JoinPoint joinPoint, Exception exception){
        String methodName = joinPoint.getSignature().getName();

        System.out.println("[예외발생] "+ methodName +" 메서드 실행 중 발생한 예외 메시지 : " + exception.getMessage());
    }
}
