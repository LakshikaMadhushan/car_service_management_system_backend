package com.esoft.carservice.dto.requset;

import com.esoft.carservice.enums.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSaveItemRequestDTO {
    public long itemId;
    public long categoryId;
    public String itemName;
    public double sellingPrice;
    public double buyingPrice;
    public String brand;
    public int quantity;
    @Enumerated(EnumType.STRING)
    public ItemStatus itemStatus;
    public String sellerName;
}
