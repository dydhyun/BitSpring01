package com.bit.springboard.coupling;

public class HyundaiCar implements Car {
    public void engineOn() {
        System.out.println("HyundaiCar 시동을 건다.");
    }

    public void engineOff() {
        System.out.println("HyundaiCar 시동을 끈다.");
    }

    public void speedUp() {
        System.out.println("HyundaiCar 속도를 올린다.");
    }

    public void speedDown() {
        System.out.println("HyundaiCar 속도를 낮춘다.");
    }

    public void initMethod(){
        System.out.println("HyundaiCar 객체 생성");
    }

    public void destroyMethod(){
        System.out.println("HyundaiCar 객체 삭제");
    }
}
