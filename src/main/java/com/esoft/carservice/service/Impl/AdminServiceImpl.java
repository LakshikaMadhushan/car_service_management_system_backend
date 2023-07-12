package com.esoft.carservice.service.Impl;

import com.esoft.carservice.configuration.exception.CustomException;
import com.esoft.carservice.configuration.exception.ServiceException;
import com.esoft.carservice.dto.requset.UpdateSaveAdminRequestDTO;
import com.esoft.carservice.dto.responce.GetAdminResponseDTO;
import com.esoft.carservice.entity.Admin;
import com.esoft.carservice.repository.AdminRepository;
import com.esoft.carservice.service.AdminService;
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
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public List<GetAdminResponseDTO> getAllAdmin() {
        log.info("Execute method getAllAdmin : ");
        try {
            List<Admin> adminList = adminRepository.findAll();
            List<GetAdminResponseDTO> getAdminResponseDTOList = new ArrayList<>();
            for (Admin admin : adminList) {
                GetAdminResponseDTO getAdminResponseDTO = new GetAdminResponseDTO();
                getAdminResponseDTO.setAddress1(admin.getAddress1());
                getAdminResponseDTO.setAddress2(admin.getAddress2());
                getAdminResponseDTO.setAdminId(admin.getAdminId());
                getAdminResponseDTO.setMobileNumber(admin.getMobileNumber());
                getAdminResponseDTO.setName(admin.getName());
                getAdminResponseDTO.setQualification(admin.getQualification());
                getAdminResponseDTO.setStatus(admin.getStatus());
                getAdminResponseDTOList.add(getAdminResponseDTO);
            }
            return getAdminResponseDTOList;
        } catch (Exception e) {
            log.error("Method getAllAdmin : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public GetAdminResponseDTO getAdmin(long id) {
        log.info("Execute method getAdmin :  @param : {}", id);
        try {
            Optional<Admin> optionalAdmin = adminRepository.findById(id);
            if (!optionalAdmin.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the admin you are finding cannot be found. ");
            }
            Admin admin = optionalAdmin.get();

            GetAdminResponseDTO getAdminResponseDTO = new GetAdminResponseDTO();

            getAdminResponseDTO.setAddress1(admin.getAddress1());
            getAdminResponseDTO.setAddress2(admin.getAddress2());
            getAdminResponseDTO.setAdminId(admin.getAdminId());
            getAdminResponseDTO.setMobileNumber(admin.getMobileNumber());
            getAdminResponseDTO.setName(admin.getName());
            getAdminResponseDTO.setQualification(admin.getQualification());
            getAdminResponseDTO.setStatus(admin.getStatus());

            return getAdminResponseDTO;
        } catch (Exception e) {
            log.error("Method getAdmin : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateAdmin(UpdateSaveAdminRequestDTO requestDTO) {
        log.info("Execute method updateAdmin : @param : {} ", requestDTO);
        try {
            Optional<Admin> optionalAdmin = adminRepository.findById(requestDTO.adminId);
            if (!optionalAdmin.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the admin you are finding cannot be found. ");
            }
            Admin admin = optionalAdmin.get();
            admin.setAddress1(admin.getAddress1());
            admin.setAddress2(admin.getAddress2());
            admin.setAdminId(admin.getAdminId());
            admin.setMobileNumber(admin.getMobileNumber());
            admin.setName(admin.getName());
            admin.setQualification(admin.getQualification());
            admin.setStatus(admin.getStatus());

            adminRepository.save(admin);


        } catch (Exception e) {
            log.error("Method updateAdmin : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }


    }

    @Override
    public void saveAdmin(UpdateSaveAdminRequestDTO requestDTO) {
        log.info("Execute method updateAdmin : @param : {} ", requestDTO);
        try {
            Admin admin = new Admin();
            admin.setAddress1(admin.getAddress1());
            admin.setAddress2(admin.getAddress2());
            admin.setMobileNumber(admin.getMobileNumber());
            admin.setName(admin.getName());
            admin.setQualification(admin.getQualification());
            admin.setStatus(admin.getStatus());

            adminRepository.save(admin);
        } catch (Exception e) {
            log.error("Method updateAdmin : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }

    }
}
