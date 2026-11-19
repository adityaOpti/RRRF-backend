package com.probity.echawdi.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.probity.echawdi.entity.Users;
import com.probity.echawdi.utils.CommonConstants;

import lombok.Builder;
import lombok.Data;

/**
 * @author Bhagyashri Lolage
 *
 */
@Data
@Builder
public class UserDetailsImpl implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private String fullname;
	private String username;
	@JsonIgnore
	private String password;
	private String emailId;
	private String marathiName;
	private String talukaCode;
	private String districtCode;
	private String talukaName;
	private String districtName;
	private String desg;
	private Collection<? extends GrantedAuthority> authorities;

	private String logIn;
	
	private List<String> sajjaCodes;
	
	

	

	public UserDetailsImpl(String fullname, String username, String emailId, String password,
			String marathiName, String talukaCode, String districtCode, String talukaName, String districtName,String desg,
			Collection<? extends GrantedAuthority> authorities, String login,  List<String> sajjaCodes) {
		super();
		this.fullname = fullname;
		this.username = username;
		this.emailId = emailId;
		this.password = password;
		this.marathiName = marathiName;
		this.talukaCode = talukaCode;
		this.districtCode = districtCode;
		this.talukaName = talukaName;
		this.districtName = districtName;
		this.desg =desg;
		this.authorities = authorities;
		this.logIn = login;
		this.sajjaCodes = sajjaCodes;
		
	}
	
	
	
	
	
	public UserDetailsImpl(String fullname, String districtCode, String username,
            String talukaCode, String logIn, String password,
            String marathiName, Collection<? extends GrantedAuthority> authorities) {
			super();
			this.fullname = fullname;
			this.districtCode = districtCode;
			this.username = username;
			this.talukaCode = talukaCode;
			this.logIn = logIn;
			this.password = password;
			this.marathiName = marathiName;
			this.authorities = authorities;
			}

	
	
	public static UserDetailsImpl createDDEUser(
	        String fullname,
	        String username,
	        String emailId,
	        String password,
	        String marathiName,
	        String districtCode,
	        String desg,
	        Collection<? extends GrantedAuthority> authorities) {

	    UserDetailsImpl user = new UserDetailsImpl(
	            fullname,
	            districtCode,
	            username,
	            null,
	            null,
	            password,
	            marathiName,
	            authorities
	    );

	    user.emailId = emailId;
	    user.desg = desg;

	    return user;
	}

	
	public static UserDetailsImpl build(Users user) {
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if(user.getServarthId().equals("Collector")) {
			authorities.add(new SimpleGrantedAuthority("ROLE_COLLECTOR"));
		}
		else if(user.getServarthId().startsWith("DYSLR_")) {
			System.out.println("role DYSLR KE UNDER HU-----");
			authorities.add(new SimpleGrantedAuthority("ROLE_DYSLR"));
		}else if(user.getServarthId().startsWith("TAH_")) {
			System.out.println("Tahsildar role em hu------------");
			authorities.add(new SimpleGrantedAuthority("ROLE_TAH"));
		}else if(user.getDesg().trim().equals(CommonConstants.MANDAL_ADHIKARI_PREFIX)){
			
			System.out.println("user.getDesg()-----------------: "+user.getDesg());
			
			authorities.add(new SimpleGrantedAuthority("ROLE_MANDAL_ADHIKARI"));
			
		}else if(user.getDesg().equals(CommonConstants.TAHSILDAR_PREFIX)){
			
			System.out.println("user.getDesg()-----------------: "+user.getDesg());
			
			authorities.add(new SimpleGrantedAuthority("ROLE_TAHSHILDAR"));
			
		}else if(user.getDesg().equals(CommonConstants.COLLECTOR_PREFIX)){
			System.out.println("user.getDesg()-----------------: "+user.getDesg());
			
			authorities.add(new SimpleGrantedAuthority("ROLE_COLLECTOR"));
			
		}else {
			authorities.add(new SimpleGrantedAuthority("ROLE_TALATHI"));
		}
		
		
			
		if(user.getServarthId().startsWith("TAH_")) {
			

			
			
			return new UserDetailsImpl(
				    user.getFullName(),
				    user.getDistrictCode(),
				    user.getServarthId(),           // username = login_name (TAH_NAG_KAT)
				    user.getTalukaCode(),
				    user.getLogIn(),           // login (can be same as username)
				    user.getPassword(),
				    user.getMarathiName(),     // "तहसिलदार"
				    authorities
				);




			
		}else if (user.getTalukaCode() == null || user.getTalukaCode().trim().isEmpty()) {

		    // Execute your specific block of code here
		    System.out.println("Taluka code is null or empty");
		    return UserDetailsImpl.createDDEUser(
		            user.getFullName(),
		            user.getServarthId().trim(),
		            user.getEmailId(),
		            user.getPassword(),
		            user.getMarathiName(),
		            user.getDistrictCode().trim(),
		            user.getDesg().trim(),
		            authorities
		    );

		}else{
			
		
			
			return new UserDetailsImpl(
					
					
					
					user.getFullName(),
					user.getServarthId().trim(), 
					user.getEmailId(),
					user.getPassword(),
					user.getMarathiName(),
					user.getTalukaCode().trim(),
					user.getDistrictCode().trim(),
					user.getTalukaName(),
					user.getDistrictName(),
					user.getDesg().trim(),
					authorities,
					user.getLogIn(),
					user.getSajjaCodes()
					);
			
		}
			
			
			
		
			
			
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}





/*package com.probity.echawdi.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.probity.echawdi.entity.Users;

import lombok.Builder;
import lombok.Data;

/**
 * @author Bhagyashri Lolage
 *
 */
/*@Data
@Builder
public class UserDetailsImpl implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private String fullname;
	private String username;
	@JsonIgnore
	private String password;
	private String emailId;
	private String marathiName;
	private String talukaCode;
	private String districtCode;
	private String talukaName;
	private String districtName;
	private String desg;
	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(String fullname, String username, String emailId, String password,
			String marathiName, String talukaCode, String districtCode, String talukaName, String districtName,String desg,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.fullname = fullname;
		this.username = username;
		this.emailId = emailId;
		this.password = password;
		this.marathiName = marathiName;
		this.talukaCode = talukaCode;
		this.districtCode = districtCode;
		this.talukaName = talukaName;
		this.districtName = districtName;
		this.desg =desg;
		this.authorities = authorities;
	}
	
	public static UserDetailsImpl build(Users user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if(user.getServarthId().equals("Collector")) {
			authorities.add(new SimpleGrantedAuthority("ROLE_COLLECTOR"));
		}
		else if(user.getServarthId().startsWith("DYSLR_")) {
			authorities.add(new SimpleGrantedAuthority("ROLE_DYSLR"));
		}else {
			authorities.add(new SimpleGrantedAuthority("ROLE_TALATHI"));
		}
		return new UserDetailsImpl(
				user.getFullName(),
				user.getServarthId().trim(), 
				user.getEmailId(),
				user.getPassword(),
				user.getMarathiName(),
				user.getTalukaCode().trim(),
				user.getDistrictCode().trim(),
				user.getTalukaName(),
				user.getDistrictName(),
				user.getDesg().trim(),
				authorities
				);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}*/