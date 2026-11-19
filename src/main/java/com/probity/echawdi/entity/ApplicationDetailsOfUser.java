package com.probity.echawdi.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "application_details")
public class ApplicationDetailsOfUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "application_id")
	private String applicationId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "district_code")
	private String districtCode;

	@Column(name = "taluka_code")
	private String talukaCode;

	@Column(name = "ccode")
	private String cCode;

	@Column(name = "kabja_info")
	private String kabjaInfo;

	@Column(name = "applicant_address")
	private String applicantAddress;

	@Column(name = "mob_no")
	private String mobileNo;

	@Column(name = "kabja_additional_info")
	private String additionalInfo;

	@Column(name = "kabjedar_name")
	private String kabjedarName;

	@Column(name = "kabja_land_address")
	private String landAddress;

	@Column(name = "mob_no_2")
	private String mobileNo2;

	@Column(name = "kabja_proof_path")
	private String kabjaProofPath;

	@Column(name = "kabja_proof_filename")
	private String kabjaProofFileName;

	@Column(name = "applicant_photo_path")
	private String applicantPhotoPath;

	@Column(name = "applicant_photo_filename")
	private String applicantPhotoFileName;

	@Column(name = "flag")
	private String activeFlag;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private UserRegistration userRegistration;

	@OneToOne(fetch = FetchType.LAZY)
	private SeasonCode seasonCode;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

}
