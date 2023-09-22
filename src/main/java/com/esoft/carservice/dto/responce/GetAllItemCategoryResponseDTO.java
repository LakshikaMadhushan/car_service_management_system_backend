package com.esoft.carservice.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllItemCategoryResponseDTO {
    public long categoryId;
    public String categoryName;
}
