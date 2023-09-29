package com.esoft.carservice.service;

import com.esoft.carservice.dto.requset.MechanicServiceCategoryFilterRequestDTO;
import com.esoft.carservice.dto.requset.UpdateAndSaveMechanicServiceCategoryRequestDTO;
import com.esoft.carservice.dto.responce.GetAllMechanicServiceCategoryResponseDTO;

import java.util.List;

public interface MechanicServiceCategoryService {
    List<GetAllMechanicServiceCategoryResponseDTO> getAllMechanicServiceCategory();

    GetAllMechanicServiceCategoryResponseDTO getMechanicServiceCategory(long id);

    void updateMechanicServiceCategory(UpdateAndSaveMechanicServiceCategoryRequestDTO requestDTO);

    void saveMechanicServiceCategory(UpdateAndSaveMechanicServiceCategoryRequestDTO requestDTO);

    List<GetAllMechanicServiceCategoryResponseDTO> getMechanicServiceCategoryFilter(MechanicServiceCategoryFilterRequestDTO requestDTO);

    void deleteMechanicServiceCategory(long id);
}
