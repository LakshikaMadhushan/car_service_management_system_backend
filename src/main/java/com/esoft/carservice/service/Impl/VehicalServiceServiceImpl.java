package com.esoft.carservice.service.Impl;

import com.esoft.carservice.configuration.exception.CustomException;
import com.esoft.carservice.configuration.exception.ServiceException;
import com.esoft.carservice.dto.requset.SaveServiceDetailsRequestDTO;
import com.esoft.carservice.dto.requset.SaveServiceRequestDTO;
import com.esoft.carservice.dto.requset.ServiceFilterRequestDTO;
import com.esoft.carservice.dto.requset.UpdateAndSaveServiceRequestDTO;
import com.esoft.carservice.dto.responce.GetServiceResponseDTO;
import com.esoft.carservice.entity.ServiceDetails;
import com.esoft.carservice.entity.Technician;
import com.esoft.carservice.entity.Vehicle;
import com.esoft.carservice.repository.ServiceRepository;
import com.esoft.carservice.repository.TechnicianRepository;
import com.esoft.carservice.repository.VehicleRepository;
import com.esoft.carservice.service.VehicalServiceService;
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
public class VehicalServiceServiceImpl implements VehicalServiceService {
    private final ServiceRepository serviceRepository;
    private final TechnicianRepository technicianRepository;
    private final VehicleRepository vehicleRepository;

    public VehicalServiceServiceImpl(ServiceRepository serviceRepository, TechnicianRepository technicianRepository, VehicleRepository vehicleRepository) {
        this.serviceRepository = serviceRepository;
        this.technicianRepository = technicianRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public GetServiceResponseDTO getService(long id) {
        log.info("Execute method getService :  @param : {}", id);
        try {
            Optional<com.esoft.carservice.entity.Service> serviceOptional = serviceRepository.findById(id);
            if (!serviceOptional.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the service you are finding cannot be found. ");
            }

            com.esoft.carservice.entity.Service service = serviceOptional.get();

            GetServiceResponseDTO getServiceResponseDTO = new GetServiceResponseDTO();
            getServiceResponseDTO.setCost(service.getCost());
            getServiceResponseDTO.setTechnicianName(service.getTechnician().getName());
            getServiceResponseDTO.setNumberPlate(service.getVehicle().getNumberPlate());
            getServiceResponseDTO.setService_date(service.getService_date());
            getServiceResponseDTO.setServiceId(service.getServiceId());
            getServiceResponseDTO.setTechnicianId(service.getTechnician().getTechnicianId());
            getServiceResponseDTO.setType(service.getType());
            getServiceResponseDTO.setVehicleId(service.getVehicle().getVehicleId());
            return getServiceResponseDTO;

        } catch (Exception e) {
            log.error("Method getService : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateService(UpdateAndSaveServiceRequestDTO requestDTO) {
        log.info("Execute method updateService : @param : {} ", requestDTO);
        try {
            Optional<com.esoft.carservice.entity.Service> serviceOptional = serviceRepository.findById(requestDTO.getServiceId());
            if (!serviceOptional.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the service you are finding cannot be found. ");
            }

            com.esoft.carservice.entity.Service service = serviceOptional.get();
            Optional<Vehicle> optionalVehicle = vehicleRepository.findById(requestDTO.getVehicleId());
            if (!optionalVehicle.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the vehicle you are finding cannot be found. ");
            }
            Optional<Technician> optionalTechnician = technicianRepository.findById(requestDTO.technicianId);
            if (!optionalTechnician.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the technician you are finding cannot be found. ");
            }
            service.setVehicle(optionalVehicle.get());
            service.setTechnician(optionalTechnician.get());
            service.setType(requestDTO.getType());
            service.setServiceId(requestDTO.getServiceId());
            service.setService_date(requestDTO.getService_date());
            service.setCost(requestDTO.getCost());


            serviceRepository.save(service);
        } catch (Exception e) {
            log.error("Method updateService : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public List<GetServiceResponseDTO> getServiceFilter(ServiceFilterRequestDTO requestDTO) {
        log.info("Execute method getServiceFilter : @param : {} ", requestDTO);
        try {
            List<com.esoft.carservice.entity.Service> serviceList = serviceRepository.getAllServiceFilter(requestDTO.getServiceId(), requestDTO.getVehicleId(), requestDTO.getType(), requestDTO.getStartService_date(), requestDTO.getEndService_date());
            List<GetServiceResponseDTO> serviceResponseDTOList = new ArrayList<>();
            for (com.esoft.carservice.entity.Service service : serviceList) {
                GetServiceResponseDTO getServiceResponseDTO = new GetServiceResponseDTO();
                getServiceResponseDTO.setCost(service.getCost());
                getServiceResponseDTO.setTechnicianName(service.getTechnician().getName());
                getServiceResponseDTO.setNumberPlate(service.getVehicle().getNumberPlate());
                getServiceResponseDTO.setService_date(service.getService_date());
                getServiceResponseDTO.setTechnicianId(service.getTechnician().getTechnicianId());
                getServiceResponseDTO.setType(service.getType());
                getServiceResponseDTO.setVehicleId(service.getVehicle().getVehicleId());
                serviceResponseDTOList.add(getServiceResponseDTO);
            }
            return serviceResponseDTOList;
        } catch (Exception e) {
            log.error("Method getServiceFilter : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveService(SaveServiceRequestDTO requestDTO) {
        log.info("Execute method updateService : @param : {} ", requestDTO);
        try {

            com.esoft.carservice.entity.Service service = new com.esoft.carservice.entity.Service();
            Optional<Vehicle> optionalVehicle = vehicleRepository.findById(requestDTO.getVehicleId());
            if (!optionalVehicle.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the vehicle you are finding cannot be found. ");
            }
            Optional<Technician> optionalTechnician = technicianRepository.findById(requestDTO.technicianId);
            if (!optionalTechnician.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the technician you are finding cannot be found. ");
            }
            service.setVehicle(optionalVehicle.get());
            service.setTechnician(optionalTechnician.get());
            service.setType(requestDTO.getType());
            service.setService_date(requestDTO.getService_date());
            service.setCost(requestDTO.getCost());

            List<ServiceDetails> serviceDetailsList = new ArrayList<>();
            for (SaveServiceDetailsRequestDTO saveServiceDetailsRequestDTO : requestDTO.getSaveServiceDetailsRequestDTOS()) {
                ServiceDetails serviceDetails = new ServiceDetails();

                serviceDetailsList.add(serviceDetails);
            }

            service.setServiceDetailsList(serviceDetailsList);
            serviceRepository.save(service);
        } catch (Exception e) {
            log.error("Method updateService : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }
}
