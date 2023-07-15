package com.esoft.carservice.dto.requset;

import com.esoft.carservice.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSaveMechanicServiceRequestDTO {
    public long mechanicServiceId;
    public String name;
    public double price;
    @Enumerated(EnumType.STRING)
    public VehicleType vehicleType;
}
