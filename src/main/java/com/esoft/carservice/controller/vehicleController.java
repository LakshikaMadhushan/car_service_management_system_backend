package com.esoft.carservice.controller;

import com.esoft.carservice.dto.common.CommonResponse;
import com.esoft.carservice.dto.requset.UpdateSaveVehicleRequestDTO;
import com.esoft.carservice.dto.requset.VehicleFilterRequestDTO;
import com.esoft.carservice.service.VehicleService;
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
@RequestMapping("v1/vehicle")
public class vehicleController {
    private final VehicleService vehicleService;

    public vehicleController(VehicleService vehicleService) {

        this.vehicleService = vehicleService;
    }

    @GetMapping(value = "/{vehicleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> getItem(@PathVariable long vehicleId) {

        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS, vehicleService.getVehicle(vehicleId),
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> updateAdmin(@RequestBody UpdateSaveVehicleRequestDTO dto) {
        vehicleService.updateVehicle(dto);
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS,
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> saveAdmin(@RequestBody UpdateSaveVehicleRequestDTO dto) {
        vehicleService.saveVehicle(dto);
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS,
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> itemFilter(@RequestBody VehicleFilterRequestDTO dto) {
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS, vehicleService.getVehicleFilter(dto),
                SUCCESS_RESPONSE), HttpStatus.OK);
    }
}
