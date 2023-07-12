package com.esoft.carservice.dto.requset;

import com.esoft.carservice.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSaveCustomer {
    public long customerId;
    public String name;
    public String address1;
    public String address2;
    public UserStatus status;
    public String mobileNumber;
}