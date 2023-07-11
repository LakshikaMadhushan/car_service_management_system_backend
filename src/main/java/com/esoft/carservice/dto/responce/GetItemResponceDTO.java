package com.esoft.carservice.dto.responce;

import com.esoft.carservice.enums.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetItemResponceDTO {
    public long itemId;
    public String itemName;
    public double sellingPrice;
    public double buyingPrice;
    public String brand;
    public int quantity;
    public ItemStatus itemStatus;
}
