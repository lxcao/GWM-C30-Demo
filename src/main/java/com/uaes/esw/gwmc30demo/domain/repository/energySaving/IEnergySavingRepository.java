package com.uaes.esw.gwmc30demo.domain.repository.energySaving;

import com.uaes.esw.gwmc30demo.constant.EnergySavingConstants;
import com.uaes.esw.gwmc30demo.domain.model.entity.can.*;
import com.uaes.esw.gwmc30demo.domain.model.scenario.energySaving.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.uaes.esw.gwmc30demo.constant.CommonConstants.RESPONSE_CODE_SUCCESS;
import static com.uaes.esw.gwmc30demo.constant.DrivingModeConstants.*;
import static com.uaes.esw.gwmc30demo.constant.DrivingModeConstants.DRIVING_MODE_ABB_CST;
import static com.uaes.esw.gwmc30demo.constant.DrivingModeConstants.DRIVING_MODE_ABB_POW;
import static com.uaes.esw.gwmc30demo.constant.EnergySavingConstants.*;
import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.*;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromJSON2Object;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromObject2JSON;
import static com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler.*;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils.*;

public interface IEnergySavingRepository {

    static VCU61CanMessage getLastVCU61MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_61_ZSET),
                VCU61CanMessage.class);
    }

    static VCU62CanMessage getLastVCU62MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_62_ZSET),
                VCU62CanMessage.class);
    }

    static VCU63CanMessage getLastVCU63MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_63_ZSET),
                VCU63CanMessage.class);
    }

    static VCU64CanMessage getLastVCU64MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_64_ZSET),
                VCU64CanMessage.class);
    }

    static VCU65CanMessage getLastVCU65MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_65_ZSET),
                VCU65CanMessage.class);
    }

    static VCU66CanMessage getLastVCU66MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_66_ZSET),
                VCU66CanMessage.class);
    }

    static VCU67CanMessage getLastVCU67MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_67_ZSET),
                VCU67CanMessage.class);
    }

    static VCU68CanMessage getLastVCU68MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_68_ZSET),
                VCU68CanMessage.class);
    }

    static VCU69CanMessage getLastVCU69MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_69_ZSET),
                VCU69CanMessage.class);
    }

    static VCU70CanMessage getLastVCU70MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_70_ZSET),
                VCU70CanMessage.class);
    }

    static VCU71CanMessage getLastVCU71MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_71_ZSET),
                VCU71CanMessage.class);
    }

    static VCU72CanMessage getLastVCU72MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_72_ZSET),
                VCU72CanMessage.class);
    }

    static VCU73CanMessage getLastVCU73MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_73_ZSET),
                VCU73CanMessage.class);
    }

    static VCU73CanMessage getPreviousVCU73MessageFromRedis(){
        return transferFromJSON2Object(getPreviousOneStringFromZset(REDIS_VCU_73_ZSET),
                VCU73CanMessage.class);
    }

    static VCU75CanMessage getLastVCU75MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_75_ZSET),
                VCU75CanMessage.class);
    }

    static VCU76CanMessage getLastVCU76MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_76_ZSET),
                VCU76CanMessage.class);
    }

    static ESRemind getRemind(){
        VCU73CanMessage vcu73CanMessage = getLastVCU73MessageFromRedis();
        ESRemind esRemind = ESRemind.builder()
                .motorEfficiency(vcu73CanMessage.getMotor_Efficiency())
                .ACOnOff(vcu73CanMessage.getAC_On_Off_Remind())
                .driveStatus(vcu73CanMessage.getFierce_Drive_Status()).build();
        return esRemind;
    }

    static int getHVPowerOnStatusNow(){
        VCU73CanMessage vcu73CanMessage = getLastVCU73MessageFromRedis();
        return vcu73CanMessage.getHV_PowerOn();
    }

    static int getHVPowerOnStatusPrevious(){
        VCU73CanMessage vcu73CanMessage = getPreviousVCU73MessageFromRedis();
        return vcu73CanMessage.getHV_PowerOn();
    }

    static double calEnergyKWH(VCU61CanMessage vcu61CanMessage){
        return vcu61CanMessage.getHVBatt_Energy_Sum_Cyc()/ELECTRICITY_SAVING_K;
    }

    static double calEnergyKWHPer100KM(VCU61CanMessage vcu61CanMessage, VCU62CanMessage vcu62CanMessage){
        return calEnergyKWH(vcu61CanMessage)*ELECTRICITY_SAVING_100KM/vcu62CanMessage.getMileage_Cyc();
    }

    static double calCSTEnergyKWH(VCU75CanMessage vcu75CanMessage){
        return vcu75CanMessage.getHVBatt_Energy_Custom_Cyc()/ELECTRICITY_SAVING_K;
    }

    static double calCSTEnergyKWHPer100KM(VCU75CanMessage vcu75CanMessage, VCU76CanMessage vcu76CanMessage){
        return calCSTEnergyKWH(vcu75CanMessage)*ELECTRICITY_SAVING_100KM/vcu76CanMessage.getMileage_Custom_Cyc();
    }

    static double calNOREnergyKWH(VCU67CanMessage vcu67CanMessage){
        return vcu67CanMessage.getHVBatt_Energy_Norm_Cyc()/ELECTRICITY_SAVING_K;
    }

    static double calNOREnergyKWHPer100KM(VCU67CanMessage vcu67CanMessage, VCU68CanMessage vcu68CanMessage){
        return calNOREnergyKWH(vcu67CanMessage)*ELECTRICITY_SAVING_100KM/vcu68CanMessage.getMileage_Norm_Cyc();
    }

    static double calPOWEnergyKWH(VCU69CanMessage vcu69CanMessage){
        return vcu69CanMessage.getHVBatt_Energy_Sport_Cyc()/ELECTRICITY_SAVING_K;
    }

    static double calPOWEnergyKWHPer100KM(VCU69CanMessage vcu69CanMessage, VCU70CanMessage vcu70CanMessage){
        return calPOWEnergyKWH(vcu69CanMessage)*ELECTRICITY_SAVING_100KM/vcu70CanMessage.getMileage_Sport_Cyc();
    }

    static double calEPMEnergyKWH(VCU71CanMessage vcu71CanMessage){
        return vcu71CanMessage.getHVBatt_Energy_UltraSport_Cyc()/ELECTRICITY_SAVING_K;
    }

    static double calEPMEnergyKWHPer100KM(VCU71CanMessage vcu71CanMessage, VCU72CanMessage vcu72CanMessage){
        return calEPMEnergyKWH(vcu71CanMessage)*ELECTRICITY_SAVING_100KM/vcu72CanMessage.getMileage_UltraSport_Cyc();
    }

    static double calECOEnergyKWH(VCU65CanMessage vcu65CanMessage){
        return vcu65CanMessage.getHVBatt_Energy_Eco_Cyc()/ELECTRICITY_SAVING_K;
    }

    static double calECOEnergyKWHPer100KM(VCU65CanMessage vcu65CanMessage, VCU66CanMessage vcu66CanMessage){
        return calECOEnergyKWH(vcu65CanMessage)*ELECTRICITY_SAVING_100KM/vcu66CanMessage.getMileage_Eco_Cyc();
    }

    static double calEEMEnergyKWH(VCU63CanMessage vcu63CanMessage){
        return vcu63CanMessage.getHVBatt_Energy_UltraEco_Cyc()/ELECTRICITY_SAVING_K;
    }

    static double calEEMEnergyKWHPer100KM(VCU63CanMessage vcu63CanMessage, VCU64CanMessage vcu64CanMessage){
        return calEEMEnergyKWH(vcu63CanMessage)*ELECTRICITY_SAVING_100KM/vcu64CanMessage.getMileage_UltraEco_Cyc();
    }

    static double calEnergyCost(VCU61CanMessage vcu61CanMessage){
        return calEnergyKWH(vcu61CanMessage)*ELECTRICITY_SAVING_FEE_RMBYUAN_PER_KWH;
    }

    static double calCompACEnergyPert(VCU61CanMessage vcu61CanMessage, VCU62CanMessage vcu62CanMessage){
        return (vcu61CanMessage.getPTC_Energy_Sum_Cyc()+vcu62CanMessage.getAC_Energy_Sum_Cyc())
                /vcu61CanMessage.getHVBatt_Energy_Sum_Cyc();
    }
    static double calCompDCDCEnergyPert(VCU61CanMessage vcu61CanMessage){
        return vcu61CanMessage.getDCDC_Energy_Sum_Cyc()/vcu61CanMessage.getHVBatt_Energy_Sum_Cyc();
    }

    static double calCompMTEnergyPert(VCU61CanMessage vcu61CanMessage){
        return vcu61CanMessage.getMotor_Energy_Sum_Cyc()/vcu61CanMessage.getHVBatt_Energy_Sum_Cyc();
    }

    static double calCompBRKEnergyPert(VCU61CanMessage vcu61CanMessage, VCU62CanMessage vcu62CanMessage){
        return vcu62CanMessage.getFriction_Brk_Energy_Sum_Cyc()/vcu61CanMessage.getHVBatt_Energy_Sum_Cyc();
    }

    static double calCompRESTEnergyPert(VCU61CanMessage vcu61CanMessage, VCU62CanMessage vcu62CanMessage){
        return ELECTRICITY_SAVING_ONE - calCompACEnergyPert(vcu61CanMessage,vcu62CanMessage)
                - calCompDCDCEnergyPert(vcu61CanMessage) - calCompMTEnergyPert(vcu61CanMessage);
    }

    static double calEEMACEnergyPert(VCU63CanMessage vcu63CanMessage, VCU64CanMessage vcu64CanMessage){
        return (vcu63CanMessage.getPTC_Energy_UltraEco_Cyc()+vcu64CanMessage.getAC_Energy_UltraEco_Cyc())
                /vcu63CanMessage.getHVBatt_Energy_UltraEco_Cyc();
    }
    static double calEEMDCDCEnergyPert(VCU63CanMessage vcu63CanMessage){
        return vcu63CanMessage.getDCDC_Energy_UltraEco_Cyc()/vcu63CanMessage.getHVBatt_Energy_UltraEco_Cyc();
    }

    static double calEEMMTEnergyPert(VCU63CanMessage vcu63CanMessage){
        return vcu63CanMessage.getMotor_Energy_UltraEco_Cyc()/vcu63CanMessage.getHVBatt_Energy_UltraEco_Cyc();
    }

    static double calEEMRESTEnergyPert(VCU63CanMessage vcu63CanMessage, VCU64CanMessage vcu64CanMessage){
        return ELECTRICITY_SAVING_ONE - calEEMACEnergyPert(vcu63CanMessage,vcu64CanMessage)
                - calEEMDCDCEnergyPert(vcu63CanMessage) - calEEMMTEnergyPert(vcu63CanMessage);
    }

    static double calECOACEnergyPert(VCU65CanMessage vcu65CanMessage, VCU66CanMessage vcu66CanMessage){
        return (vcu65CanMessage.getPTC_Energy_Eco_Cyc()+vcu66CanMessage.getAC_Energy_Eco_Cyc())
                /vcu65CanMessage.getHVBatt_Energy_Eco_Cyc();
    }
    static double calECODCDCEnergyPert(VCU65CanMessage vcu65CanMessage){
        return vcu65CanMessage.getDCDC_Energy_Eco_Cyc()/vcu65CanMessage.getHVBatt_Energy_Eco_Cyc();
    }

    static double calECOMTEnergyPert(VCU65CanMessage vcu65CanMessage){
        return vcu65CanMessage.getMotor_Energy_Eco_Cyc()/vcu65CanMessage.getHVBatt_Energy_Eco_Cyc();
    }

    static double calECORESTEnergyPert(VCU65CanMessage vcu65CanMessage, VCU66CanMessage vcu66CanMessage){
        return ELECTRICITY_SAVING_ONE - calECOACEnergyPert(vcu65CanMessage,vcu66CanMessage)
                - calECODCDCEnergyPert(vcu65CanMessage) - calECOMTEnergyPert(vcu65CanMessage);
    }

    static double calNORACEnergyPert(VCU67CanMessage vcu67CanMessage, VCU68CanMessage vcu68CanMessage){
        return (vcu67CanMessage.getPTC_Energy_Norm_Cyc()+vcu68CanMessage.getAC_Energy_Norm_Cyc())
                /vcu67CanMessage.getHVBatt_Energy_Norm_Cyc();
    }
    static double calNORDCDCEnergyPert(VCU67CanMessage vcu67CanMessage){
        return vcu67CanMessage.getDCDC_Energy_Norm_Cyc()/vcu67CanMessage.getHVBatt_Energy_Norm_Cyc();
    }

    static double calNORMTEnergyPert(VCU67CanMessage vcu67CanMessage){
        return vcu67CanMessage.getMotor_Energy_Norm_Cyc()/vcu67CanMessage.getHVBatt_Energy_Norm_Cyc();
    }

    static double calNORRESTEnergyPert(VCU67CanMessage vcu67CanMessage, VCU68CanMessage vcu68CanMessage){
        return ELECTRICITY_SAVING_ONE - calNORACEnergyPert(vcu67CanMessage,vcu68CanMessage)
                - calNORDCDCEnergyPert(vcu67CanMessage) - calNORMTEnergyPert(vcu67CanMessage);
    }

    static double calEPMACEnergyPert(VCU71CanMessage vcu71CanMessage, VCU72CanMessage vcu72CanMessage){
        return (vcu71CanMessage.getPTC_Energy_UltraSport_Cyc()+vcu72CanMessage.getAC_Energy_UltraSport_Cyc())
                /vcu71CanMessage.getHVBatt_Energy_UltraSport_Cyc();
    }
    static double calEPMDCDCEnergyPert(VCU71CanMessage vcu71CanMessage){
        return vcu71CanMessage.getDCDC_Energy_UltraSport_Cyc()/vcu71CanMessage.getHVBatt_Energy_UltraSport_Cyc();
    }

    static double calEPMMTEnergyPert(VCU71CanMessage vcu71CanMessage){
        return vcu71CanMessage.getMotor_Energy_UltraSport_Cyc()/vcu71CanMessage.getHVBatt_Energy_UltraSport_Cyc();
    }

    static double calEPMRESTEnergyPert(VCU71CanMessage vcu71CanMessage, VCU72CanMessage vcu72CanMessage){
        return ELECTRICITY_SAVING_ONE - calEPMACEnergyPert(vcu71CanMessage,vcu72CanMessage)
                - calEPMDCDCEnergyPert(vcu71CanMessage) - calEPMMTEnergyPert(vcu71CanMessage);
    }

    static double calPOWACEnergyPert(VCU69CanMessage vcu69CanMessage, VCU70CanMessage vcu70CanMessage){
        return (vcu69CanMessage.getPTC_Energy_Sport_Cyc()+vcu70CanMessage.getAC_Energy_Sport_Cyc())
                /vcu69CanMessage.getHVBatt_Energy_Sport_Cyc();
    }
    static double calPOWDCDCEnergyPert(VCU69CanMessage vcu69CanMessage){
        return vcu69CanMessage.getDCDC_Energy_Sport_Cyc()/vcu69CanMessage.getHVBatt_Energy_Sport_Cyc();
    }

    static double calPOWMTEnergyPert(VCU69CanMessage vcu69CanMessage){
        return vcu69CanMessage.getMotor_Energy_Sport_Cyc()/vcu69CanMessage.getHVBatt_Energy_Sport_Cyc();
    }

    static double calPOWRESTEnergyPert(VCU69CanMessage vcu69CanMessage, VCU70CanMessage vcu70CanMessage){
        return ELECTRICITY_SAVING_ONE - calPOWACEnergyPert(vcu69CanMessage,vcu70CanMessage)
                - calPOWDCDCEnergyPert(vcu69CanMessage) - calPOWMTEnergyPert(vcu69CanMessage);
    }

    static double calCSTACEnergyPert(VCU75CanMessage vcu75CanMessage, VCU76CanMessage vcu76CanMessage){
        return (vcu75CanMessage.getPTC_Energy_Custom_Cyc()+vcu76CanMessage.getAC_Energy_Custom_Cyc())
                /vcu75CanMessage.getHVBatt_Energy_Custom_Cyc();
    }
    static double calCSTDCDCEnergyPert(VCU75CanMessage vcu75CanMessage){
        return vcu75CanMessage.getDCDC_Energy_Custom_Cyc()/vcu75CanMessage.getHVBatt_Energy_Custom_Cyc();
    }

    static double calCSTMTEnergyPert(VCU75CanMessage vcu75CanMessage){
        return vcu75CanMessage.getMotor_Energy_Custom_Cyc()/vcu75CanMessage.getHVBatt_Energy_Custom_Cyc();
    }

    static double calCSTRESTEnergyPert(VCU75CanMessage vcu75CanMessage, VCU76CanMessage vcu76CanMessage){
        return ELECTRICITY_SAVING_ONE - calCSTACEnergyPert(vcu75CanMessage,vcu76CanMessage)
                - calCSTDCDCEnergyPert(vcu75CanMessage) - calCSTMTEnergyPert(vcu75CanMessage);
    }

    static QueryESRes getCurrentES(QueryESReq esReq){
        System.out.println("esReq="+esReq);
        VCU61CanMessage vcu61CanMessage = getLastVCU61MessageFromRedis();
        System.out.println("vcu61CanMessage="+vcu61CanMessage);
        VCU62CanMessage vcu62CanMessage = getLastVCU62MessageFromRedis();
        System.out.println("vcu62CanMessage="+vcu62CanMessage);
        ESSummary esSummary = ESSummary.builder()
                .energyPer100KM(calEnergyKWHPer100KM(vcu61CanMessage,vcu62CanMessage))
                .sumEnergy(calEnergyKWH(vcu61CanMessage))
                .sumEnergyCost(calEnergyCost(vcu61CanMessage))
                .build();
        System.out.println("esSummary="+esSummary.toString());
        ComponentsPercentSum componentsPercentSum = ComponentsPercentSum.builder()
                .RESTEnergyPert(calCompRESTEnergyPert(vcu61CanMessage,vcu62CanMessage))
                .ACEnergyPert(calCompACEnergyPert(vcu61CanMessage,vcu62CanMessage))
                .DCDCEngergyPert(calCompDCDCEnergyPert(vcu61CanMessage))
                .MTEnergyPert(calCompMTEnergyPert(vcu61CanMessage))
                .BRKEnergyPert(calCompBRKEnergyPert(vcu61CanMessage,vcu62CanMessage))
                .build();
        System.out.println("componentsPercentSum="+componentsPercentSum.toString());
        VCU63CanMessage vcu63CanMessage = getLastVCU63MessageFromRedis();
        System.out.println("vcu63CanMessage="+vcu63CanMessage);
        VCU64CanMessage vcu64CanMessage = getLastVCU64MessageFromRedis();
        System.out.println("vcu64CanMessage="+vcu64CanMessage);
        VCU65CanMessage vcu65CanMessage = getLastVCU65MessageFromRedis();
        System.out.println("vcu65CanMessage="+vcu65CanMessage);
        VCU66CanMessage vcu66CanMessage = getLastVCU66MessageFromRedis();
        System.out.println("vcu66CanMessage="+vcu66CanMessage);
        VCU67CanMessage vcu67CanMessage = getLastVCU67MessageFromRedis();
        System.out.println("vcu67CanMessage="+vcu67CanMessage);
        VCU68CanMessage vcu68CanMessage = getLastVCU68MessageFromRedis();
        System.out.println("vcu68CanMessage="+vcu68CanMessage);
        VCU69CanMessage vcu69CanMessage = getLastVCU69MessageFromRedis();
        System.out.println("vcu69CanMessage="+vcu69CanMessage);
        VCU70CanMessage vcu70CanMessage = getLastVCU70MessageFromRedis();
        System.out.println("vcu70CanMessage="+vcu70CanMessage);
        VCU71CanMessage vcu71CanMessage = getLastVCU71MessageFromRedis();
        System.out.println("vcu71CanMessage="+vcu71CanMessage);
        VCU72CanMessage vcu72CanMessage = getLastVCU72MessageFromRedis();
        System.out.println("vcu72CanMessage="+vcu72CanMessage);
        //TODO: need to get from redis
/*        VCU75CanMessage vcu75CanMessage = getLastVCU75MessageFromRedis();
        System.out.println("vcu75CanMessage="+vcu75CanMessage);
        VCU76CanMessage vcu76CanMessage = getLastVCU76MessageFromRedis();
        System.out.println("vcu76CanMessage="+vcu76CanMessage);*/
        VCU75CanMessage vcu75CanMessage = VCU75CanMessage.builder()
                .DCDC_Energy_Custom_Cyc(0.0).HVBatt_Energy_Custom_Cyc(0.0).Motor_Energy_Custom_Cyc(0.0)
                .PTC_Energy_Custom_Cyc(0.0).build();
        System.out.println("vcu75CanMessage="+vcu75CanMessage);
        VCU76CanMessage vcu76CanMessage = VCU76CanMessage.builder()
                .AC_Energy_Custom_Cyc(0.0).Mileage_Custom_Cyc(0.0).build();
        System.out.println("vcu76CanMessage="+vcu76CanMessage);
                Per100KMByDM per100KMByDM = Per100KMByDM.builder()
                .CustomerDM(calCSTEnergyKWHPer100KM(vcu75CanMessage,vcu76CanMessage))
                .EconomyDM(calECOEnergyKWHPer100KM(vcu65CanMessage,vcu66CanMessage))
                .NormalDM(calNOREnergyKWHPer100KM(vcu67CanMessage,vcu68CanMessage))
                .PowerDM(calPOWEnergyKWHPer100KM(vcu69CanMessage,vcu70CanMessage))
                .EPowerDM(calEPMEnergyKWHPer100KM(vcu71CanMessage,vcu72CanMessage))
                .EEconomyDM(calEEMEnergyKWHPer100KM(vcu63CanMessage,vcu64CanMessage))
                .build();
        System.out.println("per100KMByDM="+per100KMByDM.toString());
        Map<String,ComponentsPercent> cpByDM = new HashMap<>();
        ComponentsPercent componentsPercentEEM = ComponentsPercent.builder()
                .RESTEnergyPert(calEEMRESTEnergyPert(vcu63CanMessage,vcu64CanMessage))
                .ACEnergyPert(calEEMACEnergyPert(vcu63CanMessage,vcu64CanMessage))
                .DCDCEngergyPert(calEEMDCDCEnergyPert(vcu63CanMessage))
                .MTEnergyPert(calEEMMTEnergyPert(vcu63CanMessage))
                .build();
        System.out.println("componentsPercentEEM="+componentsPercentEEM.toString());
        cpByDM.put(DRIVING_MODE_ABB_EEM,componentsPercentEEM);
        ComponentsPercent componentsPercentECO = ComponentsPercent.builder()
                .RESTEnergyPert(calECORESTEnergyPert(vcu65CanMessage,vcu66CanMessage))
                .ACEnergyPert(calECOACEnergyPert(vcu65CanMessage,vcu66CanMessage))
                .DCDCEngergyPert(calECODCDCEnergyPert(vcu65CanMessage))
                .MTEnergyPert(calECOMTEnergyPert(vcu65CanMessage))
                .build();
        System.out.println("componentsPercentECO="+componentsPercentECO);
        cpByDM.put(DRIVING_MODE_ABB_ECO,componentsPercentECO);
        ComponentsPercent componentsPercentNOR = ComponentsPercent.builder()
                .RESTEnergyPert(calNORRESTEnergyPert(vcu67CanMessage,vcu68CanMessage))
                .ACEnergyPert(calNORACEnergyPert(vcu67CanMessage,vcu68CanMessage))
                .DCDCEngergyPert(calNORDCDCEnergyPert(vcu67CanMessage))
                .MTEnergyPert(calNORMTEnergyPert(vcu67CanMessage))
                .build();
        System.out.println("componentsPercentNOR="+componentsPercentNOR);
        cpByDM.put(DRIVING_MODE_ABB_NOR,componentsPercentNOR);
        ComponentsPercent componentsPercentEPM = ComponentsPercent.builder()
                .RESTEnergyPert(calEPMRESTEnergyPert(vcu71CanMessage,vcu72CanMessage))
                .ACEnergyPert(calEPMACEnergyPert(vcu71CanMessage,vcu72CanMessage))
                .DCDCEngergyPert(calEPMDCDCEnergyPert(vcu71CanMessage))
                .MTEnergyPert(calEPMMTEnergyPert(vcu71CanMessage))
                .build();
        System.out.println("componentsPercentEPM="+componentsPercentEPM);
        cpByDM.put(DRIVING_MODE_ABB_EPM,componentsPercentEPM);
        ComponentsPercent componentsPercentPOW = ComponentsPercent.builder()
                .RESTEnergyPert(calPOWRESTEnergyPert(vcu69CanMessage,vcu70CanMessage))
                .ACEnergyPert(calPOWACEnergyPert(vcu69CanMessage,vcu70CanMessage))
                .DCDCEngergyPert(calPOWDCDCEnergyPert(vcu69CanMessage))
                .MTEnergyPert(calPOWMTEnergyPert(vcu69CanMessage))
                .build();
        System.out.println("componentsPercentPOW="+componentsPercentPOW);
        cpByDM.put(DRIVING_MODE_ABB_POW,componentsPercentPOW);
        ComponentsPercent componentsPercentCST = ComponentsPercent.builder()
                .RESTEnergyPert(calCSTRESTEnergyPert(vcu75CanMessage,vcu76CanMessage))
                .ACEnergyPert(calCSTACEnergyPert(vcu75CanMessage,vcu76CanMessage))
                .DCDCEngergyPert(calCSTDCDCEnergyPert(vcu75CanMessage))
                .MTEnergyPert(calCSTMTEnergyPert(vcu75CanMessage))
                .build();
        System.out.println("componentsPercentCST="+componentsPercentCST);
        cpByDM.put(DRIVING_MODE_ABB_CST,componentsPercentCST);
        QueryESRes queryESRes = QueryESRes.builder()
                .driver(esReq.getDriver())
                .dateTime(esReq.getDateTime())
                .esSummary(esSummary)
                .componentsPercentSum(componentsPercentSum)
                .per100KMByDM(per100KMByDM)
                .CPByDM(cpByDM)
                .responseCode(RESPONSE_CODE_SUCCESS)
                .build();
        System.out.println("queryESRes="+queryESRes);
        return queryESRes;
    }

    static QueryESRes getTodayCycleES(QueryESReq esReq){
        Set<String> energySavingTodaySet = zRangeByScore(REDIS_ENERGY_SAVING_DRIVING_CYCLE_ZSET,
                getTodayBeginUnixTime(),getTodayEndUnixTime());
        return getCycleESByPeriod(esReq, energySavingTodaySet);
    }

    static QueryESRes getWeeklyCycleES(QueryESReq esReq){
        Set<String> energySavingTodaySet = zRangeByScore(REDIS_ENERGY_SAVING_DRIVING_CYCLE_ZSET,
                getBeforeOneWeekBeginUnixTime(),getTodayEndUnixTime());
        return getCycleESByPeriod(esReq, energySavingTodaySet);
    }

    static QueryESRes getCustomerCycleES(QueryESReq esReq){
        Set<String> energySavingTodaySet = zRangeByScore(REDIS_ENERGY_SAVING_DRIVING_CYCLE_ZSET,
                transfer2UnixTime(esReq.getStartDateTime()),transfer2UnixTime(esReq.getEndDateTime()));
        return getCycleESByPeriod(esReq, energySavingTodaySet);
    }


    static QueryESRes getCycleESByPeriod(QueryESReq esReq, Set<String> energySavingSet){
        System.out.println("esReq="+esReq);
        EnergySavingCanMessage energySavingCanMessageAll = EnergySavingCanMessage.builder().build();
        energySavingSet.forEach(est -> {
            EnergySavingCanMessage energySavingCanMessage = transferFromJSON2Object(
                    est,EnergySavingCanMessage.class);
            energySavingCanMessageAll.setVcu61CanMessage(energySavingCanMessageAll
                    .getVcu61CanMessage().adding(energySavingCanMessage.getVcu61CanMessage()));
            energySavingCanMessageAll.setVcu62CanMessage(energySavingCanMessageAll
                    .getVcu62CanMessage().adding(energySavingCanMessage.getVcu62CanMessage()));
            energySavingCanMessageAll.setVcu63CanMessage(energySavingCanMessageAll
                    .getVcu63CanMessage().adding(energySavingCanMessage.getVcu63CanMessage()));
            energySavingCanMessageAll.setVcu64CanMessage(energySavingCanMessageAll
                    .getVcu64CanMessage().adding(energySavingCanMessage.getVcu64CanMessage()));
            energySavingCanMessageAll.setVcu65CanMessage(energySavingCanMessageAll
                    .getVcu65CanMessage().adding(energySavingCanMessage.getVcu65CanMessage()));
            energySavingCanMessageAll.setVcu66CanMessage(energySavingCanMessageAll
                    .getVcu66CanMessage().adding(energySavingCanMessage.getVcu66CanMessage()));
            energySavingCanMessageAll.setVcu67CanMessage(energySavingCanMessageAll
                    .getVcu67CanMessage().adding(energySavingCanMessage.getVcu67CanMessage()));
            energySavingCanMessageAll.setVcu68CanMessage(energySavingCanMessageAll
                    .getVcu68CanMessage().adding(energySavingCanMessage.getVcu68CanMessage()));
            energySavingCanMessageAll.setVcu69CanMessage(energySavingCanMessageAll
                    .getVcu69CanMessage().adding(energySavingCanMessage.getVcu69CanMessage()));
            energySavingCanMessageAll.setVcu70CanMessage(energySavingCanMessageAll
                    .getVcu70CanMessage().adding(energySavingCanMessage.getVcu70CanMessage()));
            energySavingCanMessageAll.setVcu71CanMessage(energySavingCanMessageAll
                    .getVcu71CanMessage().adding(energySavingCanMessage.getVcu71CanMessage()));
            energySavingCanMessageAll.setVcu72CanMessage(energySavingCanMessageAll
                    .getVcu72CanMessage().adding(energySavingCanMessage.getVcu72CanMessage()));
            energySavingCanMessageAll.setVcu75CanMessage(energySavingCanMessageAll
                    .getVcu75CanMessage().adding(energySavingCanMessage.getVcu75CanMessage()));
            energySavingCanMessageAll.setVcu76CanMessage(energySavingCanMessageAll
                    .getVcu76CanMessage().adding(energySavingCanMessage.getVcu76CanMessage()));
        });


        VCU61CanMessage vcu61CanMessage = energySavingCanMessageAll.getVcu61CanMessage();
        System.out.println("vcu61CanMessage="+vcu61CanMessage);
        VCU62CanMessage vcu62CanMessage = energySavingCanMessageAll.getVcu62CanMessage();
        System.out.println("vcu62CanMessage="+vcu62CanMessage);
        ESSummary esSummary = ESSummary.builder()
                .energyPer100KM(calEnergyKWHPer100KM(vcu61CanMessage,vcu62CanMessage))
                .sumEnergy(calEnergyKWH(vcu61CanMessage))
                .sumEnergyCost(calEnergyCost(vcu61CanMessage))
                .build();
        System.out.println("esSummary="+esSummary.toString());
        ComponentsPercentSum componentsPercentSum = ComponentsPercentSum.builder()
                .RESTEnergyPert(calCompRESTEnergyPert(vcu61CanMessage,vcu62CanMessage))
                .ACEnergyPert(calCompACEnergyPert(vcu61CanMessage,vcu62CanMessage))
                .DCDCEngergyPert(calCompDCDCEnergyPert(vcu61CanMessage))
                .MTEnergyPert(calCompMTEnergyPert(vcu61CanMessage))
                .BRKEnergyPert(calCompBRKEnergyPert(vcu61CanMessage,vcu62CanMessage))
                .build();
        System.out.println("componentsPercentSum="+componentsPercentSum.toString());
        VCU63CanMessage vcu63CanMessage = energySavingCanMessageAll.getVcu63CanMessage();
        System.out.println("vcu63CanMessage="+vcu63CanMessage);
        VCU64CanMessage vcu64CanMessage = energySavingCanMessageAll.getVcu64CanMessage();
        System.out.println("vcu64CanMessage="+vcu64CanMessage);
        VCU65CanMessage vcu65CanMessage = energySavingCanMessageAll.getVcu65CanMessage();
        System.out.println("vcu65CanMessage="+vcu65CanMessage);
        VCU66CanMessage vcu66CanMessage = energySavingCanMessageAll.getVcu66CanMessage();
        System.out.println("vcu66CanMessage="+vcu66CanMessage);
        VCU67CanMessage vcu67CanMessage = energySavingCanMessageAll.getVcu67CanMessage();
        System.out.println("vcu67CanMessage="+vcu67CanMessage);
        VCU68CanMessage vcu68CanMessage = energySavingCanMessageAll.getVcu68CanMessage();
        System.out.println("vcu68CanMessage="+vcu68CanMessage);
        VCU69CanMessage vcu69CanMessage = energySavingCanMessageAll.getVcu69CanMessage();
        System.out.println("vcu69CanMessage="+vcu69CanMessage);
        VCU70CanMessage vcu70CanMessage = energySavingCanMessageAll.getVcu70CanMessage();
        System.out.println("vcu70CanMessage="+vcu70CanMessage);
        VCU71CanMessage vcu71CanMessage = energySavingCanMessageAll.getVcu71CanMessage();
        System.out.println("vcu71CanMessage="+vcu71CanMessage);
        VCU72CanMessage vcu72CanMessage = energySavingCanMessageAll.getVcu72CanMessage();
        System.out.println("vcu72CanMessage="+vcu72CanMessage);
        VCU75CanMessage vcu75CanMessage = energySavingCanMessageAll.getVcu75CanMessage();
        System.out.println("vcu75CanMessage="+vcu75CanMessage);
        VCU76CanMessage vcu76CanMessage = energySavingCanMessageAll.getVcu76CanMessage();
        System.out.println("vcu76CanMessage="+vcu76CanMessage);
        Per100KMByDM per100KMByDM = Per100KMByDM.builder()
                .CustomerDM(calCSTEnergyKWHPer100KM(vcu75CanMessage,vcu76CanMessage))
                .EconomyDM(calECOEnergyKWHPer100KM(vcu65CanMessage,vcu66CanMessage))
                .NormalDM(calNOREnergyKWHPer100KM(vcu67CanMessage,vcu68CanMessage))
                .PowerDM(calPOWEnergyKWHPer100KM(vcu69CanMessage,vcu70CanMessage))
                .EPowerDM(calEPMEnergyKWHPer100KM(vcu71CanMessage,vcu72CanMessage))
                .EEconomyDM(calEEMEnergyKWHPer100KM(vcu63CanMessage,vcu64CanMessage))
                .build();
        System.out.println("per100KMByDM="+per100KMByDM.toString());
        Map<String,ComponentsPercent> cpByDM = new HashMap<>();
        ComponentsPercent componentsPercentEEM = ComponentsPercent.builder()
                .RESTEnergyPert(calEEMRESTEnergyPert(vcu63CanMessage,vcu64CanMessage))
                .ACEnergyPert(calEEMACEnergyPert(vcu63CanMessage,vcu64CanMessage))
                .DCDCEngergyPert(calEEMDCDCEnergyPert(vcu63CanMessage))
                .MTEnergyPert(calEEMMTEnergyPert(vcu63CanMessage))
                .build();
        System.out.println("componentsPercentEEM="+componentsPercentEEM.toString());
        cpByDM.put(DRIVING_MODE_ABB_EEM,componentsPercentEEM);
        ComponentsPercent componentsPercentECO = ComponentsPercent.builder()
                .RESTEnergyPert(calECORESTEnergyPert(vcu65CanMessage,vcu66CanMessage))
                .ACEnergyPert(calECOACEnergyPert(vcu65CanMessage,vcu66CanMessage))
                .DCDCEngergyPert(calECODCDCEnergyPert(vcu65CanMessage))
                .MTEnergyPert(calECOMTEnergyPert(vcu65CanMessage))
                .build();
        System.out.println("componentsPercentECO="+componentsPercentECO);
        cpByDM.put(DRIVING_MODE_ABB_ECO,componentsPercentECO);
        ComponentsPercent componentsPercentNOR = ComponentsPercent.builder()
                .RESTEnergyPert(calNORRESTEnergyPert(vcu67CanMessage,vcu68CanMessage))
                .ACEnergyPert(calNORACEnergyPert(vcu67CanMessage,vcu68CanMessage))
                .DCDCEngergyPert(calNORDCDCEnergyPert(vcu67CanMessage))
                .MTEnergyPert(calNORMTEnergyPert(vcu67CanMessage))
                .build();
        System.out.println("componentsPercentNOR="+componentsPercentNOR);
        cpByDM.put(DRIVING_MODE_ABB_NOR,componentsPercentNOR);
        ComponentsPercent componentsPercentEPM = ComponentsPercent.builder()
                .RESTEnergyPert(calEPMRESTEnergyPert(vcu71CanMessage,vcu72CanMessage))
                .ACEnergyPert(calEPMACEnergyPert(vcu71CanMessage,vcu72CanMessage))
                .DCDCEngergyPert(calEPMDCDCEnergyPert(vcu71CanMessage))
                .MTEnergyPert(calEPMMTEnergyPert(vcu71CanMessage))
                .build();
        System.out.println("componentsPercentEPM="+componentsPercentEPM);
        cpByDM.put(DRIVING_MODE_ABB_EPM,componentsPercentEPM);
        ComponentsPercent componentsPercentPOW = ComponentsPercent.builder()
                .RESTEnergyPert(calPOWRESTEnergyPert(vcu69CanMessage,vcu70CanMessage))
                .ACEnergyPert(calPOWACEnergyPert(vcu69CanMessage,vcu70CanMessage))
                .DCDCEngergyPert(calPOWDCDCEnergyPert(vcu69CanMessage))
                .MTEnergyPert(calPOWMTEnergyPert(vcu69CanMessage))
                .build();
        System.out.println("componentsPercentPOW="+componentsPercentPOW);
        cpByDM.put(DRIVING_MODE_ABB_POW,componentsPercentPOW);
        ComponentsPercent componentsPercentCST = ComponentsPercent.builder()
                .RESTEnergyPert(calCSTRESTEnergyPert(vcu75CanMessage,vcu76CanMessage))
                .ACEnergyPert(calCSTACEnergyPert(vcu75CanMessage,vcu76CanMessage))
                .DCDCEngergyPert(calCSTDCDCEnergyPert(vcu75CanMessage))
                .MTEnergyPert(calCSTMTEnergyPert(vcu75CanMessage))
                .build();
        System.out.println("componentsPercentCST="+componentsPercentCST);
        cpByDM.put(DRIVING_MODE_ABB_CST,componentsPercentCST);
        QueryESRes queryESRes = QueryESRes.builder()
                .driver(esReq.getDriver())
                .dateTime(esReq.getDateTime())
                .esSummary(esSummary)
                .componentsPercentSum(componentsPercentSum)
                .per100KMByDM(per100KMByDM)
                .CPByDM(cpByDM)
                .responseCode(RESPONSE_CODE_SUCCESS)
                .build();
        System.out.println("queryESRes="+queryESRes);
        return queryESRes;
    }

    static QueryESRes getLastCycleES(QueryESReq esReq){
        System.out.println("esReq="+esReq);
        EnergySavingCanMessage energySavingCanMessage = transferFromJSON2Object(
                getLastOneStringFromZset(REDIS_ENERGY_SAVING_DRIVING_CYCLE_ZSET)
                ,EnergySavingCanMessage.class);
        VCU61CanMessage vcu61CanMessage = energySavingCanMessage.getVcu61CanMessage();
        System.out.println("vcu61CanMessage="+vcu61CanMessage);
        VCU62CanMessage vcu62CanMessage = energySavingCanMessage.getVcu62CanMessage();
        System.out.println("vcu62CanMessage="+vcu62CanMessage);
        ESSummary esSummary = ESSummary.builder()
                .energyPer100KM(calEnergyKWHPer100KM(vcu61CanMessage,vcu62CanMessage))
                .sumEnergy(calEnergyKWH(vcu61CanMessage))
                .sumEnergyCost(calEnergyCost(vcu61CanMessage))
                .build();
        System.out.println("esSummary="+esSummary.toString());
        ComponentsPercentSum componentsPercentSum = ComponentsPercentSum.builder()
                .RESTEnergyPert(calCompRESTEnergyPert(vcu61CanMessage,vcu62CanMessage))
                .ACEnergyPert(calCompACEnergyPert(vcu61CanMessage,vcu62CanMessage))
                .DCDCEngergyPert(calCompDCDCEnergyPert(vcu61CanMessage))
                .MTEnergyPert(calCompMTEnergyPert(vcu61CanMessage))
                .BRKEnergyPert(calCompBRKEnergyPert(vcu61CanMessage,vcu62CanMessage))
                .build();
        System.out.println("componentsPercentSum="+componentsPercentSum.toString());
        VCU63CanMessage vcu63CanMessage = energySavingCanMessage.getVcu63CanMessage();
        System.out.println("vcu63CanMessage="+vcu63CanMessage);
        VCU64CanMessage vcu64CanMessage = energySavingCanMessage.getVcu64CanMessage();
        System.out.println("vcu64CanMessage="+vcu64CanMessage);
        VCU65CanMessage vcu65CanMessage = energySavingCanMessage.getVcu65CanMessage();
        System.out.println("vcu65CanMessage="+vcu65CanMessage);
        VCU66CanMessage vcu66CanMessage = energySavingCanMessage.getVcu66CanMessage();
        System.out.println("vcu66CanMessage="+vcu66CanMessage);
        VCU67CanMessage vcu67CanMessage = energySavingCanMessage.getVcu67CanMessage();
        System.out.println("vcu67CanMessage="+vcu67CanMessage);
        VCU68CanMessage vcu68CanMessage = energySavingCanMessage.getVcu68CanMessage();
        System.out.println("vcu68CanMessage="+vcu68CanMessage);
        VCU69CanMessage vcu69CanMessage = energySavingCanMessage.getVcu69CanMessage();
        System.out.println("vcu69CanMessage="+vcu69CanMessage);
        VCU70CanMessage vcu70CanMessage = energySavingCanMessage.getVcu70CanMessage();
        System.out.println("vcu70CanMessage="+vcu70CanMessage);
        VCU71CanMessage vcu71CanMessage = energySavingCanMessage.getVcu71CanMessage();
        System.out.println("vcu71CanMessage="+vcu71CanMessage);
        VCU72CanMessage vcu72CanMessage = energySavingCanMessage.getVcu72CanMessage();
        System.out.println("vcu72CanMessage="+vcu72CanMessage);
        VCU75CanMessage vcu75CanMessage = energySavingCanMessage.getVcu75CanMessage();
        System.out.println("vcu75CanMessage="+vcu75CanMessage);
        VCU76CanMessage vcu76CanMessage = energySavingCanMessage.getVcu76CanMessage();
        System.out.println("vcu76CanMessage="+vcu76CanMessage);
        Per100KMByDM per100KMByDM = Per100KMByDM.builder()
                .CustomerDM(calCSTEnergyKWHPer100KM(vcu75CanMessage,vcu76CanMessage))
                .EconomyDM(calECOEnergyKWHPer100KM(vcu65CanMessage,vcu66CanMessage))
                .NormalDM(calNOREnergyKWHPer100KM(vcu67CanMessage,vcu68CanMessage))
                .PowerDM(calPOWEnergyKWHPer100KM(vcu69CanMessage,vcu70CanMessage))
                .EPowerDM(calEPMEnergyKWHPer100KM(vcu71CanMessage,vcu72CanMessage))
                .EEconomyDM(calEEMEnergyKWHPer100KM(vcu63CanMessage,vcu64CanMessage))
                .build();
        System.out.println("per100KMByDM="+per100KMByDM.toString());
        Map<String,ComponentsPercent> cpByDM = new HashMap<>();
        ComponentsPercent componentsPercentEEM = ComponentsPercent.builder()
                .RESTEnergyPert(calEEMRESTEnergyPert(vcu63CanMessage,vcu64CanMessage))
                .ACEnergyPert(calEEMACEnergyPert(vcu63CanMessage,vcu64CanMessage))
                .DCDCEngergyPert(calEEMDCDCEnergyPert(vcu63CanMessage))
                .MTEnergyPert(calEEMMTEnergyPert(vcu63CanMessage))
                .build();
        System.out.println("componentsPercentEEM="+componentsPercentEEM.toString());
        cpByDM.put(DRIVING_MODE_ABB_EEM,componentsPercentEEM);
        ComponentsPercent componentsPercentECO = ComponentsPercent.builder()
                .RESTEnergyPert(calECORESTEnergyPert(vcu65CanMessage,vcu66CanMessage))
                .ACEnergyPert(calECOACEnergyPert(vcu65CanMessage,vcu66CanMessage))
                .DCDCEngergyPert(calECODCDCEnergyPert(vcu65CanMessage))
                .MTEnergyPert(calECOMTEnergyPert(vcu65CanMessage))
                .build();
        System.out.println("componentsPercentECO="+componentsPercentECO);
        cpByDM.put(DRIVING_MODE_ABB_ECO,componentsPercentECO);
        ComponentsPercent componentsPercentNOR = ComponentsPercent.builder()
                .RESTEnergyPert(calNORRESTEnergyPert(vcu67CanMessage,vcu68CanMessage))
                .ACEnergyPert(calNORACEnergyPert(vcu67CanMessage,vcu68CanMessage))
                .DCDCEngergyPert(calNORDCDCEnergyPert(vcu67CanMessage))
                .MTEnergyPert(calNORMTEnergyPert(vcu67CanMessage))
                .build();
        System.out.println("componentsPercentNOR="+componentsPercentNOR);
        cpByDM.put(DRIVING_MODE_ABB_NOR,componentsPercentNOR);
        ComponentsPercent componentsPercentEPM = ComponentsPercent.builder()
                .RESTEnergyPert(calEPMRESTEnergyPert(vcu71CanMessage,vcu72CanMessage))
                .ACEnergyPert(calEPMACEnergyPert(vcu71CanMessage,vcu72CanMessage))
                .DCDCEngergyPert(calEPMDCDCEnergyPert(vcu71CanMessage))
                .MTEnergyPert(calEPMMTEnergyPert(vcu71CanMessage))
                .build();
        System.out.println("componentsPercentEPM="+componentsPercentEPM);
        cpByDM.put(DRIVING_MODE_ABB_EPM,componentsPercentEPM);
        ComponentsPercent componentsPercentPOW = ComponentsPercent.builder()
                .RESTEnergyPert(calPOWRESTEnergyPert(vcu69CanMessage,vcu70CanMessage))
                .ACEnergyPert(calPOWACEnergyPert(vcu69CanMessage,vcu70CanMessage))
                .DCDCEngergyPert(calPOWDCDCEnergyPert(vcu69CanMessage))
                .MTEnergyPert(calPOWMTEnergyPert(vcu69CanMessage))
                .build();
        System.out.println("componentsPercentPOW="+componentsPercentPOW);
        cpByDM.put(DRIVING_MODE_ABB_POW,componentsPercentPOW);
        ComponentsPercent componentsPercentCST = ComponentsPercent.builder()
                .RESTEnergyPert(calCSTRESTEnergyPert(vcu75CanMessage,vcu76CanMessage))
                .ACEnergyPert(calCSTACEnergyPert(vcu75CanMessage,vcu76CanMessage))
                .DCDCEngergyPert(calCSTDCDCEnergyPert(vcu75CanMessage))
                .MTEnergyPert(calCSTMTEnergyPert(vcu75CanMessage))
                .build();
        System.out.println("componentsPercentCST="+componentsPercentCST);
        cpByDM.put(DRIVING_MODE_ABB_CST,componentsPercentCST);
        QueryESRes queryESRes = QueryESRes.builder()
                .driver(esReq.getDriver())
                .dateTime(esReq.getDateTime())
                .esSummary(esSummary)
                .componentsPercentSum(componentsPercentSum)
                .per100KMByDM(per100KMByDM)
                .CPByDM(cpByDM)
                .responseCode(RESPONSE_CODE_SUCCESS)
                .build();
        System.out.println("queryESRes="+queryESRes);
        return queryESRes;
    }


    static QueryESRes createCurrentZero(QueryESReq esReq){
        ESSummary esSummary = ESSummary.builder().energyPer100KM(ELECTRICITY_SAVING_ZERO)
                .sumEnergy(ELECTRICITY_SAVING_ZERO).sumEnergyCost(ELECTRICITY_SAVING_ZERO).build();
        ComponentsPercentSum componentsPercentSum = ComponentsPercentSum.builder()
                .RESTEnergyPert(ELECTRICITY_SAVING_ZERO).ACEnergyPert(ELECTRICITY_SAVING_ZERO)
                .DCDCEngergyPert(ELECTRICITY_SAVING_ZERO).MTEnergyPert(ELECTRICITY_SAVING_ZERO)
                .BRKEnergyPert(ELECTRICITY_SAVING_ZERO).build();
        Per100KMByDM per100KMByDM = Per100KMByDM.builder()
                .CustomerDM(ELECTRICITY_SAVING_ZERO).EconomyDM(ELECTRICITY_SAVING_ZERO)
                .NormalDM(ELECTRICITY_SAVING_ZERO).PowerDM(ELECTRICITY_SAVING_ZERO)
                .EPowerDM(ELECTRICITY_SAVING_ZERO).EEconomyDM(ELECTRICITY_SAVING_ZERO).build();
        Map<String,ComponentsPercent> cpByDM = new HashMap<>();
        ComponentsPercent componentsPercentEEM = ComponentsPercent.builder()
                .RESTEnergyPert(ELECTRICITY_SAVING_ZERO).ACEnergyPert(ELECTRICITY_SAVING_ZERO)
                .DCDCEngergyPert(ELECTRICITY_SAVING_ZERO).MTEnergyPert(ELECTRICITY_SAVING_ZERO).build();
        cpByDM.put(DRIVING_MODE_ABB_EEM,componentsPercentEEM);
        ComponentsPercent componentsPercentECO = ComponentsPercent.builder()
                .RESTEnergyPert(ELECTRICITY_SAVING_ZERO).ACEnergyPert(ELECTRICITY_SAVING_ZERO)
                .DCDCEngergyPert(ELECTRICITY_SAVING_ZERO).MTEnergyPert(ELECTRICITY_SAVING_ZERO).build();
        cpByDM.put(DRIVING_MODE_ABB_ECO,componentsPercentECO);
        ComponentsPercent componentsPercentNOR = ComponentsPercent.builder()
                .RESTEnergyPert(ELECTRICITY_SAVING_ZERO).ACEnergyPert(ELECTRICITY_SAVING_ZERO)
                .DCDCEngergyPert(ELECTRICITY_SAVING_ZERO).MTEnergyPert(ELECTRICITY_SAVING_ZERO).build();
        cpByDM.put(DRIVING_MODE_ABB_NOR,componentsPercentNOR);
        ComponentsPercent componentsPercentEPM = ComponentsPercent.builder()
                .RESTEnergyPert(ELECTRICITY_SAVING_ZERO).ACEnergyPert(ELECTRICITY_SAVING_ZERO)
                .DCDCEngergyPert(ELECTRICITY_SAVING_ZERO).MTEnergyPert(ELECTRICITY_SAVING_ZERO).build();
        cpByDM.put(DRIVING_MODE_ABB_EPM,componentsPercentEPM);
        ComponentsPercent componentsPercentPOW = ComponentsPercent.builder()
                .RESTEnergyPert(ELECTRICITY_SAVING_ZERO).ACEnergyPert(ELECTRICITY_SAVING_ZERO)
                .DCDCEngergyPert(ELECTRICITY_SAVING_ZERO).MTEnergyPert(ELECTRICITY_SAVING_ZERO).build();
        cpByDM.put(DRIVING_MODE_ABB_POW,componentsPercentPOW);
        ComponentsPercent componentsPercentCST = ComponentsPercent.builder()
                .RESTEnergyPert(ELECTRICITY_SAVING_ZERO).ACEnergyPert(ELECTRICITY_SAVING_ZERO)
                .DCDCEngergyPert(ELECTRICITY_SAVING_ZERO).MTEnergyPert(ELECTRICITY_SAVING_ZERO).build();
        cpByDM.put(DRIVING_MODE_ABB_CST,componentsPercentCST);
        QueryESRes queryESRes = QueryESRes.builder()
                .driver(esReq.getDriver())
                .dateTime(esReq.getDateTime())
                .esSummary(esSummary)
                .componentsPercentSum(componentsPercentSum)
                .per100KMByDM(per100KMByDM)
                .CPByDM(cpByDM)
                .responseCode(RESPONSE_CODE_SUCCESS)
                .build();
        return queryESRes;
    }

    static void storeLastEnergySavingCycle(){
        System.out.println("foundLastEnergySavingCycle");
        VCU61CanMessage vcu61CanMessage = getLastVCU61MessageFromRedis();
        System.out.println("vcu61CanMessage="+vcu61CanMessage);
        VCU62CanMessage vcu62CanMessage = getLastVCU62MessageFromRedis();
        System.out.println("vcu62CanMessage="+vcu62CanMessage);
        VCU63CanMessage vcu63CanMessage = getLastVCU63MessageFromRedis();
        System.out.println("vcu63CanMessage="+vcu63CanMessage);
        VCU64CanMessage vcu64CanMessage = getLastVCU64MessageFromRedis();
        System.out.println("vcu64CanMessage="+vcu64CanMessage);
        VCU65CanMessage vcu65CanMessage = getLastVCU65MessageFromRedis();
        System.out.println("vcu65CanMessage="+vcu65CanMessage);
        VCU66CanMessage vcu66CanMessage = getLastVCU66MessageFromRedis();
        System.out.println("vcu66CanMessage="+vcu66CanMessage);
        VCU67CanMessage vcu67CanMessage = getLastVCU67MessageFromRedis();
        System.out.println("vcu67CanMessage="+vcu67CanMessage);
        VCU68CanMessage vcu68CanMessage = getLastVCU68MessageFromRedis();
        System.out.println("vcu68CanMessage="+vcu68CanMessage);
        VCU69CanMessage vcu69CanMessage = getLastVCU69MessageFromRedis();
        System.out.println("vcu69CanMessage="+vcu69CanMessage);
        VCU70CanMessage vcu70CanMessage = getLastVCU70MessageFromRedis();
        System.out.println("vcu70CanMessage="+vcu70CanMessage);
        VCU71CanMessage vcu71CanMessage = getLastVCU71MessageFromRedis();
        System.out.println("vcu71CanMessage="+vcu71CanMessage);
        VCU72CanMessage vcu72CanMessage = getLastVCU72MessageFromRedis();
        System.out.println("vcu72CanMessage="+vcu72CanMessage);
        //TODO: need to get from redis
/*        VCU75CanMessage vcu75CanMessage = getLastVCU75MessageFromRedis();
        System.out.println("vcu75CanMessage="+vcu75CanMessage);
        VCU76CanMessage vcu76CanMessage = getLastVCU76MessageFromRedis();
        System.out.println("vcu76CanMessage="+vcu76CanMessage);*/
        VCU75CanMessage vcu75CanMessage = VCU75CanMessage.builder()
                .DCDC_Energy_Custom_Cyc(0.0).HVBatt_Energy_Custom_Cyc(0.0).Motor_Energy_Custom_Cyc(0.0)
                .PTC_Energy_Custom_Cyc(0.0).build();
        System.out.println("vcu75CanMessage="+vcu75CanMessage);
        VCU76CanMessage vcu76CanMessage = VCU76CanMessage.builder()
                .AC_Energy_Custom_Cyc(0.0).Mileage_Custom_Cyc(0.0).build();
        System.out.println("vcu76CanMessage="+vcu76CanMessage);

        EnergySavingCanMessage energySavingCanMessage = EnergySavingCanMessage.builder()
                .vcu61CanMessage(vcu61CanMessage)
                .vcu62CanMessage(vcu62CanMessage)
                .vcu63CanMessage(vcu63CanMessage)
                .vcu64CanMessage(vcu64CanMessage)
                .vcu65CanMessage(vcu65CanMessage)
                .vcu66CanMessage(vcu66CanMessage)
                .vcu67CanMessage(vcu67CanMessage)
                .vcu68CanMessage(vcu68CanMessage)
                .vcu69CanMessage(vcu69CanMessage)
                .vcu70CanMessage(vcu70CanMessage)
                .vcu71CanMessage(vcu71CanMessage)
                .vcu72CanMessage(vcu72CanMessage)
                .vcu75CanMessage(vcu75CanMessage)
                .vcu76CanMessage(vcu76CanMessage)
                .timestamp(getDateTimeNowTimeStamp())
                .build();

        inputValue2ZSET(REDIS_ENERGY_SAVING_DRIVING_CYCLE_ZSET,
                energySavingCanMessage.getTimestamp(),
                transferFromObject2JSON(energySavingCanMessage));

        System.out.println("storeLastEnergySavingCycle="+energySavingCanMessage.toString());

    }

}
