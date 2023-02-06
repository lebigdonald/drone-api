package com.example.drone;

import com.example.drone.dto.DroneDto;
import com.example.drone.model.Model;
import com.example.drone.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Random;

@SpringBootApplication
@EnableScheduling
public class DroneApplication implements CommandLineRunner {

    @Autowired
    private DroneService droneService;

    public static void main(String[] args) {
        SpringApplication.run(DroneApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if(droneService.getAllDrones().size() == 0){
            for (int i=0; i<10; i++){
                droneService.createDrone(new DroneDto("ABC-"+randomInt("NUM"), Model.Heavyweight, randomInt("WEI"), randomInt("BAT")));
            }
        }
    }

    private int randomInt(String type){
        Random random = new Random();
        int ran;
        if(type.equals("NUM")){
            ran = random.nextInt(10000); // Random Numbers
        }else if(type.equals("WEI")){
            ran = random.nextInt(500); // Random Weight
        }else{
            ran = random.nextInt(100); // Random Battery Level
        }
        return ran;
    }

    private char randomCharacters(){
        Random rnd = new Random();
        char c = (char) ('a' + rnd.nextInt(26));
        return c;
    }
}
