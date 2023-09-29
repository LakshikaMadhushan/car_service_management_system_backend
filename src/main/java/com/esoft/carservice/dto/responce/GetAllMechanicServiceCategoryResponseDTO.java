package com.esoft.carservice.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllMechanicServiceCategoryResponseDTO {
    public long mechanicServiceCategoryId;
    public String name;
}
