package com.esoft.carservice.service;

import com.esoft.carservice.dto.requset.ItemFilterRequestDTO;
import com.esoft.carservice.dto.requset.UpdateSaveItemRequestDTO;
import com.esoft.carservice.dto.responce.GetItemResponseDTO;

import java.util.List;

public interface ItemService {
    public List<GetItemResponseDTO> getAllItem();

    public GetItemResponseDTO getItem(long id);

    public void updateItem(UpdateSaveItemRequestDTO requestDTO);

    public void saveItem(UpdateSaveItemRequestDTO requestDTO);

    public List<GetItemResponseDTO> getItemFilter(ItemFilterRequestDTO requestDTO);
}
