package com.esoft.carservice.dto.requset;

import com.esoft.carservice.enums.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaveServiceRequestDTO {
    public long serviceId;
    public long vehicleId;
    public long technicianId;
    @Temporal(TemporalType.DATE)
    public Date service_date;
    @Enumerated(EnumType.STRING)
    public ServiceType type;
    public String cost;

    List<SaveServiceDetailsRequestDTO> saveServiceDetails = new ArrayList<>();
}
