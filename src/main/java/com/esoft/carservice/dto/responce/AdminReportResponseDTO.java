package com.esoft.carservice.dto.responce;

import com.esoft.carservice.enums.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminReportResponseDTO {

    public long serviceId;
    public Date service_date;
    @Enumerated(EnumType.STRING)
    public ServiceType type;
    public String cost;
    public List<GetServiceDetailsResponseDTO> serviceDetailsResponseDTOS;
    public GetVehicleResponseDTO vehicle;
    public GetTechnicianResponseDTO technician;


}
