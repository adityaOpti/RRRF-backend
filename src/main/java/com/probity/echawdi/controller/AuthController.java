/**
 * 
 */
package com.probity.echawdi.controller;

import java.io.BufferedInputStream;
import com.probity.echawdi.dao.Login7BResponse;
import com.probity.echawdi.entity.UserRegistration7B;
import com.probity.echawdi.serviceImpl.UserRegistrationService7B;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.config.WithDatabase;
import com.probity.echawdi.dao.ChallanHeadsDao;
import com.probity.echawdi.dao.Form1AbstractDyslrDao;
import com.probity.echawdi.dao.RevenueYearDao;
import com.probity.echawdi.dao.UsersDao;
import com.probity.echawdi.dao.VillageDao;
import com.probity.echawdi.dao.VillageResponseLoginDTO;
import com.probity.echawdi.entity.DashBoardStatic;
import com.probity.echawdi.entity.DashboardSupervisoryDtl;
import com.probity.echawdi.entity.District;
import com.probity.echawdi.entity.Division;
import com.probity.echawdi.entity.DivisionDistrictTalukaCount;
import com.probity.echawdi.entity.Form1;
import com.probity.echawdi.entity.Form14;
import com.probity.echawdi.entity.Form15;
import com.probity.echawdi.entity.Form19;
import com.probity.echawdi.entity.Form1A;
import com.probity.echawdi.entity.Form1B;
import com.probity.echawdi.entity.Form1C;
import com.probity.echawdi.entity.Form1D;
import com.probity.echawdi.entity.Form1E;
import com.probity.echawdi.entity.Form2;
import com.probity.echawdi.entity.Form3;
import com.probity.echawdi.entity.Form6B;
import com.probity.echawdi.entity.Form6D;
import com.probity.echawdi.entity.Form7A;
import com.probity.echawdi.entity.Form7B;
import com.probity.echawdi.entity.LGDMapper;
import com.probity.echawdi.entity.LgdMapperMis;
import com.probity.echawdi.entity.MVillageMapper;
import com.probity.echawdi.entity.Nirank;
import com.probity.echawdi.entity.Taluka;
import com.probity.echawdi.entity.Token;
import com.probity.echawdi.entity.Users;
import com.probity.echawdi.exception.CustomAccessDeniedHandler;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.DummyResponse;
import com.probity.echawdi.payload.DummyResponse3;
import com.probity.echawdi.payload.JwtResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.DashboardStaticRepository;
import com.probity.echawdi.repository.DashboardSupervisoryDtlRepository;
import com.probity.echawdi.repository.DistrictRepository;
import com.probity.echawdi.repository.DivisionDemoRepository;
import com.probity.echawdi.repository.DivisionDistrictTalukaCountRepository;
import com.probity.echawdi.repository.DivisionRepository;
import com.probity.echawdi.repository.Form14Repository;
import com.probity.echawdi.repository.Form15Repository;
import com.probity.echawdi.repository.Form19Repository;
import com.probity.echawdi.repository.Form1ARepository;
import com.probity.echawdi.repository.Form1BRepository;
import com.probity.echawdi.repository.Form1CRepository;
import com.probity.echawdi.repository.Form1DRepository;
import com.probity.echawdi.repository.Form1ERepository;
import com.probity.echawdi.repository.Form1Repository;
import com.probity.echawdi.repository.Form2Repository;
import com.probity.echawdi.repository.Form3Repository;
import com.probity.echawdi.repository.Form6BRepository;
import com.probity.echawdi.repository.Form6DRepository;
import com.probity.echawdi.repository.Form7ARepository;
import com.probity.echawdi.repository.Form7BRepository;
import com.probity.echawdi.repository.LgdMapperRepository;
import com.probity.echawdi.repository.MVillageMapperRepository;
import com.probity.echawdi.repository.NirankRepository;
import com.probity.echawdi.repository.RevenueYearRepository;
import com.probity.echawdi.repository.TokenRepsitory;
import com.probity.echawdi.repository.VillageFormMasterRepository;
import com.probity.echawdi.repository.VillageRepository;
import com.probity.echawdi.service.Form1DyslrService;
import com.probity.echawdi.service.LandRevenueService;
import com.probity.echawdi.serviceImpl.UserDetailsImpl;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.CommonConstants.FileName;
import com.probity.echawdi.utils.DataSourceKeeper;
import com.probity.echawdi.utils.DataSourceType;
import com.probity.echawdi.utils.DecryptPassword;
import com.probity.echawdi.utils.JwtUtils;
import com.probity.echawdi.utils.Scheduler;
import com.probity.echawdi.utils.StaticPropertyFetcher;




@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRegistrationService7B userRegistrationService7B;

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private VillageRepository villageRepository;
	@Autowired
	private RevenueYearRepository revenueYearRepository;
	@Autowired
	private DivisionDistrictTalukaCountRepository divisionDistrictTalukaCountRepository; 
	@Autowired
	private MVillageMapperRepository mVillageMapperRepository;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	@Autowired
	private Form1Repository form1Repository;
	@Autowired
	private Form1ARepository form1ARepository;
	@Autowired
	private Form1BRepository form1BRepository;
	@Autowired
	private Form1CRepository form1CRepository;
	@Autowired
	private Form1DRepository form1DRepository;
	@Autowired
	private Form1ERepository form1ERepository;
	@Autowired
	private Form2Repository form2Repository;
	@Autowired
	private Form3Repository form3Repository;
	@Autowired
	private Form6BRepository form6BRepository;
	@Autowired
	private Form6DRepository form6DRepository;
	@Autowired
	private Form7ARepository form7ARepository;
	@Autowired
	private Form7BRepository form7BRepository;
	@Autowired
	private Form14Repository form14Repository;
	@Autowired
	private Form19Repository form19Repository;
	@Autowired
	private Form15Repository form15Repository;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private DivisionRepository divisionRepository;
	@Autowired
	private DivisionDemoRepository divisionDemoRepository;
	@Autowired
	private Scheduler scheduler;
	@Autowired
	private LgdMapperRepository lgdMapperRepository;
	@Autowired
	private VillageFormMasterRepository villageFormMasterRepository;
	@Autowired
	private NirankRepository nirankRepository;
	@Autowired
	private DistrictRepository districtRepository;
	@Autowired
	private DashboardSupervisoryDtlRepository dashboardSupervisoryDtlRepository;
	
	@Autowired
	private DashboardStaticRepository dashboardStatic;
	@Autowired
	private Form1DyslrService form1DyslrService; 
	@Autowired
	private TokenRepsitory tokenRepsitory;
	
	//for collector login new 15 Oct 2026
	@Autowired
	private LandRevenueService landRevenueService;
	
	//for collector login
	//for collector login
		 @Value("${mapper.db.name}")
		 private String mapperDbName;

		 @Value("${mapper.db.host}")
		 private String mapperHost;

		 @Value("${mapper.db.user}")
		 private String mapperUser;

		 @Value("${mapper.db.password}")
		 private String mapperPassword;
	
	
	
	@Autowired
	private CustomAccessDeniedHandler customAccessDeniedHandler;
	
//	@Autowired
//	private VillageFormMasterRepository villageFormMasterRepository;
	
