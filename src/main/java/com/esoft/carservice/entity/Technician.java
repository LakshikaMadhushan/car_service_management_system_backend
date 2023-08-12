package com.esoft.carservice.entity;

import com.esoft.carservice.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Technician {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long technicianId;
    public String name;
    public String address1;
    public String address2;
    public UserStatus status;
    public String mobileNumber;
    public String email;
    public String password;
    public String qualification;

    @OneToMany(mappedBy = "technician")
    private List<Service> serviceList;
}
