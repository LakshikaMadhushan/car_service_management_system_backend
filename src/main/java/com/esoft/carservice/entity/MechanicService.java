package com.esoft.carservice.entity;

import com.esoft.carservice.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class MechanicService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long mechanicServiceId;
    public long name;
    public long price;
    @Enumerated(EnumType.STRING)
    public VehicleType vehicleType;
}
