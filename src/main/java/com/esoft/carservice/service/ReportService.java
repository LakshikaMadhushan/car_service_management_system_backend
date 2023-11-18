package com.esoft.carservice.service;

import com.esoft.carservice.dto.requset.AdminReportFilterRequestDTO;
import com.esoft.carservice.dto.requset.CustomerDashboardFilterRequestDTO;
import com.esoft.carservice.dto.responce.AdminAllDashboardResponseDTO;
import com.esoft.carservice.dto.responce.AdminAllReportResponseDTO;
import com.esoft.carservice.dto.responce.CustomerAllDashboardResponseDTO;

public interface ReportService {

    public AdminAllReportResponseDTO getAllAdminReport(AdminReportFilterRequestDTO requestDTO);

    public AdminAllDashboardResponseDTO getAllAdminDashboard();

    public CustomerAllDashboardResponseDTO getAllCustomerDashboard(CustomerDashboardFilterRequestDTO requestDTO);
}
