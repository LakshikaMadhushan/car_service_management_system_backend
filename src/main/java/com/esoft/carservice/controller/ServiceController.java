package com.esoft.carservice.controller;

import com.esoft.carservice.dto.common.CommonResponse;
import com.esoft.carservice.dto.requset.ServiceFilterRequestDTO;
import com.esoft.carservice.dto.requset.UpdateAndSaveServiceRequestDTO;
import com.esoft.carservice.service.VehicalServiceService;
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
@RequestMapping("v1/service")
public class ServiceController {

    private final VehicalServiceService vehicalServiceService;

    public ServiceController(VehicalServiceService vehicalServiceService) {
        this.vehicalServiceService = vehicalServiceService;
    }


    @GetMapping(value = "/{serviceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> getService(@PathVariable long serviceId) {

        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS, vehicalServiceService.getService(serviceId),
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> saveService(@RequestBody UpdateAndSaveServiceRequestDTO dto) {
        vehicalServiceService.saveServiceOny(dto);
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS,
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> updateService(@RequestBody UpdateAndSaveServiceRequestDTO dto) {
        vehicalServiceService.updateService(dto);
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS,
                SUCCESS_RESPONSE), HttpStatus.OK);
    }


    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> serviceFilter(@RequestBody ServiceFilterRequestDTO dto) {
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS, vehicalServiceService.getServiceFilter(dto),
                SUCCESS_RESPONSE), HttpStatus.OK);
    }


    @PostMapping(value = "/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> serviceSaveWithDetails(@RequestBody ServiceFilterRequestDTO dto) {
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS, vehicalServiceService.getServiceFilter(dto),
                SUCCESS_RESPONSE), HttpStatus.OK);
    }
}
