package com.esoft.carservice.dto.requset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSaveItemCategoryRequestDTO {
    public long categoryId;
    public String categoryName;
}
