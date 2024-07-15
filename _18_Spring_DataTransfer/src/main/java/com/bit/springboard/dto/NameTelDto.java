package com.bit.springboard.dto;

public class NameTelDto {
    private String name;
    private String tel;

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }

    public void setName(String name) {
        System.out.println("setName() 자동실행");
        this.name = name;
    }

    public void setTel(String tel) {
        System.out.println("setTel() 자동실행");
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "NameTelDto{" +
                "name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
