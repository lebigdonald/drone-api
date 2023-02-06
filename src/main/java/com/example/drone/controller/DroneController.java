package com.example.drone.controller;

import com.example.drone.dto.DroneDto;
import com.example.drone.dto.MedicationDto;
import com.example.drone.model.Drone;
import com.example.drone.model.Medication;
import com.example.drone.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/drone")
public class DroneController {

    @Autowired
    private DroneService droneService;

    @PostMapping("/add")
    public ResponseEntity registerDrone(@RequestBody @Valid DroneDto droneDto){
        return ResponseEntity.ok(droneService.createDrone(droneDto));
    }

    @PostMapping("/load/medication/{drone_serialNumber}")
    public ResponseEntity loadingDroneWithMedication(@PathVariable String drone_serialNumber, @RequestBody @Valid MedicationDto medicationDto){
        DroneDto droneDto = droneService.getDroneDtoBySerialNumber(drone_serialNumber);

        boolean overLoaded = checkOverLoading(droneDto, medicationDto);
        if(overLoaded){
            return ResponseEntity.ok("Medication is more heavy than drone sorry. \nTry a less heavy medication");
        }

        boolean totalOverLoaded = checkTotalOverLoading(droneService.getDroneBySerialNumber(drone_serialNumber), medicationDto);
        if(totalOverLoaded){
            return ResponseEntity.ok("Total weight of Medication in the Drone is heavy than Drone Weight Limit. \nTry a less heavy medication");
        }

        boolean batteryLevelLow = checkLowBatteryLevel(droneDto);
        if(batteryLevelLow){
            return ResponseEntity.ok("Drone can not be in a Loading State. \nBattery Capacity is Low, Need to be more than 25%");
        }

        boolean checkMedicationName_Code = checkMedicationName_Code(medicationDto);
        if(checkMedicationName_Code){
            return ResponseEntity.ok("Medication name or code is Incorrect. \nTry a Combination of AlphaNumeric + (-, _) For Name & Uppercase + Numbers and (_)");
        }

        Medication medication = droneService.createMedication(medicationDto);

        return ResponseEntity.ok(droneService.addMedicationToDrone(drone_serialNumber, medication));
    }

    @GetMapping("/medications")
    public ResponseEntity checkLoadedMedication(@RequestParam String droneSerialNumber){

        return ResponseEntity.ok(droneService.getDroneMedicationBySerialNumber(droneSerialNumber));
    }

    @GetMapping("/available")
    public ResponseEntity checkingAvailableDroneForLoading(){
        return ResponseEntity.ok(droneService.getAvailableDroneForLoading());
    }

    @GetMapping("/battery/{droneSerialNumber}")
    public ResponseEntity checkDroneBatteryLevel(@PathVariable String droneSerialNumber){
        DroneDto droneDto = droneService.getDroneDtoBySerialNumber(droneSerialNumber);
        return ResponseEntity.ok("Battery Level: "+droneDto.getBatteryCapacity()+"%");
    }

    private boolean checkOverLoading(DroneDto droneDto, MedicationDto medicationDto){
        return medicationDto.getWeight() > droneDto.getWeightLimit();
    }

    private boolean checkTotalOverLoading(Drone drone, MedicationDto medicationDto){
        int totalDroneMedicationWeight = drone.getMedicationList().stream().map(Medication::getWeight).reduce(0, Integer::sum);
        int estimatedTotalDroneWeight = totalDroneMedicationWeight + medicationDto.getWeight();

        return estimatedTotalDroneWeight > drone.getWeightLimit();
    }

    private boolean checkLowBatteryLevel(DroneDto droneDto){
        return droneDto.getBatteryCapacity() < 25;
    }

    private boolean checkMedicationName_Code(MedicationDto medicationDto){
        boolean result = true;
        boolean resultName = true;
        boolean resultCode = true;
        for (int i = 0; i < medicationDto.getName().length(); i++) {
            String name = medicationDto.getName();
            // checks whether the character is neither a letter nor a digit
            resultName = (Character.isLetterOrDigit(name.charAt(i))) || name.equals("-") || name.equals("_");
            if (!resultName){
                break;
            }
        }

        for (int i = 0; i < medicationDto.getCode().length(); i++) { //Code and Value may not have the same length hence using another Loop
            String code = medicationDto.getCode();
            // checks whether the character is neither a letter nor a digit
            resultCode = (Character.isUpperCase(code.charAt(i))) || (Character.isDigit(code.charAt(i))) || code.equals("_");
            if (!resultCode){
                break;
            }
        }

        if(!resultName || !resultCode){
            result = false;
        }

        return result;
    }
}
