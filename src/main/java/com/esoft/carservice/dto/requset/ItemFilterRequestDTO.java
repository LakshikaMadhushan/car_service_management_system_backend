package com.esoft.carservice.dto.requset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemFilterRequestDTO {
    public long categoryId;
    public String name;
}