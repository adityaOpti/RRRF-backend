
package com.probity.echawdi.serviceImpl;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.probity.echawdi.dao.AkrushakRateDao;
import com.probity.echawdi.entity.UserRegistration7B;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class UserDetails7BImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final Integer id;
    private final String username;
    private final String password;
    private final String name;
    private final String ccode;
    private final String districtCode;
    private final String talukaCode;

//    public UserDetails7BImpl(
//            Integer id,
//            String username,
//            String password,
//            String name,
//            String ccode,
//            String districtCode,
//            String talukaCode) {
//
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.name = name;
//        this.ccode = ccode;
//        this.districtCode = districtCode;
//        this.talukaCode = talukaCode;
//    }

    public static UserDetails7BImpl build(UserRegistration7B user) {
        return new UserDetails7BImpl(
                user.getId(),
                user.getMobileNumber(),
                user.getPassword(),
                user.getName(),
                user.getCcode(),
                user.getDistrictCode(),
                user.getTalukaCode()
        );
    }

//    public Integer getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getCcode() {
//        return ccode;
//    }
//
//    public String getDistrictCode() {
//        return districtCode;
//    }
//
//    public String getTalukaCode() {
//        return talukaCode;
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
return Collections.singletonList(
        new SimpleGrantedAuthority("ROLE_7B")
);    }

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}

