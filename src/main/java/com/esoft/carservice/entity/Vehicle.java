package com.esoft.carservice.entity;

import com.esoft.carservice.enums.VehicleStatus;
import com.esoft.carservice.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    @Enumerated(EnumType.STRING)
    public VehicleType vehicleType;
    @ManyToOne(fetch = FetchType.LAZY)
    public Customer customer;

    @OneToMany(mappedBy = "vehicle")
    private List<Service> serviceList;
    @OneToMany(mappedBy = "vehicle")
    private List<Appointment> appointmentList;
}
