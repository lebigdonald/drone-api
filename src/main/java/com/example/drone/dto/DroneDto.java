package com.example.drone.dto;

import com.example.drone.model.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DroneDto {

    @NotBlank
    @Size(max = 100, message = "Serial Number can not be more than 100 chars")
    private String serialNumber;

    @NotNull
    private Model model;

    @Max(value = 500, message = "Weight Light of a Drone is 500gr")
    private int weightLimit;

    @Min(value = 0, message = "Battery Capacity Minimum is 0")
    @Max(value = 100, message = "Battery Capacity Maximum is 100")
    @NotNull
    private int batteryCapacity;
}
