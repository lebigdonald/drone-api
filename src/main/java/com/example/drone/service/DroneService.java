package com.example.drone.service;

import com.example.drone.dto.DroneDto;
import com.example.drone.dto.MedicationDto;
import com.example.drone.model.Drone;
import com.example.drone.model.Medication;
import com.example.drone.model.State;
import com.example.drone.repository.DroneRepository;
import com.example.drone.repository.MedicationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DroneService {

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    public Drone createDrone(DroneDto droneDto){
        Drone droneToCreate = new Drone();
        BeanUtils.copyProperties(droneDto, droneToCreate);
        droneToCreate.setState(State.IDLE); //At creation a drone is empty
        Drone drone = droneRepository.save(droneToCreate);

        return drone != null ? drone : null;
    }

    public DroneDto getDroneDtoBySerialNumber(String serialNumber){
        Drone drone = droneRepository.findDroneBySerialNumber(serialNumber);
        return toDroneDto(drone);
    }

    public Drone getDroneBySerialNumber(String serialNumber){
        return droneRepository.findDroneBySerialNumber(serialNumber);
    }

    public List<Medication> getDroneMedicationBySerialNumber(String serialNumber){
        Drone drone = droneRepository.findDroneBySerialNumber(serialNumber);
        return drone.getMedicationList();
    }

    public Medication createMedication(MedicationDto medicationDto){
        Medication medicationToCreate = new Medication();
        BeanUtils.copyProperties(medicationDto, medicationToCreate);
        Medication createdMedication = medicationRepository.saveAndFlush(medicationToCreate);
        System.out.println("Medication Created Successfully");

        return createdMedication;
    }

    public Drone addMedicationToDrone(String droneSerialNumber, Medication medication){
        Drone drone = droneRepository.findDroneBySerialNumber(droneSerialNumber);

        int actualDroneWeight = 0;
        if(drone.getMedicationList() == null){ //If drone has no medication
            List<Medication> medicationList = new ArrayList<>();
            medicationList.add(medication);
            drone.setMedicationList(medicationList);
            actualDroneWeight = medication.getWeight();
        }else{ //If drone already has medication
            drone.getMedicationList().add(medication);
            actualDroneWeight = drone.getMedicationList().stream().map(Medication::getWeight).reduce(0, Integer::sum);
        }

        if(actualDroneWeight == drone.getWeightLimit()){
            drone.setState(State.LOADED); //When all medication weight equals the Drone weight Limit
        }else{
            drone.setState(State.LOADING); //When all medication weight is not equals the Drone weight Limit
        }


        Drone droneUpdated = droneRepository.save(drone);
        System.out.println("Drone Loaded with Medication Successfully");

        return droneUpdated;
    }

    public List<Drone> getAvailableDroneForLoading(){
        return droneRepository
                .findAll()
                .stream()
                .filter(drone -> drone.getState().equals(State.LOADING) || drone.getState().equals(State.IDLE))
                .collect(Collectors.toList());
    }

    public List<Drone> getAllDrones(){
        return droneRepository.findAll();
    }

    @Scheduled(fixedRate = 180000)
    private void auditBatteryLevel(){
        System.out.println("---------- Audit System -----------");
        try{
            File logs = new File("Audit-Battery-Logs.txt");
            if(!logs.exists()){
                logs.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(logs, true));
            getAllDrones().forEach(drone -> {
                try {
                    writer.write(drone.toStringBatteryInfo()+"\n");
                } catch (IOException e) {
                    System.err.println("Could not write in Logs: "+e.getMessage());
                }
            });
            writer.close();
        }catch(Exception ex){
            System.err.println("Error while creating or reading Audit-Battery-Logs");
        }

    }

    private DroneDto toDroneDto(Drone drone){
        DroneDto droneDto = new DroneDto();
        BeanUtils.copyProperties(drone, droneDto);
        return droneDto;
    }
}
