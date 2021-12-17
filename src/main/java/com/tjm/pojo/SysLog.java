package com.tjm.pojo;

import com.tjm.config.ExcelColumn;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
public class SysLog {
    @ExcelColumn(value = "log_id", col = 1)
    int log_id;

    @ExcelColumn(value = "username", col = 2)
    String username;

    @ExcelColumn(value = "class_name",col = 3)
    String class_name;

    @ExcelColumn(value = "method_name",col = 4)
    String method_name;

    @ExcelColumn(value = "args",col = 5)
    String args;

    @ExcelColumn(value = "time",col = 6)
    Timestamp creat_time;

    @ExcelColumn(value = "ip",col = 7)
    String ip;

    public int getLog_id() {
        return log_id;
    }

    public void setLog_id(int log_id) {
        this.log_id = log_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getMethod_name() {
        return method_name;
    }

    public void setMethod_name(String method_name) {
        this.method_name = method_name;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public Timestamp getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(Timestamp creat_time) {
        this.creat_time = creat_time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "SysLog{" +
                "log_id=" + log_id +
                ", username='" + username + '\'' +
                ", class_name='" + class_name + '\'' +
                ", method_name='" + method_name + '\'' +
                ", args='" + args + '\'' +
                ", creat_time=" + creat_time +
                ", ip='" + ip + '\'' +
                '}';
    }
}
