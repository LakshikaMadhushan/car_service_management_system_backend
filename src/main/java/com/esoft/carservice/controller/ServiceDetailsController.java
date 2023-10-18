package com.esoft.carservice.controller;

import com.esoft.carservice.dto.common.CommonResponse;
import com.esoft.carservice.dto.requset.SaveServiceDetailsRequestDTO;
import com.esoft.carservice.dto.requset.ServiceDetailsFilterRequestDTO;
import com.esoft.carservice.service.VehicleServiceDetailsService;
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
@RequestMapping("v1/service-details")
public class ServiceDetailsController {
    private final VehicleServiceDetailsService vehicleServiceDetailsService;

    public ServiceDetailsController(VehicleServiceDetailsService vehicleServiceDetailsService) {
        this.vehicleServiceDetailsService = vehicleServiceDetailsService;
    }


    @GetMapping(value = "/{serviceDetailId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> getServiceDetails(@PathVariable long serviceDetailId) {

        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS, vehicleServiceDetailsService.getServiceDetail(serviceDetailId),
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> updateServiceDetails(@RequestBody SaveServiceDetailsRequestDTO dto) {
        vehicleServiceDetailsService.updateServiceDetail(dto);
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS,
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> saveServiceDetails(@RequestBody SaveServiceDetailsRequestDTO dto) {
        vehicleServiceDetailsService.saveServiceDetail(dto);
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS,
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> serviceDetailsFilter(@RequestBody ServiceDetailsFilterRequestDTO dto) {
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS, vehicleServiceDetailsService.getServiceFilterDetail(dto),
                SUCCESS_RESPONSE), HttpStatus.OK);
    }
}
