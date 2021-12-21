package com.tjm.pojo.testCase;

public class FunctionPoint {
    private int fid;
    private String serial;
    private String description;

    public FunctionPoint(String serial, String description) {
        this.serial = serial;
        this.description = description;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
