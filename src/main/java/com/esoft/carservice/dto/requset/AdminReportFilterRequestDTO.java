package com.esoft.carservice.dto.requset;

import com.esoft.carservice.enums.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminReportFilterRequestDTO {
    public long technicianId;
    public long customerId;
    @Temporal(TemporalType.DATE)
    public Date start;
    @Temporal(TemporalType.DATE)
    public Date end;
    public long vehicleId;
    public ServiceType type;
}
