package com.tjm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    int project_id;
    String software_uid;
    String software_name;
    String project_name;
    String point_names;
    String point_ids;
    String technical_requirements;
}
