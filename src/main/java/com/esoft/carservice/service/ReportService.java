package com.esoft.carservice.service;

import com.esoft.carservice.dto.requset.AdminReportFilterRequestDTO;
import com.esoft.carservice.dto.responce.AdminAllReportResponseDTO;

public interface ReportService {

    public AdminAllReportResponseDTO getAllAdminReport(AdminReportFilterRequestDTO requestDTO);
}
