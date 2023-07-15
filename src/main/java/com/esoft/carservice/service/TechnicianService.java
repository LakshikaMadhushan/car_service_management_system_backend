package com.esoft.carservice.service;

import com.esoft.carservice.dto.requset.UpdateAndSaveTechnicianRequestDTO;
import com.esoft.carservice.dto.responce.GetTechnicianResponseDTO;

import java.util.List;

public interface TechnicianService {
    public List<GetTechnicianResponseDTO> getAllTechnician();

    public GetTechnicianResponseDTO getTechnician(long id);

    public void updateTechnician(UpdateAndSaveTechnicianRequestDTO requestDTO);

    public void saveTechnician(UpdateAndSaveTechnicianRequestDTO requestDTO);
}
