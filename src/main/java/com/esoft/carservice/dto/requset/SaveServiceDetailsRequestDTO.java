package com.esoft.carservice.dto.requset;

import com.esoft.carservice.enums.ServiceDetailsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaveServiceDetailsRequestDTO {
    @Enumerated(EnumType.STRING)
    public ServiceDetailsType type;
    public double cost;
    public long serviceId;
    public long itemId;
    public long name;
    public long mechanicServiceId;

    public long vehicleServiceId;


}
