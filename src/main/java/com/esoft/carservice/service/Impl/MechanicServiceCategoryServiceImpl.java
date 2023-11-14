package com.esoft.carservice.service.Impl;

import com.esoft.carservice.configuration.exception.CustomException;
import com.esoft.carservice.configuration.exception.ServiceException;
import com.esoft.carservice.dto.requset.MechanicServiceCategoryFilterRequestDTO;
import com.esoft.carservice.dto.requset.UpdateAndSaveMechanicServiceCategoryRequestDTO;
import com.esoft.carservice.dto.responce.GetAllMechanicServiceCategoryResponseDTO;
import com.esoft.carservice.entity.MechanicServiceCategory;
import com.esoft.carservice.repository.MechanicServiceCategoryRepository;
import com.esoft.carservice.service.MechanicServiceCategoryService;
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
public class MechanicServiceCategoryServiceImpl implements MechanicServiceCategoryService {
    private final MechanicServiceCategoryRepository mechanicServiceCategoryRepository;

    public MechanicServiceCategoryServiceImpl(MechanicServiceCategoryRepository mechanicServiceCategoryRepository) {
        this.mechanicServiceCategoryRepository = mechanicServiceCategoryRepository;
    }

    @Override
    public List<GetAllMechanicServiceCategoryResponseDTO> getAllMechanicServiceCategory() {
        log.info("Execute method getAllMechanicServiceCategory : ");
        try {
            List<MechanicServiceCategory> mechanicServiceCategories = mechanicServiceCategoryRepository.findAll();
            List<GetAllMechanicServiceCategoryResponseDTO> getAllMechanicServiceCategoryResponseDTOS = new ArrayList<>();

            for (MechanicServiceCategory mechanicServiceCategory : mechanicServiceCategories) {
                GetAllMechanicServiceCategoryResponseDTO categoryResponseDTO = new GetAllMechanicServiceCategoryResponseDTO();
                categoryResponseDTO.setMechanicServiceCategoryId(mechanicServiceCategory.getMechanicServiceCategoryId());
                categoryResponseDTO.setName(mechanicServiceCategory.getName());
                getAllMechanicServiceCategoryResponseDTOS.add(categoryResponseDTO);
            }
            return getAllMechanicServiceCategoryResponseDTOS;

        } catch (Exception e) {
            log.error("Method getAllMechanicServiceCategory : " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public GetAllMechanicServiceCategoryResponseDTO getMechanicServiceCategory(long id) {
        log.info("Execute method getMechanicServiceCategory :  @param : {}", id);
        try {
            Optional<MechanicServiceCategory> optionalMechanicServiceCategory = mechanicServiceCategoryRepository.findById(id);
            if (!optionalMechanicServiceCategory.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the mechanic service category you are finding cannot be found. ");
            }
            MechanicServiceCategory mechanicServiceCategory = optionalMechanicServiceCategory.get();

            GetAllMechanicServiceCategoryResponseDTO getItemResponseDTO = new GetAllMechanicServiceCategoryResponseDTO();
            getItemResponseDTO.setMechanicServiceCategoryId(mechanicServiceCategory.getMechanicServiceCategoryId());
            getItemResponseDTO.setName(mechanicServiceCategory.getName());


            return getItemResponseDTO;
        } catch (Exception e) {
            log.error("Method getMechanicServiceCategory : " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateMechanicServiceCategory(UpdateAndSaveMechanicServiceCategoryRequestDTO requestDTO) {
        log.info("Execute method updateMechanicServiceCategory : @param : {} ", requestDTO);
        try {
            Optional<MechanicServiceCategory> optionalMechanicServiceCategory = mechanicServiceCategoryRepository.findById(requestDTO.getMechanicServiceCategoryId());
            if (!optionalMechanicServiceCategory.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the mechanic service category you are finding cannot be found. ");
            }

            List<MechanicServiceCategory> mechanicServiceCategoryList = mechanicServiceCategoryRepository.findMechanicServiceCategoryNameUpdate(requestDTO.getName(), requestDTO.getMechanicServiceCategoryId());

            if (!mechanicServiceCategoryList.isEmpty()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the mechanic service category already used. ");
            }

            MechanicServiceCategory mechanicServiceCategory = optionalMechanicServiceCategory.get();
            mechanicServiceCategory.setName(requestDTO.getName());

            mechanicServiceCategoryRepository.save(mechanicServiceCategory);
        } catch (Exception e) {
            log.error("Method updateMechanicServiceCategory : " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveMechanicServiceCategory(UpdateAndSaveMechanicServiceCategoryRequestDTO requestDTO) {
        log.info("Execute method saveMechanicServiceCategory : @param : {} ", requestDTO);
        try {

            List<MechanicServiceCategory> mechanicServiceCategoryList = mechanicServiceCategoryRepository.findMechanicServiceCategoryName(requestDTO.getName());

            if (!mechanicServiceCategoryList.isEmpty()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the mechanic service category already used. ");
            }


            MechanicServiceCategory mechanicServiceCategory = new MechanicServiceCategory();
            mechanicServiceCategory.setMechanicServiceCategoryId(requestDTO.getMechanicServiceCategoryId());
            mechanicServiceCategory.setName(requestDTO.getName());

            mechanicServiceCategoryRepository.save(mechanicServiceCategory);
        } catch (Exception e) {
            log.error("Method saveMechanicServiceCategory : " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<GetAllMechanicServiceCategoryResponseDTO> getMechanicServiceCategoryFilter(MechanicServiceCategoryFilterRequestDTO requestDTO) {
        log.info("Execute method getItemFilter : @param : {} ", requestDTO);
        try {
            List<MechanicServiceCategory> mechanicServiceCategoryFilter = mechanicServiceCategoryRepository.getAllMechanicServiceCategoryFilter(requestDTO.getName(), requestDTO.getMechanicServiceCategoryId());
            List<GetAllMechanicServiceCategoryResponseDTO> mechanicServiceCategories = new ArrayList<>();
            for (MechanicServiceCategory mechanicServiceCategory : mechanicServiceCategoryFilter) {
                GetAllMechanicServiceCategoryResponseDTO categoryResponseDTO = new GetAllMechanicServiceCategoryResponseDTO();
                categoryResponseDTO.setMechanicServiceCategoryId(mechanicServiceCategory.getMechanicServiceCategoryId());
                categoryResponseDTO.setName(mechanicServiceCategory.getName());
                mechanicServiceCategories.add(categoryResponseDTO);
            }
            return mechanicServiceCategories;
        } catch (Exception e) {
            log.error("Method getItemFilter : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public void deleteMechanicServiceCategory(long id) {

    }
}
