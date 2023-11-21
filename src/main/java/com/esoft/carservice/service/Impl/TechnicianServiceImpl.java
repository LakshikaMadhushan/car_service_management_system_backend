package com.esoft.carservice.service.Impl;

import com.esoft.carservice.configuration.exception.CustomException;
import com.esoft.carservice.configuration.exception.ServiceException;
import com.esoft.carservice.dto.requset.TechnicianFilterRequestDTO;
import com.esoft.carservice.dto.requset.UpdateAndSaveTechnicianRequestDTO;
import com.esoft.carservice.dto.responce.GetTechnicianResponseDTO;
import com.esoft.carservice.entity.Technician;
import com.esoft.carservice.repository.TechnicianRepository;
import com.esoft.carservice.service.TechnicianService;
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
public class TechnicianServiceImpl implements TechnicianService {
    private final TechnicianRepository technicianRepository;

    public TechnicianServiceImpl(TechnicianRepository technicianRepository) {
        this.technicianRepository = technicianRepository;
    }

    @Override
    public List<GetTechnicianResponseDTO> getAllTechnician() {
        log.info("Execute method getAllTechnician : ");
        try {
            List<Technician> technicianList = technicianRepository.findAll();
            List<GetTechnicianResponseDTO> technicianResponseDTOList = new ArrayList<>();
            for (Technician technician : technicianList) {
                GetTechnicianResponseDTO getTechnicianResponseDTO = new GetTechnicianResponseDTO();
                getTechnicianResponseDTO.setAddress1(technician.getAddress1());
                getTechnicianResponseDTO.setNic(technician.getNic());
                getTechnicianResponseDTO.setEmail(technician.getEmail());
                getTechnicianResponseDTO.setMobileNumber(technician.getMobileNumber());
                getTechnicianResponseDTO.setName(technician.getName());
                getTechnicianResponseDTO.setPassword(technician.getPassword());
                getTechnicianResponseDTO.setStatus(technician.getStatus());
                getTechnicianResponseDTO.setTechnicianId(technician.getTechnicianId());
                technicianResponseDTOList.add(getTechnicianResponseDTO);
            }
            return technicianResponseDTOList;
        } catch (Exception e) {
            log.error("Method getAllTechnician : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public GetTechnicianResponseDTO getTechnician(long id) {
        log.info("Execute method GetTechnicianResponseDTO :  @param : {}", id);
        try {
            Optional<Technician> optionalTechnician = technicianRepository.findById(id);
            if (!optionalTechnician.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the technician you are finding cannot be found. ");
            }
            Technician technician = optionalTechnician.get();
            GetTechnicianResponseDTO getTechnicianResponseDTO = new GetTechnicianResponseDTO();
            getTechnicianResponseDTO.setAddress1(technician.getAddress1());
            getTechnicianResponseDTO.setNic(technician.getNic());
            getTechnicianResponseDTO.setEmail(technician.getEmail());
            getTechnicianResponseDTO.setMobileNumber(technician.getMobileNumber());
            getTechnicianResponseDTO.setName(technician.getName());
            getTechnicianResponseDTO.setPassword(technician.getPassword());
            getTechnicianResponseDTO.setStatus(technician.getStatus());
            getTechnicianResponseDTO.setTechnicianId(technician.getTechnicianId());
            return getTechnicianResponseDTO;
        } catch (Exception e) {
            log.error("Method GetTechnicianResponseDTO : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateTechnician(UpdateAndSaveTechnicianRequestDTO requestDTO) {
        log.info("Execute method updateTechnician : @param : {} ", requestDTO);
        try {
            Optional<Technician> optionalTechnician = technicianRepository.findById(requestDTO.getTechnicianId());
            if (!optionalTechnician.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the technician you are finding cannot be found. ");
            }
            Technician technician = optionalTechnician.get();
            technician.setAddress1(requestDTO.getAddress1());
            technician.setNic(requestDTO.getNic());
            technician.setEmail(requestDTO.getEmail());
            technician.setMobileNumber(requestDTO.getMobileNumber());
            technician.setName(requestDTO.getName());
            technician.setPassword(requestDTO.getPassword());
            technician.setStatus(requestDTO.getStatus());
            technician.setTechnicianId(requestDTO.getTechnicianId());

            technicianRepository.save(technician);
        } catch (Exception e) {
            log.error("Method updateTechnician : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveTechnician(UpdateAndSaveTechnicianRequestDTO requestDTO) {
        log.info("Execute method saveTechnician : @param : {} ", requestDTO);
        try {
            Technician technician = new Technician();
            technician.setAddress1(requestDTO.getAddress1());
            technician.setNic(requestDTO.getNic());
            technician.setEmail(requestDTO.getEmail());
            technician.setMobileNumber(requestDTO.getMobileNumber());
            technician.setName(requestDTO.getName());
            technician.setPassword(requestDTO.getPassword());
            technician.setStatus(requestDTO.getStatus());
            technician.setTechnicianId(requestDTO.getTechnicianId());

            technicianRepository.save(technician);
        } catch (Exception e) {
            log.error("Method saveTechnician : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public List<GetTechnicianResponseDTO> getTechnicianFilter(TechnicianFilterRequestDTO requestDTO) {
        log.info("Execute method getTechnicianFilter : @param : {} ", requestDTO);
        try {
            String status = null;
            if (requestDTO.getStatus() != null) {
                status = requestDTO.getStatus().toString();
            }
            List<Technician> technicianList = technicianRepository.getAllTechnicianFilter(requestDTO.getName(), requestDTO.getTechnicianId(), requestDTO.getEmail(), requestDTO.getNic(), status);
            List<GetTechnicianResponseDTO> technicianResponseDTOList = new ArrayList<>();
            for (Technician technician : technicianList) {
                GetTechnicianResponseDTO getTechnicianResponseDTO = new GetTechnicianResponseDTO();
                getTechnicianResponseDTO.setAddress1(technician.getAddress1());
                getTechnicianResponseDTO.setNic(technician.getNic());
                getTechnicianResponseDTO.setEmail(technician.getEmail());
                getTechnicianResponseDTO.setMobileNumber(technician.getMobileNumber());
                getTechnicianResponseDTO.setName(technician.getName());
                getTechnicianResponseDTO.setPassword(technician.getPassword());
                getTechnicianResponseDTO.setStatus(technician.getStatus());
                getTechnicianResponseDTO.setTechnicianId(technician.getTechnicianId());
                technicianResponseDTOList.add(getTechnicianResponseDTO);
            }
            return technicianResponseDTOList;
        } catch (Exception e) {
            log.error("Method getTechnicianFilter : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteTechnician(long id) {
        log.info("Execute method deleteTechnician : @param : {} ", id);
        try {
            Optional<Technician> optionalTechnician = technicianRepository.findById(id);
            if (!optionalTechnician.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the technician you are finding cannot be found. ");
            }
            Technician technician = optionalTechnician.get();

            technicianRepository.delete(technician);
        } catch (Exception e) {
            log.error("Method deleteTechnician : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }
}
