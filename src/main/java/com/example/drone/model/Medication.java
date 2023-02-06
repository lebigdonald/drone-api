package com.example.drone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(schema = "Medication")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private int weight;
    private String code;
    private String image;
    @ManyToOne
    @JsonIgnore
    private Drone drone_id;

    public Medication(int id, int droneId) {
        this.id = id;
        this.drone_id = new Drone(droneId);
    }

    @Override
    public String toString() {
        return "Medication(" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", code='" + code + '\'' +
                ", image='" + image + '\'' +
                ')';
    }
}
