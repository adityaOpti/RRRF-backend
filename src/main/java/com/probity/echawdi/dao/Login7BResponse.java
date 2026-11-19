package com.probity.echawdi.dao;

import java.util.Date;

import com.probity.echawdi.entity.UserRegistration7B;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper =false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Login7BResponse {
	 private UserRegistration7B user;

	    private String jwtToken;


}
