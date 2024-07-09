package com.bit.springboard.coupling;

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
