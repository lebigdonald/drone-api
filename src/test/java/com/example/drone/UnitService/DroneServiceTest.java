package com.example.drone.UnitService;

import com.example.drone.model.State;
import com.example.drone.service.DroneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DroneServiceTest {

    @Autowired
    DroneService droneService;

    @Test
    void testIfInitDronesExist(){

        assertEquals(10, droneService.getAllDrones().size(), "Drone Created at Initialization Exist");
    }

    @Test
    void testAvailableDrones(){
        assertEquals(10, droneService.getAllDrones().stream().filter(drone -> drone.getState().equals(State.IDLE) || drone.equals(State.LOADING)).count(), "Drone Created at Initialization Need to be all available");
    }

}
