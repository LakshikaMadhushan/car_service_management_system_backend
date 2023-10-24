package com.esoft.carservice.service;

import com.esoft.carservice.dto.requset.CustomerFilterRequestDTO;
import com.esoft.carservice.dto.requset.UpdateSaveCustomer;
import com.esoft.carservice.dto.responce.GetCustomerResponseDTO;

import java.util.List;

public interface CustomerService {
    List<GetCustomerResponseDTO> getAllCustomer();

    GetCustomerResponseDTO getCustomer(long id);

    void updateCustomer(UpdateSaveCustomer requestDTO);

    void saveCustomer(UpdateSaveCustomer requestDTO);

    public List<GetCustomerResponseDTO> getCustomerFilter(CustomerFilterRequestDTO requestDTO);
}
