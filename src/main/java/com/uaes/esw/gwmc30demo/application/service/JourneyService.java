package com.uaes.esw.gwmc30demo.application.service;

import com.uaes.esw.gwmc30demo.domain.model.journey.Journey;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.Vehicle;

import static com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository.getVehicleSnapshot;
import static com.uaes.esw.gwmc30demo.domain.service.ChargingDomainService.calChargeTimeByChargerType4Journey;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromJSON2Object;

public interface JourneyService {

    //得到规划的里程
    static Journey getJourney(String journeyString){
        Journey journey = transferFromJSON2Object(journeyString,Journey.class);
        return journey;
    }

    //返回该车的规划里程的充电时间
    static String chargingOnDemandByJourney(String vinCode, String journeyString){
        Journey journey = transferFromJSON2Object(journeyString,Journey.class);
        Vehicle c30VehicleSnapshot = getVehicleSnapshot(vinCode);
        System.out.println("soc="+c30VehicleSnapshot.getBattery().getSoc());
        return calChargeTimeByChargerType4Journey(journey, c30VehicleSnapshot);
    }
}
