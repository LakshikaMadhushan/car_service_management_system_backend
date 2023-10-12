package com.esoft.carservice.dto.requset;

import com.esoft.carservice.enums.VehicleStatus;
import com.esoft.carservice.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleFilterRequestDTO {
    public long vehicleId;
    public String category;
    public String numberPlate;
    @Enumerated(EnumType.STRING)
    public VehicleStatus status;
    @Enumerated(EnumType.STRING)
    public VehicleType vehicleType;
    public long customerId;
}
