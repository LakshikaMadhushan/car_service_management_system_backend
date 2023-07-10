package com.esoft.carservice.entity;

import com.esoft.carservice.enums.ServiceDetailsType;
import com.esoft.carservice.enums.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ServiceDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long serviceDetailsId;
    @Enumerated(EnumType.STRING)
    public ServiceDetailsType type;
    public double cost;
}
