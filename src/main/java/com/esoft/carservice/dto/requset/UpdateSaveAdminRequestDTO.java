package com.esoft.carservice.dto.requset;

import com.esoft.carservice.enums.UserRole;
import com.esoft.carservice.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSaveAdminRequestDTO {
    public long userId;
    public String name;
    public String address1;
    public String email;
    public String nic;
    public UserStatus status;
    public String mobileNumber;
    public String qualification;
    public String password;
    public UserRole userRole;
}
