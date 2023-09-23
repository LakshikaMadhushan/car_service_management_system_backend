package com.esoft.carservice.dto.requset;

import com.esoft.carservice.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminFilterRequestDTO {
    public long userId;
    public long adminId;
    public String nic;
    public UserStatus userStatus;
    public String email;
    public String contactNo;
}
