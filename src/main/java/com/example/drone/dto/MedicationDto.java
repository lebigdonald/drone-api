package com.example.drone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MedicationDto {

    @NotBlank
    private String name;
    @NotNull
    private int weight;
    @NotBlank
    private String code;
    @NotBlank
    private String image;
}
