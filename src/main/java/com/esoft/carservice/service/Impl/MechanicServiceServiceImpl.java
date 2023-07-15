package com.esoft.carservice.service.Impl;

import com.esoft.carservice.configuration.exception.CustomException;
import com.esoft.carservice.configuration.exception.ServiceException;
import com.esoft.carservice.dto.requset.UpdateSaveMechanicServiceRequestDTO;
import com.esoft.carservice.dto.responce.GetMechanicServiceResponseDTO;
import com.esoft.carservice.entity.MechanicService;
import com.esoft.carservice.repository.MechanicServiceRepository;
import com.esoft.carservice.service.MechanicServiceService;
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
public class MechanicServiceServiceImpl implements MechanicServiceService {
    private final MechanicServiceRepository mechanicServiceRepository;

    public MechanicServiceServiceImpl(MechanicServiceRepository mechanicServiceRepository) {
        this.mechanicServiceRepository = mechanicServiceRepository;
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
                mechanicServiceResponseDTOList.add(getMechanicServiceResponseDTO);
            }
            return mechanicServiceResponseDTOList;

        } catch (Exception e) {
            log.error("Method getAllMechanicService : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
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


            return getMechanicServiceResponseDTO;
        } catch (Exception e) {
            log.error("Method getMechanicService : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
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
            MechanicService mechanicService = optionalMechanicService.get();
            mechanicService.setName(mechanicService.getName());
            mechanicService.setPrice(mechanicService.getPrice());
            mechanicService.setVehicleType(mechanicService.getVehicleType());
            mechanicServiceRepository.save(mechanicService);
        } catch (Exception e) {
            log.error("Method updateMechanicService : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveMechanicService(UpdateSaveMechanicServiceRequestDTO requestDTO) {
        log.info("Execute method saveMechanicService : @param : {} ", requestDTO);
        try {
            MechanicService mechanicService = new MechanicService();
            mechanicService.setName(mechanicService.getName());
            mechanicService.setPrice(mechanicService.getPrice());
            mechanicService.setVehicleType(mechanicService.getVehicleType());
            mechanicServiceRepository.save(mechanicService);
        } catch (Exception e) {
            log.error("Method saveMechanicService : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }
}