//	@Autowired
//	private DivisionDistrictTalukaCountRepository divisionDistrictTalukaCountRepository;
	
	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();


	
	//Online payment module updates
	
	@lombok.Data
	public static class GrassRequest {
		private String grnNo;
	}

	@PostMapping("/viewChallan")
	public void viewChallan(@RequestBody GrassRequest grassRequest, HttpServletRequest request,
		HttpServletResponse response, Locale locale) throws IOException {
		response.sendRedirect("https://scdlrpg.mahabhumi.gov.in/backend/treasuryPortal/gras/viewChallan?GRN=" + grassRequest.grnNo);
		//response.sendRedirect("http://localhost:8088/treasuryPortal/gras/viewChallan?GRN=" + grassRequest.grnNo);
		//response.sendRedirect("http://115.124.110.38:443/backend/treasuryPortal/gras/viewChallan?GRN=" + grassRequest.grnNo);
	}

	@GetMapping("/viewChallan")
	public void viewChallanG(@RequestParam String grnNo, HttpServletRequest request,
			HttpServletResponse response, Locale locale) throws IOException {
		response.sendRedirect("https://scdlrpg.mahabhumi.gov.in/backend/treasuryPortal/gras/viewChallan?GRN=" + grnNo);
	//	response.sendRedirect("http://localhost:8088/treasuryPortal/gras/viewChallan?GRN=" + grnNo);
	//	response.sendRedirect("http://115.124.110.38:443/backend/treasuryPortal/gras/viewChallan?GRN=" + grnNo);
	}
	
	//Online payment module updates end
	
	

	@Transactional
	@PostMapping("/authenticateUserByUsernameAndPassword")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody UsersDao usersDao) {
		
		
		if ("7B".equalsIgnoreCase(usersDao.getType())) {

		    UserRegistration7B user =
		            userRegistrationService7B.authenticate(
		                    usersDao.getUsername(),
		                    usersDao.getPassword()
		            );

		    // Generate 7B JWT
		    String accessToken =
		            jwtUtils.generate7BJwtToken(user.getMobileNumber());

		    String refreshToken = jwtUtils.generateRefreshToken();

		    // Store the token
		    jwtUtils.deleteByUserName(user.getMobileNumber());
		    jwtUtils.storeToken(accessToken, refreshToken);

		    // Prepare response
		    Login7BResponse response = new Login7BResponse();

		  	response.setUser(user);

		    response.setJwtToken(accessToken);
//		    response.setTokenType("Bearer");
//		    response.setIssuedAt(jwtUtils.getGenrateTime(accessToken));
//		    response.setExpiryTime(jwtUtils.getExpiryTime(accessToken));

		    return ResponseEntity.ok(response);
		}

		

		String decryptPassword = DecryptPassword.decrypt(usersDao.getPassword(),CommonConstants.secretKeyLogin);
		
		
		 if( (usersDao.getServarthId().equals("AkrushakLogin") &&
		 decryptPassword.equals("admin@123")) ||
		 (usersDao.getServarthId().equals("form17Isuue") &&
		 decryptPassword.equals("admin@123")) ||
		 (usersDao.getServarthId().equals("REVAYRF9301") &&
		 decryptPassword.equals("admin@123"))  ||
		 (usersDao.getServarthId().equals("nanduraForm15") &&
		 decryptPassword.equals("admin@123"))  ||
		 (usersDao.getServarthId().equals("REVBSJM7901") &&
		 decryptPassword.equals("admin@123")) ||
		 (usersDao.getServarthId().equals("Issuedumala2") &&
		 decryptPassword.equals("admin@123")) ||
		 (usersDao.getServarthId().equals("REVSSDF9602") &&
		 decryptPassword.equals("admin@123")) ||
		 (usersDao.getServarthId().equals("Anandi") &&
		 decryptPassword.equals("anandi@123")) ||
		 (usersDao.getServarthId().equals("inspection") &&
		 decryptPassword.equals("inspection@123")) ||
		 (usersDao.getServarthId().equals("borgaon") &&
		 decryptPassword.equals("borgaon@123")) ||
		 (usersDao.getServarthId().equals("REVNMDF9001") &&
		 decryptPassword.equals("admin@123")) ||
		 (usersDao.getServarthId().equals("belval") &&
		 decryptPassword.equals("belval@123")) ||
		 (usersDao.getServarthId().equals("REVSSNF8501") &&
		 decryptPassword.equals("admin@123")) ||
		 (usersDao.getServarthId().equals("REVPMKM9001") &&
		 decryptPassword.equals("admin@123")) ||
		 (usersDao.getServarthId().equals("REVKSJF0201") &&
		 decryptPassword.equals("admin@123")) ||
		 (usersDao.getServarthId().equals("DHULE") &&
		 decryptPassword.equals("dhule@123")) ||
		 (usersDao.getServarthId().equals("REVSDSM9601") &&  
		 decryptPassword.equals("admin@123")) ||   
		 (usersDao.getServarthId().equals("savalde") &&
		 decryptPassword.equals("savalde@123"))
		 ||(usersDao.getServarthId().equals("REVSPDM9102") &&
		 decryptPassword.equals("admin@123"))||
		 (usersDao.getServarthId().equals("REVMSDM8601") &&
		 decryptPassword.equals("admin@123")) ||
		 (usersDao.getServarthId().equals("echawadi") &&
		 decryptPassword.equals("echawadi")) ||
		 (usersDao.getServarthId().equals("dyslr") &&
		 decryptPassword.equals("Admin@123")) ||
		 (usersDao.getServarthId().equals("DyslrHAV") &&
		 decryptPassword.equals("admin@123")) ||
		 (usersDao.getServarthId().equals("MSCASPM9401") &&
		 decryptPassword.equals("admin@123")) ||
		 (usersDao.getServarthId().equals("sajan") &&
		 decryptPassword.equals("Admin@123"))
		 ||
		 (usersDao.getServarthId().equals("Collector") &&
		 decryptPassword.equals("Collector"))
		 ||
		 (usersDao.getServarthId().equals("TAH_NAG_KAT") &&
		 decryptPassword.equals("Admin@123"))
		 ||
		 (usersDao.getServarthId().equals("TAH_NAG_RUL") &&
		 decryptPassword.equals("Admin@123"))
		 ||
		 (usersDao.getServarthId().equals("REVSBAM8601") && //circle officer
				 decryptPassword.equals("Cir@3105"))
		 ||
		 (usersDao.getServarthId().equals("MEVVANM8701") &&  //tahasildar
				 decryptPassword.equals("Vishal@87"))
		 ||
		 (usersDao.getServarthId().equals("REVSBGM9303") &&
				 decryptPassword.equals("Admin@123"))
		 ||
		 (usersDao.getServarthId().equals("REVISCF7801") &&
					 decryptPassword.equals("Admin@123"))
			 )
		 	 
		 {

		Authentication authentication = null;
		try {
			if (usersDao.getServarthId().equals("dyslr")) {
				authentication = authenticationManager
//							.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
						.authenticate(new UsernamePasswordAuthenticationToken("DYSLR_RAI_TAL", "Admin@123"));
			} else if (usersDao.getServarthId().equals("REVAYRF9301")) {
				authentication = authenticationManager
//							.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
						.authenticate(new UsernamePasswordAuthenticationToken("REVAYRF9301", "Jeevi@2023"));
			}else if (usersDao.getServarthId().equals("form17Isuue")) {
				authentication = authenticationManager
//						.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
					.authenticate(new UsernamePasswordAuthenticationToken("REVJGKM8301", "Jitendra@83"));
		} else if (usersDao.getServarthId().equals("AkrushakLogin")) {
			authentication = authenticationManager
//					.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
				.authenticate(new UsernamePasswordAuthenticationToken("REVRSTM7901", "Talc1#32"));
			} 
			else if (usersDao.getServarthId().equals("REVSSNF8501")) {
				authentication = authenticationManager
//						.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
					.authenticate(new UsernamePasswordAuthenticationToken("REVSSNF8501", "Tal@8501"));
		} else if (usersDao.getServarthId().equals("REVSSDF9602")) {
				authentication = authenticationManager
//							.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
						.authenticate(new UsernamePasswordAuthenticationToken("REVSSDF9602", "Divekar@9696"));
			} else if (usersDao.getServarthId().equals("nanduraForm15")) {
				authentication = authenticationManager
//							.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
						.authenticate(new UsernamePasswordAuthenticationToken("REVSRAM7601", "Army@2023"));
			} else if (usersDao.getServarthId().equals("REVSDSM9601")) {
				authentication = authenticationManager
//							.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
						.authenticate(new UsernamePasswordAuthenticationToken("REVSDSM9601", "Sandeep@123"));
			} else if (usersDao.getServarthId().equals("REVBSJM7901")) {
				authentication = authenticationManager
//							.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
						.authenticate(new UsernamePasswordAuthenticationToken("REVBSJM7901", "Rushi#95"));
			} else if (usersDao.getServarthId().equals("Anandi")) {
				authentication = authenticationManager
//							.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
						.authenticate(new UsernamePasswordAuthenticationToken("REVNASF8701", "Nayna@786"));
			} else if (usersDao.getServarthId().equals("REVNMDF9001")) {
				authentication = authenticationManager
//							.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
						.authenticate(new UsernamePasswordAuthenticationToken("REVNMDF9001", "Nilam@90"));
			} else if (usersDao.getServarthId().equals("borgaon")) {
				authentication = authenticationManager
//							.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
						.authenticate(new UsernamePasswordAuthenticationToken("REVSSMM9202", "Kadale@123"));
			} else if (usersDao.getServarthId().equals("inspection")) {
				authentication = authenticationManager
//							.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
						.authenticate(new UsernamePasswordAuthenticationToken("REVVMPF8201", "Vaishali@123"));
			} else if (usersDao.getServarthId().equals("belval")) {
				authentication = authenticationManager
//							.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
						.authenticate(new UsernamePasswordAuthenticationToken("REVSGWM8901", "Harshu@89"));
			} else if (usersDao.getServarthId().equals("DHULE")) {
				authentication = authenticationManager
//							.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
						.authenticate(new UsernamePasswordAuthenticationToken("REVMNPM7201", "Pmn@7201"));
			} else if (usersDao.getServarthId().equals("REVPMKM9001")) {
				authentication = authenticationManager
//							.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
						.authenticate(new UsernamePasswordAuthenticationToken("REVPMKM9001", "Hridhaan@99551"));
			} else if (usersDao.getServarthId().equals("REVKSJF0201")) {
				authentication = authenticationManager
//							.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
						.authenticate(new UsernamePasswordAuthenticationToken("REVKSJF0201", "Talc1#"));
			} else if (usersDao.getServarthId().equals("Issuedumala2")) {
				authentication = authenticationManager
//							.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
						.authenticate(new UsernamePasswordAuthenticationToken("REVSDSM9601", "Sandeep@1"));
			} else if (usersDao.getServarthId().equals("savalde")) {
				authentication = authenticationManager
//							.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
						.authenticate(new UsernamePasswordAuthenticationToken("REVNSPM7602", "Nsp@7602"));
			} else if (usersDao.getServarthId().equals("sajan")) {
				authentication = authenticationManager
//							.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
						.authenticate(new UsernamePasswordAuthenticationToken("REVSMMF9301", "Smm$123"));
			} else if (usersDao.getServarthId().equals("REVSPDM9102")) {
				authentication = authenticationManager
//							.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
						.authenticate(new UsernamePasswordAuthenticationToken("REVSPDM9102", "Talspd@9102"));
			} else if (usersDao.getServarthId().equals("REVMSDM8601")) {
				authentication = authenticationManager
//							.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
						.authenticate(new UsernamePasswordAuthenticationToken("REVMSDM8601", "Shiv@1234"));
			} else if (usersDao.getServarthId().equals("DyslrHAV")) {
				authentication = authenticationManager
//				.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
						.authenticate(new UsernamePasswordAuthenticationToken("DYSLR_PUN_HAV", "Admin@123"));
			} else if (usersDao.getServarthId().equals("MSCASPM9401")) {
				authentication = authenticationManager
//				.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
						.authenticate(new UsernamePasswordAuthenticationToken("MSCASPM9401", "Abhi@2901"));
			} else if (usersDao.getServarthId().equals("echawadi")) {
				authentication = authenticationManager
//							.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
						.authenticate(new UsernamePasswordAuthenticationToken("REVSBSM8603", "Shelar@86"));
			}
			else if (usersDao.getServarthId().equals("TAH_NAG_KAT")) {
				authentication = authenticationManager
//							.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
						.authenticate(new UsernamePasswordAuthenticationToken(" TAH_NAG_KAT", "Admin@123"));
			}else if (usersDao.getServarthId().equals("TAH_NAG_RUL")) {
				authentication = authenticationManager
//						.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
					.authenticate(new UsernamePasswordAuthenticationToken("TAH_NAG_RUL", "Admin@123"));
		    }else if (usersDao.getServarthId().equals("REVSBAM8601")) {
				authentication = authenticationManager
//						.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
					.authenticate(new UsernamePasswordAuthenticationToken("REVSBAM8601", "Cir@3105"));
		    } else if (usersDao.getServarthId().equals("MEVVANM8701")) {
				authentication = authenticationManager
//						.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
					.authenticate(new UsernamePasswordAuthenticationToken("MEVVANM8701", "Vishal@87"));
		    }else if (usersDao.getServarthId().equals("REVISCF7801")) {
		    	System.out.println("UserName in AuthController :"+usersDao.getServarthId());
				System.out.println("password in AuthController :"+decryptPassword);
				authentication = authenticationManager
//						.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
					.authenticate(new UsernamePasswordAuthenticationToken("REVISCF7801", "Admin@123"));
		    }else if (usersDao.getServarthId().equals("REVSBGM9303")) {
		    	System.out.println("UserName in AuthController :"+usersDao.getServarthId());
				System.out.println("password in AuthController :"+decryptPassword);
				authentication = authenticationManager
//						.authenticate(new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
					.authenticate(new UsernamePasswordAuthenticationToken("REVSBGM9303", "Admin@123"));
		    }else {
				
				System.out.println("UserName in AuthController :"+usersDao.getServarthId());
				System.out.println("password in AuthController :"+decryptPassword);
				authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(usersDao.getServarthId(), decryptPassword));
				
			}

		} catch (Exception e) {
			throw new BadCredentialsException(this.messages
					.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}

		// get data from authentication object of spring security and assign to the
		// holder of security context
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// set data to UserDetailsImpl
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		System.out.println("userDetails:"+userDetails);

		// delete all tokens if exists
		jwtUtils.deleteByUserName(userDetails.getUsername());    //(JWT Token Handling)

		// generate token -jwtUtils
		String jwt = jwtUtils.generateJwtToken(userDetails);
		
		
		String refreshToken = jwtUtils.generateRefreshToken();
		
		
		

		jwtUtils.storeToken(jwt,  refreshToken);
		
		
		 // CREATE HTTPONLY COOKIE
//	    ResponseCookie cookie =
//	            ResponseCookie.from(
//	                            "refreshToken",
//	                            refreshToken)
//	                    .httpOnly(false)
//	                    // FALSE FOR LOCALHOST HTTP
//	                    // TRUE FOR HTTPS PRODUCTION
//	                    .secure(false)
//	                    .path("/")
//	                    //.maxAge(Duration.ofMinutes(2))
//	                   .maxAge(Duration.ofHours(8))
//	                  //  .sameSite("Strict")
//	                    .sameSite("Lax")
//	                    .build();
		
		//jwtUtils.storeToken(jwt);
		
		
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		
		



		String districtName = null;

		HashMap<String, String> infoMap = null;

		List<VillageDao> villageDaoLst = null;
		List<VillageDao> villageDaoLstNew = new ArrayList<>();
		List<Nirank> niranks = new ArrayList<Nirank>();
		
		//for collector login
		List<Taluka> talukaLst = null;
		
		
		List<VillageResponseLoginDTO> villageDaoInspectionLst = new ArrayList<>();

		List<ChallanHeadsDao> challanHeads = new ArrayList<ChallanHeadsDao>();

		District district = new District();
		if (userDetails.getDistrictCode() != null) {
			System.out.println("Here in if condition ---- : "+userDetails.getDistrictCode());
			district = districtRepository.findByDistrictCode(userDetails.getDistrictCode());
		}

	/*	if (!userDetails.getUsername().equals("Collector")) {
			infoMap = jwtUtils.getDbs(userDetails.getDistrictCode(), userDetails.getTalukaCode());
			// System.out.println("dbs:"+infoMap);

			districtName = jwtUtils.getDistrictName(infoMap.get("mhrHost1"), infoMap.get("mhrDbName1"),
					infoMap.get("mhrSchemaName1"), userDetails.getDistrictCode());
			List<Object[]> villageLst = null;
			if (!userDetails.getUsername().startsWith("DYSLR_")) {
				villageLst = villageRepository.getVillageListByUserName(infoMap.get("mhrHost1"),
						infoMap.get("mhrDbName1"), infoMap.get("mhrSchemaName1"), CommonConstants.mhrorUser,
						CommonConstants.mhrorPassword, userDetails.getUsername());

				villageDaoLst = villageLst.stream().map(object -> {
					VillageDao villageDao = new VillageDao();
					villageDao.setCCode(getProperCCode(object[0].toString()).trim());
					villageDao.setUserName(object[1].toString().trim());
					villageDao.setUserType(object[2].toString());
					villageDao.setVillageCode(object[3].toString().trim());
					villageDao.setVillageName(object[4].toString());
					villageDao.setLgdCode(object[5].toString());
					villageDao.setSajjaCode(object[6].toString().trim());
					villageDao.setSajjaName(object[7].toString());
					villageDao.setCircleCode(object[8].toString().trim());
					villageDao.setCircleName(object[9].toString());
					return villageDao;
				}).collect(Collectors.toList());
			} else {
				villageLst = villageRepository.getVillageListByUserName1(infoMap.get("mhrHost1"),
						infoMap.get("mhrDbName1"), infoMap.get("mhrSchemaName1"), CommonConstants.mhrorUser,
						CommonConstants.mhrorPassword, userDetails.getTalukaCode(), userDetails.getDistrictCode()

				);

				villageDaoLst = villageLst.stream().map(object -> {
					VillageDao villageDao1 = new VillageDao();
					villageDao1.setCCode(getProperCCode(object[0].toString()).trim());
					villageDao1.setVillageName(object[1].toString().trim());
					return villageDao1;
				}).collect(Collectors.toList());

			}

//			villageDaoLst=villageDaoLst.stream().map().collect(Collectors.toList());

			for (int i = 0; i < villageDaoLst.size(); i++) {
				VillageDao village = new VillageDao();
				village = villageDaoLst.get(i);
				if (i == 0) {
					village.setNiranks(villageFormMasterRepository.findAll());
				}
				villageDaoLstNew.add(village);
			}

			List<String> cCodes = villageDaoLst.stream().map(r -> {
				return r.getcCode();
			}).collect(Collectors.toList());
			List<LGDMapper> lgdMappers = lgdMapperRepository.findBycCodeInAndIsLoggedInIsNull(cCodes);
			lgdMappers.stream().map(l -> {
				l.setIsLoggedIn('Y');
				l.setLogInDtTm(LocalDateTime.now());
				lgdMapperRepository.save(l);
				return l;
			}).collect(Collectors.toList());
			niranks = nirankRepository.findBycCodeIn(cCodes);
			challanHeads = districtRepository.getByDivisionCode(district.getDivisionCode()).stream().map(obj -> {
				ChallanHeadsDao dao = new ChallanHeadsDao();
				dao.setDivisionCode(obj[0].toString());
				dao.setHeadNo(obj[1].toString());
				dao.setSubHeadNo(obj[2].toString());
				dao.setHeadName(obj[3].toString());
				dao.setHeadNameMarathi(obj[4].toString());
				return dao;
			}).collect(Collectors.toList());
		}*/
		
		
		
		if (!userDetails.getUsername().equals("Collector")) {
			
			
			infoMap = jwtUtils.getDbs(userDetails.getDistrictCode(), userDetails.getTalukaCode());
			// System.out.println("dbs:"+infoMap);

			
			if(userDetails.getDesg().startsWith("DDE")) {
				//only for DDE login
				districtName = jwtUtils.getDistrictNameForDDE(userDetails.getDistrictCode());
				System.out.println("DistrictName---------------------------: "+districtName);
			}else{
				
				//Continue with previous logic
				districtName = jwtUtils.getDistrictName(infoMap.get("mhrHost1"), infoMap.get("mhrDbName1"),
						infoMap.get("mhrSchemaName1"), userDetails.getDistrictCode());
			}
			
			
			
			List<Object[]> villageLst = null;
			
			
			//new code for the three roles start logice
			//if (userDetails.getUsername().startsWith("TAH_")) {
				if (true) {

			    System.out.println("Skipping new role logic for TAH user: "
			            + userDetails.getUsername());
			    
			     if(userDetails.getUsername().startsWith("TAH_")) {
					System.out.println("I am here in  tahsildar login---------------------------"+userDetails.getUsername());
					
					villageLst = villageRepository.getVillageListByLoginNameForInspection(userDetails.getUsername());
					
					
					for (Object[] object : villageLst) {
					    System.out.println("printing a villageLst  : "+Arrays.toString(object));
					}
					
					villageDaoInspectionLst = villageLst.stream().map(object -> {
						VillageResponseLoginDTO villageDao1 = new VillageResponseLoginDTO();
						villageDao1.setCCode(getProperCCode(object[0].toString()).trim());
						villageDao1.setDistMarathiName(object[1].toString().trim());
						villageDao1.setTalukaMarathiName(object[2].toString().trim());
						villageDao1.setTalukaCode(object[3].toString().trim());
						villageDao1.setDistrictCode(object[4].toString().trim());
						villageDao1.setLgdCode(object[5].toString().trim());
						villageDao1.setVillageName(object[6].toString().trim());
						
						return villageDao1;
					}).collect(Collectors.toList());

					
				

			} else if (userDetails.getDesg()
			            .startsWith(CommonConstants.COLLECTOR_PREFIX)) {
			    	
			    	
			        // COLLECTOR LOGIC
			       

			        System.out.println(
			                "I am here in NEW COLLECTOR login --------------------------- "
			                        + userDetails.getUsername()
			        );

			       
			        talukaLst =
			                landRevenueService.getDropDownsTaluka(userDetails.getDistrictCode());
			        
			        
			        
			        
			   

			    } else if (userDetails.getDesg()
			            .startsWith(CommonConstants.MANDAL_ADHIKARI_PREFIX)
			            ) {

			        System.out.println(
			                "I am here in NEW MANDAL ADHIKARI login --------------------------- "
			                        + userDetails.getUsername()
			        );

			        // ========================================================
			        // MANDAL ADHIKARI LOGIC
			        
			        List<String> sajjaCodes = villageRepository.getSajjaCodesByUsername(
			                infoMap.get("mhrHost1"),
			                infoMap.get("mhrDbName1"),
			                infoMap.get("mhrSchemaName1"),
			                CommonConstants.mhrorUser,
			                CommonConstants.mhrorPassword,
			                userDetails.getUsername()
			        );

			        userDetails.setSajjaCodes(sajjaCodes);
			        
			        villageLst = villageRepository.getVillageListForMandalAdhikari(
			                infoMap.get("mhrHost1"),
			                infoMap.get("mhrDbName1"),
			                infoMap.get("mhrSchemaName1"),
			                CommonConstants.mhrorUser,
			                CommonConstants.mhrorPassword,
			                userDetails.getUsername(),
			                userDetails.getDistrictCode(),
			                userDetails.getTalukaCode()
			        );
			        
			        System.out.println("Village size ---- : "+villageLst.size());
			        
			        villageLst.forEach(r -> System.out.println(Arrays.toString(r)));
			        
		            villageDaoLst = villageLst.stream().map(object -> {

		                VillageDao villageDao = new VillageDao();

		                villageDao.setCCode(
		                        getProperCCode(object[0].toString()).trim()
		                );

		                villageDao.setUserName(
		                        object[1].toString().trim()
		                );

		                villageDao.setVillageCode(
		                        object[2].toString().trim()
		                );

		                villageDao.setVillageName(
		                        object[3].toString()
		                );

		                villageDao.setLgdCode(
		                        object[4] != null ? object[4].toString() : null
		                );

		                villageDao.setSajjaCode(
		                        object[5].toString().trim()
		                );

		                villageDao.setSajjaName(
		                        object[6].toString()
		                );

		                villageDao.setCircleCode(
		                        object[7].toString().trim()
		                );

		                villageDao.setCircleName(
		                        object[8].toString()
		                );

		                villageDao.setTalukaCode(
		                        object[9].toString().trim()
		                );
		                
		                /*
		                villageDao.setCCode(
		                        getProperCCode(object[0].toString()).trim()
		                );

		                villageDao.setUserName(
		                        object[1].toString().trim()
		                );

		                villageDao.setUserType(
		                        object[2].toString()
		                );

		                villageDao.setVillageCode(
		                        object[2].toString().trim()
		                );

		                villageDao.setVillageName(
		                        object[3].toString()
		                );

		                villageDao.setLgdCode(
		                        object[5].toString()
		                );

		                villageDao.setSajjaCode(
		                        object[6].toString().trim()
		                );

		                villageDao.setSajjaName(
		                        object[7].toString()
		                );

		                villageDao.setCircleCode(
		                        object[8].toString().trim()
		                );

		                villageDao.setCircleName(
		                        object[9].toString()
		                );*/

		                return villageDao;

		            }).collect(Collectors.toList());



			   
			    } else if (userDetails.getDesg()
			            .startsWith(CommonConstants.TAHSILDAR_PREFIX)) {
			    	
			    	
			        // NEW TAHSILDAR LOGIC
			      

			        System.out.println(
			                "I am here in NEW TAHSILDAR login --------------------------- "
			                        + userDetails.getUsername()
			        );
			        
			        
			    
			        
			       villageLst = villageRepository.getVillageListByTalukaAndDistrict(
			                infoMap.get("mhrHost1"),
			                infoMap.get("mhrDbName1"),
			                infoMap.get("mhrSchemaName1"),
			                CommonConstants.mhrorUser,
			                CommonConstants.mhrorPassword,
			                userDetails.getUsername(),
			                userDetails.getTalukaCode(),
			                userDetails.getDistrictCode()
			        );
			        
			        
			        System.out.println("Village size ---- : "+villageLst.size());
			        
			        villageLst.forEach(r -> System.out.println(Arrays.toString(r)));
			        
		            villageDaoLst = villageLst.stream().map(object -> {

		                VillageDao villageDao = new VillageDao();
		            

		                villageDao.setCCode(
		                        getProperCCode(object[0].toString()).trim()
		                );

		                villageDao.setDistCode(
		                        object[1].toString().trim()
		                );

		                villageDao.setTalukaCode(
		                        object[2].toString()
		                );

		                villageDao.setVillageCode(
		                        object[3].toString().trim()
		                );

		                villageDao.setVillageName(
		                        object[4].toString()
		                );


		                villageDao.setSajjaCode(
		                        object[5].toString().trim()
		                );



		                return villageDao;

		            }).collect(Collectors.toList());

			       

			    // ============================================================
			    // EXISTING LOGIC
			    //
			    // Everything below remains your existing code.
			    // ============================================================

			    } else {		
			    	
			    	if (!userDetails.getUsername().startsWith("DYSLR_") &&  !userDetails.getUsername().startsWith("TAH_") ) {
					
					System.out.println("I am here into talathi login--------------: ");
					
					villageLst = villageRepository.getVillageListByUserName(infoMap.get("mhrHost1"),
							infoMap.get("mhrDbName1"), infoMap.get("mhrSchemaName1"), CommonConstants.mhrorUser,
							CommonConstants.mhrorPassword, userDetails.getUsername());

					villageDaoLst = villageLst.stream().map(object -> {
						VillageDao villageDao = new VillageDao();
						villageDao.setCCode(getProperCCode(object[0].toString()).trim());
						villageDao.setUserName(object[1].toString().trim());
						villageDao.setUserType(object[2].toString());
						villageDao.setVillageCode(object[3].toString().trim());
						villageDao.setVillageName(object[4].toString());
						villageDao.setLgdCode(object[5].toString());
						villageDao.setSajjaCode(object[6].toString().trim());
						villageDao.setSajjaName(object[7].toString());
						villageDao.setCircleCode(object[8].toString().trim());
						villageDao.setCircleName(object[9].toString());
						return villageDao;
					}).collect(Collectors.toList());
				} else {
						System.out.println("I am here in dyslr login---------------------------");
						
						villageLst = villageRepository.getVillageListByUserName1(infoMap.get("mhrHost1"),
								infoMap.get("mhrDbName1"), infoMap.get("mhrSchemaName1"), CommonConstants.mhrorUser,
								CommonConstants.mhrorPassword, userDetails.getTalukaCode(), userDetails.getDistrictCode()

						);

						villageDaoLst = villageLst.stream().map(object -> {
							VillageDao villageDao1 = new VillageDao();
							villageDao1.setCCode(getProperCCode(object[0].toString()).trim());
							villageDao1.setVillageName(object[1].toString().trim());
							return villageDao1;
						}).collect(Collectors.toList());

					}
					
					
				}
	}
			
			
			


//			villageDaoLst=villageDaoLst.stream().map().collect(Collectors.toList());
			
			
			
			
			//if(!userDetails.getUsername().startsWith("TAH_")) {
			 
			 if (!userDetails.getUsername().startsWith("TAH_")
					 && villageDaoLst != null ) {
				
				
				System.out.println("in that size error ---- :");
				
				for (int i = 0; i < villageDaoLst.size(); i++) {
					VillageDao village = new VillageDao();
					village = villageDaoLst.get(i);
					if (i == 0) {
						village.setNiranks(villageFormMasterRepository.findAll());
					}
					villageDaoLstNew.add(village);
				}
				
				
				
			}
//				
//				System.out.println("userDetails is null: " + (userDetails == null));
//				System.out.println("username: " +
//				        (userDetails != null ? userDetails.getUsername() : "userDetails is null"));
//				System.out.println("villageDaoLst is null: " + (villageDaoLst == null));
//				System.out.println("villageDaoLstNew is null: " + (villageDaoLstNew == null));
//
//				if (userDetails != null
//				        && userDetails.getUsername() != null
//				        && !userDetails.getUsername().startsWith("TAH_")) {
//
//				    System.out.println("in that size error ---- :");
//
//				    for (int i = 0; i < villageDaoLst.size(); i++) {
//				        VillageDao village = villageDaoLst.get(i);
//
//				        if (i == 0) {
//				            village.setNiranks(villageFormMasterRepository.findAll());
//				        }
//
//				        villageDaoLstNew.add(village);
//				    }
//				}

			
//			
//			if(!userDetails.getUsername().startsWith("TAH_")) {
//							
//				cCodes = villageDaoLst.stream().map(r -> {
//					return r.getcCode();
//				}).collect(Collectors.toList());
//			}else {
//				cCodes = villageDaoInspectionLst.stream().map(r -> {
//						return r.getcCode();
//						}).collect(Collectors.toList());
//			
//			}
			 
			 List<String> cCodes ;
			
			 if (userDetails.getUsername().startsWith("TAH_")) {

				    System.out.println("Skipping new role logic for TAH user: "
				            + userDetails.getUsername());

				    cCodes = villageDaoInspectionLst.stream()
				            .map(r -> r.getcCode())
				            .collect(Collectors.toList());

				} else  if (userDetails.getDesg().startsWith(CommonConstants.COLLECTOR_PREFIX)) {

		        // Collector login: do nothing here
		    	
		    	
		    	cCodes = talukaLst.stream()
		    	        .map(Taluka::getcCode)
		    	        .collect(Collectors.toList());

				
		    } else if (!userDetails.getUsername().startsWith("TAH_")) {

		        cCodes = villageDaoLst.stream()
		                .map(r -> r.getcCode())
		                .collect(Collectors.toList());

		    } 
		    	else {

		        cCodes = villageDaoInspectionLst.stream()
		                .map(r -> r.getcCode())
		                .collect(Collectors.toList());
		    }
			
			
			
			
			List<LGDMapper> lgdMappers = lgdMapperRepository.findBycCodeInAndIsLoggedInIsNull(cCodes);
			lgdMappers.stream().map(l -> {
				l.setIsLoggedIn('Y');
				l.setLogInDtTm(LocalDateTime.now());
				lgdMapperRepository.save(l);
				return l;
			}).collect(Collectors.toList());
			niranks = nirankRepository.findBycCodeIn(cCodes);
			challanHeads = districtRepository.getByDivisionCode(district.getDivisionCode()).stream().map(obj -> {
				ChallanHeadsDao dao = new ChallanHeadsDao();
				dao.setDivisionCode(obj[0].toString());
				dao.setHeadNo(obj[1].toString());
				dao.setSubHeadNo(obj[2].toString());
				dao.setHeadName(obj[3].toString());
				dao.setHeadNameMarathi(obj[4].toString());
				return dao;
				
				
			}).collect(Collectors.toList());
		}

		
		
		
		List<RevenueYearDao> revenueYearDaoLst = revenueYearRepository.findAllByActiveFlag('Y').stream().map(r -> {
			RevenueYearDao revenueYearDao = new RevenueYearDao();
			BeanUtils.copyProperties(r, revenueYearDao);
			return revenueYearDao;
		}).collect(Collectors.toList());

		DatabaseContextHolder.restoreCtx();
		// System.out.println("districtName:"+districtName);

		/*if (!userDetails.getUsername().equals("Collector")) {

			return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername().trim(), userDetails.getFullname(),
					userDetails.getEmailId(), userDetails.getDistrictCode().trim(), districtName,
					userDetails.getTalukaCode().trim(), userDetails.getTalukaName(), userDetails.getMarathiName(),
					userDetails.getDesg(), jwtUtils.getGenrateTime(jwt), jwtUtils.getExpiryTime(jwt), roles,
					infoMap.get("echDbName").trim(), infoMap.get("echSchemaName").trim(),
					infoMap.get("mhrDbName").trim(), infoMap.get("mhrSchemaName").trim(), infoMap.get("echHost").trim(),
					infoMap.get("mhrHost").trim(), villageDaoLstNew, revenueYearDaoLst, niranks, challanHeads));
		} else {
			return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername().trim(), userDetails.getFullname(),
					userDetails.getEmailId(), jwtUtils.getGenrateTime(jwt), jwtUtils.getExpiryTime(jwt),
					userDetails.getMarathiName(), roles, niranks));

		}*/
		
		if (!userDetails.getUsername().equals("Collector")) {

			
			
			 if(userDetails.getUsername().startsWith("TAH_")) {
				
				
				System.out.println("Here into the Tahasildar loginResponse------------------------: ");
				
				System.out.println("Inpseciton village list in response : "+villageDaoInspectionLst);
				
				return ResponseEntity.ok(new JwtResponse(jwt, refreshToken, userDetails.getUsername().trim(), userDetails.getFullname(),
						userDetails.getEmailId(), userDetails.getDistrictCode().trim(), districtName,
						userDetails.getTalukaCode().trim(), userDetails.getTalukaName(), userDetails.getMarathiName(),
						userDetails.getDesg(), jwtUtils.getGenrateTime(jwt), jwtUtils.getExpiryTime(jwt), roles,
						infoMap.get("echDbName").trim(), infoMap.get("echSchemaName").trim(),
						infoMap.get("mhrDbName").trim(), infoMap.get("mhrSchemaName").trim(), infoMap.get("echHost").trim(),
						infoMap.get("mhrHost").trim(), villageDaoInspectionLst, revenueYearDaoLst));
				
				
//				ResponseCookie Acookie =
//				        ResponseCookie.from(
//				                        "refreshToken",
//				                        refreshToken)
//				                .httpOnly(false)
//				                // FALSE FOR LOCALHOST HTTP
//				                // TRUE FOR HTTPS PRODUCTION
//				                .secure(false)
//				                .path("/")
//				               // .maxAge(Duration.ofMinutes(2))
//				                .maxAge(Duration.ofHours(8))
//				              //  .sameSite("Strict")
//				                .sameSite("Lax")
//				                .build();
//				return ResponseEntity.ok()
//				        .header(
//				                HttpHeaders.SET_COOKIE,
//				                cookie.toString())
//				        .body(
//				                new JwtResponse(
//				                        jwt,
//				                        userDetails.getUsername().trim(),
//				                        userDetails.getFullname(),
//				                        userDetails.getEmailId(),
//				                        userDetails.getDistrictCode().trim(),
//				                        districtName,
//				                        userDetails.getTalukaCode().trim(),
//				                        userDetails.getTalukaName(),
//				                        userDetails.getMarathiName(),
//				                        userDetails.getDesg(),
//				                        jwtUtils.getGenrateTime(jwt),
//				                        jwtUtils.getExpiryTime(jwt),
//				                        roles,
//				                        infoMap.get("echDbName").trim(),
//				                        infoMap.get("echSchemaName").trim(),
//				                        infoMap.get("mhrDbName").trim(),
//				                        infoMap.get("mhrSchemaName").trim(),
//				                        infoMap.get("echHost").trim(),
//				                        infoMap.get("mhrHost").trim(),
//				                        villageDaoInspectionLst,
//				                        revenueYearDaoLst
//				                )
//				        );
				
			}else if(userDetails.getDesg()
		            .startsWith(CommonConstants.COLLECTOR_PREFIX)
					){
				
				
				System.out.println("Here into the new DDE loginResponse------------------------: ");
				
				System.out.println("username: " + userDetails.getUsername());
				System.out.println("districtCode: " + userDetails.getDistrictCode());
				System.out.println("talukaCode: " + userDetails.getTalukaCode());
				System.out.println("infoMap: " + infoMap);
				System.out.println("echDbName: " + infoMap.get("echDbName"));
				System.out.println("echSchemaName: " + infoMap.get("echSchemaName"));
				System.out.println("mhrDbName: " + infoMap.get("mhrDbName"));
				System.out.println("mhrSchemaName: " + infoMap.get("mhrSchemaName"));
				System.out.println("echHost: " + infoMap.get("echHost"));
				System.out.println("mhrHost: " + infoMap.get("mhrHost"));
				
				/*return ResponseEntity.ok(new JwtResponse(jwt, refreshToken, userDetails.getUsername().trim(), userDetails.getFullname(),
						userDetails.getEmailId(), userDetails.getDistrictCode().trim(), districtName,
						userDetails.getTalukaCode() == null ? "" : userDetails.getTalukaCode().trim(),
						userDetails.getTalukaName() == null ? "" : userDetails.getTalukaName(), userDetails.getMarathiName(),
						userDetails.getDesg(), jwtUtils.getGenrateTime(jwt), jwtUtils.getExpiryTime(jwt), roles,
						infoMap.get("echDbName").trim(), infoMap.get("echSchemaName").trim(),
						infoMap.get("mhrDbName").trim(), infoMap.get("mhrSchemaName").trim(), infoMap.get("echHost").trim(),
						infoMap.get("mhrHost").trim(), villageDaoLstNew,villageDaoInspectionLst, revenueYearDaoLst, niranks, challanHeads,talukaLst));*/

				
				
				return ResponseEntity.ok(new JwtResponse(jwt, refreshToken, userDetails.getUsername().trim(), userDetails.getFullname(),
						userDetails.getEmailId(), userDetails.getDistrictCode().trim(), districtName,
						userDetails.getTalukaCode() == null ? "" : userDetails.getTalukaCode().trim(),
						userDetails.getTalukaName() == null ? "" : userDetails.getTalukaName(), userDetails.getMarathiName(),
						userDetails.getDesg(), jwtUtils.getGenrateTime(jwt), jwtUtils.getExpiryTime(jwt), roles,
						Objects.toString(infoMap.get("echDbName"), "").trim(),
						Objects.toString(infoMap.get("echSchemaName"), "").trim(),
						Objects.toString(infoMap.get("mhrDbName"), "").trim(),
						Objects.toString(infoMap.get("mhrSchemaName"), "").trim(),
						Objects.toString(infoMap.get("echHost"), "").trim(),
						Objects.toString(infoMap.get("mhrHost"), "").trim(), villageDaoLstNew,villageDaoInspectionLst, revenueYearDaoLst, niranks, challanHeads,talukaLst));


//				ResponseCookie Bcookie =
//				        ResponseCookie.from(
//				                        "refreshToken",
//				                        refreshToken)
//				                .httpOnly(true)
//				                // FALSE FOR LOCALHOST HTTP
//				                // TRUE FOR HTTPS PRODUCTION
//				                .secure(false)
//				                .path("/")
//				                .maxAge(Duration.ofHours(8))
//				                .sameSite("Strict")
//				                .build();
//				return ResponseEntity.ok()
//				        .header(
//				                HttpHeaders.SET_COOKIE,
//				                cookie.toString())
//				        .body(
//				                new JwtResponse(
//				                        jwt,
//				                        userDetails.getUsername().trim(),
//				                        userDetails.getFullname(),
//				                        userDetails.getEmailId(),
//				                        userDetails.getDistrictCode().trim(),
//				                        districtName,
//				                        userDetails.getTalukaCode().trim(),
//				                        userDetails.getTalukaName(),
//				                        userDetails.getMarathiName(),
//				                        userDetails.getDesg(),
//				                        jwtUtils.getGenrateTime(jwt),
//				                        jwtUtils.getExpiryTime(jwt),
//				                        roles,
//				                        infoMap.get("echDbName").trim(),
//				                        infoMap.get("echSchemaName").trim(),
//				                        infoMap.get("mhrDbName").trim(),
//				                        infoMap.get("mhrSchemaName").trim(),
//				                        infoMap.get("echHost").trim(),
//				                        infoMap.get("mhrHost").trim(),
//				                        villageDaoLstNew,
//				                        villageDaoInspectionLst,
//				                        revenueYearDaoLst,
//				                        niranks,
//				                        challanHeads,
//				                        talukaLst
//				                )
//				              );
				
			}else {
				
				System.out.println("Here into the talathi loginResponse------------------------: ");
				
				System.out.println("echDbName: " + infoMap.get("echDbName"));
				System.out.println("echSchemaName: " + infoMap.get("echSchemaName"));
				System.out.println("mhrDbName: " + infoMap.get("mhrDbName"));
				System.out.println("mhrSchemaName: " + infoMap.get("mhrSchemaName"));
				System.out.println("echHost: " + infoMap.get("echHost"));
				System.out.println("mhrHost: " + infoMap.get("mhrHost"));
				
				return ResponseEntity.ok(new JwtResponse(jwt, refreshToken, userDetails.getUsername().trim(), userDetails.getFullname(),
						userDetails.getEmailId(), userDetails.getDistrictCode().trim(), districtName,
						userDetails.getTalukaCode().trim(), userDetails.getTalukaName(), userDetails.getMarathiName(),
						userDetails.getDesg(), jwtUtils.getGenrateTime(jwt), jwtUtils.getExpiryTime(jwt), roles,
						infoMap.get("echDbName").trim(), infoMap.get("echSchemaName").trim(),
						infoMap.get("mhrDbName").trim(), infoMap.get("mhrSchemaName").trim(), infoMap.get("echHost").trim(),
						infoMap.get("mhrHost").trim(), villageDaoLstNew,villageDaoInspectionLst, revenueYearDaoLst, niranks, challanHeads));
			 		
				
				
//				ResponseCookie Bcookie =
//				        ResponseCookie.from(
//				                        "refreshToken",
//				                        refreshToken)
//				                .httpOnly(false)
//				                // FALSE FOR LOCALHOST HTTP
//				                // TRUE FOR HTTPS PRODUCTION
//				                .secure(false)
//				                .path("/")
//				                //.maxAge(Duration.ofMinutes(2))
//				                .maxAge(Duration.ofHours(8))
//				               // .sameSite("Strict")
//				                .sameSite("Lax")
//				                .build();
//				return ResponseEntity.ok()
//				        .header(
//				                HttpHeaders.SET_COOKIE,
//				                cookie.toString())
//				        .body(
//				                new JwtResponse(
//				                        jwt,
//				                        userDetails.getUsername().trim(),
//				                        userDetails.getFullname(),
//				                        userDetails.getEmailId(),
//				                        userDetails.getDistrictCode().trim(),
//				                        districtName,
//				                        userDetails.getTalukaCode().trim(),
//				                        userDetails.getTalukaName(),
//				                        userDetails.getMarathiName(),
//				                        userDetails.getDesg(),
//				                        jwtUtils.getGenrateTime(jwt),
//				                        jwtUtils.getExpiryTime(jwt),
//				                        roles,
//				                        infoMap.get("echDbName").trim(),
//				                        infoMap.get("echSchemaName").trim(),
//				                        infoMap.get("mhrDbName").trim(),
//				                        infoMap.get("mhrSchemaName").trim(),
//				                        infoMap.get("echHost").trim(),
//				                        infoMap.get("mhrHost").trim(),
//				                        villageDaoLstNew,
//				                        villageDaoInspectionLst,
//				                        revenueYearDaoLst,
//				                        niranks,
//				                        challanHeads
//				                )
//				        );
				
				
			}
		} else {
			
			System.out.println("Here into the Collector loginResponse------------------------: ");
			
			
			
			
			return ResponseEntity.ok(new JwtResponse(jwt, refreshToken, userDetails.getUsername().trim(), userDetails.getFullname(),
					userDetails.getEmailId(), jwtUtils.getGenrateTime(jwt), jwtUtils.getExpiryTime(jwt),
					userDetails.getMarathiName(), roles, niranks));
			
			
			
//			ResponseCookie Ccookie =
//			        ResponseCookie.from(
//			                        "refreshToken",
//			                        refreshToken)
//			                .httpOnly(false)
//			                // FALSE FOR LOCALHOST HTTP
//			                // TRUE FOR HTTPS PRODUCTION
//			                .secure(false)
//			                .path("/")
//			                //.maxAge(Duration.ofMinutes(2))
//			               .maxAge(Duration.ofHours(8))
//			               // .sameSite("Strict")
//			                .sameSite("Lax")
//			                .build();
//			return ResponseEntity.ok()
//			        .header(
//			                HttpHeaders.SET_COOKIE,
//			                cookie.toString())
//			        .body(
//			                new JwtResponse(
//			                        jwt,
//			                        userDetails.getUsername().trim(),
//			                        userDetails.getFullname(),
//			                        userDetails.getEmailId(),
//			                        jwtUtils.getGenrateTime(jwt),
//			                        jwtUtils.getExpiryTime(jwt),
//			                        userDetails.getMarathiName(),
//			                        roles,
//			                        niranks
//			                )
//			        );
			
			

		}

		  }else {
		  
		  
		  throw new ResourceNotFoundException("error.form.unautorized"); 
		  
		  }
		 
	}

