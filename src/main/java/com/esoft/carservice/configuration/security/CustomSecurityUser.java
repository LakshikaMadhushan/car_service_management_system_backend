package com.esoft.carservice.configuration.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;
import java.util.Date;



public class CustomSecurityUser extends User {

//    private UserRole role;
    private String name;
//    private long userId;
//
//    public String getUserRoles() {
//        return userRoles;
//    }
//
//    public void setUserRoles(String userRoles) {
//        this.userRoles = userRoles;
//    }
//
//    private String email;
//    private String firstName;
//    private String lastName = "";
//    private Gender gender;
//    private Date dateOfBirth;
//    private String profileImageUrl;
//    private String mobileNumber;
//    private String nic;
//    private String empNo;
//    private Date createdAt;
//    private String cbNumber;
//    private long studentId;
//    private boolean userEnabled = true;
//    private DepartmentBasicDTO department;

    public CustomSecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public UserRole getRole() {
//        return role;
//    }
//
//    public void setRole(UserRole role) {
//        this.role = role;
//    }
//
//    public long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(long userId) {
//        this.userId = userId;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public Gender getGender() {
//        return gender;
//    }
//
//    public void setGender(Gender gender) {
//        this.gender = gender;
//    }
//
//    public Date getDateOfBirth() {
//        return dateOfBirth;
//    }
//
//    public void setDateOfBirth(Date dateOfBirth) {
//        this.dateOfBirth = dateOfBirth;
//    }
//
//    public String getProfileImageUrl() {
//        return profileImageUrl;
//    }
//
//    public void setProfileImageUrl(String profileImageUrl) {
//        this.profileImageUrl = profileImageUrl;
//    }
//
//    public String getMobileNumber() {
//        return mobileNumber;
//    }
//
//    public void setMobileNumber(String mobileNumber) {
//        this.mobileNumber = mobileNumber;
//    }
//
//    public String getNic() {
//        return nic;
//    }
//
//    public void setNic(String nic) {
//        this.nic = nic;
//    }
//
//    public String getEmpNo() {
//        return empNo;
//    }
//
//    public void setEmpNo(String empNo) {
//        this.empNo = empNo;
//    }
//
//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public String getCbNumber() {
//        return cbNumber;
//    }
//
//    public void setCbNumber(String cbNumber) {
//        this.cbNumber = cbNumber;
//    }
//
//    public long getStudentId() {
//        return studentId;
//    }
//
//    public void setStudentId(long studentId) {
//        this.studentId = studentId;
//    }
//
//    public boolean isUserEnabled() {
//        return userEnabled;
//    }
//
//    public void setUserEnabled(boolean userEnabled) {
//        this.userEnabled = userEnabled;
//    }
}
