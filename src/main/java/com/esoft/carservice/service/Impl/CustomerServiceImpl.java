package com.esoft.carservice.service.Impl;

import com.esoft.carservice.configuration.exception.CustomException;
import com.esoft.carservice.configuration.exception.ServiceException;
import com.esoft.carservice.dto.requset.CustomerFilterRequestDTO;
import com.esoft.carservice.dto.requset.UpdateSaveCustomer;
import com.esoft.carservice.dto.responce.GetCustomerResponseDTO;
import com.esoft.carservice.entity.Customer;
import com.esoft.carservice.entity.User;
import com.esoft.carservice.enums.UserRole;
import com.esoft.carservice.repository.CustomerRepository;
import com.esoft.carservice.repository.UserRepository;
import com.esoft.carservice.service.CustomerService;
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
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public CustomerServiceImpl(CustomerRepository customerRepository, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<GetCustomerResponseDTO> getAllCustomer() {
        log.info("Execute method getAllCustomer : ");
        try {
            List<Customer> customerList = customerRepository.findAll();
            List<GetCustomerResponseDTO> getCustomerResponseDTOS = new ArrayList<>();
            for (Customer customer : customerList) {
                GetCustomerResponseDTO getCustomerResponseDTO = new GetCustomerResponseDTO();
                getCustomerResponseDTO.setAddress1(customer.getAddress1());
                getCustomerResponseDTO.setAddress2(customer.getAddress2());
                getCustomerResponseDTO.setCustomerId(customer.getCustomerId());
                getCustomerResponseDTO.setMobileNumber(customer.getMobileNumber());
                getCustomerResponseDTO.setName(customer.getName());

                getCustomerResponseDTOS.add(getCustomerResponseDTO);
            }
            return getCustomerResponseDTOS;
        } catch (Exception e) {
            log.error("Method getAllCustomer : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public GetCustomerResponseDTO getCustomer(long id) {
        log.info("Execute method getCustomer :  @param : {}", id);
        try {
            Optional<Customer> optionalCustomer = customerRepository.getAllCustomerFilter(id);
            if (!optionalCustomer.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the customer you are finding cannot be found. ");

            }
            Customer customer = optionalCustomer.get();

            GetCustomerResponseDTO getCustomerResponseDTO = new GetCustomerResponseDTO();
            getCustomerResponseDTO.setAddress1(customer.getAddress1());
            getCustomerResponseDTO.setAddress2(customer.getAddress2());
            getCustomerResponseDTO.setCustomerId(customer.getCustomerId());
            getCustomerResponseDTO.setMobileNumber(customer.getMobileNumber());
            getCustomerResponseDTO.setName(customer.getName());
            getCustomerResponseDTO.setEmail(customer.getUser().getEmail());
            getCustomerResponseDTO.setNic(customer.getUser().getNic());
            getCustomerResponseDTO.setStatus(customer.getUser().getStatus());


            return getCustomerResponseDTO;
        } catch (Exception e) {
            log.error("Method getCustomer : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateCustomer(UpdateSaveCustomer requestDTO) {
        log.info("Execute method UpdateSaveCustomer :  @param : {}", requestDTO);
        try {
            Optional<Customer> optionalCustomer = customerRepository.getAllCustomerFilter(requestDTO.getCustomerId());
            if (!optionalCustomer.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the customer you are finding cannot be found. ");

            }
            Customer customer = optionalCustomer.get();

            Optional<User> optionalUser = userRepository.findById(customer.getUser().getUserId());
            if (!optionalUser.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the user you are finding cannot be found. ");

            }
            User user = optionalUser.get();

            List<User> userList = userRepository.checkEmailANDNicUpdate(requestDTO.getCustomerEmail(), requestDTO.getNic(), user.getUserId());
            if (!userList.isEmpty()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the  user email or nic already Used. ");

            }

            if (requestDTO.getStatus() != null) {
                user.setStatus(requestDTO.getStatus());
            }
            if (requestDTO.getName() != null) {
                user.setName(requestDTO.getName());
            }
            if (requestDTO.getNic() != null) {
                user.setNic(requestDTO.getNic());
            }
            if (requestDTO.getCustomerPassword() != null) {
                user.setPassword(passwordEncoder.encode(requestDTO.getCustomerPassword()));
            }
            if (requestDTO.getCustomerEmail() != null) {
                user.setEmail(requestDTO.getCustomerEmail());
            }

            userRepository.save(user);


            if (requestDTO.getAddress1() != null) {
                customer.setAddress1(requestDTO.getAddress1());
            }
            if (requestDTO.getNic() != null) {
                customer.setNic(requestDTO.getNic());
            }
            if (requestDTO.getMobileNumber() != null) {
                customer.setMobileNumber(requestDTO.getMobileNumber());
            }
            if (requestDTO.getName() != null) {
                customer.setName(requestDTO.getName());
            }
            customerRepository.save(customer);

        } catch (Exception e) {
            log.error("Method UpdateSaveCustomer : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveCustomer(UpdateSaveCustomer requestDTO) {
        log.info("Execute method saveCustomer :  @param : {}", requestDTO);
        try {
            User user = new User();
            List<User> userList = userRepository.checkEmailANDNic(requestDTO.getCustomerEmail(), requestDTO.getNic());
            if (!userList.isEmpty()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the  user email or nic already Used. ");

            }
            user.setUserRole(UserRole.CUSTOMER);
            user.setEmail(requestDTO.getCustomerEmail());
            user.setNic(requestDTO.getNic());
            user.setPassword(passwordEncoder.encode(requestDTO.getCustomerPassword()));
            user.setName(requestDTO.getName());
            user.setStatus(requestDTO.getStatus());


            Customer customer = new Customer();
            customer.setAddress1(requestDTO.getAddress1());
            customer.setNic(requestDTO.getNic());
            customer.setMobileNumber(requestDTO.getMobileNumber());
            customer.setName(requestDTO.getName());

            userRepository.save(user);
            customer.setUser(user);
            customerRepository.save(customer);


            log.info("final");
        } catch (Exception e) {
            log.error("Method saveCustomer : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public List<GetCustomerResponseDTO> getCustomerFilter(CustomerFilterRequestDTO requestDTO) {
        log.info("Execute method getAllCustomer :  @param : {} ", requestDTO);
        try {
            String userStatus = null;
            if (requestDTO.getUserStatus() != null) {
                userStatus = requestDTO.getUserStatus().toString();
            }
            List<Customer> customerList = customerRepository.getAllCustomerFilter(requestDTO.getContactNo(), requestDTO.getAdminId(), requestDTO.getEmail(), requestDTO.getUserId(), requestDTO.getNic(), userStatus);

            List<GetCustomerResponseDTO> getCustomerResponseDTOS = new ArrayList<>();
            for (Customer customer : customerList) {
                GetCustomerResponseDTO getCustomerResponseDTO = new GetCustomerResponseDTO();
                getCustomerResponseDTO.setAddress1(customer.getAddress1());
                getCustomerResponseDTO.setAddress2(customer.getAddress2());
                getCustomerResponseDTO.setCustomerId(customer.getCustomerId());
                getCustomerResponseDTO.setMobileNumber(customer.getMobileNumber());
                getCustomerResponseDTO.setName(customer.getName());
                getCustomerResponseDTO.setNic(customer.getNic());
                if (customer.getUser() != null) {
                    getCustomerResponseDTO.setStatus(customer.getUser().getStatus());
                    getCustomerResponseDTO.setEmail(customer.getUser().getEmail());
                }

                getCustomerResponseDTOS.add(getCustomerResponseDTO);
            }
            return getCustomerResponseDTOS;
        } catch (Exception e) {
            log.error("Method getAllCustomer : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }
}
