package com.esoft.carservice.dto.responce;

import com.esoft.carservice.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAdminResponseDTO {
    public long adminId;
    public long userId;
    public String name;
    public String email;
    public String address1;
    public String nic;
    public UserStatus status;
    public String mobileNumber;
    public String qualification;
}
