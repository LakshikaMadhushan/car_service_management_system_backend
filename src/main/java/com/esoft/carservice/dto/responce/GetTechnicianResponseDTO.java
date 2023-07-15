package com.esoft.carservice.dto.responce;

import com.esoft.carservice.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetTechnicianResponseDTO {
    public long technicianId;
    public String name;
    public String address1;
    public String address2;
    public UserStatus status;
    public String mobileNumber;
    public String email;
    public String password;
}
