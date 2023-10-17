package com.esoft.carservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long customerId;
    public String name;
    public String address1;
    public String address2;
    public String mobileNumber;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "customer")
    private User user;
    @OneToMany(mappedBy = "customer")
    private List<Vehicle> vehicleList;
    @OneToMany(mappedBy = "customer")
    private List<Order> orderList;
    @OneToMany(mappedBy = "customer")
    private List<Appointment> appointmentList;
}
