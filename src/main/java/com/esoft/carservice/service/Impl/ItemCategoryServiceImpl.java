package com.esoft.carservice.service.Impl;

import com.esoft.carservice.configuration.exception.CustomException;
import com.esoft.carservice.configuration.exception.ServiceException;
import com.esoft.carservice.dto.requset.ItemCategoryFilterRequestDTO;
import com.esoft.carservice.dto.requset.UpdateSaveItemCategoryRequestDTO;
import com.esoft.carservice.dto.responce.GetAllItemCategoryResponseDTO;
import com.esoft.carservice.entity.ItemCategory;
import com.esoft.carservice.repository.ItemCategoryRepository;
import com.esoft.carservice.service.ItemCategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.esoft.carservice.constant.ResponseCodes.OPERATION_FAILED;
import static com.esoft.carservice.constant.ResponseCodes.RESOURCE_NOT_FOUND;
import static com.esoft.carservice.constant.ResponseMessages.UNEXPECTED_ERROR_OCCURRED;

@Log4j2
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ItemCategoryServiceImpl implements ItemCategoryService {
    private final ItemCategoryRepository itemCategoryRepository;

    public ItemCategoryServiceImpl(ItemCategoryRepository itemCategoryRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
    }

    @Override
    public List<GetAllItemCategoryResponseDTO> getAllCategory() {
        log.info("Execute method getAllCategory : ");
        try {
            List<ItemCategory> itemCategoryList = itemCategoryRepository.findAll();
            List<GetAllItemCategoryResponseDTO> itemCategoryResponseDTOList = new ArrayList<>();
            for (ItemCategory itemCategory : itemCategoryList) {
                GetAllItemCategoryResponseDTO getAllItemCategoryResponseDTO = new GetAllItemCategoryResponseDTO();
                getAllItemCategoryResponseDTO.setCategoryId(itemCategory.getItemCategoryId());
                getAllItemCategoryResponseDTO.setCategoryName(itemCategory.getName());
            }
            return itemCategoryResponseDTOList;
        } catch (Exception e) {
            log.error("Method getAllCategory : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public GetAllItemCategoryResponseDTO getCategory(long id) {
        log.info("Execute method getCategory :  @param : {}", id);
        try {

            Optional<ItemCategory> optionalItemCategory = itemCategoryRepository.findById(id);
            if (!optionalItemCategory.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the item category you are finding cannot be found. ");
            }
            ItemCategory itemCategory = optionalItemCategory.get();
            GetAllItemCategoryResponseDTO getAllItemCategoryResponseDTO = new GetAllItemCategoryResponseDTO();
            getAllItemCategoryResponseDTO.setCategoryId(itemCategory.getItemCategoryId());
            getAllItemCategoryResponseDTO.setCategoryName(itemCategory.getName());

            return getAllItemCategoryResponseDTO;
        } catch (Exception e) {
            log.error("Method getCategory : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateCategory(UpdateSaveItemCategoryRequestDTO requestDTO) {
        log.info("Execute method updateCategory : @param : {} ", requestDTO);
        try {

            Optional<ItemCategory> optionalItemCategory = itemCategoryRepository.findById(requestDTO.getCategoryId());
            if (!optionalItemCategory.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the item category you are finding cannot be found. ");
            }

            List<ItemCategory> itemCategoryList = itemCategoryRepository.findItemByItemCategoryNameUpdate(requestDTO.getCategoryName(), requestDTO.getCategoryId());

            if (!itemCategoryList.isEmpty()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the item category name already used. ");
            }

            ItemCategory itemCategory = optionalItemCategory.get();
            itemCategory.setName(requestDTO.getCategoryName());


            itemCategoryRepository.save(itemCategory);
        } catch (Exception e) {
            log.error("Method updateCategory : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, e.getMessage());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveCategory(UpdateSaveItemCategoryRequestDTO requestDTO) {
        log.info("Execute method saveCategory : @param : {} ", requestDTO);
        try {

            List<ItemCategory> itemCategoryList = itemCategoryRepository.findItemCategoryByItemName(requestDTO.getCategoryName());

            if (!itemCategoryList.isEmpty()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the item category name already used. ");
            }

            ItemCategory itemCategory = new ItemCategory();
            itemCategory.setName(requestDTO.getCategoryName());

            itemCategoryRepository.save(itemCategory);
        } catch (Exception e) {
            log.error("Method saveCategory : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, e.getMessage());
        }
    }

    @Override
    public List<GetAllItemCategoryResponseDTO> getCategoryFilter(ItemCategoryFilterRequestDTO requestDTO) {
        log.info("Execute method getCategoryFilter : @param : {} ", requestDTO);
        try {
            List<ItemCategory> itemCategoryList = itemCategoryRepository.getAllItemCategoryFilter(requestDTO.getCategoryName(), requestDTO.getCategoryId());
            List<GetAllItemCategoryResponseDTO> itemCategoryResponseDTOList = new ArrayList<>();
            for (ItemCategory itemCategory : itemCategoryList) {
                GetAllItemCategoryResponseDTO getAllItemCategoryResponseDTO = new GetAllItemCategoryResponseDTO();
                getAllItemCategoryResponseDTO.setCategoryId(itemCategory.getItemCategoryId());
                getAllItemCategoryResponseDTO.setCategoryName(itemCategory.getName());

                itemCategoryResponseDTOList.add(getAllItemCategoryResponseDTO);
            }
            return itemCategoryResponseDTOList;
        } catch (Exception e) {
            log.error("Method getCategoryFilter : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteCategory(long id) {
        log.info("Execute method deleteCategory :  @param : {}", id);
        try {
            Optional<ItemCategory> optionalItemCategory = itemCategoryRepository.findById(id);
            if (!optionalItemCategory.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the item category you are finding cannot be found. ");
            }
            ItemCategory itemCategory = optionalItemCategory.get();

            itemCategoryRepository.delete(itemCategory);
        } catch (Exception e) {
            log.error("Method deleteCategory : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }
}
