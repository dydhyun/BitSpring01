package com.bit.springboard.dto;

// DTO(Data Transfer Object): 데이터를 전송하는 객체
//                            화면에서 넘어오는 데이터를 받아서 DB 까지 전달하거나
//                            컨트롤러에서 화면으로 데이터를 전송할 때 사용하는 객체
//                            VO(Value Object)랑 쓰임새가 비슷하다.
public class MemberDto {
    private int id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String tel;
    private String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "MemberDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
