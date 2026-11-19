package com.probity.echawdi.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.entity.Users;
import com.probity.echawdi.repository.MVillageMapperRepository;
import com.probity.echawdi.utils.DataSourceType;

/**
 * @author Bhagyashri Lolage
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private MVillageMapperRepository mVillageMapperRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		System.out.println(" UserDetailsServiceImpl mdhla username ----:"+username);
		
		DatabaseContextHolder.setCtx(DataSourceType.echmst);
		Users users=new Users();
		if(username.equals("Collector") || username.startsWith("DYSLR_") || username.startsWith("TAH_") ) {
			
			if(username.startsWith("TAH_")){
				
				DatabaseContextHolder.setCtx(DataSourceType.echmst);
				List<Object[]> userDetail= mVillageMapperRepository.getUserLoginInspection(username);
				DatabaseContextHolder.restoreCtx();
			
				users=userDetail.stream().map((object)->{
					Users user=new Users();
									
					user.setLogIn(object[1] != null ? object[1].toString() : null);
					System.out.println("login_name (username): " + user.getLogIn());

					user.setServarthId(object[0] != null ? object[0].toString() : null);
					System.out.println("sevarthid: " + user.getServarthId());

					user.setFullName(object[2] != null ? object[2].toString() : null);
					System.out.println("full_name: " + user.getFullName());

					user.setDistrictCode(object[3] != null ? object[3].toString() : null);
					System.out.println("districtCode: " + user.getDistrictCode());

					user.setTalukaCode(object[4] != null ? object[4].toString() : null);
					System.out.println("talukaCode: " + user.getTalukaCode());

					user.setPassword(object[5] != null ? object[5].toString() : null);
					System.out.println("password: " + user.getPassword());

					user.setMarathiName("तहसिलदार");  // Set Marathi name here as requested

					  
					return user;
				}).collect(Collectors.toList()).get(0);
				
			}else {
				
				DatabaseContextHolder.setCtx(DataSourceType.echmst);
				List<Object[]> userDetail= mVillageMapperRepository.getUserLogin(username);
				DatabaseContextHolder.restoreCtx();

				
				System.out.println("I came here in dyslr UserDetailsServiceImpl class ----: ");
				
				users=userDetail.stream().map((object)->{
					Users user=new Users();
					user.setServarthId(object[0].toString());
					user.setFullName(object[1].toString());
					user.setEmailId(object[2].toString());
					user.setDistrictCode(object[3].toString());
					user.setTalukaCode(object[4].toString());
					user.setTalukaName(object[5].toString());
					user.setMarathiName(object[6].toString());
					user.setDesg(object[7].toString());
					user.setPassword(object[8].toString());
					System.out.println("servarthId :"+user.getPassword());
					return user;
				}).collect(Collectors.toList()).get(0);
			}			
		}else{
			
			DatabaseContextHolder.setCtx(DataSourceType.echmst);
			List<Object[]> userDetail= mVillageMapperRepository.getUser(username);
			for (Object[] row : userDetail) {
		        for (int i = 0; i < row.length; i++) {
		            System.out.println("Column " + i + " = " + row[i]);
		        }
		        System.out.println("-----------------------------");
		    }
		
			if (userDetail != null && !userDetail.isEmpty()) {

			    Object[] user1 = userDetail.get(0);

			    // taluka_code is the 5th column in your SELECT query (index 4)
			    String talukaCode = user1[4] != null ? user1[4].toString().trim() : "";

			    if (talukaCode.isEmpty()) {

			        // Execute your alternate block of code
			        System.out.println("Taluka code is empty or null");

			        // TODO: Add your alternate logic here
			        DatabaseContextHolder.setCtx(DataSourceType.echmst);
					List<Object[]> userDetails= mVillageMapperRepository.getDDEUser(username);
					
					for (Object[] row : userDetails) {
				        for (int i = 0; i < row.length; i++) {
				            System.out.println("Column " + i + " = " + row[i]);
				        }
				        System.out.println("-----------------------------");
				    }
					
					DatabaseContextHolder.restoreCtx();

					users = userDetails.stream().map(object -> {
					    Users userDDE = new Users();

					    userDDE.setServarthId(object[0] != null ? object[0].toString() : null);
					    userDDE.setFullName(object[1] != null ? object[1].toString() : null);
					    userDDE.setEmailId(object[2] != null ? object[2].toString() : null);
					    //userDDE.setDistrictCode(object[3] != null ? object[3].toString() : null);
					    userDDE.setDistrictCode(object[3].toString());
					    userDDE.setMarathiName(object[4] != null ? object[4].toString() : null);
					    userDDE.setDesg(object[5] != null ? object[5].toString() : null);
					    userDDE.setPassword(object[6] != null ? object[6].toString() : null);
					    return userDDE;
					    
					}).collect(Collectors.toList()).get(0);
				
			        

			    } else {

			        // Continue with your existing block of code
			        System.out.println("Taluka code: " + talukaCode);
			        
			    	DatabaseContextHolder.restoreCtx();
//					DatabaseContextHolder.setCtx(DataSourceType.echmst);
//					DatabaseContextHolder.restoreCtx();
					users=userDetail.stream().map((object)->{
						Users user=new Users();
						user.setServarthId(object[0].toString());
						user.setFullName(object[1].toString());
						user.setEmailId(object[2].toString());
						user.setDistrictCode(object[3].toString());
						user.setTalukaCode(object[4].toString());
						user.setTalukaName(object[5].toString());
						user.setMarathiName(object[6].toString());
						user.setDesg(object[7].toString());
						user.setPassword(object[8].toString());
						return user;
					}).collect(Collectors.toList()).get(0);

			        // TODO: Add your existing logic here
			    }
			}
			
			
		
		}
		return UserDetailsImpl.build(users);
	}
}






/*package com.probity.echawdi.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.entity.Users;
import com.probity.echawdi.repository.MVillageMapperRepository;
import com.probity.echawdi.utils.DataSourceType;

/**
 * @author Bhagyashri Lolage
 *
 */
