package com.esoft.carservice.service;

import com.esoft.carservice.dto.requset.TechnicianFilterRequestDTO;
import com.esoft.carservice.dto.requset.UpdateAndSaveTechnicianRequestDTO;
import com.esoft.carservice.dto.responce.GetTechnicianResponseDTO;

import java.util.List;

public interface TechnicianService {
    public List<GetTechnicianResponseDTO> getAllTechnician();

    public GetTechnicianResponseDTO getTechnician(long id);

    public void updateTechnician(UpdateAndSaveTechnicianRequestDTO requestDTO);

    public void saveTechnician(UpdateAndSaveTechnicianRequestDTO requestDTO);

    public List<GetTechnicianResponseDTO> getTechnicianFilter(TechnicianFilterRequestDTO requestDTO);

    public void deleteTechnician(long id);
}
