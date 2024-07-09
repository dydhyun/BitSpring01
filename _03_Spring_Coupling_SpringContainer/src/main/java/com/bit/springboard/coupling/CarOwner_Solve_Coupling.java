package com.bit.springboard.coupling;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class CarOwner_Solve_Coupling {
    public static void main(String[] args) {

        // 1. 스프링 컨테이너 구동
        // 스프링 컨테이너 객체 생성
        // bean 엘리먼트로 등록되어 있는 클래스들의 객체 자동으로 생성
        AbstractApplicationContext factory =
                // 어떤 설정파일로 스프링 컨테이너를 구동할지 지정
                new GenericXmlApplicationContext("root-context.xml");

        // 2. 의존성 검색(Dependency Lookup) : bean 객체로 생성된 객체를 찾는 기능
        // 의존성 주입(Dependency Injection) : 의존성 검색을 통해 찾는 객체를 변수에 담아주는 기능
        Car car = factory.getBean("KiaCar", Car.class);
//        Car car = (Car) factory.getBean("KiaCar");

        car.engineOn();
        car.engineOff();
        car.speedUp();
        car.speedDown();

        car = (Car) factory.getBean("HyundaiCar");

        car.engineOn();
        car.engineOff();
        car.speedUp();
        car.speedDown();

        // 스프링 컨테이너 구동 종료
        factory.close();
        
    }
}
