package com.esoft.carservice.dto.requset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateAndSaveMechanicServiceCategoryRequestDTO {
    public long mechanicServiceCategoryId;
    public String name;
}
