package com.esoft.carservice.service.Impl;

import com.esoft.carservice.configuration.exception.CustomException;
import com.esoft.carservice.configuration.exception.ServiceException;
import com.esoft.carservice.dto.requset.SaveServiceDetailsRequestDTO;
import com.esoft.carservice.dto.requset.ServiceDetailsFilterRequestDTO;
import com.esoft.carservice.dto.responce.GetServiceDetailsResponseDTO;
import com.esoft.carservice.entity.Item;
import com.esoft.carservice.entity.MechanicService;
import com.esoft.carservice.entity.ServiceDetails;
import com.esoft.carservice.enums.ServiceDetailsType;
import com.esoft.carservice.repository.ItemRepository;
import com.esoft.carservice.repository.MechanicServiceRepository;
import com.esoft.carservice.repository.ServiceDetailsRepository;
import com.esoft.carservice.repository.ServiceRepository;
import com.esoft.carservice.service.VehicleServiceDetailsService;
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
public class VehicleServiceDetailsServiceImpl implements VehicleServiceDetailsService {
    private final ServiceDetailsRepository serviceDetailsRepository;
    private final ItemRepository itemRepository;
    private final MechanicServiceRepository mechanicServiceRepository;
    private final ServiceRepository serviceRepository;

    public VehicleServiceDetailsServiceImpl(ServiceDetailsRepository serviceDetailsRepository, ItemRepository itemRepository, MechanicServiceRepository mechanicServiceRepository, ServiceRepository serviceRepository) {
        this.serviceDetailsRepository = serviceDetailsRepository;
        this.itemRepository = itemRepository;
        this.mechanicServiceRepository = mechanicServiceRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public GetServiceDetailsResponseDTO getServiceDetail(long id) {
        log.info("Execute method getServiceDetail :  @param : {}", id);
        try {
            Optional<ServiceDetails> optionalServiceDetails = serviceDetailsRepository.findById(id);
            if (!optionalServiceDetails.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the service details you are finding cannot be found. ");
            }
            ServiceDetails serviceDetails = optionalServiceDetails.get();

            GetServiceDetailsResponseDTO getServiceDetailsResponseDTO = new GetServiceDetailsResponseDTO();

            getServiceDetailsResponseDTO.setServiceDetailsId(serviceDetails.getServiceDetailsId());
            getServiceDetailsResponseDTO.setType(serviceDetails.getType());
            getServiceDetailsResponseDTO.setCost(serviceDetails.getCost());
            if (serviceDetails.getType() == ServiceDetailsType.SERVICE) {
                getServiceDetailsResponseDTO.setItemId(serviceDetails.getMechanicService().getMechanicServiceId());
                getServiceDetailsResponseDTO.setItemName(serviceDetails.getMechanicService().getName());
            } else if (serviceDetails.getType() == ServiceDetailsType.ITEM) {
                getServiceDetailsResponseDTO.setItemId(serviceDetails.getItem().getItemId());
                getServiceDetailsResponseDTO.setItemName(serviceDetails.getItem().getItemName());
            }

            return getServiceDetailsResponseDTO;
        } catch (Exception e) {
            log.error("Method getServiceDetail : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateServiceDetail(SaveServiceDetailsRequestDTO requestDTO) {
        log.info("Execute method updateServiceDetail : @param : {} ", requestDTO);
        try {

            Optional<ServiceDetails> optionalServiceDetails = serviceDetailsRepository.findById(requestDTO.getServiceId());
            if (!optionalServiceDetails.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the service you are finding cannot be found. ");
            }
            ServiceDetails serviceDetails = optionalServiceDetails.get();

            serviceDetails.setType(requestDTO.getType());

            if (requestDTO.getType() == ServiceDetailsType.ITEM) {
                Optional<Item> optionalItem = itemRepository.findById(requestDTO.getItemId());
                if (!optionalItem.isPresent()) {
                    throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the item you are finding cannot be found. ");
                }
                Item item = optionalItem.get();
                serviceDetails.setCost(item.getSellingPrice());
                serviceDetails.setItem(item);
            } else if (requestDTO.getType() == ServiceDetailsType.SERVICE) {
                Optional<MechanicService> optionalMechanicService = mechanicServiceRepository.findById(requestDTO.getItemId());
                if (!optionalMechanicService.isPresent()) {
                    throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the mechanic service you are finding cannot be found. ");
                }
                MechanicService mechanicService = optionalMechanicService.get();
                serviceDetails.setCost(mechanicService.getPrice());
                serviceDetails.setMechanicService(mechanicService);
            }


            serviceDetailsRepository.save(serviceDetails);


        } catch (Exception e) {
            log.error("Method updateServiceDetail : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }


    }

    @Override
    public List<GetServiceDetailsResponseDTO> getServiceFilterDetail(ServiceDetailsFilterRequestDTO requestDTO) {
        log.info("Execute method getServiceFilterDetail :  @param : {}", requestDTO);
        try {
            List<GetServiceDetailsResponseDTO> responseDTOList = new ArrayList<>();

            String type = null;
            if (requestDTO.getType() != null) {
                type = requestDTO.getType().toString();
            }

            List<ServiceDetails> allServiceDetailFilter = serviceDetailsRepository.getAllServiceDetailFilter(requestDTO.getServiceDetailId(), requestDTO.getServiceId(), requestDTO.getItemId(), requestDTO.getMechanicServiceId(), type);
            for (ServiceDetails serviceDetails : allServiceDetailFilter) {
                GetServiceDetailsResponseDTO getServiceDetailsResponseDTO = new GetServiceDetailsResponseDTO();

                getServiceDetailsResponseDTO.setServiceDetailsId(serviceDetails.getServiceDetailsId());
                getServiceDetailsResponseDTO.setServiceDetailsId(serviceDetails.getService().getServiceId());
                getServiceDetailsResponseDTO.setType(serviceDetails.getType());
                getServiceDetailsResponseDTO.setCost(serviceDetails.getCost());
                if (serviceDetails.getType() == ServiceDetailsType.SERVICE) {
                    getServiceDetailsResponseDTO.setItemId(serviceDetails.getMechanicService().getMechanicServiceId());
                    getServiceDetailsResponseDTO.setItemName(serviceDetails.getMechanicService().getName());
                    if (serviceDetails.getMechanicService().getMechanicServiceCategory() != null) {
                        getServiceDetailsResponseDTO.setCategoryId(serviceDetails.getMechanicService().getMechanicServiceCategory().getMechanicServiceCategoryId());
                        getServiceDetailsResponseDTO.setCategoryName(serviceDetails.getMechanicService().getMechanicServiceCategory().getName());
                    }
                } else if (serviceDetails.getType() == ServiceDetailsType.ITEM) {
                    getServiceDetailsResponseDTO.setItemId(serviceDetails.getItem().getItemId());
                    getServiceDetailsResponseDTO.setItemName(serviceDetails.getItem().getItemName());

                    if (serviceDetails.getItem().getItemCategory() != null) {
                        getServiceDetailsResponseDTO.setCategoryId(serviceDetails.getItem().getItemCategory().getItemCategoryId());
                        getServiceDetailsResponseDTO.setCategoryName(serviceDetails.getItem().getItemCategory().getName());
                    }
                }
                responseDTOList.add(getServiceDetailsResponseDTO);
            }


            return responseDTOList;
        } catch (Exception e) {
            log.error("Method getServiceFilterDetail : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveServiceDetail(SaveServiceDetailsRequestDTO requestDTO) {
        log.info("Execute method saveServiceDetail : @param : {} ", requestDTO);
        try {

            ServiceDetails serviceDetails = new ServiceDetails();

            serviceDetails.setType(requestDTO.getType());

            if (requestDTO.getType() == ServiceDetailsType.ITEM) {
                Optional<Item> optionalItem = itemRepository.findById(requestDTO.getItemId());
                if (!optionalItem.isPresent()) {
                    throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the item you are finding cannot be found. ");
                }
                Item item = optionalItem.get();
                serviceDetails.setCost(item.getSellingPrice());
                serviceDetails.setItem(item);
            } else if (requestDTO.getType() == ServiceDetailsType.SERVICE) {
                Optional<MechanicService> optionalMechanicService = mechanicServiceRepository.findById(requestDTO.getItemId());
                if (!optionalMechanicService.isPresent()) {
                    throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the mechanic service you are finding cannot be found. ");
                }
                MechanicService mechanicService = optionalMechanicService.get();
                serviceDetails.setCost(mechanicService.getPrice());
                serviceDetails.setMechanicService(mechanicService);
            }

            Optional<com.esoft.carservice.entity.Service> optionalService = serviceRepository.findById(requestDTO.getServiceId());
            if (!optionalService.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the service you are finding cannot be found. ");
            }
            com.esoft.carservice.entity.Service service = optionalService.get();
            serviceDetails.setService(service);
            serviceDetailsRepository.save(serviceDetails);


        } catch (Exception e) {
            log.error("Method saveServiceDetail : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }
}
