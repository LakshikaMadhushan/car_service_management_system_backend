package com.esoft.carservice.service;

import com.esoft.carservice.dto.requset.UpdateSaveItemRequestDTO;
import com.esoft.carservice.dto.responce.GetItemResponceDTO;

import java.util.List;

public interface ItemService {
    public List<GetItemResponceDTO> getAllItem();

    public GetItemResponceDTO getItem(long id);

    public void updateItem(UpdateSaveItemRequestDTO requestDTO);

    public void saveItem(UpdateSaveItemRequestDTO requestDTO);
}
