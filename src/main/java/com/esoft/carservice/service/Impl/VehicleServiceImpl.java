package com.esoft.carservice.service.Impl;

import com.esoft.carservice.dto.requset.UpdateSaveVehicleRequestDTO;
import com.esoft.carservice.dto.requset.VehicleFilterRequestDTO;
import com.esoft.carservice.dto.responce.GetVehicleResponseDTO;
import com.esoft.carservice.repository.CustomerRepository;
import com.esoft.carservice.repository.TechnicianRepository;
import com.esoft.carservice.service.VehicleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VehicleServiceImpl implements VehicleService {
    private final TechnicianRepository technicianRepository;
    private final CustomerRepository customerRepository;

    public VehicleServiceImpl(TechnicianRepository technicianRepository, CustomerRepository customerRepository) {
        this.technicianRepository = technicianRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public GetVehicleResponseDTO getVehicle(long id) {
        return null;
    }

    @Override
    public void updateVehicle(UpdateSaveVehicleRequestDTO requestDTO) {

    }

    @Override
    public void saveVehicle(UpdateSaveVehicleRequestDTO requestDTO) {

    }

    @Override
    public List<GetVehicleResponseDTO> getVehicleFilter(VehicleFilterRequestDTO requestDTO) {
        return null;
    }
}
