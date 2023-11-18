package com.esoft.carservice.controller;

import com.esoft.carservice.dto.common.CommonResponse;
import com.esoft.carservice.dto.requset.AdminReportFilterRequestDTO;
import com.esoft.carservice.dto.requset.CustomerDashboardFilterRequestDTO;
import com.esoft.carservice.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.esoft.carservice.constant.ResponseCodes.OPERATION_SUCCESS;
import static com.esoft.carservice.constant.ResponseMessages.SUCCESS_RESPONSE;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("v1/report")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {

        this.reportService = reportService;
    }


    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> itemFilter(@RequestBody AdminReportFilterRequestDTO dto) {
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS, reportService.getAllAdminReport(dto),
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @GetMapping(value = "/admin/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> adminDashboard() {
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS, reportService.getAllAdminDashboard(),
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @GetMapping(value = "/customer/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> customerDashboard(@RequestBody CustomerDashboardFilterRequestDTO dto) {
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS, reportService.getAllCustomerDashboard(dto),
                SUCCESS_RESPONSE), HttpStatus.OK);
    }


}
