package com.esoft.carservice.service.Impl;

import com.esoft.carservice.configuration.exception.CustomException;
import com.esoft.carservice.configuration.exception.ServiceException;
import com.esoft.carservice.dto.requset.UpdateSaveCustomer;
import com.esoft.carservice.dto.responce.GetCustomerResponseDTO;
import com.esoft.carservice.entity.Customer;
import com.esoft.carservice.repository.CustomerRepository;
import com.esoft.carservice.service.CustomerService;
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
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
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
                getCustomerResponseDTO.setStatus(customer.getStatus());

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
            Optional<Customer> optionalCustomer = customerRepository.findById(id);
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
            getCustomerResponseDTO.setStatus(customer.getStatus());


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
            Optional<Customer> optionalCustomer = customerRepository.findById(requestDTO.getCustomerId());
            if (!optionalCustomer.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the customer you are finding cannot be found. ");

            }
            Customer customer = optionalCustomer.get();

            customer.setAddress1(requestDTO.getAddress1());
            customer.setAddress2(requestDTO.getAddress2());
            customer.setCustomerId(requestDTO.getCustomerId());
            customer.setMobileNumber(requestDTO.getMobileNumber());
            customer.setName(requestDTO.getName());
            customer.setStatus(requestDTO.getStatus());

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
            Customer customer = new Customer();

            customer.setAddress1(requestDTO.getAddress1());
            customer.setAddress2(requestDTO.getAddress2());
            customer.setMobileNumber(requestDTO.getMobileNumber());
            customer.setName(requestDTO.getName());
            customer.setStatus(requestDTO.getStatus());

            customerRepository.save(customer);
        } catch (Exception e) {
            log.error("Method saveCustomer : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }
}
