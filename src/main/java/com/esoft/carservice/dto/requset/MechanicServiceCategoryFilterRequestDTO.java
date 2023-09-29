package com.esoft.carservice.dto.requset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MechanicServiceCategoryFilterRequestDTO {
    public long mechanicServiceCategoryId;
    public String name;
}
