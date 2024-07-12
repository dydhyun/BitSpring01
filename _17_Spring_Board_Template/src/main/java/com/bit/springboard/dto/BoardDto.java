package com.bit.springboard.dto;

import java.time.LocalDateTime;

public class BoardDto {
    private int id;
    private String title;
    private String content;
    private int WRITER_ID;
    private String nickname;
    private LocalDateTime regdate;
    private LocalDateTime moddate;
    private int cnt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getWRITER_ID() {
        return WRITER_ID;
    }

    public void setWRITER_ID(int WRITER_ID) {
        this.WRITER_ID = WRITER_ID;
    }

    public LocalDateTime getRegdate() {
        return regdate;
    }

    public void setRegdate(LocalDateTime regdate) {
        this.regdate = regdate;
    }

    public LocalDateTime getModdate() {
        return moddate;
    }

    public void setModdate(LocalDateTime moddate) {
        this.moddate = moddate;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "BoardDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", WRITER_ID=" + WRITER_ID +
                ", nickname='" + nickname + '\'' +
                ", regdate=" + regdate +
                ", moddate=" + moddate +
                ", cnt=" + cnt +
                '}';
    }
}
