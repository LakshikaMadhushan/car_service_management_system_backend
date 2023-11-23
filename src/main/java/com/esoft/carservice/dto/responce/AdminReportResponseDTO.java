package com.esoft.carservice.dto.responce;

import com.esoft.carservice.enums.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminReportResponseDTO {

    public long serviceId;
    @Temporal(TemporalType.DATE)
    public Date service_date;
    @Enumerated(EnumType.STRING)
    public ServiceType type;
    public double cost;
    public List<GetServiceDetailsResponseDTO> serviceDetailsResponseDTOS;
    public long vehicleId;
    public String vehicle;
    public long technicianId;
    public String technician;
    public String customer;
    public long customerId;


}
