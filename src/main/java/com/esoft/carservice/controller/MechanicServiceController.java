package com.esoft.carservice.controller;

import com.esoft.carservice.dto.common.CommonResponse;
import com.esoft.carservice.dto.requset.MechanicServiceFilterRequestDTO;
import com.esoft.carservice.dto.requset.UpdateSaveMechanicServiceRequestDTO;
import com.esoft.carservice.service.MechanicServiceService;
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
@RequestMapping("v1/mechanic-service")
public class MechanicServiceController {
    private final MechanicServiceService mechanicServiceService;

    public MechanicServiceController(MechanicServiceService mechanicServiceService) {
        this.mechanicServiceService = mechanicServiceService;
    }


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> getAllMechanicService() {

        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS, mechanicServiceService.getAllMechanicService(),
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @GetMapping(value = "/{mechanicServiceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> getMechanicService(@PathVariable long mechanicServiceId) {

        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS, mechanicServiceService.getMechanicService(mechanicServiceId),
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> updateMechanicService(@RequestBody UpdateSaveMechanicServiceRequestDTO dto) {
        mechanicServiceService.updateMechanicService(dto);
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS,
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> saveMechanicService(@RequestBody UpdateSaveMechanicServiceRequestDTO dto) {
        mechanicServiceService.saveMechanicService(dto);
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS,
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{mechanicServiceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> deleteItem(@PathVariable long mechanicServiceId) {
        mechanicServiceService.deleteMechanicService(mechanicServiceId);
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS,
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> itemFilter(@RequestBody MechanicServiceFilterRequestDTO dto) {
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS, mechanicServiceService.getMechanicServiceFilter(dto),
                SUCCESS_RESPONSE), HttpStatus.OK);
    }
}
