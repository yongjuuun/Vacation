package com.example.API.domain.user;

// 로그인 회원 정보
public class User {
    private Long id;
    private String password;
    private String name;
    private int remainVaction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRemainVaction() {
        return remainVaction;
    }

    public void setRemainVaction(int remainVaction) {
        this.remainVaction = remainVaction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
