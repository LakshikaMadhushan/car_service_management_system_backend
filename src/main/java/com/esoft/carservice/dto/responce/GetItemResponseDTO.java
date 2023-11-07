package com.esoft.carservice.dto.responce;

import com.esoft.carservice.enums.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetItemResponseDTO {
    public long itemId;
    public String itemName;
    public double sellingPrice;
    public double buyingPrice;
    public String brand;
    public String categoryName;
    public String sellerName;
    public long categoryId;
    public int quantity;
    public ItemStatus itemStatus;
}
