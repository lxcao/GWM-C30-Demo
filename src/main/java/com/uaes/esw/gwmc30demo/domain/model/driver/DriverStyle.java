package com.uaes.esw.gwmc30demo.domain.model.driver;

import com.uaes.esw.gwmc30demo.domain.model.vehicle.DrivingMode;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class DriverStyle {
    String defaultDrivingMode;
    String currentDrivingMode;
}
