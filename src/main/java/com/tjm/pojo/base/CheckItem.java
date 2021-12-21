package com.tjm.pojo.base;

public class CheckItem {
    private int cid;
    private String serial;
    private String advice;
    private int weight;
    private String name;

    public CheckItem(String serial, String advice, int weight) {
        this.serial = serial;
        this.advice = advice;
        this.weight = weight;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
