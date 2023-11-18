package com.esoft.carservice.dto.responce;

import com.esoft.carservice.enums.ServiceDetailsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetServiceDetailsResponseDTO {
    public long serviceDetailsId;
    @Enumerated(EnumType.STRING)
    public ServiceDetailsType type;
    public double cost;

    public long itemId;
    public String itemName;
    public long categoryId;
    public String categoryName;

}
