package com.esoft.carservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class AppointmentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long appointmentDetailsId;

    @ManyToOne(fetch = FetchType.LAZY)
    public Appointment appointment;
    @ManyToOne(fetch = FetchType.LAZY)
    public MechanicService mechanicService;
}
