package com.tjm.pojo;

import com.tjm.config.ExcelColumn;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class OperationLog {
    @ExcelColumn(value = "oper_id", col = 1)
    int oper_id;
    @ExcelColumn(value = "oper_modul", col = 2)
    String oper_modul;
    @ExcelColumn(value = "oper_type",col = 3)
    String oper_type;
    @ExcelColumn(value = "oper_desc",col = 4)
    String oper_desc;
    @ExcelColumn(value = "username",col = 5)
    String username;
    @ExcelColumn(value = "oper_creat_time",col = 6)
    Timestamp oper_creat_time;

    public int getOper_id() {
        return oper_id;
    }

    public void setOper_id(int oper_id) {
        this.oper_id = oper_id;
    }

    public String getOper_modul() {
        return oper_modul;
    }

    public void setOper_modul(String oper_modul) {
        this.oper_modul = oper_modul;
    }

    public String getOper_type() {
        return oper_type;
    }

    public void setOper_type(String oper_type) {
        this.oper_type = oper_type;
    }

    public String getOper_desc() {
        return oper_desc;
    }

    public void setOper_desc(String oper_desc) {
        this.oper_desc = oper_desc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getOper_creat_time() {
        return oper_creat_time;
    }

    public void setOper_creat_time(Timestamp oper_creat_time) {
        this.oper_creat_time = oper_creat_time;
    }

    @Override
    public String toString() {
        return "OperationLog{" +
                "oper_id=" + oper_id +
                ", oper_modul='" + oper_modul + '\'' +
                ", oper_type='" + oper_type + '\'' +
                ", oper_desc='" + oper_desc + '\'' +
                ", username='" + username + '\'' +
                ", oper_creat_time=" + oper_creat_time +
                '}';
    }
}
