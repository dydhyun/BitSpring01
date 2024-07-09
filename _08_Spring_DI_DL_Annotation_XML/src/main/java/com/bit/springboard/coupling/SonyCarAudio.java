package com.bit.springboard.coupling;

import org.springframework.stereotype.Component;

// 스프링 설정파일의 context:component-scan 엘리먼트에 의해 자동으로 객체생성 된다.
@Component
public class SonyCarAudio implements CarAudio{
    public SonyCarAudio(){
        System.out.println("SonyCar Audio 객체 생성");
    }

    public void volumeUp(){
        System.out.println("SonyCarAudio 소리 증가");
    }

    public void volumeDown(){
        System.out.println("SonyCarAudio 소리 감소");
    }
}
