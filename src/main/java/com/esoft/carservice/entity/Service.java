package com.esoft.carservice.entity;

import com.esoft.carservice.enums.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long serviceId;
    public String service_date;
    @Enumerated(EnumType.STRING)
    public ServiceType type;
    public String cost;

    @ManyToOne(fetch = FetchType.LAZY)
    public Vehicle vehicle;
    @ManyToOne(fetch = FetchType.LAZY)
    public Technician technician;
    @OneToMany(mappedBy = "service")
    private List<ServiceDetails> serviceDetailsList;
}
