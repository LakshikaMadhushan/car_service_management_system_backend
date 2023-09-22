package com.esoft.carservice.service;

import com.esoft.carservice.dto.requset.ItemCategoryFilterRequestDTO;
import com.esoft.carservice.dto.requset.UpdateSaveItemCategoryRequestDTO;
import com.esoft.carservice.dto.responce.GetAllItemCategoryResponseDTO;

import java.util.List;

public interface ItemCategoryService {
    public List<GetAllItemCategoryResponseDTO> getAllCategory();

    public GetAllItemCategoryResponseDTO getCategory(long id);

    public void updateCategory(UpdateSaveItemCategoryRequestDTO requestDTO);

    public void saveCategory(UpdateSaveItemCategoryRequestDTO requestDTO);

    public List<GetAllItemCategoryResponseDTO> getCategoryFilter(ItemCategoryFilterRequestDTO requestDTO);

    public void deleteCategory(long id);
}
