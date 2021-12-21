package com.tjm.pojo.quality;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CodeParseInfo implements Serializable {
    Integer uid;

    Integer codeLine;
    Integer commentLine;
    Integer validCodeLine;
    Integer maxFanIn;
    Integer maxFanOut;
}
