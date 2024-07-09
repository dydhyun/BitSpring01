package com.bit.springboard.coupling;

public class CarOwner_Solve_Coupling {
    public static void main(String[] args) {
        // 인터페이스 상속을 통한 다형성 구축을 통해 결합도를 낮추는 방식.
        Car car = new KiaCar();

        car.engineOn();
        car.engineOff();
        car.speedUp();
        car.speedDown();

        car = new HyundaiCar();

        car.engineOn();
        car.engineOff();
        car.speedUp();
        car.speedDown();

    }
}
