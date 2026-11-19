
package com.probity.echawdi.dao;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String ownerType;

    private String name;
    private String organizationName;

    private String mobileNumber;
    private String aadhar;
    private String cCode;

    private String currentAddress;
    private String addressOfLand;
    private String gender;
    private String dob;

    private String districtCode;
    private String districtNameMarathi;
    private String talukaCode;
    private String talukaNameMarathi;

//    public String getEmail() { return email; }
//    public void setEmail(String email) { this.email = email; }
//
//    public String getPassword() { return password; }
//    public void setPassword(String password) { this.password = password; }
//
//    public String getOwnerType() { return ownerType; }
//    public void setOwnerType(String ownerType) { this.ownerType = ownerType; }
//
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//
//    public String getOrganizationName() { return organizationName; }
//    public void setOrganizationName(String organizationName) {
//        this.organizationName = organizationName;
//    }
//
//    public String getMobileNumber() { return mobileNumber; }
//    public void setMobileNumber(String mobileNumber) {
//        this.mobileNumber = mobileNumber;
//    }
//
//    public String getAadhar() { return aadhar; }
//    public void setAadhar(String aadhar) { this.aadhar = aadhar; }
//
//    public String getcCode() { return cCode; }
//    public void setcCode(String cCode) { this.cCode = cCode; }
//
//    public String getCurrentAddress() { return currentAddress; }
//    public void setCurrentAddress(String currentAddress) {
//        this.currentAddress = currentAddress;
//    }
//
//    public String getAddressOfLand() { return addressOfLand; }
//    public void setAddressOfLand(String addressOfLand) {
//        this.addressOfLand = addressOfLand;
//    }
//
//    public String getGender() { return gender; }
//    public void setGender(String gender) { this.gender = gender; }
//
//    public String getDob() { return dob; }
//    public void setDob(String dob) { this.dob = dob; }
//
//    public String getDistrictCode() { return districtCode; }
//    public void setDistrictCode(String districtCode) {
//        this.districtCode = districtCode;
//    }
//
//    public String getDistrictNameMarathi() { return districtNameMarathi; }
//    public void setDistrictNameMarathi(String districtNameMarathi) {
//        this.districtNameMarathi = districtNameMarathi;
//    }
//
//    public String getTalukaCode() { return talukaCode; }
//    public void setTalukaCode(String talukaCode) {
//        this.talukaCode = talukaCode;
//    }
//
//    public String getTalukaNameMarathi() { return talukaNameMarathi; }
//    public void setTalukaNameMarathi(String talukaNameMarathi) {
//        this.talukaNameMarathi = talukaNameMarathi;
//    }
}