//old one method for refresh token
//  	@PostMapping("/refreshtoken")
//	public ResponseEntity<JwtResponse> refreshtokens(@RequestParam ("token") String token) {
//		return jwtUtils.findByToken(token).map(jwtUtils::verifyExpiration).map(Token::getServarthId).map(user -> { 
//			String jwt = jwtUtils.generateTokenFromUsername(user.toString());
//			jwtUtils.storeToken(jwt);
//			return ResponseEntity.ok(new JwtResponse(jwt,jwtUtils.getGenrateTime(jwt),jwtUtils.getExpiryTime(jwt)));
//		}).orElseThrow(() -> new TokenRefreshException(token,"token is not in database!"));
//	}
	
	
	
	//New RefreshToken Api ( 2 June 2026 --> Tuesday )	
//	@PostMapping("/refreshtoken")
//	public ResponseEntity<?> refreshToken(
//	        @RequestParam String refreshToken) {
	
	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshToken(
			 @RequestParam String refreshToken,
			HttpServletRequest request,
	        HttpServletResponse response) throws IOException{
		
		
		System.out.println("refreshToken send by frontend------: "+refreshToken);

	    Token token = tokenRepsitory
	            .findByRefreshToken(refreshToken)
	            .orElseThrow(() ->
	                    new RuntimeException("Refresh token not found"));

	    // CHECK LOGOUT
	    if (Boolean.TRUE.equals(token.getLoggedOut())
	            || Boolean.TRUE.equals(token.getRevoked())) {

	        throw new RuntimeException("Token revoked");
	    }

	    // CHECK EXPIRY
	    if (token.getRefreshExpiryDate()
	            .isBefore(Instant.now())) {
	    	
	    	
	    	 customAccessDeniedHandler.handleRefreshTokenExpired(
	                 request,
	                 response
	         );

	    	 
	    	return null;
	      
	    }

	    // USERNAME FROM DB
	    String username = token.getServarthId();

	    // GENERATE NEW ACCESS TOKEN
	    String newAccessToken =
	            jwtUtils.generateTokenFromUsername(username);

	    // UPDATE DB
	    token.setAccessToken(newAccessToken);

	    token.setGeneratedTime(
	            jwtUtils.getGenrateTime(newAccessToken));

	    token.setExpiryTime(
	            jwtUtils.getExpiryTime(newAccessToken));

	    tokenRepsitory.save(token);

//	    return ResponseEntity.ok(
//	            Map.of(
//	                    "accessToken", newAccessToken
//	            ));
	    
	    
	    Map<String, Object> resp = new HashMap<>();
	    
	    System.out.println("NewAccessToken send after access token expired ---- : "+newAccessToken);

	    resp.put("accessToken", newAccessToken);
	  

	    return ResponseEntity.ok(resp);
	}
	


	//New logout api ( 2 June 2026 --> Tuesday )
	@PostMapping("/logout")
	public ResponseEntity<?> logout(
	        String refreshToken,
	        HttpServletResponse response) {
		
		
		  System.out.println("RefreshToken----------------: "+refreshToken);


	    // TOKEN EXISTS
	    if (refreshToken != null) {

	        Optional<Token> optionalToken =
	                tokenRepsitory
	                        .findByRefreshToken(
	                                refreshToken);
	        
	        
	        System.out.println("RefreshToken----------------: "+refreshToken);

	        if (optionalToken.isPresent()) {

	            Token token =
	                    optionalToken.get();
	            
	            System.out.println("I am here in logout api above setters of true : ");

	            token.setLoggedOut(true);

	            token.setRevoked(true);
	            
	            System.out.println("I am here in logout api above setters of true (Now its sets ) : ");

	            tokenRepsitory.save(token);
	            
	    		response.setHeader("Clear-Site-Data",
	            "\"cache\", \"cookies\", \"storage\"");
	        }
	    }

	    // ALWAYS CLEAR COOKIE
	    ResponseCookie deleteCookie =
	            ResponseCookie.from(
	                            "refreshToken",
	                            "")
	                    .httpOnly(false)
	                    .secure(false)
	                    .path("/")
	                    .maxAge(0)
	                    //.sameSite("Strict")
	                    .sameSite("Lax")
	                    .build();

	    return ResponseEntity.ok()

	            .header(
	                    HttpHeaders.SET_COOKIE,
	                    deleteCookie.toString())

	            .body("Logout successful");
	}

	


//	@PostMapping("/logout")
//	@ResponseStatus(HttpStatus.IM_USED)
//	public MessageResponse logoutUser(@RequestParam ("servarthId") String servarthId) {
//		
//		System.out.println("servarth id --------- : "+servarthId);
//		
//		jwtUtils.deleteByUserName(servarthId);
//		
//		return MessageResponse.builder().status("SUCCESS").message("LogOut Successfully!!!!").build();  
//	}

	@PostMapping("/login/account")
	public ResponseEntity<DummyResponse> account(@RequestBody UsersDao usersDao){
		DummyResponse dummyResponse= new DummyResponse();
		dummyResponse.setStatus("ok");
		dummyResponse.setCurrentAuthority("admin");
		dummyResponse.setType("account");
		return new ResponseEntity<>(dummyResponse,HttpStatus.OK);
	}
	
	

	
	
	/*@GetMapping("/currentUser")
	public ResponseEntity<DummyResponse2> currentUser(  ) {
		
		
		Authentication authentication =
		        SecurityContextHolder.getContext().getAuthentication();

		System.out.println(authentication);
		
		System.out.println("Authentication Object printing here ----------------- :"+authentication);

		DummyResponse2 response = new DummyResponse2();
		Data data = new Data();

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		System.out.println("FULL NAME: " + userDetails.getFullname());

		data.setAccess("admin");
		data.setName(userDetails.getFullname());

		response.setSuccess(true);
		response.setData(data);

		return ResponseEntity.ok(response);
	}*/
	

