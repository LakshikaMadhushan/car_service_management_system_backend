package com.esoft.carservice.service.Impl;

import com.esoft.carservice.configuration.exception.CustomException;
import com.esoft.carservice.configuration.exception.ServiceException;
import com.esoft.carservice.dto.requset.UpdateSaveItemRequestDTO;
import com.esoft.carservice.dto.responce.GetItemResponseDTO;
import com.esoft.carservice.entity.Item;
import com.esoft.carservice.repository.ItemRepository;
import com.esoft.carservice.service.ItemService;
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
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<GetItemResponseDTO> getAllItem() {
        log.info("Execute method getAllItem : ");
        try {
            List<Item> itemList = itemRepository.findAll();
            List<GetItemResponseDTO> itemResponceDTOList = new ArrayList<>();
            for (Item item : itemList) {
                GetItemResponseDTO getItemResponseDTO = new GetItemResponseDTO();
                getItemResponseDTO.setBrand(item.getBrand());
                getItemResponseDTO.setBuyingPrice(item.getBuyingPrice());
                getItemResponseDTO.setItemId(item.getItemId());
                getItemResponseDTO.setItemName(item.getItemName());
                getItemResponseDTO.setQuantity(item.getQuantity());
                getItemResponseDTO.setSellingPrice(item.getSellingPrice());
                getItemResponseDTO.setItemStatus(item.getItemStatus());
                itemResponceDTOList.add(getItemResponseDTO);
            }
            return itemResponceDTOList;
        } catch (Exception e) {
            log.error("Method getAllItem : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public GetItemResponseDTO getItem(long id) {
        log.info("Execute method getItem :  @param : {}", id);
        try {
            Optional<Item> optionalItem = itemRepository.findById(id);
            if (!optionalItem.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the item you are finding cannot be found. ");
            }
            Item item = optionalItem.get();
            GetItemResponseDTO getItemResponseDTO = new GetItemResponseDTO();
            getItemResponseDTO.setBrand(item.getBrand());
            getItemResponseDTO.setBuyingPrice(item.getBuyingPrice());
            getItemResponseDTO.setItemId(item.getItemId());
            getItemResponseDTO.setItemName(item.getItemName());
            getItemResponseDTO.setQuantity(item.getQuantity());
            getItemResponseDTO.setSellingPrice(item.getSellingPrice());
            getItemResponseDTO.setItemStatus(item.getItemStatus());
            return getItemResponseDTO;
        } catch (Exception e) {
            log.error("Method getItem : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateItem(UpdateSaveItemRequestDTO requestDTO) {
        log.info("Execute method updateItem : @param : {} ", requestDTO);
        try {
            Optional<Item> optionalItem = itemRepository.findById(requestDTO.getItemId());
            if (!optionalItem.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the item you are finding cannot be found. ");
            }

            List<Item> itemList = itemRepository.findItemByItemNameUpdate(requestDTO.itemName, requestDTO.getItemId());

            if (!itemList.isEmpty()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the item name already used. ");
            }

            Item item = optionalItem.get();
            item.setBrand(item.getBrand());
            item.setBuyingPrice(item.getBuyingPrice());
            item.setItemId(item.getItemId());
            item.setItemName(item.getItemName());
            item.setQuantity(item.getQuantity());
            item.setSellingPrice(item.getSellingPrice());
            item.setItemStatus(item.getItemStatus());

            itemRepository.save(item);
        } catch (Exception e) {
            log.error("Method updateItem : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveItem(UpdateSaveItemRequestDTO requestDTO) {
        log.info("Execute method saveItem : @param : {} ", requestDTO);
        try {

            List<Item> itemList = itemRepository.findItemByItemName(requestDTO.itemName);

            if (!itemList.isEmpty()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the item name already used. ");
            }

            Item item = new Item();
            item.setBrand(item.getBrand());
            item.setBuyingPrice(item.getBuyingPrice());
            item.setItemId(item.getItemId());
            item.setItemName(item.getItemName());
            item.setQuantity(item.getQuantity());
            item.setSellingPrice(item.getSellingPrice());
            item.setItemStatus(item.getItemStatus());

            itemRepository.save(item);
        } catch (Exception e) {
            log.error("Method saveItem : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }
}
