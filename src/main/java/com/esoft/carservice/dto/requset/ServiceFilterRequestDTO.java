package com.esoft.carservice.dto.requset;

import com.esoft.carservice.enums.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceFilterRequestDTO {
    public long serviceId;
    public long vehicleId;
    public long technicianId;
    @Temporal(TemporalType.DATE)
    public Date start;
    @Temporal(TemporalType.DATE)
    public Date end;
    @Enumerated(EnumType.STRING)
    public ServiceType type;
}