@PostMapping("/login/outLogin")
	public ResponseEntity<DummyResponse3> logout(HttpServletRequest request){
	
		 DummyResponse3 response = new DummyResponse3();
		 
		 

		    response.setSuccess(true);
		    return new ResponseEntity<>(response, HttpStatus.OK);
	}	

	public String getProperCCode(String cCode) {
		if(cCode.length()<18) {
			int whiteSpaceCount=18-cCode.length();
			for(int i=whiteSpaceCount;i>0;i--) {
				cCode=cCode.concat(" ");
			}
		}
		return cCode;
	}



	@GetMapping("/file/1")
	public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response) throws IOException {
		File file = new File(StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "NEWS") + "one.pdf");
		
		//change for null document 23/04/2026 
				if (!file.exists()) {
				    response.setContentType("text/plain");
				    response.getWriter().write("File does not exist , Plz try again later");
				    return;
				}
		
		if (file.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}

	@GetMapping("/file/2")
	public void downloadPDFResource2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		File file = new File(StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "NEWS") + "two.pdf");
		
		//change for null document 23/04/2026 
		if (!file.exists()) {
		    response.setContentType("text/plain");
		    response.getWriter().write("File does not exist , Plz try again later");
		    return;
		}
		
		if (file.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}
	
	@GetMapping("/file/3")
	public void downloadPDFResource3(HttpServletRequest request, HttpServletResponse response) throws IOException {
		File file = new File(StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "NEWS") + "three.pdf");
		//change for null document 23/04/2026 
		if (!file.exists()) {
		    response.setContentType("text/plain");
		    response.getWriter().write("File does not exist , Plz try again later");
		    return;
		}
		if (file.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}
	
	@GetMapping("/file/4")
	public void downloadPDFResource4(HttpServletRequest request, HttpServletResponse response) throws IOException {
		File file = new File(StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "NEWS") + "four.pdf");
		//change for null document 23/04/2026 
		if (!file.exists()) {
		    response.setContentType("text/plain");
		    response.getWriter().write("File does not exist , Plz try again later");
		    return;
		}
		if (file.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}

	@GetMapping("/file/5")
	public void downloadPDFResource5(HttpServletRequest request, HttpServletResponse response) throws IOException {
		File file = new File(StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "NEWS") + "five.pdf");
		//change for null document 23/04/2026 
		if (!file.exists()) {
		    response.setContentType("text/plain");
		    response.getWriter().write("File does not exist , Plz try again later");
		    return;
		}
		if (file.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}
	
	@GetMapping("/file/6")
	public void downloadPDFResource6(HttpServletRequest request, HttpServletResponse response) throws IOException {
		File file = new File(StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "NEWS") + "six.pdf");
		//change for null document 23/04/2026 
		if (!file.exists()) {
		    response.setContentType("text/plain");
		    response.getWriter().write("File does not exist , Plz try again later");
		    return;
		}
		if (file.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}
	
	@GetMapping("/file/7")
	public void downloadPDFResource7(HttpServletRequest request, HttpServletResponse response) throws IOException {
		File file = new File(StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "NEWS") + "seven.pdf");
		//change for null document 23/04/2026 
		if (!file.exists()) {
		    response.setContentType("text/plain");
		    response.getWriter().write("File does not exist , Plz try again later");
		    return;
		}
		if (file.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}
	
	@GetMapping("/file/8")
	public void downloadPDFResource8(HttpServletRequest request, HttpServletResponse response) throws IOException {
		File file = new File(StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "NEWS") + "eight.pdf");
		//change for null document 23/04/2026 
		if (!file.exists()) {
		    response.setContentType("text/plain");
		    response.getWriter().write("File does not exist , Plz try again later");
		    return;
		}
		if (file.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}
	
	@GetMapping("/file/9")
	public void downloadPDFResource9(HttpServletRequest request, HttpServletResponse response) throws IOException {
		File file = new File(StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "NEWS") + "nine.pdf");
		//change for null document 23/04/2026 
		if (!file.exists()) {
		    response.setContentType("text/plain");
		    response.getWriter().write("File does not exist , Plz try again later");
		    return;
		}
		if (file.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}
	
	@GetMapping("/file/10")
	public void downloadPDFResource10(HttpServletRequest request, HttpServletResponse response) throws IOException {
		File file = new File(StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "NEWS") + "ten.pdf");
		//change for null document 23/04/2026 
		if (!file.exists()) {
		    response.setContentType("text/plain");
		    response.getWriter().write("File does not exist , Plz try again later");
		    return;
		}
		if (file.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}
	
	//-----Newly Added for pdf download link on login page /echawdi_nfs/echawdidocument/news/eleven.pdf
	@GetMapping("/file/11")
	public void downloadPDFResource11(HttpServletRequest request, HttpServletResponse response) throws IOException {
		File file = new File(StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "NEWS") + "eleven.pdf");
		//change for null document 23/04/2026 
		if (!file.exists()) {
		    response.setContentType("text/plain");
		    response.getWriter().write("File does not exist , Plz try again later");
		    return;
		}
		if (file.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}
	
	
	//-----Newly Added for pdf download link on login page /echawdi_nfs/echawdidocument/news/eleven.pdf(24 jun 2025)
	@GetMapping("/file/12")
	public void downloadPDFResource12(HttpServletRequest request, HttpServletResponse response) throws IOException {
		File file = new File(StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "NEWS") + "twelve.pdf");
		//change for null document 23/04/2026 
		if (!file.exists()) {
		    response.setContentType("text/plain");
		    response.getWriter().write("File does not exist , Plz try again later");
		    return;
		}
		if (file.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}
	
	
	
	//-----Newly Added for pdf download link on login page /echawdi_nfs/echawdidocument/news/Thirteen.pdf(31 july 2025)
			@GetMapping("/file/13")
			public void downloadPDFResource13(HttpServletRequest request, HttpServletResponse response) throws IOException {
				File file = new File(StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "NEWS") + "Thirteen.pdf");
				//change for null document 23/04/2026 
				if (!file.exists()) {
				    response.setContentType("text/plain");
				    response.getWriter().write("File does not exist , Plz try again later");
				    return;
				}
				if (file.exists()) {
					String mimeType = URLConnection.guessContentTypeFromName(file.getName());
					if (mimeType == null) {
						mimeType = "application/octet-stream";
					}
					response.setContentType(mimeType);
					response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
					response.setContentLength((int) file.length());
					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
					FileCopyUtils.copy(inputStream, response.getOutputStream());
				}
			}
			
			
			//-----Newly Added for pdf download link on login page /echawdi_nfs/echawdidocument/news/Fourteen.pdf(14 Nov 2025)
					@GetMapping("/file/14")
					public void downloadPDFResource14(HttpServletRequest request, HttpServletResponse response) throws IOException {
						
						File file = new File(StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "NEWS") + "Fourteen.pdf");
						//change for null document 23/04/2026 
						if (!file.exists()) {
						    response.setContentType("text/plain");
						    response.getWriter().write("File does not exist , Plz try again later");
						    return;
						}
						if (file.exists()) {
							String mimeType = URLConnection.guessContentTypeFromName(file.getName());
							if (mimeType == null) {
								mimeType = "application/octet-stream";
							}
							response.setContentType(mimeType);
							response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
							response.setContentLength((int) file.length());
							InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
							FileCopyUtils.copy(inputStream, response.getOutputStream());
						}
					}
		
	
	
	//	@GetMapping("/migrate")
	@WithDatabase(DataSourceType.echmst)
	public ResponseEntity<?> dataMigrate(@RequestParam("formName") String formName){
		String cCodes="270100020002580000|270300010016560000|270300060021040000|270300070022040000|270300140029000000|270400100042560000|270500010046630000|270500050052280000|270500060053360000|270500070054110000|270600040060490000|270700120081150000|270800020085360000|270800050091410030|270800070093520000|270800080096770000|270900020100760000|270900040101930000|270900050103860000|270900060104910000|270900070106420000|270900080107520000|270900140115720000|271000070124390000|271100070132940000|271100080133370000|271200020135500000|271200040137570000|271200050139610000|271200080144190000|271200100147730000|271300020153360000|271300040157260000|271300050159080000|271300060160080000|271300070160960000|271300080162700000|271300110165040500|271500060196140000|271500110199880000|271600030209840000|271600040210670000|271700050218120000|271700060218940000|271700070219140000|271700070219920000|271700080220270000|271700090221280000|271800020224090000|271800040226740000|271900010233130000|271900020234440000|271900020234520000|271900050236750000|271900060238550000|271900070240910000|271900090246030000|272000010246060000|272000040250590000|272000110258680000|272000150263450000|272100060270270000|272100080273230000|272100110277120000|272100150280620000|272200010000001800|272400010282310000|272400030285370000|272400040286660000|272400110296420000|272500060309970000|272500100312970000|272600100330270000|272600120332890000|272700080346260000|272700090343310000|272800040352410000|272800070354230000|272800100358060000|272900010359510000|272900020359980000|272900030361120000|272900080365120000|273000010366550000|273000040370100000|273000060371860000|273100010377840000|273100020378120000|273100030379830000|273100040380850000|273100070384350000|273100100391500000|273200020395970000|273200080406890000|273300010410990000|273300020411750000|273300050414490000|273300060415770000|273300070416830000|273500030432080000|AB1600020208320000|271500070197320000|272500130318050000|270900090109360000";
		//		String cCodes="271500070197320000|272400110296420000|272500130318050000|270900090109360000|272000040250590000";//vategaon 273500020430890000|
		List<Object[]> mapper=mVillageMapperRepository.getAll();
		//		Response response=new Response();
		if(formName.equals("form15")) {	

			List<Users> userLst=mVillageMapperRepository.getAllUsersFromOldDb().stream().map(obj->{

				Users u=new Users();
				u.setServarthId(obj[0].toString());
				u.setDistrictCode(obj[1].toString());
				u.setTalukaCode(obj[2].toString());
				return u;
			}).collect(Collectors.toList());

			List<Form15> form15Lst=mVillageMapperRepository.getForm15().stream().map(obj->{

				Form15 form15=new Form15();
				form15.setCommunicationReceivedFrom(obj[0].toString());
				form15.setNoOfCommunication(obj[1].toString());

				if(obj[2] !=null) {
					form15.setDateOfReceipt(LocalDate.parse(obj[2].toString()));
				}

				if(obj[3] !=null) {
					form15.setCommunicationDispatchedToWhom(obj[3].toString());
				}
				if(obj[4] !=null) {
					form15.setDateOfDispatch(LocalDate.parse(obj[4].toString()));
				}
				form15.setNoInList(obj[5].toString());
				if(obj[6] !=null) {
					form15.setRemarks(obj[6].toString());
				}
				form15.setDesignation(obj[7].toString());
				form15.setUserId(obj[8].toString());
				if(obj[9] !=null) {
					form15.setSubjectActionToTake(obj[9].toString());
				}
				return form15;
			}).collect(Collectors.toList());

			for(Users user : userLst){
				Response response =new Response();
				List<Form15> form15LstToPersist=new ArrayList<Form15>();
				for(Form15 form15:form15Lst){
					if(form15.getUserId().equals(user.getServarthId())){
						form15LstToPersist.add(form15);
					}
				}
				//				DatabaseContextHolder.setCtx(DataSourceType.echmst);
				MVillageMapper mVillageMapper=mVillageMapperRepository.findByDistrictCodeAndTalukaCode(user.getDistrictCode(),user.getTalukaCode());
				if(!form15LstToPersist.isEmpty() && mVillageMapper!=null) {
					response.setForm15(form15LstToPersist);
					response.setDbName(mVillageMapper.getEchDbName());
					response.setSchemaName(mVillageMapper.getEchSchemaName());
					restTemplate.postForEntity("http://localhost:8091/echawdi/auth/form15",response,boolean.class);
				}
			}
		}else if(formName.equals("form19")) {
			List<Users> userLst=mVillageMapperRepository.getAllUsersFromOldDb().stream().map(obj->{
				Users u=new Users();
				u.setServarthId(obj[0].toString());
				u.setDistrictCode(obj[1].toString());
				u.setTalukaCode(obj[2].toString());
				return u;
			}).collect(Collectors.toList());

			List<Form19> form19Lst=mVillageMapperRepository.getForm19().stream().map(obj->{

				Form19 form19 =new Form19();
				if(obj[0]!=null) {
					form19.setDescriptionOfArticle(obj[0].toString());
				}
				if(obj[1]!=null) {
					form19.setAuthorityOfPurchase(obj[1].toString());
				}
				if(obj[2]!=null) {
					form19.setDateOfPurchase(LocalDate.parse(obj[2].toString()));
				}
				if(obj[3]!=null) {
					form19.setNumberOrQuantity(obj[3].toString());
				}
				if(obj[4]!=null) {
					form19.setAuthorityOfVoucher(obj[4].toString());
				}
				if(obj[5]!=null) {
					form19.setAmountWrittenOff(obj[5].toString());
				}
				if(obj[6]!=null) {
					form19.setAmountRealized(obj[6].toString());
				}
				if(obj[7]!=null) {
					form19.setDateOfCreditAtTreasury(LocalDate.parse(obj[7].toString()));
				}
				if(obj[8]!=null) {
					form19.setNumber(obj[8].toString());
				}
				if(obj[9]!=null) {
					form19.setValue(obj[9].toString());
				}
				if(obj[10]!=null) {
					form19.setUserId(obj[10].toString());
				}
				if(obj[11]!=null) {
					form19.setRemarks(obj[11].toString());
				}
				return form19;	
			}
					).collect(Collectors.toList());

			for(Users user : userLst){
				Response response =new Response();
				List<Form19> form19LstToPersist=new ArrayList<>();
				for(Form19 form19:form19Lst){
					if(form19.getUserId().equals(user.getServarthId())){
						form19LstToPersist.add(form19);
					}
				}
				MVillageMapper mVillageMapper=mVillageMapperRepository.findByDistrictCodeAndTalukaCode(user.getDistrictCode(),user.getTalukaCode());
				if(!form19LstToPersist.isEmpty() && mVillageMapper!=null) {
					response.setForm19(form19LstToPersist);
					response.setDbName(mVillageMapper.getEchDbName());
					response.setSchemaName(mVillageMapper.getEchSchemaName());
					restTemplate.postForEntity("http://localhost:8091/echawdi/auth/form19",response,boolean.class);
				}
			}
		}else {
			for(Object[] mvMapp:mapper) {
				Response response=new Response();

				if(formName.equals("form1")) {

					if(!mvMapp[0].toString().matches(cCodes)) {
						List<Object[]> form1ObjectLst=mVillageMapperRepository.getForm1(mvMapp[0].toString());
						List<Form1> form1lst =form1ObjectLst
								.stream()
								.map(obj->{

									Form1 form1 =new Form1();

									form1.setDistrictCode(Integer.valueOf(obj[0].toString()));
									form1.setTalukaCode(Integer.valueOf(obj[1].toString()));
									form1.setCCode(obj[2].toString());

									form1.setPin(obj[3].toString());
									form1.setPin1(obj[4].toString());
									form1.setPin2(obj[5].toString());
									form1.setPin3(obj[6].toString());
									form1.setPin4(obj[7].toString());
									form1.setPin5(obj[8].toString());
									form1.setPin6(obj[9].toString());
									form1.setPin7(obj[10].toString());
									form1.setPin8(obj[11].toString());

									form1.setPublicRightsOfWayAndEasements(obj[12].toString());
									form1.setParticularsOfAlteration(obj[13].toString());

									if(obj[14]!=null) {
										form1.setDesignation(obj[14].toString());
									}

									if(obj[15]!=null) {
										form1.setDesignationOfAuthority(obj[15].toString());
									}

									if(obj[16]!=null) {
										form1.setOrderDate(LocalDate.parse(obj[16].toString()));
									}

									form1.setOrderNo(obj[17].toString());
									if(obj[18]!=null) {
										form1.setRemarks(obj[18].toString());
									}
									return form1;	
								}
										).collect(Collectors.toList());
						if(!form1lst.isEmpty() && form1lst.size() > 0) {
							response.setForm1(form1lst);
							response.setDbName(mvMapp[1].toString());
							response.setSchemaName(mvMapp[2].toString());
							restTemplate.postForEntity("http://localhost:8091/echawdi/auth/form1", response, boolean.class);
						}
					}
				}

				else if(formName.equals("form14")) {

					if(!mvMapp[0].toString().matches(cCodes)) {
						List<Object[]> form14ObjectLst=mVillageMapperRepository.getForm14(mvMapp[0].toString());
						List<Form14> form14lst =form14ObjectLst
								.stream()
								.map(obj->{

									Form14 form14 =new Form14();

									form14.setDistrictCode(Integer.valueOf(obj[0].toString()));
									form14.setTalukaCode(Integer.valueOf(obj[1].toString()));
									form14.setCCode(obj[2].toString());

									if(obj[3]!=null) {
										form14.setPin(obj[3].toString());
									}
									if(obj[4]!=null) {
										form14.setPin1(obj[4].toString());
									}
									if(obj[5]!=null) {
										form14.setPin2(obj[5].toString());
									}
									if(obj[6]!=null) {
										form14.setPin3(obj[6].toString());
									}
									if(obj[7]!=null) {
										form14.setPin4(obj[7].toString());
									}
									if(obj[8]!=null) {
										form14.setPin5(obj[8].toString());
									}
									if(obj[9]!=null) {
										form14.setPin6(obj[9].toString());
									}
									if(obj[10]!=null) {
										form14.setPin7(obj[10].toString());
									}
									if(obj[11]!=null) {
										form14.setPin8(obj[11].toString());
									}

									if(obj[12]!=null) {
										form14.setPosition(obj[12].toString());
									}
									if(obj[13]!=null) {
										form14.setEWaterSourceName(obj[13].toString());
									}

									if(obj[14]!=null) {
										form14.setPakkaOrKaccha(obj[14].toString());
									}

									if(obj[15]!=null) {
										form14.setPurposeOfUse(obj[15].toString());
									}

									if(obj[16]!=null) {
										form14.setGovernmentbodyOrPrivate(obj[16].toString());
									}

									if(obj[17]!=null) {
										form14.setOtherType(obj[17].toString());
									}
									if(obj[18]!=null) {
										form14.setRemarks(obj[18].toString());
									}
									return form14;	
								}
										).collect(Collectors.toList());
						if(!form14lst.isEmpty() && form14lst.size() > 0) {
							response.setForm14(form14lst);
							response.setDbName(mvMapp[1].toString());
							response.setSchemaName(mvMapp[2].toString());
							restTemplate.postForEntity("http://localhost:8091/echawdi/auth/form14", response, boolean.class);
						}
					}
				}
				else if(formName.equals("form1A")) {

					if(!mvMapp[0].toString().matches(cCodes)) {
						List<Object[]> form1AObjectLst=mVillageMapperRepository.getForm1A(mvMapp[0].toString());
						List<Form1A> form1Alst =form1AObjectLst
								.stream()
								.map(obj->{

									Form1A form1A =new Form1A();

									form1A.setDistrictCode(Integer.valueOf(obj[0].toString()));
									form1A.setTalukaCode(Integer.valueOf(obj[1].toString()));
									form1A.setCCode(obj[2].toString());

									if(obj[3]!=null) {
										form1A.setPin(obj[3].toString());
									}
									if(obj[4]!=null) {
										form1A.setPin1(obj[4].toString());
									}
									if(obj[5]!=null) {
										form1A.setPin2(obj[5].toString());
									}
									if(obj[6]!=null) {
										form1A.setPin3(obj[6].toString());
									}
									if(obj[7]!=null) {
										form1A.setPin4(obj[7].toString());
									}
									if(obj[8]!=null) {
										form1A.setPin5(obj[8].toString());
									}
									if(obj[9]!=null) {
										form1A.setPin6(obj[9].toString());
									}
									if(obj[10]!=null) {
										form1A.setPin7(obj[10].toString());
									}
									if(obj[11]!=null) {
										form1A.setPin8(obj[11].toString());
									}

									if(obj[12]!=null) {
										form1A.setArea(BigDecimal.valueOf(Double.valueOf(obj[12].toString())));
									}
									if(obj[13]!=null) {
										form1A.setRightsRecordedByTheForestOfficer(obj[13].toString());
									}

									if(obj[14]!=null) {
										form1A.setVillageForest(BigDecimal.valueOf(Double.valueOf(obj[14].toString())));
									}

									if(obj[15]!=null) {
										form1A.setProtectedForest(BigDecimal.valueOf(Double.valueOf(obj[15].toString())));
									}

									if(obj[16]!=null) {
										form1A.setReservedForest(BigDecimal.valueOf(Double.valueOf(obj[16].toString())));
									}

									if(obj[17]!=null) {
										form1A.setForestNoIfAny(obj[17].toString());
									}
									if(obj[18]!=null) {
										form1A.setRemarks(obj[18].toString());
									}
									return form1A;	
								}
										).collect(Collectors.toList());
						if(!form1Alst.isEmpty() && form1Alst.size() > 0) {
							response.setForm1A(form1Alst);
							response.setDbName(mvMapp[1].toString());
							response.setSchemaName(mvMapp[2].toString());
							restTemplate.postForEntity("http://localhost:8091/echawdi/auth/form1A", response, boolean.class);
						}
					}
				}


				else if(formName.equals("form1B")) {

					if(!mvMapp[0].toString().matches(cCodes)) {
						List<Object[]> form1BObjectLst=mVillageMapperRepository.getForm1B(mvMapp[0].toString());
						List<Form1B> form1Blst =form1BObjectLst
								.stream()
								.map(obj->{

									Form1B form1B =new Form1B();

									form1B.setDistrictCode(Integer.valueOf(obj[0].toString()));
									form1B.setTalukaCode(Integer.valueOf(obj[1].toString()));
									form1B.setCCode(obj[2].toString());

									form1B.setPin(obj[3].toString());
									form1B.setPin1(obj[4].toString());
									form1B.setPin2(obj[5].toString());
									form1B.setPin3(obj[6].toString());
									form1B.setPin4(obj[7].toString());
									form1B.setPin5(obj[8].toString());
									form1B.setPin6(obj[9].toString());
									form1B.setPin7(obj[10].toString());
									form1B.setPin8(obj[11].toString());

									if(obj[12]!=null) {
										form1B.setUnassessedArea(BigDecimal.valueOf(Double.valueOf(obj[12].toString())));
									}
									if(obj[13]!=null) {
										form1B.setPublicRightsOfWayAndEasements(obj[13].toString());
									}

									if(obj[14]!=null) {
										form1B.setRemarks(obj[14].toString());
									}
									return form1B;	
								}
										).collect(Collectors.toList());
						if(!form1Blst.isEmpty() && form1Blst.size() > 0) {
							response.setForm1B(form1Blst);
							response.setDbName(mvMapp[1].toString());
							response.setSchemaName(mvMapp[2].toString());
							restTemplate.postForEntity("http://localhost:8091/echawdi/auth/form1B", response, boolean.class);
						}
					}
				}
				else if(formName.equals("form1C")) {

					if(!mvMapp[0].toString().matches(cCodes)) {
						List<Object[]> form1CObjectLst=mVillageMapperRepository.getForm1C(mvMapp[0].toString());
						List<Form1C> form1Clst =form1CObjectLst
								.stream()
								.map(obj->{

									Form1C form1C =new Form1C();

									form1C.setDistrictCode(Integer.valueOf(obj[0].toString()));
									form1C.setTalukaCode(Integer.valueOf(obj[1].toString()));
									form1C.setCCode(obj[2].toString());

									form1C.setPin(obj[3].toString());
									form1C.setPin1(obj[4].toString());
									form1C.setPin2(obj[5].toString());
									form1C.setPin3(obj[6].toString());
									form1C.setPin4(obj[7].toString());
									form1C.setPin5(obj[8].toString());
									form1C.setPin6(obj[9].toString());
									form1C.setPin7(obj[10].toString());
									form1C.setPin8(obj[11].toString());

									if(obj[12]!=null) {
										form1C.setNameOfVillagePanchayat(obj[12].toString());
									}
									if(obj[13]!=null) {
										form1C.setTermsOfGrant(obj[13].toString());
									}
									if(obj[14]!=null) {
										form1C.setAuthorityCompetentToPermitTransferOfLand(obj[14].toString());
									}
									if(obj[15]!=null) {
										form1C.setDetailsOfUnclaimedAmount(obj[15].toString());
									}
									if(obj[16]!=null) {
										form1C.setBreachOfCondition(obj[16].toString());
									}
									if(obj[17]!=null) {
										form1C.setInspectingOfficer(obj[17].toString());
									}
									if(obj[18]!=null) {
										form1C.setInspectionDate(Date.valueOf(obj[18].toString()));
									}
									if(obj[19]!=null) {
										form1C.setAadeshOnDate(obj[19].toString());
									}
									if(obj[20]!=null) {
										form1C.setPurposeOfLandIntended(obj[20].toString());
									}
									if(obj[21]!=null) {
										form1C.setRemarks(obj[21].toString());
									}
									return form1C;	
								}
										).collect(Collectors.toList());
						if(!form1Clst.isEmpty() && form1Clst.size() > 0) {
							response.setForm1C(form1Clst);
							response.setDbName(mvMapp[1].toString());
							response.setSchemaName(mvMapp[2].toString());
							restTemplate.postForEntity("http://localhost:8091/echawdi/auth/form1C", response, boolean.class);
						}
					}
				}
				else if(formName.equals("form1D")) {

					if(!mvMapp[0].toString().matches(cCodes)) {
						List<Object[]> form1DObjectLst=mVillageMapperRepository.getForm1D(mvMapp[0].toString());
						List<Form1D> form1Dlst =form1DObjectLst
								.stream()
								.map(obj->{

									Form1D form1D =new Form1D();

									form1D.setDistrictCode(Integer.valueOf(obj[0].toString()));
									form1D.setTalukaCode(Integer.valueOf(obj[1].toString()));
									form1D.setCCode(obj[2].toString());

									form1D.setPin(obj[3].toString());
									form1D.setPin1(obj[4].toString());
									form1D.setPin2(obj[5].toString());
									form1D.setPin3(obj[6].toString());
									form1D.setPin4(obj[7].toString());
									form1D.setPin5(obj[8].toString());
									form1D.setPin6(obj[9].toString());
									form1D.setPin7(obj[10].toString());
									form1D.setPin8(obj[11].toString());

									if(obj[12]!=null) {
										form1D.setRules(obj[12].toString());

									}
									if(obj[13]!=null) {
										form1D.setRemarks(obj[13].toString());
									}
									return form1D;	
								}
										).collect(Collectors.toList());
						if(!form1Dlst.isEmpty() && form1Dlst.size() > 0) {
							response.setForm1D(form1Dlst);
							response.setDbName(mvMapp[1].toString());
							response.setSchemaName(mvMapp[2].toString());
							restTemplate.postForEntity("http://localhost:8091/echawdi/auth/form1D", response, boolean.class);
						}
					}
				}
				else if(formName.equals("form1E")) {

					if(!mvMapp[0].toString().matches(cCodes)) {
						List<Object[]> form1EObjectLst=mVillageMapperRepository.getForm1E(mvMapp[0].toString());
						List<Form1E> form1Elst =form1EObjectLst
								.stream()
								.map(obj->{

									Form1E form1E =new Form1E();

									form1E.setDistrictCode(Integer.valueOf(obj[0].toString()));
									form1E.setTalukaCode(Integer.valueOf(obj[1].toString()));
									form1E.setCCode(obj[2].toString());

									form1E.setPin(obj[3].toString());
									form1E.setPin1(obj[4].toString());
									form1E.setPin2(obj[5].toString());
									form1E.setPin3(obj[6].toString());
									form1E.setPin4(obj[7].toString());
									form1E.setPin5(obj[8].toString());
									form1E.setPin6(obj[9].toString());
									form1E.setPin7(obj[10].toString());
									form1E.setPin8(obj[11].toString());

									if(obj[12]!=null) {
										form1E.setNameOfEncroacher(obj[12].toString());
									}
									if(obj[13]!=null) {
										form1E.setDateOfEncroachment(LocalDate.parse(obj[13].toString()));
									}
									if(obj[14]!=null) {
										form1E.setPurposeOfEncroachmentLand(obj[14].toString());
									}
									if(obj[15]!=null) {
										form1E.setYearFromWhichRentRecoverable(Integer.valueOf(obj[15].toString()));
									}
									if(obj[16]!=null) {
										form1E.setYearOfEncroachmentNotedIfRemoved(obj[16].toString());
									}
									if(obj[17]!=null) {
										form1E.setDateOfDecisionOnEncroachment(LocalDate.parse(obj[17].toString()));
									}
									if(obj[18]!=null) {
										form1E.setAreaOfEncroached(BigDecimal.valueOf(Double.valueOf(obj[18].toString())));
									}
									if(obj[19]!=null) {
										form1E.setTotalArea(BigDecimal.valueOf(Double.valueOf(obj[19].toString())));
									}
									if(obj[20]!=null) {
										form1E.setAreaOfEncroachedPath(obj[20].toString());
									}
									if(obj[21]!=null) {
										form1E.setRemarks(obj[21].toString());
									}
									return form1E;	
								}
										).collect(Collectors.toList());
						if(!form1Elst.isEmpty() && form1Elst.size() > 0) {
							response.setForm1E(form1Elst);
							response.setDbName(mvMapp[1].toString());
							response.setSchemaName(mvMapp[2].toString());
							restTemplate.postForEntity("http://localhost:8091/echawdi/auth/form1E", response, boolean.class);
						}
					}
				}
				else if(formName.equals("form2")) {

					if(!mvMapp[0].toString().matches(cCodes)) {
						List<Object[]> form2ObjectLst=mVillageMapperRepository.getForm2(mvMapp[0].toString());
						List<Form2> form2lst =form2ObjectLst
								.stream()
								.map(obj->{

									Form2 form2 =new Form2();

									form2.setDistrictCode(Integer.valueOf(obj[0].toString()));
									form2.setTalukaCode(Integer.valueOf(obj[1].toString()));
									form2.setCCode(obj[2].toString());

									form2.setPin(obj[3].toString());
									form2.setPin1(obj[4].toString());
									form2.setPin2(obj[5].toString());
									form2.setPin3(obj[6].toString());
									form2.setPin4(obj[7].toString());
									form2.setPin5(obj[8].toString());
									form2.setPin6(obj[9].toString());
									form2.setPin7(obj[10].toString());
									form2.setPin8(obj[11].toString());

									if(obj[12]!=null) {
										form2.setPart(obj[12].toString());
									}
									if(obj[13]!=null) {
										form2.setPurposeOfUse(obj[13].toString());
									}
									//							if(obj[14]!=null) {
									//								form2.setAuthorityOrderNo(obj[14].toString());
									//							}
									//							if(obj[15]!=null) {
									//								form2.setAuthorityDate(Date.valueOf(obj[15].toString()));
									//							}
									if(obj[14]!=null) {
										form2.setOccupanyPrice(obj[14].toString());
									}
									if(obj[15]!=null) {
										form2.setPeriodFrom(Date.valueOf(obj[15].toString()));
									}
									if(obj[16]!=null) {
										form2.setPeriodTo(Date.valueOf(obj[16].toString()));
									}
									if(obj[17]!=null) {
										form2.setEntryNumberInTalukaForm2(obj[17].toString());
									}
									if(obj[18]!=null) {
										form2.setAuthority(obj[18].toString());
									}
									if(obj[19]!=null) {
										form2.setNameOfFirstOccupant(obj[19].toString());
									}
									if(obj[20]!=null) {
										form2.setRemarks(obj[20].toString());
									}
									return form2;	
								}
										).collect(Collectors.toList());
						if(!form2lst.isEmpty() && form2lst.size() > 0) {
							response.setForm2(form2lst);
							response.setDbName(mvMapp[1].toString());
							response.setSchemaName(mvMapp[2].toString());
							restTemplate.postForEntity("http://localhost:8091/echawdi/auth/form2", response, boolean.class);
						}
					}
				}
				else if(formName.equals("form3")) {

					if(!mvMapp[0].toString().matches(cCodes)) {
						List<Object[]> form3ObjectLst=mVillageMapperRepository.getForm3(mvMapp[0].toString());
						List<Form3> form3lst =form3ObjectLst
								.stream()
								.map(obj->{

									Form3 form3 =new Form3();

									form3.setDistrictCode(Integer.valueOf(obj[0].toString()));
									form3.setTalukaCode(Integer.valueOf(obj[1].toString()));
									form3.setCCode(obj[2].toString());

									form3.setPin(obj[3].toString());
									form3.setPin1(obj[4].toString());
									form3.setPin2(obj[5].toString());
									form3.setPin3(obj[6].toString());
									form3.setPin4(obj[7].toString());
									form3.setPin5(obj[8].toString());
									form3.setPin6(obj[9].toString());
									form3.setPin7(obj[10].toString());
									form3.setPin8(obj[11].toString());

									if(obj[12]!=null) {
										form3.setClasses(obj[12].toString());
									}
									if(obj[13]!=null) {
										form3.setRegisterNoAndTypeEligibility(obj[13].toString());
									}
									if(obj[14]!=null) {
										form3.setSanadNo(obj[14].toString());
									}
									if(obj[15]!=null) {
										form3.setNaArea(obj[15].toString());
									}
									if(obj[16]!=null) {
										form3.setNaAssessment(obj[16].toString());
									}
									if(obj[17]!=null) {
										form3.setJuniSalami(BigDecimal.valueOf(Double.valueOf(obj[17].toString())));
									}
									if(obj[18]!=null) {
										form3.setTotal(obj[18].toString());
									}
									if(obj[19]!=null) {
										form3.setRate(obj[19].toString());
									}
									if(obj[20]!=null) {
										form3.setEchAssessment(obj[20].toString());
									}
									if(obj[21]!=null) {
										form3.setNetPending(BigDecimal.valueOf(Double.valueOf(obj[21].toString())));
									}
									if(obj[22]!=null) {
										form3.setTenure(obj[22].toString());
									}
									if(obj[23]!=null) {
										form3.setKindAndHowLongContinuable(obj[23].toString());
									}
									if(obj[24]!=null) {
										form3.setRunningNoEachClass(Long.valueOf(obj[24].toString()));
									}
									if(obj[25]!=null) {
										form3.setNoInRegisterOfLands(obj[25].toString());
									}
									if(obj[26]!=null) {
										form3.setRemarks(obj[26].toString());
									}
									return form3;	
								}
										).collect(Collectors.toList());
						if(!form3lst.isEmpty() && form3lst.size() > 0) {
							response.setForm3(form3lst);
							response.setDbName(mvMapp[1].toString());
							response.setSchemaName(mvMapp[2].toString());
							restTemplate.postForEntity("http://localhost:8091/echawdi/auth/form3", response, boolean.class);
						}
					}
				}
				else if(formName.equals("form6B")) {

					if(!mvMapp[0].toString().matches(cCodes)) {
						List<Object[]> form6BObjectLst=mVillageMapperRepository.getForm6B(mvMapp[0].toString());
						List<Form6B> form6Blst =form6BObjectLst
								.stream()
								.map(obj->{

									Form6B form6B =new Form6B();

									form6B.setDistrictCode(Integer.valueOf(obj[0].toString()));
									form6B.setTalukaCode(Integer.valueOf(obj[1].toString()));
									form6B.setCCode(obj[2].toString());

									if(obj[3]!=null) {
										form6B.setOrderOfTahsildarAsFine(obj[3].toString());
									}
									if(obj[4]!=null) {
										form6B.setReceiptNo(obj[4].toString());
									}
									if(obj[5]!=null) {
										form6B.setReceiptDate(LocalDate.parse(obj[5].toString()));
									}
									if(obj[6]!=null) {
										form6B.setKhataNo(obj[6].toString());
									}
									if(obj[7]!=null) {
										form6B.setEntryVillageForm6(Integer.valueOf(obj[7].toString()));
									}
									if(obj[8]!=null) {
										form6B.setDateOfDeath(LocalDate.parse(obj[8].toString()));
									}
									if(obj[9]!=null) {
										form6B.setReasonOfDeath(obj[9].toString());
									}
									if(obj[10]!=null) {
										form6B.setNameOfAcquirerOfRight(obj[10].toString());
									}

									return form6B;	
								}
										).collect(Collectors.toList());
						if(!form6Blst.isEmpty() && form6Blst.size() > 0) {
							response.setForm6B(form6Blst);
							response.setDbName(mvMapp[1].toString());
							response.setSchemaName(mvMapp[2].toString());
							restTemplate.postForEntity("http://localhost:8091/echawdi/auth/form6B", response, boolean.class);
						}
					}
				}
				else if(formName.equals("form6D")) {

					if(!mvMapp[0].toString().matches(cCodes)) {
						List<Object[]> form6DObjectLst=mVillageMapperRepository.getForm6D(mvMapp[0].toString());
						List<Form6D> form6Dlst =form6DObjectLst
								.stream()
								.map(obj->{

									Form6D form6D =new Form6D();

									form6D.setDistrictCode(Integer.valueOf(obj[0].toString()));
									form6D.setTalukaCode(Integer.valueOf(obj[1].toString()));
									form6D.setCCode(obj[2].toString());

									form6D.setPin(obj[3].toString());
									form6D.setPin1(obj[4].toString());
									form6D.setPin2(obj[5].toString());
									form6D.setPin3(obj[6].toString());
									form6D.setPin4(obj[7].toString());
									form6D.setPin5(obj[8].toString());
									form6D.setPin6(obj[9].toString());
									form6D.setPin7(obj[10].toString());
									form6D.setPin8(obj[11].toString());
									if(obj[12]!=null) {
										form6D.setNatureOfChangeInMap(obj[12].toString());
									}
									if(obj[13]!=null) {
										form6D.setByWhomDone(obj[13].toString());
									}
									if(obj[14]!=null) {
										form6D.setDateOfChange(Date.valueOf(obj[14].toString()));
									}
									if(obj[15]!=null) {
										form6D.setMutationEntryInVillageForm6(obj[15].toString());
									}
									return form6D;	
								}
										).collect(Collectors.toList());
						if(!form6Dlst.isEmpty() && form6Dlst.size() > 0) {
							response.setForm6D(form6Dlst);
							response.setDbName(mvMapp[1].toString());
							response.setSchemaName(mvMapp[2].toString());
							restTemplate.postForEntity("http://localhost:8091/echawdi/auth/form6D", response, boolean.class);
						}
					}
				}
				else if(formName.equals("form7A")) {

					if(!mvMapp[0].toString().matches(cCodes)) {
						List<Object[]> form7AObjectLst=mVillageMapperRepository.getForm7A(mvMapp[0].toString());
						List<Form7A> form7Alst =form7AObjectLst
								.stream()
								.map(obj->{

									Form7A form7A =new Form7A();

									form7A.setDistrictCode(Integer.valueOf(obj[0].toString()));
									form7A.setTalukaCode(Integer.valueOf(obj[1].toString()));
									form7A.setCCode(obj[2].toString());

									form7A.setPin(obj[3].toString());
									form7A.setPin1(obj[4].toString());
									form7A.setPin2(obj[5].toString());
									form7A.setPin3(obj[6].toString());
									form7A.setPin4(obj[7].toString());
									form7A.setPin5(obj[8].toString());
									form7A.setPin6(obj[9].toString());
									form7A.setPin7(obj[10].toString());
									form7A.setPin8(obj[11].toString());
									if(obj[12]!=null) {
										form7A.setKhataNo(obj[12].toString());
									}
									if(obj[13]!=null) {
										form7A.setLastYearSerialNo(obj[13].toString());
									}
									if(obj[14]!=null) {
										form7A.setNameOfTenant(obj[14].toString());
									}
									if(obj[15]!=null) {
										form7A.setRent(obj[15].toString());
									}
									if(obj[16]!=null) {
										form7A.setSerialNoInMutationReg(obj[16].toString());
									}
									if(obj[17]!=null) {
										form7A.setArea(obj[17].toString());
									}
									if(obj[18]!=null) {
										form7A.setRemarks(obj[18].toString());
									}
									return form7A;	
								}
										).collect(Collectors.toList());
						if(!form7Alst.isEmpty() && form7Alst.size() > 0) {
							response.setForm7A(form7Alst);
							response.setDbName(mvMapp[1].toString());
							response.setSchemaName(mvMapp[2].toString());
							restTemplate.postForEntity("http://localhost:8091/echawdi/auth/form7A", response, boolean.class);
						}
					}
				}
				else if(formName.equals("form7B")) {

					if(!mvMapp[0].toString().matches(cCodes)) {
						List<Object[]> form7BObjectLst=mVillageMapperRepository.getForm7B(mvMapp[0].toString());
						List<Form7B> form7Blst =form7BObjectLst
								.stream()
								.map(obj->{

									Form7B form7B =new Form7B();

									form7B.setDistrictCode(Integer.valueOf(obj[0].toString()));
									form7B.setTalukaCode(Integer.valueOf(obj[1].toString()));
									form7B.setCCode(obj[2].toString());

									form7B.setPin(obj[3].toString());
									form7B.setPin1(obj[4].toString());
									form7B.setPin2(obj[5].toString());
									form7B.setPin3(obj[6].toString());
									form7B.setPin4(obj[7].toString());
									form7B.setPin5(obj[8].toString());
									form7B.setPin6(obj[9].toString());
									form7B.setPin7(obj[10].toString());
									form7B.setPin8(obj[11].toString());
									if(obj[12]!=null) {
										form7B.setKhataNo(obj[12].toString());
									}
									if(obj[13]!=null) {
										form7B.setYear(obj[13].toString());
									}
									if(obj[14]!=null) {
										form7B.setFnamePersonInPossession(obj[14].toString());
									}
									if(obj[15]!=null) {
										form7B.setMnamePersonInPossession(obj[15].toString());
									}
									if(obj[16]!=null) {
										form7B.setLnamePersonInPossession(obj[16].toString());
									}
									if(obj[17]!=null) {
										form7B.setPossessionDate(LocalDate.parse(obj[17].toString()));
									}
									if(obj[18]!=null) {
										form7B.setDesignation(obj[18].toString());
									}
									if(obj[19]!=null) {
										form7B.setOrderDate(LocalDate.parse(obj[19].toString()));
									}
									if(obj[20]!=null) {
										form7B.setOrderNo(obj[20].toString());
									}
									if(obj[21]!=null) {
										form7B.setRemarks(obj[21].toString());
									}
									return form7B;	
								}
										).collect(Collectors.toList());
						if(!form7Blst.isEmpty() && form7Blst.size() > 0) {
							response.setForm7B(form7Blst);
							response.setDbName(mvMapp[1].toString());
							response.setSchemaName(mvMapp[2].toString());
							restTemplate.postForEntity("http://localhost:8091/echawdi/auth/form7B", response, boolean.class);
						}
					}
				}
			}

		}
		return ResponseEntity.ok("Success");
	}

	@PostMapping("/form1")
	public boolean migrateform1(@RequestBody Response response) {
		try {
			dataSourceKeeper.setDataSourceEchwadi(response.getDbName(), response.getSchemaName());
			System.out.println(DatabaseContextHolder.peekDataSource().get());
			System.out.println(response.getForm1().stream().map(r->{return r;}));
			form1Repository.saveAll(response.getForm1());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@PostMapping("/form1A")
	public boolean migrateform1A(@RequestBody Response response) {
		try {
			dataSourceKeeper.setDataSourceEchwadi(response.getDbName(), response.getSchemaName());
			System.out.println(DatabaseContextHolder.peekDataSource().get());
			System.out.println(response.getForm1A().stream().map(r->{return r;}));
			form1ARepository.saveAll(response.getForm1A());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@PostMapping("/form1B")
	public boolean migrateform1B(@RequestBody Response response) {
		try {
			dataSourceKeeper.setDataSourceEchwadi(response.getDbName(), response.getSchemaName());
			System.out.println(DatabaseContextHolder.peekDataSource().get());
			System.out.println(response.getForm1B().stream().map(r->{return r;}));
			form1BRepository.saveAll(response.getForm1B());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@PostMapping("/form1C")
	public boolean migrateform1C(@RequestBody Response response) {
		try {
			dataSourceKeeper.setDataSourceEchwadi(response.getDbName(), response.getSchemaName());
			System.out.println(DatabaseContextHolder.peekDataSource().get());
			System.out.println(response.getForm1C().stream().map(r->{return r;}));
			form1CRepository.saveAll(response.getForm1C());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@PostMapping("/form1D")
	public boolean migrateform1D(@RequestBody Response response) {
		try {
			dataSourceKeeper.setDataSourceEchwadi(response.getDbName(), response.getSchemaName());
			System.out.println(DatabaseContextHolder.peekDataSource().get());
			System.out.println(response.getForm1D().stream().map(r->{return r;}));
			form1DRepository.saveAll(response.getForm1D());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@PostMapping("/form1E")
	public boolean migrateform1E(@RequestBody Response response) {
		try {
			dataSourceKeeper.setDataSourceEchwadi(response.getDbName(), response.getSchemaName());
			System.out.println(DatabaseContextHolder.peekDataSource().get());
			System.out.println(response.getForm1E().stream().map(r->{return r;}));
			form1ERepository.saveAll(response.getForm1E());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@PostMapping("/form2")
	public boolean migrateform2(@RequestBody Response response) {
		try {
			dataSourceKeeper.setDataSourceEchwadi(response.getDbName(), response.getSchemaName());
			System.out.println(DatabaseContextHolder.peekDataSource().get());
			System.out.println(response.getForm2().stream().map(r->{return r;}));
			form2Repository.saveAll(response.getForm2());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@PostMapping("/form3")
	public boolean migrateform3(@RequestBody Response response) {
		try {
			dataSourceKeeper.setDataSourceEchwadi(response.getDbName(), response.getSchemaName());
			System.out.println(DatabaseContextHolder.peekDataSource().get());
			System.out.println(response.getForm3().stream().map(r->{return r;}));
			form3Repository.saveAll(response.getForm3());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@PostMapping("/form6B")
	public boolean migrateform6B(@RequestBody Response response) {
		try {
			dataSourceKeeper.setDataSourceEchwadi(response.getDbName(), response.getSchemaName());
			System.out.println(DatabaseContextHolder.peekDataSource().get());
			System.out.println(response.getForm6B().stream().map(r->{return r;}));
			form6BRepository.saveAll(response.getForm6B());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@PostMapping("/form6D")
	public boolean migrateform6D(@RequestBody Response response) {
		try {
			dataSourceKeeper.setDataSourceEchwadi(response.getDbName(), response.getSchemaName());
			System.out.println(DatabaseContextHolder.peekDataSource().get());
			System.out.println(response.getForm6D().stream().map(r->{return r;}));
			form6DRepository.saveAll(response.getForm6D());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@PostMapping("/form7A")
	public boolean migrateform7A(@RequestBody Response response) {
		try {
			dataSourceKeeper.setDataSourceEchwadi(response.getDbName(), response.getSchemaName());
			System.out.println(DatabaseContextHolder.peekDataSource().get());
			System.out.println(response.getForm7A().stream().map(r->{return r;}));
			form7ARepository.saveAll(response.getForm7A());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@PostMapping("/form7B")
	public boolean migrateform7B(@RequestBody Response response) {
		try {
			dataSourceKeeper.setDataSourceEchwadi(response.getDbName(), response.getSchemaName());
			System.out.println(DatabaseContextHolder.peekDataSource().get());
			System.out.println(response.getForm7B().stream().map(r->{return r;}));
			form7BRepository.saveAll(response.getForm7B());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@PostMapping("/form19")
	public boolean migrateform19(@RequestBody Response response) {
		try {
			dataSourceKeeper.setDataSourceEchwadi(response.getDbName(), response.getSchemaName());
			if(!DatabaseContextHolder.peekDataSource().isPresent()) {
				System.out.println(response.getDbName());
				System.out.println(response.getSchemaName());
			}
			System.out.println(DatabaseContextHolder.peekDataSource().get());
			form19Repository.saveAll(response.getForm19());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@PostMapping("/form14")
	public boolean migrateform14(@RequestBody Response response) {
		try {
			dataSourceKeeper.setDataSourceEchwadi(response.getDbName(), response.getSchemaName());
			System.out.println(DatabaseContextHolder.peekDataSource().get());
			form14Repository.saveAll(response.getForm14());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@PostMapping("/form15")
	public boolean migrateform15(@RequestBody Response response) {
		try {
			dataSourceKeeper.setDataSourceEchwadi(response.getDbName(), response.getSchemaName());
			System.out.println(DatabaseContextHolder.peekDataSource().get());
			form15Repository.saveAll(response.getForm15());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	//	@PostMapping("/persistDivisionDistrictTalukaCount")
	//	@ResponseStatus(HttpStatus.OK)
	//	public void persistDivisionDistrictTalukaCount(@RequestBody Response response) {
	//		try {
	//			divisionDistrictTalukaCountRepository.saveAll(response.getDivisionDistrictTalukaCount());
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//	}

	//	@PostMapping("/persistDivisionDistrictTalukaCount")
	//	@WithDatabase(DataSourceType.echmst)
	//	public ResponseEntity<?> getDivisionDistrictTalukaCount() {
	//		try {
	//			List<Object[]> talukaCountLst=mVillageMapperRepository.getTalukaCount();
	//			List<DivisionDistrictTalukaCount> divisionDistrictTalukaCountLst=talukaCountLst.stream().map(obj->{
	//				DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
	//				divisionDistrictTalukaCount.setDivisionCode(obj[0].toString());
	//				divisionDistrictTalukaCount.setDistrictCode(obj[0].toString());
	//				divisionDistrictTalukaCount.setTalukaCode(obj[0].toString());
	//				divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[0].toString()));
	//				divisionDistrictTalukaCount.setTotalPhase1Villages(Long.valueOf(obj[0].toString()));
	//				return divisionDistrictTalukaCount;
	//			}).collect(Collectors.toList());
	//			divisionDistrictTalukaCountRepository.saveAll(divisionDistrictTalukaCountLst);
	//			return ResponseEntity.ok("Success");
	//		} catch (Exception ex) {
	//			ex.printStackTrace();
	//			return ResponseEntity.ok("Failure");
	//		}
	//	}
	
	
	
//pre
	@PostMapping("/adminDashboard")
	public List<DivisionDistrictTalukaCount> adminDashboard(@RequestParam String divisionCode){
		List<Object[]> result=new ArrayList<>();
		if(!divisionCode.isEmpty()) {
			 result=divisionDistrictTalukaCountRepository.getByDivisionCodePre(divisionCode);
			return result.stream().map(obj->{
				DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
				divisionDistrictTalukaCount.setDistrictCode(obj[0].toString());
				divisionDistrictTalukaCount.setDistrictName(obj[1].toString());
				divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[2].toString()));
				divisionDistrictTalukaCount.setTotalPhase1Villages(Long.valueOf(obj[3]==null? "0": obj[3].toString()));
				divisionDistrictTalukaCount.setTotalPhase1Login(Long.valueOf(obj[4].toString()));
				divisionDistrictTalukaCount.setTotalEntry(Long.valueOf(obj[5]==null? "0": obj[5].toString()));
				divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[6].toString()));
				divisionDistrictTalukaCount.setTotalOnlineVillagesCount(Long.valueOf(obj[7].toString()));
				divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[8]==null? "0": obj[8].toString()));
				divisionDistrictTalukaCount.setTotalKhataNumbers(Long.valueOf(obj[9]==null? "0": obj[9].toString()));
				return divisionDistrictTalukaCount;
			}).collect(Collectors.toList());			
		}else {
			result=divisionDistrictTalukaCountRepository.getByAllDivisionCodePre();
			return result.stream().map(obj->{
				DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
				divisionDistrictTalukaCount.setDivisionCode(obj[0].toString());
				divisionDistrictTalukaCount.setDivisionName(obj[1].toString());
				divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[2]==null? "0": obj[2].toString()));
				divisionDistrictTalukaCount.setTotalPhase1Villages(Long.valueOf(obj[3]==null? "0": obj[3].toString()));
				divisionDistrictTalukaCount.setTotalPhase1Login(Long.valueOf(obj[4]==null? "0": obj[4].toString()));
				divisionDistrictTalukaCount.setTotalEntry(Long.valueOf(obj[5]==null? "0": obj[5].toString()));
				divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[6]==null? "0": obj[6].toString()));
				divisionDistrictTalukaCount.setTotalOnlineVillagesCount(Long.valueOf(obj[7]==null? "0": obj[7].toString()));
				divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[8]==null? "0": obj[8].toString()));
				divisionDistrictTalukaCount.setTotalKhataNumbers(Long.valueOf(obj[9]==null? "0": obj[9].toString()));
				return divisionDistrictTalukaCount;
			}).collect(Collectors.toList());			
		}
		
	}
	
	
	
	//new 2024-25
	@PostMapping("/adminDashboardNew")
	public List<DivisionDistrictTalukaCount> adminDashboardNew(@RequestParam String divisionCode){
		List<Object[]> result=new ArrayList<>();
		if(!divisionCode.isEmpty()) {
			 result=divisionDistrictTalukaCountRepository.getByDivisionCode(divisionCode);
			return result.stream().map(obj->{
				DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
				divisionDistrictTalukaCount.setDistrictCode(obj[0].toString());
				divisionDistrictTalukaCount.setDistrictName(obj[1].toString());
				divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[2].toString()));
				divisionDistrictTalukaCount.setTotalPhase1Villages(Long.valueOf(obj[3]==null? "0": obj[3].toString()));
				divisionDistrictTalukaCount.setTotalPhase1Login(Long.valueOf(obj[4].toString()));
				divisionDistrictTalukaCount.setTotalEntry(Long.valueOf(obj[5]==null? "0": obj[5].toString()));
				divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[6].toString()));
				divisionDistrictTalukaCount.setTotalOnlineVillagesCount(Long.valueOf(obj[7].toString()));
				divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[8]==null? "0": obj[8].toString()));
				divisionDistrictTalukaCount.setTotalKhataNumbers(Long.valueOf(obj[9]==null? "0": obj[9].toString()));
				return divisionDistrictTalukaCount;
			}).collect(Collectors.toList());			
		}else {
			result=divisionDistrictTalukaCountRepository.getByAllDivisionCode();
			return result.stream().map(obj->{
				DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
				divisionDistrictTalukaCount.setDivisionCode(obj[0].toString());
				divisionDistrictTalukaCount.setDivisionName(obj[1].toString());
				divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[2]==null? "0": obj[2].toString()));
				divisionDistrictTalukaCount.setTotalPhase1Villages(Long.valueOf(obj[3]==null? "0": obj[3].toString()));
				divisionDistrictTalukaCount.setTotalPhase1Login(Long.valueOf(obj[4]==null? "0": obj[4].toString()));
				divisionDistrictTalukaCount.setTotalEntry(Long.valueOf(obj[5]==null? "0": obj[5].toString()));
				divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[6]==null? "0": obj[6].toString()));
				divisionDistrictTalukaCount.setTotalOnlineVillagesCount(Long.valueOf(obj[7]==null? "0": obj[7].toString()));
				divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[8]==null? "0": obj[8].toString()));
				divisionDistrictTalukaCount.setTotalKhataNumbers(Long.valueOf(obj[9]==null? "0": obj[9].toString()));
				return divisionDistrictTalukaCount;
			}).collect(Collectors.toList());			
		}
		
	}
	
	
	
	//Dyslr changes 26-06-2024 start
	
	 //for division
	// MIS भूमी उपाधीक्षक प्रगती अहवाल (DYSLR) grid
			@PostMapping("/dyslrmistableTab4")
			public List<DivisionDistrictTalukaCount> dyslrmistable(@RequestParam String divisionCode){
				List<Object[]> result=new ArrayList<>();
				if(!divisionCode.isEmpty()) {
					 result=divisionDistrictTalukaCountRepository.getByDivisionCodeforDYSLR(divisionCode);
					return result.stream().map(obj->{
						DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
						divisionDistrictTalukaCount.setDivisionCode(obj[0].toString());
						divisionDistrictTalukaCount.setDistrictCode(obj[1].toString());
						divisionDistrictTalukaCount.setDistrictName(obj[2].toString());
						divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[3].toString()));
						divisionDistrictTalukaCount.setTotalDyslrWorkCount(Long.valueOf(obj[4]==null? "0": obj[4].toString()));
						divisionDistrictTalukaCount.setDyslrWorkCompletedCount(Long.valueOf(obj[5]==null? "0": obj[5].toString()));
						divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[6].toString()));
						divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[7]==null? "0": obj[7].toString()));
						//newly added for abstract 
						divisionDistrictTalukaCount.setDyslrAdbstractAddedCount(Long.valueOf(obj[8]==null? "0": obj[8].toString()));
						return divisionDistrictTalukaCount;
					}).collect(Collectors.toList());			
				}else {
					result=divisionDistrictTalukaCountRepository.getByAllDivisionCodeforDYSLR();
					return result.stream().map(obj->{
						DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
						divisionDistrictTalukaCount.setDivisionCode(obj[0].toString());
						divisionDistrictTalukaCount.setDivisionName(obj[1].toString());
						divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[2]==null? "0": obj[2].toString()));
						divisionDistrictTalukaCount.setTotalDyslrWorkCount(Long.valueOf(obj[3]==null? "0": obj[3].toString()));
						divisionDistrictTalukaCount.setDyslrWorkCompletedCount(Long.valueOf(obj[4]==null? "0": obj[4].toString()));
						divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[5]==null? "0": obj[5].toString()));
						divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[6]==null? "0": obj[6].toString()));
						divisionDistrictTalukaCount.setDyslrAdbstractAddedCount(Long.valueOf(obj[7]==null? "0": obj[7].toString()));
						return divisionDistrictTalukaCount;
					}).collect(Collectors.toList());			
				}
				
			}
			
			

			
			//for District
			//total MIS DYSLR
			@PostMapping("/dyslrmistableTotalTab4")
			public List<DivisionDistrictTalukaCount> dyslrmistableCountTab4(@RequestParam String divisionCode,
					@RequestParam String revenueYear){
				List<Object[]> result=new ArrayList<>();
				if(!divisionCode.isEmpty()) {
					
					

					 result=divisionDistrictTalukaCountRepository.getTotalByDivisionCodeforDYSLR(divisionCode);
					return result.stream().map(obj->{
						DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
						//divisionDistrictTalukaCount.setDivisionCode(obj[0].toString());
						divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[0].toString()));
						divisionDistrictTalukaCount.setTotalDyslrWorkCount(Long.valueOf(obj[1]==null? "0": obj[1].toString()));
						divisionDistrictTalukaCount.setDyslrWorkCompletedCount(Long.valueOf(obj[2]==null? "0": obj[2].toString()));
						divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[3]==null? "0": obj[3].toString()));
						divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[4]==null? "0": obj[4].toString()));
						divisionDistrictTalukaCount.setDyslrAdbstractAddedCount(Long.valueOf(obj[5]==null? "0": obj[5].toString()));
						return divisionDistrictTalukaCount;
					}).collect(Collectors.toList());			
				}else {
					
					
					
					
					result=divisionDistrictTalukaCountRepository.getTotalByAllDivisionCodeforDYSLR();
					return result.stream().map(obj->{
						DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
						divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[0]==null? "0": obj[0].toString()));
						divisionDistrictTalukaCount.setTotalDyslrWorkCount(Long.valueOf(obj[1]==null? "0": obj[1].toString()));
						divisionDistrictTalukaCount.setDyslrWorkCompletedCount(Long.valueOf(obj[2]==null? "0": obj[2].toString()));
						divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[3]==null? "0": obj[3].toString()));
						divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[4]==null? "0": obj[4].toString()));
						divisionDistrictTalukaCount.setDyslrAdbstractAddedCount(Long.valueOf(obj[5]==null? "0": obj[5].toString()));
						return divisionDistrictTalukaCount;
					}).collect(Collectors.toList());			
				}
				
			}
			
			

			///////////////////////////////////////////////////////////////
		    @PostMapping("/adminDashboardTalukaDetailTab4")
					public List<DivisionDistrictTalukaCount> adminDashboar_ddTab4 (@RequestParam String divisionCode,@RequestParam String districtCode){
						List<Object[]> result=new ArrayList<>();
						if(!districtCode.isEmpty()) {
							 result=divisionDistrictTalukaCountRepository.getByTalukaNameAndCodeTab4(divisionCode, districtCode);
							return result.stream().map(obj->{
								DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
								divisionDistrictTalukaCount.setDistrictCode(obj[0].toString());
								divisionDistrictTalukaCount.setDistrictName(obj[1].toString());
								divisionDistrictTalukaCount.setTalukaCode(obj[2].toString());
								divisionDistrictTalukaCount.setTalukaName(obj[3].toString());
								
								divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[4].toString()));				
							
								divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[5].toString()));
								divisionDistrictTalukaCount.setTotalDyslrWorkCount(Long.valueOf(obj[6].toString()));
								divisionDistrictTalukaCount.setDyslrWorkCompletedCount(Long.valueOf(obj[7].toString()));
								divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[8].toString()));
								divisionDistrictTalukaCount.setDyslrAdbstractAddedCount(Long.valueOf(obj[9].toString()));
								
								return divisionDistrictTalukaCount;
							}).collect(Collectors.toList());			
						}else {
							result=divisionDistrictTalukaCountRepository.getByAllDivisionCodeforDYSLR();
							return result.stream().map(obj->{
								DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
								divisionDistrictTalukaCount.setDivisionCode(obj[0].toString());
								divisionDistrictTalukaCount.setDivisionName(obj[1].toString());
								divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[2]==null? "0": obj[2].toString()));
								divisionDistrictTalukaCount.setTotalDyslrWorkCount(Long.valueOf(obj[3]==null? "0": obj[3].toString()));
								divisionDistrictTalukaCount.setDyslrWorkCompletedCount(Long.valueOf(obj[4]==null? "0": obj[4].toString()));
								divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[5]==null? "0": obj[5].toString()));
								divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[6]==null? "0": obj[6].toString()));
								divisionDistrictTalukaCount.setDyslrAdbstractAddedCount(Long.valueOf(obj[7]==null? "0": obj[7].toString()));
								return divisionDistrictTalukaCount;
							}).collect(Collectors.toList());	
						}
						
					}
			
			
			
			/*---------------------End of Dyslr changes 24-06-2024 ---------------------------*/
