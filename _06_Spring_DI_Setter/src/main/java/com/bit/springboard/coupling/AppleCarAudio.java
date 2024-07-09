package com.bit.springboard.coupling;

public class AppleCarAudio implements CarAudio{
    public AppleCarAudio(){
        System.out.println("AppleCar Audio 객체 생성");
    }

    public void volumeUp(){
        System.out.println("AppleCarAudio 소리 증가");
    }

    public void volumeDown(){
        System.out.println("AppleCarAudio 소리 감소");
    }
}
