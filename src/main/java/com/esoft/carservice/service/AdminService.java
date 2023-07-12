package com.esoft.carservice.service;

import com.esoft.carservice.dto.requset.UpdateSaveAdminRequestDTO;
import com.esoft.carservice.dto.responce.GetAdminResponseDTO;

import java.util.List;

public interface AdminService {
    public List<GetAdminResponseDTO> getAllAdmin();

    public GetAdminResponseDTO getAdmin(long id);

    public void updateAdmin(UpdateSaveAdminRequestDTO requestDTO);

    public void saveAdmin(UpdateSaveAdminRequestDTO requestDTO);
}