//new 2024-25
	@PostMapping("/getDivisionCount")
	public DivisionDistrictTalukaCount getDivisionCount(@RequestParam String divisionCode){
		List<Object[]> result=new ArrayList<>();
		if(!divisionCode.isEmpty()) {
		  result=divisionDistrictTalukaCountRepository.getTotalByDivisionCode(divisionCode);
			return result.stream().map(obj->{
				DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
				divisionDistrictTalukaCount.setDivisionCode(obj[0].toString());
				divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[1].toString()));
				divisionDistrictTalukaCount.setTotalPhase1Villages(Long.valueOf(obj[2]==null? "0": obj[2].toString()));
				divisionDistrictTalukaCount.setTotalPhase1Login(Long.valueOf(obj[3].toString()));
				divisionDistrictTalukaCount.setTotalEntry(Long.valueOf(obj[4]==null? "0": obj[4].toString()));
				divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[5]==null? "0": obj[5].toString()));
				divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[6]==null? "0": obj[6].toString()));
				divisionDistrictTalukaCount.setTotalOnlineVillagesCount(Long.valueOf(obj[7].toString()));
				divisionDistrictTalukaCount.setTotalKhataNumbers(Long.valueOf(obj[8]==null? "0": obj[8].toString()));
				return divisionDistrictTalukaCount;
			}).collect(Collectors.toList()).get(0);			
		}else {
			result=divisionDistrictTalukaCountRepository.getTotalByAllDivisionCode();
			return result.stream().map(obj->{
				DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
				divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[0].toString()));
				divisionDistrictTalukaCount.setTotalPhase1Villages(Long.valueOf(obj[1]==null? "0": obj[1].toString()));
				divisionDistrictTalukaCount.setTotalPhase1Login(Long.valueOf(obj[2].toString()));
				divisionDistrictTalukaCount.setTotalEntry(Long.valueOf(obj[3]==null? "0": obj[3].toString()));
				divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[4]==null? "0": obj[4].toString()));
				divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[5]==null? "0": obj[5].toString()));
				divisionDistrictTalukaCount.setTotalOnlineVillagesCount(Long.valueOf(obj[6].toString()));
				divisionDistrictTalukaCount.setTotalKhataNumbers(Long.valueOf(obj[7]==null? "0": obj[7].toString()));
				return divisionDistrictTalukaCount;
			}).collect(Collectors.toList()).get(0);	
		}
		
	}

	//Pre 2023-24
	
	@PostMapping("/getDivisionCountPre")
	public DivisionDistrictTalukaCount getDivisionCountPre(@RequestParam String divisionCode){
		List<Object[]> result=new ArrayList<>();
		if(!divisionCode.isEmpty()) {
		  result=divisionDistrictTalukaCountRepository.getTotalByDivisionCodePre(divisionCode);
			return result.stream().map(obj->{
				DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
				divisionDistrictTalukaCount.setDivisionCode(obj[0].toString());
				divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[1].toString()));
				divisionDistrictTalukaCount.setTotalPhase1Villages(Long.valueOf(obj[2]==null? "0": obj[2].toString()));
				divisionDistrictTalukaCount.setTotalPhase1Login(Long.valueOf(obj[3].toString()));
				divisionDistrictTalukaCount.setTotalEntry(Long.valueOf(obj[4]==null? "0": obj[4].toString()));
				divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[5]==null? "0": obj[5].toString()));
				divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[6]==null? "0": obj[6].toString()));
				divisionDistrictTalukaCount.setTotalOnlineVillagesCount(Long.valueOf(obj[7].toString()));
				divisionDistrictTalukaCount.setTotalKhataNumbers(Long.valueOf(obj[8]==null? "0": obj[8].toString()));
				return divisionDistrictTalukaCount;
			}).collect(Collectors.toList()).get(0);			
		}else {
			result=divisionDistrictTalukaCountRepository.getTotalByAllDivisionCodePre();
			return result.stream().map(obj->{
				DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
				divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[0].toString()));
				divisionDistrictTalukaCount.setTotalPhase1Villages(Long.valueOf(obj[1]==null? "0": obj[1].toString()));
				divisionDistrictTalukaCount.setTotalPhase1Login(Long.valueOf(obj[2].toString()));
				divisionDistrictTalukaCount.setTotalEntry(Long.valueOf(obj[3]==null? "0": obj[3].toString()));
				divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[4]==null? "0": obj[4].toString()));
				divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[5]==null? "0": obj[5].toString()));
				divisionDistrictTalukaCount.setTotalOnlineVillagesCount(Long.valueOf(obj[6].toString()));
				divisionDistrictTalukaCount.setTotalKhataNumbers(Long.valueOf(obj[7]==null? "0": obj[7].toString()));
				return divisionDistrictTalukaCount;
			}).collect(Collectors.toList()).get(0);	
		}
		
	}

	
	
	//Updated MIS By vipl
	//2024-25
	  @PostMapping("/adminDashboardUpdatedMIS")
		public List<DivisionDistrictTalukaCount> adminDashboardUpdatedMIS(@RequestParam String divisionCode){
			List<Object[]> result=new ArrayList<>();
			if(!divisionCode.isEmpty()) {
				 result=divisionDistrictTalukaCountRepository.getByDivisionCodeUpdatedMis(divisionCode);
				return result.stream().map(obj->{
					DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
					divisionDistrictTalukaCount.setDistrictCode(obj[0].toString());
					divisionDistrictTalukaCount.setDistrictName(obj[1].toString());
					divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[2].toString()));
					divisionDistrictTalukaCount.setTotalEntry(Long.valueOf(obj[3]==null? "0": obj[3].toString()));
					divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[4].toString()));
					divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[5]==null? "0": obj[5].toString()));
					divisionDistrictTalukaCount.setTotalGaonNamunaCount(Long.valueOf(obj[6]==null? "0": obj[6].toString()));
					divisionDistrictTalukaCount.setTotalDemandCompletedCount(Long.valueOf(obj[7]==null? "0": obj[7].toString()));
					divisionDistrictTalukaCount.setVasuliWithoutDemandCount(Long.valueOf(obj[8]==null? "0": obj[8].toString()));
					divisionDistrictTalukaCount.setVasuliCompletedCount(Long.valueOf(obj[9]==null? "0": obj[9].toString()));
					divisionDistrictTalukaCount.setDemandCount(Long.valueOf(obj[10]==null? "0": obj[10].toString()));
					divisionDistrictTalukaCount.setTotalOfSevenEight();
					
					//for mis
					divisionDistrictTalukaCount.setTotalDemandJm(Long.valueOf(obj[11]==null? "0": obj[11].toString()));
					
					divisionDistrictTalukaCount.setTotalDemandSakirn(Long.valueOf(obj[12]==null? "0": obj[12].toString()));
					
					divisionDistrictTalukaCount.setTotalCollectedJm(Long.valueOf(obj[13]==null? "0": obj[13].toString()));
					
					divisionDistrictTalukaCount.setTotalCollectedSankirn(Long.valueOf(obj[14]==null? "0": obj[14].toString()));
					

					
					return divisionDistrictTalukaCount;
				}).collect(Collectors.toList());			
			}else {
				result=divisionDistrictTalukaCountRepository.getByAllDivisionCodeUpdatedMIs();
				return result.stream().map(obj->{
					DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
					divisionDistrictTalukaCount.setDivisionCode(obj[0].toString());
					divisionDistrictTalukaCount.setDivisionName(obj[1].toString());
					divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[2]==null? "0": obj[2].toString()));
					divisionDistrictTalukaCount.setTotalEntry(Long.valueOf(obj[3]==null? "0": obj[3].toString()));
					divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[4]==null? "0": obj[4].toString()));
					divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[5]==null? "0": obj[5].toString()));
					divisionDistrictTalukaCount.setTotalGaonNamunaCount(Long.valueOf(obj[6]==null? "0": obj[6].toString()));
					divisionDistrictTalukaCount.setTotalDemandCompletedCount(Long.valueOf(obj[7]==null? "0": obj[7].toString()));
					divisionDistrictTalukaCount.setVasuliWithoutDemandCount(Long.valueOf(obj[8]==null? "0": obj[8].toString()));
					divisionDistrictTalukaCount.setVasuliCompletedCount(Long.valueOf(obj[9]==null? "0": obj[9].toString()));
					divisionDistrictTalukaCount.setDemandCount(Long.valueOf(obj[10]==null? "0": obj[10].toString()));
					divisionDistrictTalukaCount.setTotalOfSevenEight();
					
					//for Mis
					divisionDistrictTalukaCount.setTotalDemandJm(Long.valueOf(obj[11]==null? "0": obj[11].toString()));
					
					divisionDistrictTalukaCount.setTotalDemandSakirn(Long.valueOf(obj[12]==null? "0": obj[12].toString()));
					
					divisionDistrictTalukaCount.setTotalCollectedJm(Long.valueOf(obj[13]==null? "0": obj[13].toString()));
					
					divisionDistrictTalukaCount.setTotalCollectedSankirn(Long.valueOf(obj[14]==null? "0": obj[14].toString()));
				
					
					
					return divisionDistrictTalukaCount;
				}).collect(Collectors.toList());			
			}
			
		}
	 //2023-24 
	  @PostMapping("/adminDashboardUpdatedMISPre")
		public List<DivisionDistrictTalukaCount> adminDashboardUpdatedMIS_2023(@RequestParam String divisionCode, @RequestParam String revenueYear ){
			List<Object[]> result=new ArrayList<>();
			if(!divisionCode.isEmpty()) {
				
				//pre year changes for MIS
				String[] revSingleString1=revenueYear.split("-");
				String yearName=revSingleString1[0];			
				System.out.println("YearName----------------------------:"+yearName);
				
				
				 result=divisionDistrictTalukaCountRepository.getByDivisionCodeUpdatedMisPre(divisionCode,yearName);
				return result.stream().map(obj->{
					DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
					divisionDistrictTalukaCount.setDistrictCode(obj[0].toString());
					divisionDistrictTalukaCount.setDistrictName(obj[1].toString());
					divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[2].toString()));
					divisionDistrictTalukaCount.setTotalEntry(Long.valueOf(obj[3]==null? "0": obj[3].toString()));
					divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[4].toString()));
					divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[5]==null? "0": obj[5].toString()));
					divisionDistrictTalukaCount.setTotalGaonNamunaCount(Long.valueOf(obj[6]==null? "0": obj[6].toString()));
					divisionDistrictTalukaCount.setTotalDemandCompletedCount(Long.valueOf(obj[7]==null? "0": obj[7].toString()));
					divisionDistrictTalukaCount.setVasuliWithoutDemandCount(Long.valueOf(obj[8]==null? "0": obj[8].toString()));
					divisionDistrictTalukaCount.setVasuliCompletedCount(Long.valueOf(obj[9]==null? "0": obj[9].toString()));
					divisionDistrictTalukaCount.setDemandCount(Long.valueOf(obj[10]==null? "0": obj[10].toString()));
					divisionDistrictTalukaCount.setTotalOfSevenEight();
					//new line for mis 02/11/2026

					divisionDistrictTalukaCount.setTotalDemandJm(Long.valueOf(obj[11]==null? "0": obj[11].toString()));

					
					divisionDistrictTalukaCount.setTotalDemandSakirn(Long.valueOf(obj[12]==null? "0": obj[12].toString()));
					
					divisionDistrictTalukaCount.setTotalCollectedJm(Long.valueOf(obj[13]==null? "0": obj[13].toString()));
					
					divisionDistrictTalukaCount.setTotalCollectedSankirn(Long.valueOf(obj[14]==null? "0": obj[14].toString()));
				
					return divisionDistrictTalukaCount;
					
					
				}).collect(Collectors.toList());			
			}else {
				
				//pre year changes for MIS
				String[] revSingleString1=revenueYear.split("-");
				String yearName=revSingleString1[0];			
				System.out.println("YearName----------------------------:"+yearName);
				
				result=divisionDistrictTalukaCountRepository.getByAllDivisionCodeUpdatedMIsPre(yearName);
				return result.stream().map(obj->{
					DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
					divisionDistrictTalukaCount.setDivisionCode(obj[0].toString());
					divisionDistrictTalukaCount.setDivisionName(obj[1].toString());
					divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[2]==null? "0": obj[2].toString()));
					divisionDistrictTalukaCount.setTotalEntry(Long.valueOf(obj[3]==null? "0": obj[3].toString()));
					divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[4]==null? "0": obj[4].toString()));
					divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[5]==null? "0": obj[5].toString()));
					divisionDistrictTalukaCount.setTotalGaonNamunaCount(Long.valueOf(obj[6]==null? "0": obj[6].toString()));
					divisionDistrictTalukaCount.setTotalDemandCompletedCount(Long.valueOf(obj[7]==null? "0": obj[7].toString()));
					divisionDistrictTalukaCount.setVasuliWithoutDemandCount(Long.valueOf(obj[8]==null? "0": obj[8].toString()));
					divisionDistrictTalukaCount.setVasuliCompletedCount(Long.valueOf(obj[9]==null? "0": obj[9].toString()));
					divisionDistrictTalukaCount.setDemandCount(Long.valueOf(obj[10]==null? "0": obj[10].toString()));
					divisionDistrictTalukaCount.setTotalOfSevenEight();
					//new line for mis 02/11/2026

					divisionDistrictTalukaCount.setTotalDemandJm(Long.valueOf(obj[11]==null? "0": obj[11].toString()));

					
					divisionDistrictTalukaCount.setTotalDemandSakirn(Long.valueOf(obj[12]==null? "0": obj[12].toString()));
					
					divisionDistrictTalukaCount.setTotalCollectedJm(Long.valueOf(obj[13]==null? "0": obj[13].toString()));
					
					divisionDistrictTalukaCount.setTotalCollectedSankirn(Long.valueOf(obj[14]==null? "0": obj[14].toString()));
				
					return divisionDistrictTalukaCount;
				}).collect(Collectors.toList());			
			}
			
		}
	  
		//2024-25
		@PostMapping("/getDivisionCountUpdatedMIS")
		public DivisionDistrictTalukaCount getDivisionCountUpdatedMIS(@RequestParam String divisionCode){
			List<Object[]> result=new ArrayList<>();
			if(!divisionCode.isEmpty()) {
			  result=divisionDistrictTalukaCountRepository.getTotalByDivisionCodeUpdatedMIS(divisionCode);
				return result.stream().map(obj->{
					DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
					//divisionDistrictTalukaCount.setDivisionCode(obj[0].toString());
					divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[0].toString()));
					divisionDistrictTalukaCount.setTotalEntry(Long.valueOf(obj[1]==null? "0": obj[1].toString()));
					divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[2].toString()));
					divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[3]==null? "0": obj[3].toString()));
					divisionDistrictTalukaCount.setTotalGaonNamunaCount(Long.valueOf(obj[4]==null? "0": obj[4].toString()));
					divisionDistrictTalukaCount.setTotalDemandCompletedCount(Long.valueOf(obj[5]==null? "0": obj[5].toString()));
					divisionDistrictTalukaCount.setVasuliWithoutDemandCount(Long.valueOf(obj[6]==null? "0": obj[6].toString()));
					divisionDistrictTalukaCount.setVasuliCompletedCount(Long.valueOf(obj[7]==null? "0": obj[7].toString()));
					divisionDistrictTalukaCount.setDemandCount(Long.valueOf(obj[8]==null? "0": obj[8].toString()));
					divisionDistrictTalukaCount.setTotalOfSevenEight();
					
					// for mis 25 March 2025 - Tushar
					divisionDistrictTalukaCount.setTotalDemandJm(Long.valueOf(obj[9]==null? "0": obj[9].toString()));
					divisionDistrictTalukaCount.setTotalDemandSakirn(Long.valueOf(obj[10]==null? "0": obj[10].toString()));
					divisionDistrictTalukaCount.setTotalCollectedJm(Long.valueOf(obj[11]==null? "0": obj[11].toString()));
					divisionDistrictTalukaCount.setTotalCollectedSankirn(Long.valueOf(obj[12]==null? "0": obj[12].toString()));
					
					
					
					return divisionDistrictTalukaCount;
				}).collect(Collectors.toList()).get(0);			
			}else {
				result=divisionDistrictTalukaCountRepository.getTotalByAllDivisionCodeUpdatedMIS();
				return result.stream().map(obj->{
					DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
					divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[0].toString()));
					divisionDistrictTalukaCount.setTotalEntry(Long.valueOf(obj[1]==null? "0": obj[1].toString()));
					divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[2]==null? "0": obj[2].toString()));
					divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[3]==null? "0": obj[3].toString()));
					divisionDistrictTalukaCount.setTotalGaonNamunaCount(Long.valueOf(obj[4]==null? "0": obj[4].toString()));
					divisionDistrictTalukaCount.setTotalDemandCompletedCount(Long.valueOf(obj[5]==null? "0": obj[5].toString()));
					divisionDistrictTalukaCount.setVasuliWithoutDemandCount(Long.valueOf(obj[6]==null? "0": obj[6].toString()));
					divisionDistrictTalukaCount.setVasuliCompletedCount(Long.valueOf(obj[7]==null? "0": obj[7].toString()));
					divisionDistrictTalukaCount.setDemandCount(Long.valueOf(obj[8]==null? "0": obj[8].toString()));
					divisionDistrictTalukaCount.setTotalOfSevenEight();
					
					//totalDemandJm
					divisionDistrictTalukaCount.setTotalDemandJm(Long.valueOf(obj[9]==null? "0": obj[9].toString()));
					divisionDistrictTalukaCount.setTotalDemandSakirn(Long.valueOf(obj[10]==null? "0": obj[10].toString()));
					divisionDistrictTalukaCount.setTotalCollectedJm(Long.valueOf(obj[11]==null? "0": obj[11].toString()));
					divisionDistrictTalukaCount.setTotalCollectedSankirn(Long.valueOf(obj[12]==null? "0": obj[12].toString()));
				
					
					return divisionDistrictTalukaCount;
				}).collect(Collectors.toList()).get(0);	
			}
			
		}
		
		
		//2023-24
		@PostMapping("/getDivisionCountUpdatedMISPre")
		public DivisionDistrictTalukaCount getDivisionCountUpdatedMIS_2023(@RequestParam String divisionCode, String revenueYear){
			List<Object[]> result=new ArrayList<>();
			if(!divisionCode.isEmpty()) {
				
				//pre year changes for MIS
				String[] revSingleString1=revenueYear.split("-");
				String yearName=revSingleString1[0];
				
				System.out.println("YearName----------------------------:"+yearName);
				
				
				
			  result=divisionDistrictTalukaCountRepository.getTotalByDivisionCodeUpdatedMISPre(divisionCode, yearName);
				return result.stream().map(obj->{
					
					
					DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
					//divisionDistrictTalukaCount.setDivisionCode(obj[0].toString());
					divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[0].toString()));
					divisionDistrictTalukaCount.setTotalEntry(Long.valueOf(obj[1]==null? "0": obj[1].toString()));
					divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[2].toString()));
					divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[3]==null? "0": obj[3].toString()));
					divisionDistrictTalukaCount.setTotalGaonNamunaCount(Long.valueOf(obj[4]==null? "0": obj[4].toString()));
					divisionDistrictTalukaCount.setTotalDemandCompletedCount(Long.valueOf(obj[5]==null? "0": obj[5].toString()));
					divisionDistrictTalukaCount.setVasuliWithoutDemandCount(Long.valueOf(obj[6]==null? "0": obj[6].toString()));
					divisionDistrictTalukaCount.setVasuliCompletedCount(Long.valueOf(obj[7]==null? "0": obj[7].toString()));
					divisionDistrictTalukaCount.setDemandCount(Long.valueOf(obj[8]==null? "0": obj[8].toString()));
					divisionDistrictTalukaCount.setTotalOfSevenEight();
					return divisionDistrictTalukaCount;
				}).collect(Collectors.toList()).get(0);			
			}else {
				

				//pre year changes for MIS
				String[] revSingleString1=revenueYear.split("-");
				String yearName=revSingleString1[0];
				
				result=divisionDistrictTalukaCountRepository.getTotalByAllDivisionCodeUpdatedMISPre(yearName);
				return result.stream().map(obj->{
					DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
					divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[0].toString()));
					divisionDistrictTalukaCount.setTotalEntry(Long.valueOf(obj[1]==null? "0": obj[1].toString()));
					divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[2]==null? "0": obj[2].toString()));
					divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[3]==null? "0": obj[3].toString()));
					divisionDistrictTalukaCount.setTotalGaonNamunaCount(Long.valueOf(obj[4]==null? "0": obj[4].toString()));
					divisionDistrictTalukaCount.setTotalDemandCompletedCount(Long.valueOf(obj[5]==null? "0": obj[5].toString()));
					divisionDistrictTalukaCount.setVasuliWithoutDemandCount(Long.valueOf(obj[6]==null? "0": obj[6].toString()));
					divisionDistrictTalukaCount.setVasuliCompletedCount(Long.valueOf(obj[7]==null? "0": obj[7].toString()));
					divisionDistrictTalukaCount.setDemandCount(Long.valueOf(obj[8]==null? "0": obj[8].toString()));
					divisionDistrictTalukaCount.setTotalOfSevenEight();
					return divisionDistrictTalukaCount;
				}).collect(Collectors.toList()).get(0);	
			}
			
		}
		
		
		//2024-25
		@PostMapping("/adminDashboardTalukaDetail")
			public List<DivisionDistrictTalukaCount> adminDashboar_dd (@RequestParam String divisionCode,@RequestParam String districtCode){
				List<Object[]> result=new ArrayList<>();
				if(!districtCode.isEmpty()) {
					 result=divisionDistrictTalukaCountRepository.getByTalukaNameAndCode(divisionCode, districtCode);
					return result.stream().map(obj->{
						DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
						divisionDistrictTalukaCount.setDistrictCode(obj[0].toString());
						divisionDistrictTalukaCount.setDistrictName(obj[1].toString());
						divisionDistrictTalukaCount.setTalukaCode(obj[2].toString());
						divisionDistrictTalukaCount.setTalukaName(obj[3].toString());
						divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[4].toString()));
						divisionDistrictTalukaCount.setTotalEntry(Long.valueOf(obj[5]==null? "0": obj[5].toString()));
						divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[6].toString()));
						divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[7]==null? "0": obj[7].toString()));
						divisionDistrictTalukaCount.setTotalGaonNamunaCount(Long.valueOf(obj[8].toString()));
						divisionDistrictTalukaCount.setTotalDemandCompletedCount(Long.valueOf(obj[9].toString()));
						divisionDistrictTalukaCount.setVasuliWithoutDemandCount(Long.valueOf(obj[10].toString()));
						divisionDistrictTalukaCount.setVasuliCompletedCount(Long.valueOf(obj[11].toString()));
						divisionDistrictTalukaCount.setDemandCount(Long.valueOf(obj[12].toString()));
						divisionDistrictTalukaCount.setTotalOfSevenEight();
						
						//for Mis Vasuli
						divisionDistrictTalukaCount.setTotalDemandJm(Long.valueOf(obj[13]==null? "0": obj[13].toString()));
						divisionDistrictTalukaCount.setTotalDemandSakirn(Long.valueOf(obj[14]==null? "0": obj[14].toString()));
						divisionDistrictTalukaCount.setTotalCollectedJm(Long.valueOf(obj[15]==null? "0": obj[15].toString()));
						divisionDistrictTalukaCount.setTotalCollectedSankirn(Long.valueOf(obj[16]==null? "0": obj[16].toString()));
						

						
						return divisionDistrictTalukaCount;
					}).collect(Collectors.toList());
				}else {
					result=divisionDistrictTalukaCountRepository.getByAllDivisionCodeUpdatedMIs();
					return result.stream().map(obj->{
						DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
						divisionDistrictTalukaCount.setDivisionCode(obj[0].toString());
						divisionDistrictTalukaCount.setDivisionName(obj[1].toString());
						divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[2]==null? "0": obj[2].toString()));
						divisionDistrictTalukaCount.setTotalEntry(Long.valueOf(obj[3]==null? "0": obj[3].toString()));
						divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[4]==null? "0": obj[4].toString()));
						divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[5]==null? "0": obj[5].toString()));
						divisionDistrictTalukaCount.setTotalGaonNamunaCount(Long.valueOf(obj[6]==null? "0": obj[6].toString()));
						divisionDistrictTalukaCount.setTotalDemandCompletedCount(Long.valueOf(obj[7]==null? "0": obj[7].toString()));
						divisionDistrictTalukaCount.setVasuliWithoutDemandCount(Long.valueOf(obj[8]==null? "0": obj[8].toString()));
						divisionDistrictTalukaCount.setVasuliCompletedCount(Long.valueOf(obj[9]==null? "0": obj[9].toString()));
						divisionDistrictTalukaCount.setDemandCount(Long.valueOf(obj[10].toString()));	
						divisionDistrictTalukaCount.setTotalOfSevenEight();
						
						//for mis vasuli
						divisionDistrictTalukaCount.setTotalDemandJm(Long.valueOf(obj[11]==null? "0": obj[11].toString()));
						divisionDistrictTalukaCount.setTotalDemandSakirn(Long.valueOf(obj[12]==null? "0": obj[12].toString()));
						divisionDistrictTalukaCount.setTotalCollectedJm(Long.valueOf(obj[13]==null? "0": obj[13].toString()));
						divisionDistrictTalukaCount.setTotalCollectedSankirn(Long.valueOf(obj[14]==null? "0": obj[14].toString()));
				
						
						return divisionDistrictTalukaCount;
					}).collect(Collectors.toList());	
				
				}
			}
	
		
		//2023-24
		@PostMapping("/adminDashboardTalukaDetailPre")
		public List<DivisionDistrictTalukaCount> adminDashboar_dd_2023 (@RequestParam String divisionCode,@RequestParam String districtCode, @RequestParam String revenueYear){
			List<Object[]> result=new ArrayList<>();
			if(!districtCode.isEmpty()) {
				
				//pre year changes for MIS
				String[] revSingleString1=revenueYear.split("-");
				String yearName=revSingleString1[0];			
				System.out.println("YearName----------------------------:"+yearName);
				
				
				 result=divisionDistrictTalukaCountRepository.getByTalukaNameAndCodePre(divisionCode, districtCode, yearName);
				return result.stream().map(obj->{
					DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
					divisionDistrictTalukaCount.setDistrictCode(obj[0].toString());
					divisionDistrictTalukaCount.setDistrictName(obj[1].toString());
					divisionDistrictTalukaCount.setTalukaCode(obj[2].toString());
					divisionDistrictTalukaCount.setTalukaName(obj[3].toString());
					divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[4].toString()));
					divisionDistrictTalukaCount.setTotalEntry(Long.valueOf(obj[5]==null? "0": obj[5].toString()));
					divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[6].toString()));
					divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[7]==null? "0": obj[7].toString()));
					divisionDistrictTalukaCount.setTotalGaonNamunaCount(Long.valueOf(obj[8].toString()));
					divisionDistrictTalukaCount.setTotalDemandCompletedCount(Long.valueOf(obj[9].toString()));
					divisionDistrictTalukaCount.setVasuliWithoutDemandCount(Long.valueOf(obj[10].toString()));
					divisionDistrictTalukaCount.setVasuliCompletedCount(Long.valueOf(obj[11].toString()));
					divisionDistrictTalukaCount.setDemandCount(Long.valueOf(obj[12].toString()));
					divisionDistrictTalukaCount.setTotalOfSevenEight();
					return divisionDistrictTalukaCount;
				}).collect(Collectors.toList());
			}else {
				
				//pre year changes for MIS
				String[] revSingleString1=revenueYear.split("-");
				String yearName=revSingleString1[0];			
				System.out.println("YearName----------------------------:"+yearName);
				result=divisionDistrictTalukaCountRepository.getByAllDivisionCodeUpdatedMIsPre(yearName);
				return result.stream().map(obj->{
					DivisionDistrictTalukaCount divisionDistrictTalukaCount=new DivisionDistrictTalukaCount();
					divisionDistrictTalukaCount.setDivisionCode(obj[0].toString());
					divisionDistrictTalukaCount.setDivisionName(obj[1].toString());
					divisionDistrictTalukaCount.setTotalVillages(Long.valueOf(obj[2]==null? "0": obj[2].toString()));
					divisionDistrictTalukaCount.setTotalEntry(Long.valueOf(obj[3]==null? "0": obj[3].toString()));
					divisionDistrictTalukaCount.setTotalTaluka(Long.valueOf(obj[4]==null? "0": obj[4].toString()));
					divisionDistrictTalukaCount.setDistrictsCount(Long.valueOf(obj[5]==null? "0": obj[5].toString()));
					divisionDistrictTalukaCount.setTotalGaonNamunaCount(Long.valueOf(obj[6]==null? "0": obj[6].toString()));
					divisionDistrictTalukaCount.setTotalDemandCompletedCount(Long.valueOf(obj[7]==null? "0": obj[7].toString()));
					divisionDistrictTalukaCount.setVasuliWithoutDemandCount(Long.valueOf(obj[8]==null? "0": obj[8].toString()));
					divisionDistrictTalukaCount.setVasuliCompletedCount(Long.valueOf(obj[9]==null? "0": obj[9].toString()));
					divisionDistrictTalukaCount.setDemandCount(Long.valueOf(obj[10].toString()));	
					divisionDistrictTalukaCount.setTotalOfSevenEight();
					return divisionDistrictTalukaCount;
				}).collect(Collectors.toList());	
			
			}
		}
		@PostMapping("/getDyslrStarted")
		public List<LgdMapperMis> getDyslrStarted(@RequestParam String districtCode, @RequestParam String talukaCode) {
			try {
				List<Object[]> lst = divisionDistrictTalukaCountRepository.getDyslrStarted(districtCode, talukaCode);
				return lst.stream().map(obj -> {
					LgdMapperMis lgd = new LgdMapperMis();

					lgd.setDistrictNameMarathi(obj[0].toString());

					lgd.setTalukaMameMarathi(obj[1].toString());

					lgd.setLgdVillageMame(obj[2].toString());

					return lgd;
				}).collect(Collectors.toList());

			} catch (Exception e) {
				return null;
			}
		}

		
