package com.probity.echawdi.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "user_registration")
public class UserRegistration {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "district_code")
	private String districtCode;

	@Column(name = "taluka_code")
	private String talukaCode;

	@Column(name = "district_name_marathi")
	private String districtName;

	@Column(name = "taluka_name_marathi")
	private String talukaName;

	@Column(name = "ccode")
	private String cCode;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "name")
	private String name;

	@Column(name = "owner_type")
	private String ownerType;

	@Column(name = "current_address")
	private String currentAddress;

	@Column(name = "address_of_land")
	private String addressOfLand;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "gender")
	private String gender;

	@Column(name = "date_of_birth")
	private String dob;

	@Column(name = "aadhar")
	private String adharNo;

	@Column(name = "active_flag")
	private Character flag;

	@Column(name = "created_on")
	private LocalDateTime createdAt;

	@Column(name = "updated_on")
	private LocalDateTime updatedAt;

	@Column(name = "is_active")
	private Character isActive;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getTalukaCode() {
		return talukaCode;
	}

	public void setTalukaCode(String talukaCode) {
		this.talukaCode = talukaCode;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getTalukaName() {
		return talukaName;
	}

	public void setTalukaName(String talukaName) {
		this.talukaName = talukaName;
	}

	public String getcCode() {
		return cCode;
	}

	public void setcCode(String cCode) {
		this.cCode = cCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}

	public String getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public String getAddressOfLand() {
		return addressOfLand;
	}

	public void setAddressOfLand(String addressOfLand) {
		this.addressOfLand = addressOfLand;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getAdharNo() {
		return adharNo;
	}

	public void setAdharNo(String adharNo) {
		this.adharNo = adharNo;
	}

	public Character getFlag() {
		return flag;
	}

	public void setFlag(Character flag) {
		this.flag = flag;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Character getIsActive() {
		return isActive;
	}

	public void setIsActive(Character isActive) {
		this.isActive = isActive;
	}

	public UserRegistration() {
		super();
	}

	public UserRegistration(Long id, String userId, String districtCode, String talukaCode, String districtName,
			String talukaName, String cCode, String email, String password, String name, String ownerType,
			String currentAddress, String addressOfLand, String mobileNumber, String gender, String dob, String adharNo,
			Character flag, LocalDateTime createdAt, LocalDateTime updatedAt, Character isActive) {
		super();
		this.id = id;
		this.userId = userId;
		this.districtCode = districtCode;
		this.talukaCode = talukaCode;
		this.districtName = districtName;
		this.talukaName = talukaName;
		this.cCode = cCode;
		this.email = email;
		this.password = password;
		this.name = name;
		this.ownerType = ownerType;
		this.currentAddress = currentAddress;
		this.addressOfLand = addressOfLand;
		this.mobileNumber = mobileNumber;
		this.gender = gender;
		this.dob = dob;
		this.adharNo = adharNo;
		this.flag = flag;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isActive = isActive;
	}

	
}