package com.probity.echawdi.entity;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="token")
public class Token {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "token_seq_gen")
	@SequenceGenerator(
			name = "token_seq_gen",
			sequenceName = "token_seq",
			allocationSize = 1)
	@Column(name = "id")
	private Long id;

	// ACCESS TOKEN
	@Column(name = "access_token", length = 2000)
	private String accessToken;

	// REFRESH TOKEN
	@Column(name = "refresh_token", length = 500)
	private String refreshToken;

	// ACCESS TOKEN GENERATED TIME
	@Column(name = "generated_time")
	private Date generatedTime;

	// ACCESS TOKEN EXPIRY TIME
	@Column(name = "expiry_time")
	private Date expiryTime;

	// REFRESH TOKEN EXPIRY
	@Column(name = "refresh_expiry_date")
	private Instant refreshExpiryDate;

	@Column(name = "servarth_id")
	private String servarthId;

	// LOGOUT STATUS
	@Column(name = "logged_out")
	private Boolean loggedOut = false;

	// REVOKE STATUS
	@Column(name = "revoked")
	private Boolean revoked = false;
	
	/*@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="token_seq_gen")
	@SequenceGenerator(name="token_seq_gen", sequenceName="token_seq", allocationSize=1)
	@Column(name="id")
	private Long id;
	
	@Column(name="token")
	private String token;
	
	@Column(name="generated_time")
	private Date generatedTime;
	
	@Column(name="expiry_time")
	private Date expiryTime;
	
	@Column(name="servarth_id")
	private String servarthId;*/
	
	
}
