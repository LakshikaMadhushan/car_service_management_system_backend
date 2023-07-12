package com.esoft.carservice.entity;

import com.esoft.carservice.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long adminId;
    public String name;
    public String address1;
    public String address2;
    public UserStatus status;
    public String mobileNumber;
    public String qualification;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "admin")
    private User user;
}
