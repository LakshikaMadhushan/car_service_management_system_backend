package com.esoft.carservice.entity;

import com.esoft.carservice.enums.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long vehicleId;
    public String category;
    public String numberPlate;
    public String colour;
    @Enumerated(EnumType.STRING)
    public VehicleStatus status;
    public String engineCapacity;
    public String mileage;
    public String nextMileage;
}
