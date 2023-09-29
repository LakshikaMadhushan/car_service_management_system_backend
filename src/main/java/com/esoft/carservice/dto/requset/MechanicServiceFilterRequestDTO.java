package com.esoft.carservice.dto.requset;

import com.esoft.carservice.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MechanicServiceFilterRequestDTO {
    public long mechanicServiceId;
    public long mechanicServiceCategoryId;
    public String name;
    public VehicleType vehicleType;
}
