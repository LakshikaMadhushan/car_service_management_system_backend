package com.esoft.carservice.entity;

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
public class MechanicService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long mechanicServiceId;
    public String name;
    public double price;
    @Enumerated(EnumType.STRING)
    public VehicleType vehicleType;

    @ManyToOne(fetch = FetchType.LAZY)
    public MechanicServiceCategory mechanicServiceCategory;
    @OneToMany(mappedBy = "mechanicService")
    private List<ServiceDetails> serviceDetailsList;
    @OneToMany(mappedBy = "mechanicService")
    private List<AppointmentDetails> appointmentDetailsList;
}
