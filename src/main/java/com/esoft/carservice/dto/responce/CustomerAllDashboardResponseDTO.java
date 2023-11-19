package com.esoft.carservice.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAllDashboardResponseDTO {
    public int serviceCount;
    public int vehicleCount;
    public double serviceCost;
    public double partCost;
    public Date lastServiceDate;
    public String vehicleNo;
}
