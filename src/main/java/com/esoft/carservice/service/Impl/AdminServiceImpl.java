package com.esoft.carservice.service.Impl;

import com.esoft.carservice.configuration.exception.CustomException;
import com.esoft.carservice.configuration.exception.ServiceException;
import com.esoft.carservice.dto.requset.AdminFilterRequestDTO;
import com.esoft.carservice.dto.requset.UpdateSaveAdminRequestDTO;
import com.esoft.carservice.dto.responce.GetAdminResponseDTO;
import com.esoft.carservice.entity.Admin;
import com.esoft.carservice.entity.User;
import com.esoft.carservice.repository.AdminRepository;
import com.esoft.carservice.repository.UserRepository;
import com.esoft.carservice.service.AdminService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepository adminRepository, UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
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
                getAdminResponseDTO.setAdminId(admin.getAdminId());
                getAdminResponseDTO.setUserId(admin.getUser().getUserId());
                getAdminResponseDTO.setMobileNumber(admin.getMobileNumber());
                getAdminResponseDTO.setEmail(admin.getUser().getEmail());
                getAdminResponseDTO.setName(admin.getName());
                getAdminResponseDTO.setQualification(admin.getQualification());
                getAdminResponseDTO.setStatus(admin.getUser().getStatus());
                getAdminResponseDTO.setNic(admin.getNic());
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
            getAdminResponseDTO.setAdminId(admin.getAdminId());
            getAdminResponseDTO.setAdminId(admin.getUser().getUserId());
            getAdminResponseDTO.setMobileNumber(admin.getMobileNumber());
            getAdminResponseDTO.setEmail(admin.getUser().getEmail());
            getAdminResponseDTO.setName(admin.getName());
            getAdminResponseDTO.setQualification(admin.getQualification());
            getAdminResponseDTO.setStatus(admin.getUser().getStatus());
            getAdminResponseDTO.setNic(admin.getNic());


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
            List<User> userList = userRepository.checkEmailANDNicUpdate(requestDTO.getEmail(), requestDTO.getNic(), requestDTO.getUserId());
            if (!userList.isEmpty()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the admin user email or nic already Used. ");

            }
            Optional<User> optionalUser = userRepository.findById(requestDTO.getUserId());
            if (!optionalUser.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the admin user you are finding cannot be found. ");
            }
            User user = optionalUser.get();
            user.setEmail(requestDTO.getEmail());
            user.setName(requestDTO.getName());
            user.setStatus(requestDTO.getStatus());

            userRepository.save(user);

            Admin admin = user.getAdmin();
            admin.setAddress1(requestDTO.getAddress1());
            admin.setMobileNumber(requestDTO.getMobileNumber());
            admin.setName(requestDTO.getName());
            admin.setQualification(requestDTO.getQualification());
            admin.setNic(requestDTO.getNic());

            adminRepository.save(admin);

        } catch (Exception e) {
            log.error("Method updateAdmin : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }


    }

    @Override
    public void saveAdmin(UpdateSaveAdminRequestDTO requestDTO) {
        log.info("Execute method saveAdmin : @param : {} ", requestDTO);
        try {

            List<User> userList = userRepository.checkEmailANDNic(requestDTO.getEmail(), requestDTO.getNic());
            if (!userList.isEmpty()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the admin user email or nic already Used. ");

            }

            User user = new User();
            user.setEmail(requestDTO.getEmail());
            user.setStatus(requestDTO.getStatus());
            user.setName(requestDTO.getName());


//            if (userEntity.getUserId() == userEntity.getUserId()) {
//
//                if (!passwordEncoder.matches(updatePasswordRequestDTO.getOldPassword(), userEntity.getGenericUser().getPassword()))
//                    throw new ApiitCustomException(INVALID_INPUT, "Your password not match with previous password");
//
//
//                // check whether passwords are same
//                if (passwordEncoder.matches(updatePasswordRequestDTO.getNewPassword(), userEntity.getGenericUser().getPassword()))
//                    throw new ApiitCustomException(INVALID_INPUT, "Your new password cannot be the same as previous password");
//
//                genericUser.setPassword(passwordEncoder.encode(updatePasswordRequestDTO.getNewPassword()));
            user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

            Admin admin = new Admin();
            admin.setAddress1(requestDTO.getAddress1());
            admin.setMobileNumber(requestDTO.getMobileNumber());
            admin.setName(requestDTO.getName());
            admin.setQualification(requestDTO.getQualification());
            admin.setNic(requestDTO.getNic());
            user.setAdmin(admin);
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Method saveAdmin : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public List<GetAdminResponseDTO> getAdminFilter(AdminFilterRequestDTO requestDTO) {
        log.info("Execute method getAdminFilter: @param : {}", requestDTO);
        try {
            List<Admin> adminList = adminRepository.getAllAdminFilter(requestDTO.getContactNo(), requestDTO.getAdminId(), requestDTO.getEmail(), requestDTO.getUserId(), requestDTO.getNic(), requestDTO.getUserStatus());
            List<GetAdminResponseDTO> getAdminResponseDTOList = new ArrayList<>();
            for (Admin admin : adminList) {
                GetAdminResponseDTO getAdminResponseDTO = new GetAdminResponseDTO();
                getAdminResponseDTO.setAddress1(admin.getAddress1());
                getAdminResponseDTO.setAdminId(admin.getAdminId());
                getAdminResponseDTO.setUserId(admin.getUser().getUserId());
                getAdminResponseDTO.setMobileNumber(admin.getMobileNumber());
                getAdminResponseDTO.setEmail(admin.getUser().getEmail());
                getAdminResponseDTO.setName(admin.getName());
                getAdminResponseDTO.setQualification(admin.getQualification());
                getAdminResponseDTO.setStatus(admin.getUser().getStatus());
                getAdminResponseDTO.setNic(admin.getNic());
                getAdminResponseDTOList.add(getAdminResponseDTO);
            }
            return getAdminResponseDTOList;
        } catch (Exception e) {
            log.error("Method getAllAdmin : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }
}
