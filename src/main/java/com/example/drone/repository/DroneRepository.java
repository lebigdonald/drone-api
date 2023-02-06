package com.example.drone.repository;

import com.example.drone.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Integer> {

    Drone findDroneBySerialNumber(String serialNumber);
}
