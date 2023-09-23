package com.esoft.carservice.dto.requset;

import com.esoft.carservice.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateAndSaveTechnicianRequestDTO {
    public long technicianId;
    public String name;
    public String address1;
    public UserStatus status;
    public String mobileNumber;
    public String email;
    public String nic;
    public String password;
}
