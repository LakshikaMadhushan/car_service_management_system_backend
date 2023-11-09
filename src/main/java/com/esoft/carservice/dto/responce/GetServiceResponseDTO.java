package com.esoft.carservice.dto.responce;

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
public class GetServiceResponseDTO {
    public long serviceId;
    public Date serviceDate;
    @Enumerated(EnumType.STRING)
    public ServiceType type;
    public String cost;

    public long vehicleId;
    public String numberPlate;

    public long technicianId;
    public String technicianName;

    public long customerId;
    public String customerName;

}
