package com.esoft.carservice.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminAllReportResponseDTO {
    public List<AdminReportResponseDTO> adminReportResponseDTOList;
    public double total;
    public double totalItem;
    public double totalService;
}