/*@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private MVillageMapperRepository mVillageMapperRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		DatabaseContextHolder.setCtx(DataSourceType.echmst);
		Users users=new Users();
		if(username.equals("Collector") || username.startsWith("DYSLR_")) {
			DatabaseContextHolder.setCtx(DataSourceType.echmst);
			List<Object[]> userDetail= mVillageMapperRepository.getUserLogin(username);
			DatabaseContextHolder.restoreCtx();

			
			users=userDetail.stream().map((object)->{
				Users user=new Users();
				user.setServarthId(object[0].toString());
				user.setFullName(object[1].toString());
				user.setEmailId(object[2].toString());
				user.setDistrictCode(object[3].toString());
				user.setTalukaCode(object[4].toString());
				user.setTalukaName(object[5].toString());
				user.setMarathiName(object[6].toString());
				user.setDesg(object[7].toString());
				user.setPassword(object[8].toString());
				return user;
			}).collect(Collectors.toList()).get(0);
//			users=new Users("Collector","8fc330d76e990ab6964af08ea1e47d2e",
//							 " "," "," "
//							 ,0,' ',
//							 " "," "," ",
//							 " "," ",
//							 "जिल्हाधिकारी", " ","echawdiquery@gmail.com", " ", " ", "COLLECTOR");
		}
//		else if(username.startsWith("DYSLR_")) {
//			users=new Users("DYSLR_san","8fc330d76e990ab6964af08ea1e47d2e",
//					 " "," "," "
//					 ,0,' ',
//					 " "," "," ",
//					 " "," ",
//					 "DYSLR", " ","test@gmail.com", " ", " ", "DYSLR");
//		}
		else {
			DatabaseContextHolder.setCtx(DataSourceType.echmst);
			List<Object[]> userDetail= mVillageMapperRepository.getUser(username);
			DatabaseContextHolder.restoreCtx();
//			DatabaseContextHolder.setCtx(DataSourceType.echmst);
//			DatabaseContextHolder.restoreCtx();
			users=userDetail.stream().map((object)->{
				Users user=new Users();
				user.setServarthId(object[0].toString());
				user.setFullName(object[1].toString());
				user.setEmailId(object[2].toString());
				user.setDistrictCode(object[3].toString());
				user.setTalukaCode(object[4].toString());
				user.setTalukaName(object[5].toString());
				user.setMarathiName(object[6].toString());
				user.setDesg(object[7].toString());
				user.setPassword(object[8].toString());
				return user;
			}).collect(Collectors.toList()).get(0);
		}
		return UserDetailsImpl.build(users);
	}
}*/