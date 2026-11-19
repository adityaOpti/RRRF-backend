package com.probity.echawdi.entity;

import java.time.LocalDateTime;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.probity.echawdi.dao.Form7ADao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_registration", schema = "public")
public class UserRegistration7B {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
        generator = "user_registration_id_gen")
    @SequenceGenerator(
        name = "user_registration_id_gen",
        sequenceName = "user_registration_id_seq",
        allocationSize = 1
    )
    private Integer id;

    @Column(name = "user_id", nullable = false, unique = true, length = 15)
    private String userId;

    @Column(name = "ccode")
    private String ccode;

    @Column(name = "taluka_code")
    private String talukaCode;

    @Column(name = "taluka_name_marathi", length = 50)
    private String talukaNameMarathi;

    @Column(name = "district_code")
    private String districtCode;

    @Column(name = "district_name_marathi", length = 50)
    private String districtNameMarathi;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "owner_type", nullable = false)
    private String ownerType;

    @Column(name = "current_address", length = 500)
    private String currentAddress;

    @Column(name = "address_of_land", length = 500)
    private String addressOfLand;

    @Column(name = "mobile_number", length = 10, unique = true)
    private String mobileNumber;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "date_of_birth", length = 15)
    private String dob;

    @Column(name = "aadhar", length = 12, unique = true)
    private String aadhar;

    @Column(name = "active_flag", length = 1)
    private String activeFlag;

    @Column(name = "created_on", updatable = false)
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Column(name = "is_active", length = 1)
    private String isActive;

    @PrePersist
    public void beforeInsert() {
        LocalDateTime now = LocalDateTime.now();
        if (createdOn == null) createdOn = now;
        updatedOn = now;

        if (activeFlag == null) activeFlag = "Y";
        if (isActive == null) isActive = "Y";
    }

    @PreUpdate
    public void beforeUpdate() {
        updatedOn = LocalDateTime.now();
    }

//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public String getUserId() {
//		return userId;
//	}
//
//	public void setUserId(String userId) {
//		this.userId = userId;
//	}
//
//	public String getCcode() {
//		return ccode;
//	}
//
//	public void setCcode(String ccode) {
//		this.ccode = ccode;
//	}
//
//	public String getTalukaCode() {
//		return talukaCode;
//	}
//
//	public void setTalukaCode(String talukaCode) {
//		this.talukaCode = talukaCode;
//	}
//
//	public String getTalukaNameMarathi() {
//		return talukaNameMarathi;
//	}
//
//	public void setTalukaNameMarathi(String talukaNameMarathi) {
//		this.talukaNameMarathi = talukaNameMarathi;
//	}
//
//	public String getDistrictCode() {
//		return districtCode;
//	}
//
//	public void setDistrictCode(String districtCode) {
//		this.districtCode = districtCode;
//	}
//
//	public String getDistrictNameMarathi() {
//		return districtNameMarathi;
//	}
//
//	public void setDistrictNameMarathi(String districtNameMarathi) {
//		this.districtNameMarathi = districtNameMarathi;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getOwnerType() {
//		return ownerType;
//	}
//
//	public void setOwnerType(String ownerType) {
//		this.ownerType = ownerType;
//	}
//
//	public String getCurrentAddress() {
//		return currentAddress;
//	}
//
//	public void setCurrentAddress(String currentAddress) {
//		this.currentAddress = currentAddress;
//	}
//
//	public String getAddressOfLand() {
//		return addressOfLand;
//	}
//
//	public void setAddressOfLand(String addressOfLand) {
//		this.addressOfLand = addressOfLand;
//	}
//
//	public String getMobileNumber() {
//		return mobileNumber;
//	}
//
//	public void setMobileNumber(String mobileNumber) {
//		this.mobileNumber = mobileNumber;
//	}
//
//	public String getGender() {
//		return gender;
//	}
//
//	public void setGender(String gender) {
//		this.gender = gender;
//	}
//
//	
//
//	public String getDob() {
//		return dob;
//	}
//
//	public void setDob(String dob) {
//		this.dob = dob;
//	}
//
//	public String getAadhar() {
//		return aadhar;
//	}
//
//	public void setAadhar(String aadhar) {
//		this.aadhar = aadhar;
//	}
//
//	public String getActiveFlag() {
//		return activeFlag;
//	}
//
//	public void setActiveFlag(String activeFlag) {
//		this.activeFlag = activeFlag;
//	}
//
//	public LocalDateTime getCreatedOn() {
//		return createdOn;
//	}
//
//	public void setCreatedOn(LocalDateTime createdOn) {
//		this.createdOn = createdOn;
//	}
//
//	public LocalDateTime getUpdatedOn() {
//		return updatedOn;
//	}
//
//	public void setUpdatedOn(LocalDateTime updatedOn) {
//		this.updatedOn = updatedOn;
//	}
//
//	public String getIsActive() {
//		return isActive;
//	}
//
//	public void setIsActive(String isActive) {
//		this.isActive = isActive;
//	}
//
//    // Generate standard getters and setters for all fields.
//    
}
