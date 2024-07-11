package com.bit.springboard.common;

import com.bit.springboard.dto.BoardDto;
import org.aspectj.lang.JoinPoint;

public class AfterReturning {
        // 두 번째 매개변수인 Object 타입의 변수는 메서드가 종료될 때 반환해 주는값을 담아주는 매개변수
        // 두 번째 매개변수같은 매개변수를 바인딩 매개변수라고 부른다.(값을 자동으로 매핑해주는 매개변수)

    public void afterReturningLog(JoinPoint joinPoint, Object returnObj){
        String methodName = joinPoint.getSignature().getName();

        if(returnObj != null && returnObj instanceof BoardDto){
            BoardDto boardDto = (BoardDto) returnObj;
            if(boardDto.getNickname().equals("비트캠프3")){
                System.out.println("작성자는 비트캠프3입니다.");
            }

        }

        if(returnObj != null){
            System.out.println("[로그] " + methodName + "()의 리턴값 " + returnObj.toString());
        } else {
            System.out.println("[로그] " + methodName + "()은 리턴값이 없습니다.");
        }
    }
    
}
