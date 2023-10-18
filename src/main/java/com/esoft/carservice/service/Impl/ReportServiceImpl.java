package com.esoft.carservice.service.Impl;

import com.esoft.carservice.dto.requset.AdminReportFilterRequestDTO;
import com.esoft.carservice.dto.responce.GetServiceDetailsResponseDTO;
import com.esoft.carservice.service.ReportService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ReportServiceImpl implements ReportService {
    @Override
    public List<GetServiceDetailsResponseDTO> getAllAdminReport(AdminReportFilterRequestDTO requestDTO) {
        return null;
    }
}
