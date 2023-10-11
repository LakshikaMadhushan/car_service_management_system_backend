package com.esoft.carservice.dto.requset;

import com.esoft.carservice.enums.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceFilterRequestDTO {
    public long serviceId;
    public long vehicleId;
    public long technicianId;
    public Date startService_date;
    public Date endService_date;
    @Enumerated(EnumType.STRING)
    public ServiceType type;
}
