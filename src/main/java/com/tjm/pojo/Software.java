package com.tjm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Software {

    String software_uid;
    String software_name;
    String version;
    String architecture;
    String database;
    String middleware;
    String other;
    String developer;
    String comment;
    int uid;
}
