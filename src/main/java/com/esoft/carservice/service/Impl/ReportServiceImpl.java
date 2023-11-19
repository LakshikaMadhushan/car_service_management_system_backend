package com.esoft.carservice.service.Impl;

import com.esoft.carservice.configuration.exception.CustomException;
import com.esoft.carservice.dto.requset.AdminReportFilterRequestDTO;
import com.esoft.carservice.dto.requset.CustomerDashboardFilterRequestDTO;
import com.esoft.carservice.dto.responce.*;
import com.esoft.carservice.entity.ServiceDetails;
import com.esoft.carservice.entity.Vehicle;
import com.esoft.carservice.enums.ServiceDetailsType;
import com.esoft.carservice.repository.*;
import com.esoft.carservice.service.ReportService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.esoft.carservice.constant.ResponseCodes.OPERATION_FAILED;
import static com.esoft.carservice.constant.ResponseMessages.UNEXPECTED_ERROR_OCCURRED;

@Log4j2
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ReportServiceImpl implements ReportService {

    private final ServiceRepository serviceRepository;
    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;
    private final MechanicServiceRepository mechanicServiceRepository;
    private final ItemRepository itemRepository;
    private final TechnicianRepository technicianRepository;
    private final ServiceDetailsRepository serviceDetailsRepository;

    public ReportServiceImpl(ServiceRepository serviceRepository, VehicleRepository vehicleRepository, CustomerRepository customerRepository, AdminRepository adminRepository, MechanicServiceRepository mechanicServiceRepository, ItemRepository itemRepository, TechnicianRepository technicianRepository, ServiceDetailsRepository serviceDetailsRepository) {
        this.serviceRepository = serviceRepository;
        this.vehicleRepository = vehicleRepository;
        this.customerRepository = customerRepository;
        this.adminRepository = adminRepository;
        this.mechanicServiceRepository = mechanicServiceRepository;
        this.itemRepository = itemRepository;
        this.technicianRepository = technicianRepository;
        this.serviceDetailsRepository = serviceDetailsRepository;
    }

    @Override
    public AdminAllReportResponseDTO getAllAdminReport(AdminReportFilterRequestDTO requestDTO) {
        log.info("Execute method getAllAdminReport: @param : {}", requestDTO);
        try {
            double total = 0;
            double totalItem = 0;
            double totalService = 0;
            AdminAllReportResponseDTO allReportResponseDTO = new AdminAllReportResponseDTO();
            List<AdminReportResponseDTO> responseDTOS = new ArrayList<>();

            String serviceType = null;
            if (requestDTO.getType() != null) {
                serviceType = requestDTO.getType().name();
            }
            List<com.esoft.carservice.entity.Service> adminReportFilter = serviceRepository.getAdminReportFilter
                    (requestDTO.getTechnicianId(), requestDTO.getVehicleId(), serviceType, requestDTO.getStart(),
                            requestDTO.getEnd(), requestDTO.getCustomerId());

            for (com.esoft.carservice.entity.Service service : adminReportFilter) {
                AdminReportResponseDTO adminReportResponseDTO = new AdminReportResponseDTO();
                adminReportResponseDTO.setCost(service.getCost());
                adminReportResponseDTO.setService_date(service.getService_date());
                adminReportResponseDTO.setServiceId(service.getServiceId());
                adminReportResponseDTO.setType(service.getType());
                if (service.getTechnician() != null) {
                    adminReportResponseDTO.setTechnician(service.getTechnician().getName());
                    adminReportResponseDTO.setTechnicianId(service.getTechnician().getTechnicianId());
                }
                if (service.getVehicle() != null) {
                    adminReportResponseDTO.setVehicle(service.getVehicle().getNumberPlate());
                    adminReportResponseDTO.setVehicleId(service.getVehicle().getVehicleId());
                    if (service.getVehicle().getCustomer() != null) {
                        adminReportResponseDTO.setCustomerId(service.getVehicle().getCustomer().getCustomerId());
                        adminReportResponseDTO.setCustomer(service.getVehicle().getCustomer().getName());
                    }
                }

                List<ServiceDetails> serviceDetailsList = service.getServiceDetailsList();
                List<GetServiceDetailsResponseDTO> serviceDetailsResponseDTOS = new ArrayList<>();
                for (ServiceDetails serviceDetails : serviceDetailsList) {
                    GetServiceDetailsResponseDTO getServiceDetailsResponseDTO = new GetServiceDetailsResponseDTO();
                    getServiceDetailsResponseDTO.setServiceDetailsId(serviceDetails.getServiceDetailsId());
                    getServiceDetailsResponseDTO.setType(serviceDetails.getType());
                    if (serviceDetails.getType() == ServiceDetailsType.ITEM) {
                        getServiceDetailsResponseDTO.setItemId(serviceDetails.getItem().getItemId());
                        getServiceDetailsResponseDTO.setItemName(serviceDetails.getItem().getItemName());
                        getServiceDetailsResponseDTO.setCost(serviceDetails.getItem().getSellingPrice());
                        total += serviceDetails.getItem().getSellingPrice();
                        totalItem += serviceDetails.getItem().getSellingPrice();
                    } else if (serviceDetails.getType() == ServiceDetailsType.SERVICE) {
                        getServiceDetailsResponseDTO.setItemId(serviceDetails.getMechanicService().getMechanicServiceId());
                        getServiceDetailsResponseDTO.setItemName(serviceDetails.getMechanicService().getName());
                        getServiceDetailsResponseDTO.setCost(serviceDetails.getMechanicService().getPrice());
                        total += serviceDetails.getMechanicService().getPrice();
                        totalService += serviceDetails.getMechanicService().getPrice();
                    }


                    serviceDetailsResponseDTOS.add(getServiceDetailsResponseDTO);
                }
                adminReportResponseDTO.setServiceDetailsResponseDTOS(serviceDetailsResponseDTOS);

                responseDTOS.add(adminReportResponseDTO);
            }

            allReportResponseDTO.setAdminReportResponseDTOList(responseDTOS);
            allReportResponseDTO.setTotal(total);
            allReportResponseDTO.setTotalItem(totalItem);
            allReportResponseDTO.setTotalService(totalService);
            return allReportResponseDTO;

        } catch (Exception e) {
            log.error("Method getAllAdminReport : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public AdminAllDashboardResponseDTO getAllAdminDashboard() {
        try {
            log.info("Execute method getAllAdminDashboard: @param : {}");
            AdminAllDashboardResponseDTO responseDTO = new AdminAllDashboardResponseDTO();

            responseDTO.setTotalAdmin(adminRepository.findAll().size());
            responseDTO.setTotalCustomer(customerRepository.findAll().size());
            responseDTO.setTotalItems(itemRepository.findAll().size());
            responseDTO.setTotalMechanicServices(mechanicServiceRepository.findAll().size());
            responseDTO.setTotalServices(serviceRepository.findAll().size());
            responseDTO.setTotalTechnician(technicianRepository.findAll().size());
            responseDTO.setTotalVehicle(vehicleRepository.findAll().size());

            return responseDTO;
        } catch (Exception e) {
            log.error("Method getAllAdminDashboard : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public CustomerAllDashboardResponseDTO getAllCustomerDashboard(CustomerDashboardFilterRequestDTO requestDTO) {
        try {
            log.info("Execute method getAllAdminCustomerDashboard: @param : {}");
            CustomerAllDashboardResponseDTO responseDTO = new CustomerAllDashboardResponseDTO();

            Optional<com.esoft.carservice.entity.Service> optionalService = serviceRepository.serviceDate(requestDTO.getUserId());
            if (optionalService.isPresent()) {
                responseDTO.setLastServiceDate(optionalService.get().getService_date());
                responseDTO.setVehicleNo(optionalService.get().getVehicle().getNumberPlate());
            }
            Long allServiceItemCost = serviceDetailsRepository.getAllServiceItemCost(requestDTO.getUserId());
            Long allServiceCost = serviceDetailsRepository.getAllServiceCost(requestDTO.getUserId());
            responseDTO.setPartCost(allServiceItemCost);
            responseDTO.setServiceCost(allServiceCost);
            List<com.esoft.carservice.entity.Service> serviceList = serviceRepository.serviceCount(requestDTO.getUserId());
            responseDTO.setServiceCount(serviceList.size());
            List<Vehicle> vehicles = vehicleRepository.vehicleCount(requestDTO.getUserId());
            responseDTO.setVehicleCount(vehicles.size());


            return responseDTO;
        } catch (Exception e) {
            log.error("Method getAllAdminCustomerDashboard : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, UNEXPECTED_ERROR_OCCURRED);
        }
    }

}
