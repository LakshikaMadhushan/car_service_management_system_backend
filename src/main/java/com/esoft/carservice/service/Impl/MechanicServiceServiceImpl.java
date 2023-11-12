package com.esoft.carservice.service.Impl;

import com.esoft.carservice.configuration.exception.CustomException;
import com.esoft.carservice.configuration.exception.ServiceException;
import com.esoft.carservice.dto.requset.MechanicServiceFilterRequestDTO;
import com.esoft.carservice.dto.requset.UpdateSaveMechanicServiceRequestDTO;
import com.esoft.carservice.dto.responce.GetMechanicServiceResponseDTO;
import com.esoft.carservice.entity.MechanicService;
import com.esoft.carservice.entity.MechanicServiceCategory;
import com.esoft.carservice.repository.MechanicServiceCategoryRepository;
import com.esoft.carservice.repository.MechanicServiceRepository;
import com.esoft.carservice.service.MechanicServiceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.esoft.carservice.constant.ResponseCodes.*;
import static com.esoft.carservice.constant.ResponseMessages.UNEXPECTED_ERROR_OCCURRED;

@Log4j2
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MechanicServiceServiceImpl implements MechanicServiceService {
    private final MechanicServiceRepository mechanicServiceRepository;
    private final MechanicServiceCategoryRepository mechanicServiceCategoryRepository;

    public MechanicServiceServiceImpl(MechanicServiceRepository mechanicServiceRepository, MechanicServiceCategoryRepository mechanicServiceCategoryRepository) {
        this.mechanicServiceRepository = mechanicServiceRepository;
        this.mechanicServiceCategoryRepository = mechanicServiceCategoryRepository;
    }

    @Override
    public List<GetMechanicServiceResponseDTO> getAllMechanicService() {
        log.info("Execute method getAllMechanicService : ");
        try {
            List<MechanicService> mechanicServiceList = mechanicServiceRepository.findAll();
            List<GetMechanicServiceResponseDTO> mechanicServiceResponseDTOList = new ArrayList<>();
            for (MechanicService mechanicService : mechanicServiceList) {
                GetMechanicServiceResponseDTO getMechanicServiceResponseDTO = new GetMechanicServiceResponseDTO();
                getMechanicServiceResponseDTO.setMechanicServiceId(mechanicService.getMechanicServiceId());
                getMechanicServiceResponseDTO.setName(mechanicService.getName());
                getMechanicServiceResponseDTO.setPrice(mechanicService.getPrice());
                getMechanicServiceResponseDTO.setVehicleType(mechanicService.getVehicleType());
                if (mechanicService.getMechanicServiceCategory() != null) {
                    getMechanicServiceResponseDTO.setMechanicServiceCategoryId(mechanicService.getMechanicServiceCategory().getMechanicServiceCategoryId());
                    getMechanicServiceResponseDTO.setMechanicServiceCategoryName(mechanicService.getMechanicServiceCategory().getName());
                }
                mechanicServiceResponseDTOList.add(getMechanicServiceResponseDTO);
            }
            return mechanicServiceResponseDTOList;

        } catch (Exception e) {
            log.error("Method getAllMechanicService : " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public GetMechanicServiceResponseDTO getMechanicService(long id) {
        log.info("Execute method getMechanicService :  @param : {}", id);
        try {
            Optional<MechanicService> optionalMechanicService = mechanicServiceRepository.findById(id);
            if (!optionalMechanicService.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the mechanic service you are finding cannot be found. ");
            }
            MechanicService mechanicService = optionalMechanicService.get();
            GetMechanicServiceResponseDTO getMechanicServiceResponseDTO = new GetMechanicServiceResponseDTO();
            getMechanicServiceResponseDTO.setMechanicServiceId(mechanicService.getMechanicServiceId());
            getMechanicServiceResponseDTO.setName(mechanicService.getName());
            getMechanicServiceResponseDTO.setPrice(mechanicService.getPrice());
            getMechanicServiceResponseDTO.setVehicleType(mechanicService.getVehicleType());
            if (mechanicService.getMechanicServiceCategory() != null) {
                getMechanicServiceResponseDTO.setMechanicServiceCategoryId(mechanicService.getMechanicServiceCategory().getMechanicServiceCategoryId());
                getMechanicServiceResponseDTO.setMechanicServiceCategoryName(mechanicService.getMechanicServiceCategory().getName());
            }


            return getMechanicServiceResponseDTO;
        } catch (Exception e) {
            log.error("Method getMechanicService : " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateMechanicService(UpdateSaveMechanicServiceRequestDTO requestDTO) {
        log.info("Execute method updateMechanicService : @param : {} ", requestDTO);
        try {
            Optional<MechanicService> optionalMechanicService = mechanicServiceRepository.findById(requestDTO.getMechanicServiceId());
            if (!optionalMechanicService.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the mechanic service you are finding cannot be found. ");
            }
            List<MechanicService> mechanicServiceByName = mechanicServiceRepository.findMechanicServiceNameUpdate(requestDTO.getName(), requestDTO.getMechanicServiceId());
            if (!mechanicServiceByName.isEmpty()) {
                throw new ServiceException(DUPLICATE_INPUT, "Sorry, the mechanic service name already used. ");
            }
            if (requestDTO.getName() == null || requestDTO.getPrice() == 0) {
                throw new ServiceException(INVALID_INPUT, "Sorry, the price and name cannot be empty. ");
            }
            MechanicService mechanicService = optionalMechanicService.get();
            mechanicService.setName(requestDTO.getName());
            mechanicService.setPrice(requestDTO.getPrice());
            mechanicService.setVehicleType(requestDTO.getVehicleType());
            Optional<MechanicServiceCategory> optionalMechanicServiceCategory = mechanicServiceCategoryRepository.findById(requestDTO.getMechanicServiceCategoryId());
            if (optionalMechanicServiceCategory.isPresent()) {
                MechanicServiceCategory mechanicServiceCategory = optionalMechanicServiceCategory.get();
                mechanicService.setMechanicServiceCategory(mechanicServiceCategory);
            } else {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the mechanic service category you are finding cannot be found. ");
            }

            mechanicServiceRepository.save(mechanicService);
        } catch (Exception e) {
            log.error("Method updateMechanicService : " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveMechanicService(UpdateSaveMechanicServiceRequestDTO requestDTO) {
        log.info("Execute method saveMechanicService : @param : {} ", requestDTO);
        try {
            MechanicService mechanicService = new MechanicService();

            List<MechanicService> mechanicServiceByName = mechanicServiceRepository.findMechanicServiceByName(requestDTO.getName());
            if (!mechanicServiceByName.isEmpty()) {
                throw new ServiceException(DUPLICATE_INPUT, "Sorry, the mechanic service name already used. ");
            }

            mechanicService.setName(requestDTO.getName());
            mechanicService.setPrice(requestDTO.getPrice());
            mechanicService.setVehicleType(requestDTO.getVehicleType());


            Optional<MechanicServiceCategory> optionalMechanicServiceCategory = mechanicServiceCategoryRepository.findById(requestDTO.getMechanicServiceCategoryId());
            if (optionalMechanicServiceCategory.isPresent()) {
                MechanicServiceCategory mechanicServiceCategory = optionalMechanicServiceCategory.get();
                mechanicService.setMechanicServiceCategory(mechanicServiceCategory);

            } else {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the mechanic service category you are finding cannot be found. ");
            }

            mechanicServiceRepository.save(mechanicService);
        } catch (Exception e) {
            log.error("Method saveMechanicService : " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<GetMechanicServiceResponseDTO> getMechanicServiceFilter(MechanicServiceFilterRequestDTO requestDTO) {
        log.info("Execute method getItemFilter : @param : {} ", requestDTO);
        try {
            String vehicleType = null;
            if (requestDTO.getVehicleType() != null) {
                vehicleType = requestDTO.getVehicleType().toString();
            }

            List<MechanicService> mechanicServiceFilter = mechanicServiceRepository.getAllMechanicServiceFilter(requestDTO.getName(), requestDTO.getMechanicServiceId(), vehicleType);
            List<GetMechanicServiceResponseDTO> mechanicServiceResponseDTOList = new ArrayList<>();
            for (MechanicService mechanicService : mechanicServiceFilter) {
                GetMechanicServiceResponseDTO getMechanicServiceResponseDTO = new GetMechanicServiceResponseDTO();
                getMechanicServiceResponseDTO.setMechanicServiceId(mechanicService.getMechanicServiceId());
                getMechanicServiceResponseDTO.setName(mechanicService.getName());
                getMechanicServiceResponseDTO.setPrice(mechanicService.getPrice());
                getMechanicServiceResponseDTO.setVehicleType(mechanicService.getVehicleType());
                if (mechanicService.getMechanicServiceCategory() != null) {
                    getMechanicServiceResponseDTO.setMechanicServiceCategoryId(mechanicService.getMechanicServiceCategory().getMechanicServiceCategoryId());
                    getMechanicServiceResponseDTO.setMechanicServiceCategoryName(mechanicService.getMechanicServiceCategory().getName());
                }
                mechanicServiceResponseDTOList.add(getMechanicServiceResponseDTO);
            }
            return mechanicServiceResponseDTOList;
        } catch (Exception e) {
            log.error("Method getItemFilter : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteMechanicService(long id) {
        log.info("Execute method deleteMechanicService :  @param : {}", id);
        try {
            Optional<MechanicService> optionalMechanicService = mechanicServiceRepository.findById(id);
            if (!optionalMechanicService.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the item you are finding cannot be found. ");
            }
            MechanicService mechanicService = optionalMechanicService.get();

            mechanicServiceRepository.delete(mechanicService);
        } catch (Exception e) {
            log.error("Method deleteMechanicService : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }
}
