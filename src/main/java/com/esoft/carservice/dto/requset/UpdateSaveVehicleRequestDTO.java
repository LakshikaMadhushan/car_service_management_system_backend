package com.esoft.carservice.dto.requset;

import com.esoft.carservice.enums.VehicleStatus;
import com.esoft.carservice.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSaveVehicleRequestDTO {
    public long vehicleId;
    public String category;
    public String numberPlate;
    public String colour;
    @Enumerated(EnumType.STRING)
    public VehicleStatus status;
    public String engineCapacity;
    public String mileage;
    public String nextMileage;
    @Enumerated(EnumType.STRING)
    public VehicleType vehicleType;
    public long customerId;
}
