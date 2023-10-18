package com.esoft.carservice.service.Impl;

import com.esoft.carservice.dto.requset.SaveServiceDetailsRequestDTO;
import com.esoft.carservice.dto.requset.ServiceDetailsFilterRequestDTO;
import com.esoft.carservice.dto.responce.GetServiceDetailsResponseDTO;
import com.esoft.carservice.service.VehicleServiceDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VehicleServiceDetailsServiceImpl implements VehicleServiceDetailsService {


    @Override
    public GetServiceDetailsResponseDTO getServiceDetail(long id) {
        return null;
    }

    @Override
    public void updateServiceDetail(SaveServiceDetailsRequestDTO requestDTO) {

    }

    @Override
    public List<GetServiceDetailsResponseDTO> getServiceFilterDetail(ServiceDetailsFilterRequestDTO requestDTO) {
        return null;
    }

    @Override
    public void saveServiceDetail(SaveServiceDetailsRequestDTO requestDTO) {

    }
}
