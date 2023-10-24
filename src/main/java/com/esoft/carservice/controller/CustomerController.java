package com.esoft.carservice.controller;

import com.esoft.carservice.dto.common.CommonResponse;
import com.esoft.carservice.dto.requset.CustomerFilterRequestDTO;
import com.esoft.carservice.dto.requset.UpdateSaveCustomer;
import com.esoft.carservice.service.CustomerService;
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
@RequestMapping("v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> getAllCustomer() {

        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS, customerService.getAllCustomer(),
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @GetMapping(value = "/{CustomerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> getCustomer(@PathVariable long CustomerId) {

        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS, customerService.getCustomer(CustomerId),
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> updateCustomer(@RequestBody UpdateSaveCustomer dto) {
        customerService.updateCustomer(dto);
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS,
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> saveCustomer(@RequestBody UpdateSaveCustomer dto) {
        customerService.saveCustomer(dto);
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS,
                SUCCESS_RESPONSE), HttpStatus.OK);
    }

    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> itemFilter(@RequestBody CustomerFilterRequestDTO dto) {
        return new ResponseEntity<>(new CommonResponse(OPERATION_SUCCESS, customerService.getCustomerFilter(dto),
                SUCCESS_RESPONSE), HttpStatus.OK);
    }
}
