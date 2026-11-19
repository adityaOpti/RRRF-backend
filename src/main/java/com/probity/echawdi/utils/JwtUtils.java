package com.probity.echawdi.utils;

import java.time.Instant;
import io.jsonwebtoken.Claims;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.probity.echawdi.entity.MVillageMapper;
import com.probity.echawdi.entity.Token;
import com.probity.echawdi.repository.LgdMapperRepository;
import com.probity.echawdi.repository.MVillageMapperRepository;
import com.probity.echawdi.repository.TokenRepsitory;
import com.probity.echawdi.service.TokenService;
import com.probity.echawdi.serviceImpl.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * @author Bhagyashri Lolage
 *
 */
@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${echawdi.app.jwtSecret}")
  private String jwtSecret;
  @Value("${echawdi.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  @Autowired
  private TokenRepsitory tokenRepsitory;
  
  
  @Autowired
  private TokenService tokenService;
  
  @Autowired
  private MVillageMapperRepository mVillageMapperRepository;
  
  @Autowired
  private LgdMapperRepository lgdMapperRepository;
  public String generate7BJwtToken(String username) {
	    return Jwts.builder()
	            .setSubject(username)
	            .claim("loginType", "7B")
	            .setIssuedAt(new Date())
	            .setExpiration(
	                    new Date(System.currentTimeMillis() + jwtExpirationMs))
	            .signWith(SignatureAlgorithm.HS512, jwtSecret)
	            .compact();
	}

	public String getLoginTypeFromJwtToken(String token) {
	    Claims claims = Jwts.parser()
	            .setSigningKey(jwtSecret)
	            .parseClaimsJws(token)
	            .getBody();

	    return claims.get("loginType", String.class);
	}
	

  public String generateJwtToken(UserDetailsImpl userPrincipal) {
    return generateTokenFromUsername(userPrincipal.getUsername());
  }

  public String generateTokenFromUsername(String username) {
  return Jwts.builder().setSubject(username).setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }

  public Date getExpiryTime(String token) {
	  return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration();
  }

  public Date getGenrateTime(String token) {
	  return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getIssuedAt();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }
  
  
  
  
  
  	//creating a refresh token
  	public String generateRefreshToken() {
		return UUID.randomUUID().toString();
	}
	
  
  	
  	 public void storeToken(
 	        String accessToken,
 	        String refreshToken) {

 	    Token token = new Token();

 	    token.setServarthId(
 	            getUserNameFromJwtToken(accessToken));

 	    // ACCESS TOKEN
 	    token.setAccessToken(accessToken);

 	    token.setGeneratedTime(
 	            getGenrateTime(accessToken));

 	    token.setExpiryTime(
 	            getExpiryTime(accessToken));
 	    
 	    System.out.println("Here I am storing refreshToken Into database----: "+refreshToken);

 	    // REFRESH TOKEN
 	    token.setRefreshToken(refreshToken);

 	    token.setRefreshExpiryDate(
 	            Instant.now().plus(8, ChronoUnit.HOURS));
 	    
// 	   token.setRefreshExpiryDate(
// 		        Instant.now().plus(2, ChronoUnit.MINUTES));

 	    token.setLoggedOut(false);

 	    token.setRevoked(false);

 	    tokenRepsitory.save(token);
 	}
   

 /* public void storeToken(String jwt) {
  		Token token=new Token();
  		token.setServarthId(getUserNameFromJwtToken(jwt));
  		token.setGeneratedTime(getGenrateTime(jwt));	
  		token.setExpiryTime(getExpiryTime(jwt));
  		token.setToken(jwt);
  		tokenRepsitory.save(token);
  }*/
  
  
  
  
  public Optional<Token> findByToken(String token) {
	  //return tokenRepsitory.findByToken(token);
	  
	  return tokenRepsitory.findByAccessToken(token);
  }
  
  
	//new method for instant logOut
	public boolean isTokenActive(String accessToken) {
		
	   
	    
	    Optional<Token> tokenOptional = tokenService.findByAccessToken(accessToken);
	    
	    

	    if (!tokenOptional.isPresent()) {
	        return false;
	    }

	    System.out.println("into logout instant method");
	    
	    Token token = tokenOptional.get();

	    System.out.println("logout instant method : "+token);
	    
	    return !Boolean.TRUE.equals(
	                token.getLoggedOut())
	            &&
	           !Boolean.TRUE.equals(
	                token.getRevoked());
	}

  
	

	
  public Token verifyExpiration(Token token) {
    if (token.getExpiryTime().compareTo(new Date()) < 0) {
      tokenRepsitory.delete(token);
    }
    return token;
  }
  
  	@Transactional
   public void deleteByUserName(String username) {
  		
  		boolean var = tokenRepsitory.existsByServarthId(username);
  		System.out.println("result of if condition : "+var);
  		
	   if (tokenRepsitory.existsByServarthId(username)) {
		   System.out.println("Into the if condition-------------- deletebyUsername----");
		   tokenRepsitory.deleteByServarthId(username);
		}
   }

   public HashMap<String, String> getDbs(String districtCode,String talukaCode) {
	   //System.out.println("distCode:"+districtCode+"\ntalukaCode:"+talukaCode);
	   MVillageMapper mVillageMapper=mVillageMapperRepository.findByDistrictCodeAndTalukaCode(districtCode,talukaCode);  
		//System.out.println("mVillageMapper:-->"+mVillageMapper);
	   	HashMap<String, String> infoMap=new HashMap<>();
	   	if(mVillageMapper!=null) {
	   		
		infoMap.put("mhrDbName1", mVillageMapper.getMhrDbName());
		infoMap.put("mhrSchemaName1", mVillageMapper.getMhrSchemaName());
		infoMap.put("mhrHost1", mVillageMapper.getMhrHost());
		
		String encryptedMhrHost = AESEncryptionAndDecryption.encrypt(mVillageMapper.getMhrHost(),CommonConstants.secretKey);
		infoMap.put("mhrHost", encryptedMhrHost);
		
		String encryptedMhrDbName = AESEncryptionAndDecryption.encrypt(mVillageMapper.getMhrDbName(),CommonConstants.secretKey);
		infoMap.put("mhrDbName", encryptedMhrDbName);
		
		String encryptedMhrSchemaName = AESEncryptionAndDecryption.encrypt(mVillageMapper.getMhrSchemaName(),CommonConstants.secretKey);
		infoMap.put("mhrSchemaName", encryptedMhrSchemaName);
		
		String encryptedEchHost = AESEncryptionAndDecryption.encrypt(mVillageMapper.getEchHost(),CommonConstants.secretKey);
		infoMap.put("echHost", encryptedEchHost);
		
		String encryptedEchDbName = AESEncryptionAndDecryption.encrypt(mVillageMapper.getEchDbName(),CommonConstants.secretKey);
		infoMap.put("echDbName", encryptedEchDbName);
		
		String encryptedEchSchemaName = AESEncryptionAndDecryption.encrypt(mVillageMapper.getEchSchemaName(),CommonConstants.secretKey);
		infoMap.put("echSchemaName", encryptedEchSchemaName);
		
	   	}
		return infoMap;
   }
   
   public String getDistrictName(String host,String dbName,String schemaName,String districtCode) {
	   String district;
	   district=mVillageMapperRepository.findDistrictcode(host,dbName,schemaName,districtCode,CommonConstants.mhrorUser,CommonConstants.mhrorPassword);
	   //return district.toString();
	   return district;
   } 
   
   
   public String getDistrictNameForDDE(String districtCode) {
	   String district;
	   district=lgdMapperRepository.getDistrictNameMarathi(districtCode);
	   //return district.toString();
	   return district;
   } 
   
  
   
}


