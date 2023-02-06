package com.example.drone.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "Drone")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String serialNumber;
    private int weightLimit;
    private Model model;
    private int batteryCapacity;
    private State state;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "medication_id", referencedColumnName = "id")
    private List<Medication> medicationList;

    public Drone(int id) {
        this.id = id;
    }

    public Drone(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String toStringBatteryInfo(){
        return "Drone ==> Serial Number=" + getSerialNumber() + ", State=" + getState().name() + ", Battery Level="+getBatteryCapacity();
    }



}
