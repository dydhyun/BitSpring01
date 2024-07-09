package com.bit.springboard.coupling;

public class KiaCar implements Car{
    public void engineOn(){
        System.out.println("Kia Car 시동 건다.");
    }

    public void engineOff(){
        System.out.println("KiaCar 시동 끈다.");
    }

    public void speedUp() {
        System.out.println("KiaCar 속도를 올린다.");
    }

    public void speedDown() {
        System.out.println("KiaCar 속도를 낮춘다.");
    }
    
    public void initMethod(){
        System.out.println("KiaCar 객체 생성");
    }
    
    public void destroyMethod(){
        System.out.println("KiaCar 객체 삭제");
    }

}