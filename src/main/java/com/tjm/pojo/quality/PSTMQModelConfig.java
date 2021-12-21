package com.tjm.pojo.quality;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Deprecated
public class PSTMQModelConfig implements Serializable {

    Integer uid;

    boolean function_completeness;
    boolean standard_compliance;

    boolean demand_density;
    boolean function_coverage;

    boolean calculation_accuracy;
    boolean precision;

    boolean data_exchange;
    boolean interface_consistency;

}
