package com.esoft.carservice.service.Impl;

import com.esoft.carservice.configuration.exception.CustomException;
import com.esoft.carservice.configuration.exception.ServiceException;
import com.esoft.carservice.dto.requset.SaveServiceDetailsRequestDTO;
import com.esoft.carservice.dto.requset.SaveServiceRequestDTO;
import com.esoft.carservice.dto.requset.ServiceFilterRequestDTO;
import com.esoft.carservice.dto.requset.UpdateAndSaveServiceRequestDTO;
import com.esoft.carservice.dto.responce.GetServiceResponseDTO;
import com.esoft.carservice.entity.*;
import com.esoft.carservice.enums.ServiceDetailsType;
import com.esoft.carservice.repository.*;
import com.esoft.carservice.service.VehicalServiceService;
import com.esoft.carservice.util.EmailSender;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private final ServiceDetailsRepository serviceDetailsRepository;
    private final ItemRepository itemRepository;
    private final TechnicianRepository technicianRepository;
    private final VehicleRepository vehicleRepository;
    private final MechanicServiceRepository mechanicServiceRepository;
    private final EmailSender emailSender;

    public VehicalServiceServiceImpl(ServiceRepository serviceRepository, ServiceDetailsRepository serviceDetailsRepository, ItemRepository itemRepository, TechnicianRepository technicianRepository, VehicleRepository vehicleRepository, MechanicServiceRepository mechanicServiceRepository, EmailSender emailSender) {
        this.serviceRepository = serviceRepository;
        this.serviceDetailsRepository = serviceDetailsRepository;
        this.itemRepository = itemRepository;
        this.technicianRepository = technicianRepository;
        this.vehicleRepository = vehicleRepository;
        this.mechanicServiceRepository = mechanicServiceRepository;
        this.emailSender = emailSender;
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
            double total = 0;
            List<ServiceDetails> serviceDetailsList = service.getServiceDetailsList();
            for (ServiceDetails serviceDetails : serviceDetailsList) {
                total += serviceDetails.getCost();
            }
            getServiceResponseDTO.setCost(total);
            getServiceResponseDTO.setTechnicianName(service.getTechnician().getName());
            getServiceResponseDTO.setNumberPlate(service.getVehicle().getNumberPlate());
            getServiceResponseDTO.setServiceDate(service.getService_date());
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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveServiceOny(UpdateAndSaveServiceRequestDTO requestDTO) {
        log.info("Execute method saveServiceOny : @param : {} ", requestDTO);
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
            service.setServiceId(requestDTO.getServiceId());
            service.setService_date(requestDTO.getService_date());
            service.setCost(requestDTO.getCost());


            serviceRepository.save(service);
        } catch (Exception e) {
            log.error("Method saveServiceOny : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public List<GetServiceResponseDTO> getServiceFilter(ServiceFilterRequestDTO requestDTO) {
        log.info("Execute method getServiceFilter : @param : {} ", requestDTO);
        try {
            String type = null;
            if (requestDTO.getType() != null) {
                type = requestDTO.getType().toString();
            }
            List<com.esoft.carservice.entity.Service> serviceList = serviceRepository.getAllServiceFilter(requestDTO.getServiceId(), requestDTO.getVehicleId(), requestDTO.getTechnicianId(), type, requestDTO.getStart(), requestDTO.getEnd());
            List<GetServiceResponseDTO> serviceResponseDTOList = new ArrayList<>();
            for (com.esoft.carservice.entity.Service service : serviceList) {
                GetServiceResponseDTO getServiceResponseDTO = new GetServiceResponseDTO();
                getServiceResponseDTO.setServiceId(service.getServiceId());
                double total = 0;
                List<ServiceDetails> serviceDetailsList = service.getServiceDetailsList();
                for (ServiceDetails serviceDetails : serviceDetailsList) {
                    total += serviceDetails.getCost();
                }
                getServiceResponseDTO.setCost(total);
                getServiceResponseDTO.setTechnicianName(service.getTechnician().getName());
                getServiceResponseDTO.setNumberPlate(service.getVehicle().getNumberPlate());
                getServiceResponseDTO.setServiceDate(service.getService_date());
                getServiceResponseDTO.setTechnicianId(service.getTechnician().getTechnicianId());
                getServiceResponseDTO.setType(service.getType());
                getServiceResponseDTO.setVehicleId(service.getVehicle().getVehicleId());
                if (service.getVehicle().getCustomer() != null) {
                    getServiceResponseDTO.setCustomerId(service.getVehicle().getCustomer().getCustomerId());
                    getServiceResponseDTO.setCustomerName(service.getVehicle().getCustomer().getName());
                }
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
            service.setService_date(new Date());
            service.setCost(requestDTO.getCost());
            serviceRepository.save(service);

            List<ServiceDetails> serviceDetailsList = new ArrayList<>();
            for (SaveServiceDetailsRequestDTO saveServiceDetailsRequestDTO : requestDTO.getSaveServiceDetails()) {

                ServiceDetails serviceDetails = new ServiceDetails();
                serviceDetails.setType(saveServiceDetailsRequestDTO.getType());
                serviceDetails.setCost(saveServiceDetailsRequestDTO.getCost());

                serviceDetails.setService(service);

                if (saveServiceDetailsRequestDTO.getType() == ServiceDetailsType.ITEM) {
                    Optional<Item> optionalItem = itemRepository.findById(saveServiceDetailsRequestDTO.getItemId());
                    if (!optionalItem.isPresent()) {
                        throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the item you are finding cannot be found. ");
                    }

                    Item item = optionalItem.get();
                    int quantity = item.getQuantity();
                    item.setQuantity(quantity - 1);
                    serviceDetails.setItem(item);

                } else {
                    Optional<MechanicService> optionalMechanicService = mechanicServiceRepository.findById(saveServiceDetailsRequestDTO.getItemId());

                    if (!optionalMechanicService.isPresent()) {
                        throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the service you are finding cannot be found. ");
                    }
                    MechanicService mechanicService = optionalMechanicService.get();
                    serviceDetails.setMechanicService(mechanicService);

                }
                serviceDetailsList.add(serviceDetails);
            }

//            service.setServiceDetailsList(serviceDetailsList);
            serviceDetailsRepository.saveAll(serviceDetailsList);


            //send service email

            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
            StringBuilder body = new StringBuilder();
            body.append("Dear Customer,\n\n");
            body.append("We hope this message finds you well. We wanted to inform you about the recent service on your vehicle.\n\n");

            body.append("Service Details:\n");
            body.append("Service ID: ").append(service.getServiceId()).append("\n");
            body.append("Service Date: ").append(dateFormat.format(service.getService_date())).append("\n");
            body.append("Service Type: ").append(service.getType()).append("\n");
            body.append("Cost: ").append(service.getCost()).append("\n");

            body.append("Vehicle Information:\n");
            body.append("Model: ").append(service.getVehicle().getVehicleType()).append("\n");
            body.append("License Plate: ").append(service.getVehicle().getNumberPlate()).append("\n");

            body.append("Technician Assigned:\n");
            body.append("Technician Name: ").append(service.getTechnician().getName()).append("\n");
            body.append("Technician Contact: ").append(service.getTechnician().getMobileNumber()).append("\n\n");

            body.append("Thank you for choosing our service. If you have any questions or need further assistance, feel free to reach out.\n\n");
            body.append("Best Regards,\nYour Service Team");


            emailSender.sendEmail(optionalVehicle.get().getCustomer().getUser().getEmail(), "Vehicle Service Information", body.toString());


        } catch (Exception e) {
            log.error("Method updateService : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }
}