//	 ____________++++++++++++getDemandGeneratedVillageCount+++++++++++++++++________________-

		@PostMapping("/getDemandGeneratedVillageCount")
		public List<LGDMapper> getDemandGeneratedVillageCount(@RequestParam String districtCode,
				@RequestParam String talukaCode) {
			try {
				List<Object[]> lst = divisionDistrictTalukaCountRepository.getDemandGeneratedVillageCount(districtCode, talukaCode);
				return lst.stream().map(obj -> {
					LGDMapper lgd = new LGDMapper();

					lgd.setDistrictNameMarathi(obj[0].toString());

					lgd.setTalukaMameMarathi(obj[1].toString());

					lgd.setLgdVillageMame(obj[2].toString());

					return lgd;
				}).collect(Collectors.toList());

			} catch (Exception e) {
				return null;
			}
		}
		
		//For current
				@PostMapping("/getCountWithOutSingleDemandGeneration")
				public List<LGDMapper> getVasooliCountWithOutSingleDemandGeneration(@RequestParam String districtCode,
						@RequestParam String talukaCode) {
					try {
						List<Object[]> lst = divisionDistrictTalukaCountRepository.getVasooliCountWithOutSingleDemandGeneration(districtCode, talukaCode);
						return lst.stream().map(obj -> {
							LGDMapper lgd = new LGDMapper();

							lgd.setDistrictNameMarathi(obj[0].toString());

							lgd.setTalukaMameMarathi(obj[1].toString());

							lgd.setLgdVillageMame(obj[2].toString());

							return lgd;
						}).collect(Collectors.toList());

					} catch (Exception e) {
						return null;
					}
				}

				//for pre
				
				@PostMapping("/getCountWithOutSingleDemandGenerationPre")
				public List<LGDMapper> getVasooliCountWithOutSingleDemandGenerationPre(@RequestParam String districtCode,
						@RequestParam String talukaCode) {
					try {
						List<Object[]> lst = divisionDistrictTalukaCountRepository.getVasooliCountWithOutSingleDemandGenerationPre(districtCode, talukaCode);
						return lst.stream().map(obj -> {
							LGDMapper lgd = new LGDMapper();

							lgd.setDistrictNameMarathi(obj[0].toString());

							lgd.setTalukaMameMarathi(obj[1].toString());

							lgd.setLgdVillageMame(obj[2].toString());

							return lgd;
						}).collect(Collectors.toList());

					} catch (Exception e) {
						return null;
					}
				}
				

		// ____________+++++++++++++getVasooliCountWithOutDemand++++++++++++++++________________-

		//-----8 Column
		@PostMapping("/getVasooliCountWithOutDemand")
		public List<LGDMapper> getVasooliCountWithOutDemand(@RequestParam String districtCode,
				@RequestParam String talukaCode) {
			try {
				List<Object[]> lst = divisionDistrictTalukaCountRepository.getVasooliCountWithOutDemand(districtCode, talukaCode);
				return lst.stream().map(obj -> {
					LGDMapper lgd = new LGDMapper();

					lgd.setDistrictNameMarathi(obj[0].toString());

					lgd.setTalukaMameMarathi(obj[1].toString());

					lgd.setLgdVillageMame(obj[2].toString());

					return lgd;
				}).collect(Collectors.toList());

			} catch (Exception e) {
				return null;
			}
		}
		// ____________++++++++++++++getVasooliCompletedVillageCount+++++++++++++++________________-
		//---9 column

		@PostMapping("/getVasooliCompletedVillageCount")
		public List<LGDMapper> getVasooliCompletedVillageCount(@RequestParam String districtCode,
				@RequestParam String talukaCode) {
			try {
				List<Object[]> lst = divisionDistrictTalukaCountRepository.getTotalVasooliCount(districtCode, talukaCode);
				return lst.stream().map(obj -> {
					LGDMapper lgd = new LGDMapper();

					lgd.setDistrictNameMarathi(obj[0].toString());

					lgd.setTalukaMameMarathi(obj[1].toString());

					lgd.setLgdVillageMame(obj[2].toString());

					return lgd;
				}).collect(Collectors.toList());

			} catch (Exception e) {
				return null;
			}
		}
		
		// ____________++++++++++++++is_gaon_namuna_Completed+++++++++++++++________________-
		// ------6 th Column
			
		@PostMapping("/gaonNamunaCompleted")
		public List<LGDMapper> getGaonNamunaCompleted(@RequestParam String districtCode,
				@RequestParam String talukaCode) {
			try {
				List<Object[]> lst = divisionDistrictTalukaCountRepository.getGaonNamunaCompleted(districtCode, talukaCode);
				return lst.stream().map(obj -> {
					LGDMapper lgd = new LGDMapper();

					lgd.setDistrictNameMarathi(obj[0].toString());

					lgd.setTalukaMameMarathi(obj[1].toString());

					lgd.setLgdVillageMame(obj[2].toString());

					return lgd;
				}).collect(Collectors.toList());

			} catch (Exception e) {
				return null;
			}
		}

	@GetMapping("/getDivisionList")
	public List<Division> divisionLst(){
		try {
			List<Object[]> lst=divisionRepository.getDivision();
			return lst.stream().map(obj->{
				Division division=new Division();
				division.setDivisionCode(obj[0].toString());
				division.setDivisionName(obj[1].toString());
				return division;
			}).collect(Collectors.toList());	

		} catch (Exception e) {
			return null;
		}
	}
	
	@GetMapping("/getDivisionLst")
	public List<Division> divisionListst(){
		try {
			List<Object[]> lst=divisionDemoRepository.getDivision();
			return lst.stream().map(obj->{
				Division division=new Division();
				division.setDivisionCode(obj[0].toString());
				division.setDivisionName(obj[1].toString());
				return division;
			}).collect(Collectors.toList());	

		} catch (Exception e) {
			return null;
		}
	}
	
	@PostMapping("/getphase1villagecount")
	public List<LGDMapper> phase1VillageCount(@RequestParam String districtCode){
		try {
			List<Object[]> lst=lgdMapperRepository.getphase1villagecount(districtCode);
			return lst.stream().map(obj->{
				LGDMapper lgd=new LGDMapper();
				lgd.setDistrictCode(obj[0].toString());
				lgd.setDistrictNameMarathi(obj[1].toString());
				lgd.setTalukaCode(obj[2].toString());
				lgd.setTalukaMameMarathi(obj[3].toString());
				lgd.setCCode(obj[4].toString());
				lgd.setLgdVillageMame(obj[5].toString());
				lgd.setDivisionCode(obj[6].toString());
				lgd.setTalukaCount(obj[7].toString());
				return lgd;
			}).collect(Collectors.toList());	

		} catch (Exception e) {
			return null;
		}
	}
	
	@PostMapping("/getphase1loginvillagecount")
	public List<LGDMapper> phase1LoginVillageCount(@RequestParam String districtCode){
		try {
			List<Object[]> lst=lgdMapperRepository.getphase1LoginVillageCount(districtCode);
			return lst.stream().map(obj->{
				LGDMapper lgd=new LGDMapper();
				lgd.setDistrictCode(obj[0].toString());
				lgd.setDistrictNameMarathi(obj[1].toString());
				lgd.setTalukaCode(obj[2].toString());
				lgd.setTalukaMameMarathi(obj[3].toString());
				lgd.setCCode(obj[4].toString());
				lgd.setLgdVillageMame(obj[5].toString());
				lgd.setDivisionCode(obj[6].toString());
				lgd.setTalukaCount(obj[7].toString());
				return lgd;
			}).collect(Collectors.toList());	

		} catch (Exception e) {
			return null;
		}
	}

	@PostMapping("/getOnlineRecoveryVillageCount")
	public List<DashboardSupervisoryDtl> onlineRecoveryVillageCount(@RequestParam String districtCode){
		try {
			List<Object[]> lst=dashboardSupervisoryDtlRepository.getOnlineRecoveryVillageCount(districtCode);
			return lst.stream().map(obj->{
				DashboardSupervisoryDtl lgd=new DashboardSupervisoryDtl();
				lgd.setCCode(obj[0].toString());
				lgd.setDistrictCode(obj[1].toString());
				lgd.setDistrictName(obj[2].toString());
				lgd.setTalukaCode(obj[3].toString());
				lgd.setTalukaName(obj[4].toString());
				lgd.setVillageName(obj[5].toString());
				lgd.setDivisionCode(obj[6].toString());
				lgd.setTotalTalukas(obj[7].toString());
				return lgd;
			}).collect(Collectors.toList());	

		} catch (Exception e) {
			return null;
		}
	}
	
	
	@GetMapping("/getLiveCount")
	public boolean getLiveCount() {
		try {
			scheduler.getLiveCount();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}

	@GetMapping("/getLoginCount")
	public boolean getLoginCount() {
		try {
			scheduler.getLoginCount();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@GetMapping("/getMISDataCount")
	public boolean getMISDataCount() {
		try {
			scheduler.getMISDataCount();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@GetMapping("/getChallanGeneratedCount")
	public boolean getChallanGeneratedCount() {
		try {
			scheduler.getChallanGeneratedCount();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@GetMapping("/getDyslrDataCount")
	public boolean getDyslrDataCount() {
		try {
			scheduler.getDyslrDataCount();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@GetMapping("/getTotalVasuliCompletedCount/{talukaCode}/{districtCode}")
	public boolean getTotalVasuliCompletedCount(@PathVariable String talukaCode, @PathVariable String districtCode) {
		try {
			scheduler.getTotalVasuliCompletedCount(talukaCode,districtCode);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}
	
	
	@GetMapping("/getKhataCountForMis/{talukaCode}/{districtCode}")
	public boolean getKhataCountForMis(@PathVariable String talukaCode, @PathVariable String districtCode) {
		try {
			scheduler.getKhataCountForMis(talukaCode,districtCode);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@GetMapping("/getDemandGenerationCount/{talukaCode}/{districtCode}")
	public boolean getDemandGenerationCount(@PathVariable String talukaCode, @PathVariable String districtCode) {
		try {
			scheduler.getDemandGenerationCount(talukaCode,districtCode);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@GetMapping("/staticDashboard")
	public List<DashBoardStatic> getDashBoardStatic(@RequestParam String code, String year ){
		List<Object[]> result=new ArrayList<>();
		  result=dashboardStatic.getStaticMIS(code,year);
			return result.stream().map(obj->{
				DashBoardStatic dbs=new DashBoardStatic();
				dbs.setVibhagjillaha1(obj[0].toString());
				dbs.setJilhanchisankhya2(obj[1].toString());
				dbs.setTalukasankhya3(obj[2].toString());
				dbs.setTotalgaonsankhya4(obj[3].toString());
				dbs.setBhagonegoansankhya5(obj[4].toString());
				dbs.setLogingoansankhya6(obj[5].toString());
				dbs.setVasuligoansankhya7(obj[6].toString());
				dbs.setTotalbharlalihiti8(obj[7].toString());
				dbs.setYear(obj[8].toString());
				dbs.setCode(obj[9].toString());
				dbs.setSequence(obj[10].toString());
				
				return dbs ;
			}).collect(Collectors.toList());	
			}	
	
	@GetMapping("/getGaonNamumeCompleted/{talukaCode}/{districtCode}")
	public boolean getGaonNamumeCompleted(@PathVariable String talukaCode, @PathVariable String districtCode) {
		try {
			scheduler.getGaonNamumeCompleted(talukaCode,districtCode);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}
	
	
//	@GetMapping("/getDataToMakeLive")
//		public boolean getDataToMakeLive() {
//			try {
//				scheduler.getDataToMakeLive();
//				return true;
//			} catch (Exception e) {
//				e.printStackTrace();
//				return false;
//			}
//		}
	
//	@PostMapping("/persistDataToMakeLive")
//	public boolean persistDataToMakeLive() {
//		try {
//			scheduler.persistDataToMakeLive();
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}

//	@Scheduled(cron = "00 45 23 * * ?")
//	@PostMapping("/persistDataToMakeLive")
//	public ResponseEntity<?> persistDataToMakeLive(@RequestBody Response response){
//		try{
//			lgdMapperRepository.saveAll(response.getLgdMapperDao().stream().map(r->{
//				LGDMapper mapper =new LGDMapper();
//				BeanUtils.copyProperties(r, mapper);
//				return mapper;
//			}).collect(Collectors.toList()));
//			return ResponseEntity.ok("Data Persisted Successfully");
//		}catch(Exception e) {
//			e.printStackTrace();
//			return  (ResponseEntity<?>) ResponseEntity.internalServerError();
//		}
//	}
	
	
	
	//	@PostMapping("/persistTalukasCount")
	//	public void persistLiveCount(@RequestBody Response response) {
	//		try {
	//
	//			System.out.println("Donee...!!");
	//			log.info("Data Dumped...!!!");
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//	}
	
	
//new Api for Dyslr Mis   28/09/2026

@GetMapping("/getForm1AbstractReportDyslrListModified")
	public List<Form1AbstractDyslrDao> getForm1AbstractReportDyslrListModified(
	        @RequestParam Integer districtCode,
	        @RequestParam Integer talukaCode,
	        Locale locale,
	        HttpServletRequest request) {

	    return form1DyslrService.getForm1AbstractReportDyslrListModified(
	            districtCode, talukaCode, locale, request);
	}
//new Api for Dyslr Mis same as above  10/09/2026
@GetMapping("/getForm1AbstractReportDyslrMIS")
public List<Form1AbstractDyslrDao> getNewForm1DyslrReportM(@RequestParam Integer districtCode, @RequestParam Integer talukaCode, Locale locale, HttpServletRequest request) {
    return form1DyslrService.getNewForm1DyslrReportM(districtCode, talukaCode, locale, request);
}


}