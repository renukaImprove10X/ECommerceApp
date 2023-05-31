package com.improve10x.igurupractice.models;

public class Rating {
    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    private Float rate;
    private Integer count;
}
