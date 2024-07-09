package com.bit.springboard.coupling;

public class KiaCar implements Car{
    private String Color;
    private CarAudio carAudio;

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


    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        System.out.println("setColor 호출");
        Color = color;
    }

    public CarAudio getCarAudio() {
        return carAudio;
    }

    public void setCarAudio(CarAudio carAudio) {
        System.out.println("setCarAudio 호출");
        this.carAudio = carAudio;
    }

}