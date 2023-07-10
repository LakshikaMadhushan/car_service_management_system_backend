package com.esoft.carservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long appointmentId;
    public Date appointmentDate;
    public Date actualDate;

    @ManyToOne(fetch = FetchType.LAZY)
    public Vehicle vehicle;
    @ManyToOne(fetch = FetchType.LAZY)
    public Customer customer;
    @OneToMany(mappedBy = "appointment")
    private List<AppointmentDetails> appointmentDetailsList;
}
