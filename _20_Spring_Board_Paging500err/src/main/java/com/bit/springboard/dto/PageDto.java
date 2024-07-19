package com.bit.springboard.dto;

public class PageDto {
    // 표출되는 첫 페이지 번호
    private int startPage;

    // 표출되는 끝 페이지 번호
    private int endPage;

    // 이전, 다음 버튼 표출 여부
    private boolean prev, next;

    // 총 개시글 갯수
    private int total;

    // Criteria 객체
    private Criteria criteria;


    public PageDto(Criteria criteria, int total){
        this.criteria = criteria;
        this.total = total;

        // 끝 페이지 계산
        this.endPage = (int) (Math.ceil(criteria.getPageNum() / 10.0 )) * 10;

        // 시작 페이지 계산
        this.startPage = this.endPage - 9;

        // 실제 마지막 게시글이 있는 페이지 계산
        int realEndPage = (int) (Math.ceil( (total / 1.0) / criteria.getAmount() ));

        // endPage 가 실제 realEndPage 보다 커지면
        // endPage 에 realEndPage 를 주입
        if(endPage > realEndPage){
            this.endPage = realEndPage;
        }

        // 이전, 다음 버튼
        this.prev = this.criteria.getPageNum() > 1;
        this.next = this.criteria.getPageNum() < this.endPage;
    }


    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public boolean isPrev() {
        return prev;
    }

    public void setPrev(boolean prev) {
        this.prev = prev;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public String toString() {
        return "PageDto{" +
                "startPage=" + startPage +
                ", endPage=" + endPage +
                ", prev=" + prev +
                ", next=" + next +
                ", total=" + total +
                ", criteria=" + criteria +
                '}';
    }
}
