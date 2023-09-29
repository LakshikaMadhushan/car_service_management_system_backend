package com.esoft.carservice.controller;

import com.esoft.carservice.dto.common.CommonResponse;
import com.esoft.carservice.dto.requset.MechanicServiceCategoryFilterRequestDTO;
import com.esoft.carservice.dto.requset.UpdateAndSaveMechanicServiceCategoryRequestDTO;
import com.esoft.carservice.service.MechanicServiceCategoryService;
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
@RequestMapping("v1/mechanic-service-category")
public class MechanicServiceCategoryController {
    private final MechanicServiceCategoryService mechanicServiceCategoryService;

    public MechanicServiceCategoryController(MechanicServiceCategoryService mechanicServiceCategoryService) {
        this.mechanicServiceCategoryService = mechanicServiceCategoryService;

    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> getAllMechanicServiceCategory() {

        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS, mechanicServiceCategoryService.getAllMechanicServiceCategory(),
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @GetMapping(value = "/{mechanicServiceCategoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> getMechanicServiceCategory(@PathVariable long mechanicServiceCategoryId) {

        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS, mechanicServiceCategoryService.getMechanicServiceCategory(mechanicServiceCategoryId),
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> updateMechanicServiceCategory(@RequestBody UpdateAndSaveMechanicServiceCategoryRequestDTO dto) {
        mechanicServiceCategoryService.updateMechanicServiceCategory(dto);
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS,
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> saveMechanicServiceCategory(@RequestBody UpdateAndSaveMechanicServiceCategoryRequestDTO dto) {
        mechanicServiceCategoryService.saveMechanicServiceCategory(dto);
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS,
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{mechanicServiceCategoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> mechanicServiceCategoryDelete(@PathVariable long mechanicServiceCategoryId) {
        mechanicServiceCategoryService.deleteMechanicServiceCategory(mechanicServiceCategoryId);
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS,
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> itemFilter(@RequestBody MechanicServiceCategoryFilterRequestDTO dto) {
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS, mechanicServiceCategoryService.getMechanicServiceCategoryFilter(dto),
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

}
