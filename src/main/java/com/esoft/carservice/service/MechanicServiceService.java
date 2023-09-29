package com.esoft.carservice.service;

import com.esoft.carservice.dto.requset.MechanicServiceFilterRequestDTO;
import com.esoft.carservice.dto.requset.UpdateSaveMechanicServiceRequestDTO;
import com.esoft.carservice.dto.responce.GetMechanicServiceResponseDTO;

import java.util.List;

public interface MechanicServiceService {
    public List<GetMechanicServiceResponseDTO> getAllMechanicService();

    public GetMechanicServiceResponseDTO getMechanicService(long id);

    public void updateMechanicService(UpdateSaveMechanicServiceRequestDTO requestDTO);

    public void saveMechanicService(UpdateSaveMechanicServiceRequestDTO requestDTO);

    public List<GetMechanicServiceResponseDTO> getMechanicServiceFilter(MechanicServiceFilterRequestDTO requestDTO);

    public void deleteMechanicService(long id);
}
