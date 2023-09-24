package com.esoft.carservice.entity;

import com.esoft.carservice.enums.UserRole;
import com.esoft.carservice.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long userId;
    public String email;
    public String password;
    public String name;
    @Enumerated(EnumType.STRING)
    public UserRole userRole;
    @Enumerated(EnumType.STRING)
    public UserStatus status;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Customer customer;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Admin admin;
}
