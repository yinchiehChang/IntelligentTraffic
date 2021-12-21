package com.tjm.pojo.testCase;

public class GuideLine {
    //用例名称
    private String case_name;
    //用例标识
    private String serial;
    //用例说明
    private String description;

    public GuideLine(String case_name, String serial, String description) {
        this.case_name = case_name;
        this.serial = serial;
        this.description = description;
    }

    public String getCase_name() {
        return case_name;
    }

    public void setCase_name(String case_name) {
        this.case_name = case_name;
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
