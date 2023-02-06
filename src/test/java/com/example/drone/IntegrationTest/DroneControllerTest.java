package com.example.drone.IntegrationTest;

import com.example.drone.dto.DroneDto;
import com.example.drone.model.Drone;
import com.example.drone.model.Model;
import com.example.drone.service.DroneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DroneControllerTest {

    @Autowired
    DroneService droneService;

    static int PORT = 8080;

    @Test
    void getTotalAvailableDrones() { //On init
        TestRestTemplate restTemplate = new TestRestTemplate();
        assertEquals(10, restTemplate.getForObject("http://localhost:" + PORT + "/drone/available", List.class).size());
    }

//    @Test
//    void createDrone() {
//        DroneDto droneDto = new DroneDto("ABC-001", Model.Cruiserweight, 300, 80);
//        TestRestTemplate restTemplate = new TestRestTemplate();
//        Drone drone = new Drone(droneDto.getSerialNumber());
//        assertEquals(drone.getSerialNumber(), restTemplate.postForEntity("http://localhost:" + PORT + "/drone/add", droneDto, Drone.class).getBody().getSerialNumber(), "Serial Number after creation should be same");
//    }
}
