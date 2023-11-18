package com.esoft.carservice.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminAllDashboardResponseDTO {
    public int totalAdmin;
    public int totalTechnician;
    public int totalCustomer;
    public int totalItems;
    public int totalMechanicServices;
    public int totalVehicle;
    public int totalServices;

}
