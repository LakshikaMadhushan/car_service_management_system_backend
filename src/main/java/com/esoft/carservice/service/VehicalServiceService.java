package com.esoft.carservice.service;

import com.esoft.carservice.dto.requset.SaveServiceRequestDTO;
import com.esoft.carservice.dto.requset.ServiceFilterRequestDTO;
import com.esoft.carservice.dto.requset.UpdateAndSaveServiceRequestDTO;
import com.esoft.carservice.dto.responce.GetServiceResponseDTO;

import java.util.List;

public interface VehicalServiceService {

    public GetServiceResponseDTO getService(long id);

    public void updateService(UpdateAndSaveServiceRequestDTO requestDTO);

    public List<GetServiceResponseDTO> getServiceFilter(ServiceFilterRequestDTO requestDTO);

    public void saveService(SaveServiceRequestDTO requestDTO);

}
