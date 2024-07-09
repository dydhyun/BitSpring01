package com.bit.springboard.coupling;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class HyundaiCar implements Car {

    // 필드 주입
    // @Autowired : 선언 된 변수의 형태 객체가 bean 에 등록되어있는지 검사하고,
    // 등록되어 있으면, 그 객체에 의존성 주입하는 어노테이션.
//    @Autowired
    // @Qualifier : bean 객체가 의존성 주입할 때 같은 타입의 객체가 두 개 이상일 때 객체의 아이디로
    // 하나의 객체만 특정할 수 있도록 하는 어노테이션. @Autowired 와 항상 같이 사용해야된다.
    // @Component, @Controller, @Repository, @Service 어노테이션에서 () 로 생성할 객체의 아이디를 지정할 수 있다.
    // @Component("sonyCarAudio")
    // 만약 객체의 아이디를 지정하지 않으면, 기본적으로 클래스명에서 첫 글자만 소문자로 바꾼 이름이 id 로 지정되게 된다.
//    @Qualifier("sonyCarAudio")
    //@Resource : @Autowired + @Qualifier (jakarta Annotation API (dependency) 추가.)
    @Resource(name = "sonyCarAudio")
    private CarAudio carAudio;
    private String color;

    public HyundaiCar() {
        System.out.println("Hyundai Car 생성자1 호출");
    }

    // 생성자 주입
//    @Autowired
//    public  HyundaiCar( @Qualifier("sonyCarAudio") CarAudio carAudio) {
//        System.out.println("Hyundai Car 생성자2 호출");
//        this.carAudio = carAudio;
//    }

//    public  HyundaiCar(CarAudio carAudio, String color) {
//        System.out.println("Hyundai Car 생성자3 호출");
//        this.carAudio = carAudio;
//        this.color = color;
//    }

//    public  HyundaiCar(SonyCarAudio sonyCarAudio,AppleCarAudio appleCarAudio, String color) {
//        System.out.println("Hyundai Car 생성자4 호출");
//        this.sonyCarAudio = sonyCarAudio;
//        this.appleCarAudio = appleCarAudio;
//        this.color = color;
//    }

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

    public void volumeUp(){
//        carAudio = new CarAudio();
        carAudio.volumeUp();
    }

    public void volumeDown(){
//        carAudio = new CarAudio();
        carAudio.volumeDown();
    } // 객체의 반복 생성을 하지 않으려고 carAudio 를 빈으로 등록해서 반복생성 막으려고함. -> 생성자 주입

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        System.out.println("HyundaiCar setColor 호출");
        this.color = color;
    }



    public CarAudio getCarAudio() {
        return carAudio;
    }

    @Autowired
    public void setCarAudio(@Qualifier("sonyCarAudio") CarAudio carAudio) {
        System.out.println("HyundaiCar setCarAudio 호출");
        this.carAudio = carAudio;
    }

}
