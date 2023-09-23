package com.esoft.carservice.dto.requset;

import com.esoft.carservice.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TechnicianFilterRequestDTO {
    public long technicianId;
    public String name;
    public UserStatus status;
    public String email;
    public String nic;
}
