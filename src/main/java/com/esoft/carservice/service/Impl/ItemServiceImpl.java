package com.esoft.carservice.service.Impl;

import com.esoft.carservice.configuration.exception.CustomException;
import com.esoft.carservice.configuration.exception.ServiceException;
import com.esoft.carservice.dto.requset.UpdateSaveItemRequestDTO;
import com.esoft.carservice.dto.responce.GetItemResponceDTO;
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
    public List<GetItemResponceDTO> getAllItem() {
        log.info("Execute method getAllItem : ");
        try {
            List<Item> itemList = itemRepository.findAll();
            List<GetItemResponceDTO> itemResponceDTOList = new ArrayList<>();
            for (Item item : itemList) {
                GetItemResponceDTO getItemResponceDTO = new GetItemResponceDTO();
                getItemResponceDTO.setBrand(item.getBrand());
                getItemResponceDTO.setBuyingPrice(item.getBuyingPrice());
                getItemResponceDTO.setItemId(item.getItemId());
                getItemResponceDTO.setItemName(item.getItemName());
                getItemResponceDTO.setQuantity(item.getQuantity());
                getItemResponceDTO.setSellingPrice(item.getSellingPrice());
                getItemResponceDTO.setItemStatus(item.getItemStatus());
                itemResponceDTOList.add(getItemResponceDTO);
            }
            return itemResponceDTOList;
        } catch (Exception e) {
            log.error("Method getAllItem : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public GetItemResponceDTO getItem(long id) {
        log.info("Execute method getItem : ");
        try {
            Optional<Item> optionalItem = itemRepository.findById(id);
            if (!optionalItem.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the item you are finding cannot be found. ");
            }
            Item item = optionalItem.get();
            GetItemResponceDTO getItemResponceDTO = new GetItemResponceDTO();
            getItemResponceDTO.setBrand(item.getBrand());
            getItemResponceDTO.setBuyingPrice(item.getBuyingPrice());
            getItemResponceDTO.setItemId(item.getItemId());
            getItemResponceDTO.setItemName(item.getItemName());
            getItemResponceDTO.setQuantity(item.getQuantity());
            getItemResponceDTO.setSellingPrice(item.getSellingPrice());
            getItemResponceDTO.setItemStatus(item.getItemStatus());
            return getItemResponceDTO;
        } catch (Exception e) {
            log.error("Method getItem : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public void updateItem(UpdateSaveItemRequestDTO requestDTO) {
        log.info("Execute method updateItem : @param : {} ", requestDTO);
        try {
            Optional<Item> optionalItem = itemRepository.findById(requestDTO.itemId);
            if (!optionalItem.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the item you are finding cannot be found. ");
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
    public void saveItem(UpdateSaveItemRequestDTO requestDTO) {
        log.info("Execute method saveItem : @param : {} ", requestDTO);
        try {
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
