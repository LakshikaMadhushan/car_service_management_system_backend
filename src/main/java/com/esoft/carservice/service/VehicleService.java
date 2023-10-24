package com.esoft.carservice.service;

import com.esoft.carservice.dto.requset.UpdateSaveVehicleRequestDTO;
import com.esoft.carservice.dto.requset.VehicleFilterRequestDTO;
import com.esoft.carservice.dto.responce.GetVehicleResponseDTO;

import java.util.List;

public interface VehicleService {
    public List<GetVehicleResponseDTO> getAllVehicle();

    public GetVehicleResponseDTO getVehicle(long id);

    public void updateVehicle(UpdateSaveVehicleRequestDTO requestDTO);

    public void saveVehicle(UpdateSaveVehicleRequestDTO requestDTO);

    public List<GetVehicleResponseDTO> getVehicleFilter(VehicleFilterRequestDTO requestDTO);

}
