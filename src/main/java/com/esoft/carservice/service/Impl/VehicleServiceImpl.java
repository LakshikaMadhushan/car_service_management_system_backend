package com.esoft.carservice.service.Impl;

import com.esoft.carservice.configuration.exception.CustomException;
import com.esoft.carservice.configuration.exception.ServiceException;
import com.esoft.carservice.dto.requset.UpdateSaveVehicleRequestDTO;
import com.esoft.carservice.dto.requset.VehicleFilterRequestDTO;
import com.esoft.carservice.dto.responce.GetVehicleResponseDTO;
import com.esoft.carservice.entity.Customer;
import com.esoft.carservice.entity.Vehicle;
import com.esoft.carservice.repository.CustomerRepository;
import com.esoft.carservice.repository.TechnicianRepository;
import com.esoft.carservice.repository.VehicleRepository;
import com.esoft.carservice.service.VehicleService;
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
public class VehicleServiceImpl implements VehicleService {
    private final TechnicianRepository technicianRepository;
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(TechnicianRepository technicianRepository, CustomerRepository customerRepository, VehicleRepository vehicleRepository) {
        this.technicianRepository = technicianRepository;
        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public GetVehicleResponseDTO getVehicle(long id) {
        log.info("Execute method getService :  @param : {}", id);
        try {
            Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
            if (!optionalVehicle.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the vehicle you are finding cannot be found. ");
            }

            Vehicle vehicle = optionalVehicle.get();

            GetVehicleResponseDTO getVehicleResponseDTO = new GetVehicleResponseDTO();
            getVehicleResponseDTO.setCategory(vehicle.getCategory());
            getVehicleResponseDTO.setColour(vehicle.getColour());
            getVehicleResponseDTO.setCustomerId(vehicle.getCustomer().getCustomerId());
            getVehicleResponseDTO.setEngineCapacity(vehicle.getEngineCapacity());
            getVehicleResponseDTO.setMileage(vehicle.getMileage());
            getVehicleResponseDTO.setNextMileage(vehicle.getNextMileage());
            getVehicleResponseDTO.setNumberPlate(vehicle.getNumberPlate());
            getVehicleResponseDTO.setStatus(vehicle.getStatus());
            getVehicleResponseDTO.setVehicleType(vehicle.getVehicleType());
            return getVehicleResponseDTO;

        } catch (Exception e) {
            log.error("Method getService : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateVehicle(UpdateSaveVehicleRequestDTO requestDTO) {
        log.info("Execute method updateVehicle : @param : {} ", requestDTO);
        try {
            Optional<Vehicle> optionalVehicle = vehicleRepository.findById(requestDTO.getVehicleId());
            if (!optionalVehicle.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the vehicle you are finding cannot be found. ");
            }
            Vehicle vehicle = optionalVehicle.get();

            Optional<Customer> optionalCustomer = customerRepository.findById(requestDTO.getCustomerId());
            if (!optionalCustomer.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the technician you are finding cannot be found. ");
            }
            Customer customer = optionalCustomer.get();

            vehicle.setCategory(vehicle.getCategory());
            vehicle.setColour(vehicle.getColour());
            vehicle.setCustomer(customer);
            vehicle.setEngineCapacity(vehicle.getEngineCapacity());
            vehicle.setMileage(vehicle.getMileage());
            vehicle.setNextMileage(vehicle.getNextMileage());
            List<Vehicle> itemByVehicleNumber = vehicleRepository.findByVehicleNumberUpdate(vehicle.getNumberPlate(), vehicle.getVehicleId());
            if (!itemByVehicleNumber.isEmpty()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the this vehicle number already used. ");

            }
            vehicle.setNumberPlate(vehicle.getNumberPlate());
            vehicle.setStatus(vehicle.getStatus());
            vehicle.setVehicleType(vehicle.getVehicleType());

            vehicleRepository.save(vehicle);
        } catch (Exception e) {
            log.error("Method updateVehicle : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveVehicle(UpdateSaveVehicleRequestDTO requestDTO) {
        log.info("Execute method saveItem : @param : {} ", requestDTO);
        try {

            Optional<Customer> optionalCustomer = customerRepository.findById(requestDTO.getCustomerId());
            if (!optionalCustomer.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the technician you are finding cannot be found. ");
            }
            Customer customer = optionalCustomer.get();

            Vehicle vehicle = new Vehicle();
            vehicle.setCategory(vehicle.getCategory());
            vehicle.setColour(vehicle.getColour());
            vehicle.setCustomer(customer);
            vehicle.setEngineCapacity(vehicle.getEngineCapacity());
            vehicle.setMileage(vehicle.getMileage());
            vehicle.setNextMileage(vehicle.getNextMileage());
            List<Vehicle> itemByVehicleNumber = vehicleRepository.findByVehicleNumber(vehicle.getNumberPlate());
            if (!itemByVehicleNumber.isEmpty()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the this vehicle number already used. ");

            }
            vehicle.setNumberPlate(vehicle.getNumberPlate());
            vehicle.setStatus(vehicle.getStatus());
            vehicle.setVehicleType(vehicle.getVehicleType());

            vehicleRepository.save(vehicle);
        } catch (Exception e) {
            log.error("Method saveItem : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, e.getMessage());
        }
    }

    @Override
    public List<GetVehicleResponseDTO> getVehicleFilter(VehicleFilterRequestDTO requestDTO) {
        log.info("Execute method getService :  @param : {}", requestDTO);
        try {
            List<Vehicle> vehicles = vehicleRepository.getAllVehicleFilter(requestDTO.getNumberPlate(), requestDTO.getVehicleId(), requestDTO.getVehicleType(), requestDTO.getCategory(), requestDTO.getStatus(), requestDTO.getCustomerId());

            List<GetVehicleResponseDTO> getVehicleResponseDTOs = new ArrayList<>();
            for (Vehicle vehicle : vehicles) {
                GetVehicleResponseDTO getVehicleResponseDTO = new GetVehicleResponseDTO();
                getVehicleResponseDTO.setCategory(vehicle.getCategory());
                getVehicleResponseDTO.setColour(vehicle.getColour());
                getVehicleResponseDTO.setCustomerId(vehicle.getCustomer().getCustomerId());
                getVehicleResponseDTO.setEngineCapacity(vehicle.getEngineCapacity());
                getVehicleResponseDTO.setMileage(vehicle.getMileage());
                getVehicleResponseDTO.setNextMileage(vehicle.getNextMileage());
                getVehicleResponseDTO.setNumberPlate(vehicle.getNumberPlate());
                getVehicleResponseDTO.setStatus(vehicle.getStatus());
                getVehicleResponseDTO.setVehicleType(vehicle.getVehicleType());
                getVehicleResponseDTOs.add(getVehicleResponseDTO);
            }

            return getVehicleResponseDTOs;

        } catch (Exception e) {
            log.error("Method getService : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }
}
