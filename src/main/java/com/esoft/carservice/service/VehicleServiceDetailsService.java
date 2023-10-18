package com.esoft.carservice.service;

import com.esoft.carservice.dto.requset.SaveServiceDetailsRequestDTO;
import com.esoft.carservice.dto.requset.ServiceDetailsFilterRequestDTO;
import com.esoft.carservice.dto.responce.GetServiceDetailsResponseDTO;

import java.util.List;

public interface VehicleServiceDetailsService {

    public GetServiceDetailsResponseDTO getServiceDetail(long id);

    public void updateServiceDetail(SaveServiceDetailsRequestDTO requestDTO);

    public List<GetServiceDetailsResponseDTO> getServiceFilterDetail(ServiceDetailsFilterRequestDTO requestDTO);

    public void saveServiceDetail(SaveServiceDetailsRequestDTO requestDTO);

}