//package com.probity.echawdi.utils;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Optional;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.transaction.Transactional;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import com.probity.echawdi.entity.MVillageMapper;
//import com.probity.echawdi.entity.Token;
//import com.probity.echawdi.repository.MVillageMapperRepository;
//import com.probity.echawdi.repository.TokenRepsitory;
//import com.probity.echawdi.serviceImpl.UserDetailsImpl;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.SignatureException;
//import io.jsonwebtoken.UnsupportedJwtException;
//
//@Component
//public class JwtUtils {
//
//    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
//
//    @Value("${echawdi.app.jwtSecret}")
//    private String jwtSecret;
//
//    @Value("${echawdi.app.jwtExpirationMs}")
//    private int jwtExpirationMs;
//
//    @Autowired
//    private TokenRepsitory tokenRepsitory;
//
//    @Autowired
//    private MVillageMapperRepository mVillageMapperRepository;
//
//    public String generateJwtToken(UserDetailsImpl userPrincipal) {
//        return generateTokenFromUsername(userPrincipal.getUsername());
//    }
//
//    public String generateTokenFromUsername(String username) {
//        String token = Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
//                .signWith(SignatureAlgorithm.HS512, jwtSecret)
//                .compact();
//
//        System.out.println("GENERATED TOKEN: " + token);
//        return token;
//    }
//
//    public String getUserNameFromJwtToken(String token) {
//        return Jwts.parser()
//                .setSigningKey(jwtSecret)
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//
//    public Date getExpiryTime(String token) {
//        return Jwts.parser()
//                .setSigningKey(jwtSecret)
//                .parseClaimsJws(token)
//                .getBody()
//                .getExpiration();
//    }
//    
//    
//   
//    public boolean isTokenPresent(String token) {
//
//        boolean exists = tokenRepsitory.existsByToken(token);
//        return exists;
//    }
//   
//  
//    
//    @Transactional
//    public void deleteToken(String token) {
//        tokenRepsitory.deleteByToken(token);
//    }
//    
//    public String getJwtFromHeader(HttpServletRequest request) {
//        String header = request.getHeader("Authorization");
//        if (header != null && header.startsWith("Bearer ")) {
//            return header.substring(7);
//        }
//        return null;
//    }
//    
//    public Date getGenrateTime(String token) {
//        return Jwts.parser()
//                .setSigningKey(jwtSecret)
//                .parseClaimsJws(token)
//                .getBody()
//                .getIssuedAt();
//    }
//
//    public boolean validateJwtToken(String authToken) {
//        try {
//            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
//            return true;
//
//        } catch (SignatureException e) {
//            logger.error("Invalid JWT signature: {}", e.getMessage());
//
//        } catch (MalformedJwtException e) {
//            logger.error("Invalid JWT token: {}", e.getMessage());
//
//        } catch (ExpiredJwtException e) {
//            logger.error("JWT token is expired: {}", e.getMessage());
//
//        } catch (UnsupportedJwtException e) {
//            logger.error("JWT token is unsupported: {}", e.getMessage());
//
//        } catch (IllegalArgumentException e) {
//            logger.error("JWT claims string is empty: {}", e.getMessage());
//        }
//
//        return false;
//    }
//
//    public void storeToken(String token, String username, Date expiry) {
//        Token t = new Token();
//        t.setToken(token);
//        t.setServarthId(username);
//        t.setExpiryTime(expiry);
//
//        System.out.println("SAVING TOKEN: " + token);
//
//        tokenRepsitory.save(t);
//    }
//
//    public Optional<Token> findByToken(String token) {
//        return tokenRepsitory.findByToken(token);
//    }
//    
//   
//
//    public Token verifyExpiration(Token token) {
//
//        if (token.getExpiryTime().before(new Date())) {
//            throw new RuntimeException("Token Expired");
//        }
//
//        return token;
//    }
//   
//    public void deleteByUserName(String username) {
//        if (tokenRepsitory.existsByServarthId(username)) {
//            tokenRepsitory.deleteByServarthId(username);
//        }
//    }
//
//    
//    
//    public HashMap<String, String> getDbs(String districtCode,String talukaCode) {
// 	   //System.out.println("distCode:"+districtCode+"\ntalukaCode:"+talukaCode);
// 	   MVillageMapper mVillageMapper=mVillageMapperRepository.findByDistrictCodeAndTalukaCode(districtCode,talukaCode);  
// 		//System.out.println("mVillageMapper:-->"+mVillageMapper);
// 	   	HashMap<String, String> infoMap=new HashMap<>();
// 	   	if(mVillageMapper!=null) {
// 		infoMap.put("mhrDbName1", mVillageMapper.getMhrDbName());
// 		infoMap.put("mhrSchemaName1", mVillageMapper.getMhrSchemaName());
// 		infoMap.put("mhrHost1", mVillageMapper.getMhrHost());
// 		
// 		String encryptedMhrHost = AESEncryptionAndDecryption.encrypt(mVillageMapper.getMhrHost(),CommonConstants.secretKey);
// 		infoMap.put("mhrHost", encryptedMhrHost);
// 		
// 		String encryptedMhrDbName = AESEncryptionAndDecryption.encrypt(mVillageMapper.getMhrDbName(),CommonConstants.secretKey);
// 		infoMap.put("mhrDbName", encryptedMhrDbName);
// 		
// 		String encryptedMhrSchemaName = AESEncryptionAndDecryption.encrypt(mVillageMapper.getMhrSchemaName(),CommonConstants.secretKey);
// 		infoMap.put("mhrSchemaName", encryptedMhrSchemaName);
// 		
// 		String encryptedEchHost = AESEncryptionAndDecryption.encrypt(mVillageMapper.getEchHost(),CommonConstants.secretKey);
// 		infoMap.put("echHost", encryptedEchHost);
// 		
// 		String encryptedEchDbName = AESEncryptionAndDecryption.encrypt(mVillageMapper.getEchDbName(),CommonConstants.secretKey);
// 		infoMap.put("echDbName", encryptedEchDbName);
// 		
// 		String encryptedEchSchemaName = AESEncryptionAndDecryption.encrypt(mVillageMapper.getEchSchemaName(),CommonConstants.secretKey);
// 		infoMap.put("echSchemaName", encryptedEchSchemaName);
// 		
// 	   	}
// 		return infoMap;
//    }
//
//    
//    public String getDistrictName(String host, String dbName, String schemaName, String districtCode) {
//
//        String district = mVillageMapperRepository.findDistrictcode(
//                host,
//                dbName,
//                schemaName,
//                districtCode,
//                CommonConstants.mhrorUser,
//                CommonConstants.mhrorPassword
//        );
//
//        return district != null ? district.toString() : null;
//    }
//}