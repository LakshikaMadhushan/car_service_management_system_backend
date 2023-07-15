package com.esoft.carservice.controller;

import com.esoft.carservice.dto.common.CommonResponse;
import com.esoft.carservice.dto.requset.UpdateAndSaveTechnicianRequestDTO;
import com.esoft.carservice.service.TechnicianService;
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
@RequestMapping("v1/technician")
public class TechnicianController {
    private final TechnicianService technicianService;

    public TechnicianController(TechnicianService technicianService) {
        this.technicianService = technicianService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> getAllTechnician() {

        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS, technicianService.getAllTechnician(),
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @GetMapping(value = "/{technicianId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> getTechnician(@PathVariable long technicianId) {

        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS, technicianService.getTechnician(technicianId),
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> updateTechnician(@RequestBody UpdateAndSaveTechnicianRequestDTO dto) {
        technicianService.updateTechnician(dto);
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS,
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> saveTechnician(@RequestBody UpdateAndSaveTechnicianRequestDTO dto) {
        technicianService.saveTechnician(dto);
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS,
                SUCCESS_RESPONSE), HttpStatus.OK);
    }
}
