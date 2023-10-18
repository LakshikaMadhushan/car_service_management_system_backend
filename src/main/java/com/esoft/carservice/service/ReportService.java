package com.esoft.carservice.service;

import com.esoft.carservice.dto.requset.AdminReportFilterRequestDTO;
import com.esoft.carservice.dto.responce.GetServiceDetailsResponseDTO;

import java.util.List;

public interface ReportService {

    public List<GetServiceDetailsResponseDTO> getAllAdminReport(AdminReportFilterRequestDTO requestDTO);
}
