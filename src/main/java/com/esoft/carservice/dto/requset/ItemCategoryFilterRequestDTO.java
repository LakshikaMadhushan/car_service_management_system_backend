package com.esoft.carservice.dto.requset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCategoryFilterRequestDTO {
    public long categoryId;
    public String categoryName;
}
