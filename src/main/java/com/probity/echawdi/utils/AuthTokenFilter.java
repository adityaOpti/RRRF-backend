	package com.probity.echawdi.utils;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.probity.echawdi.config.WithDatabase;
import com.probity.echawdi.entity.Token;
import com.probity.echawdi.repository.TokenRepsitory;
import com.probity.echawdi.service.TokenService;
import com.probity.echawdi.serviceImpl.UserDetailsService7BImpl;
import com.probity.echawdi.serviceImpl.UserDetailsServiceImpl;


/**
 * @author Bhagyashri Lolage
 *
 */
public class AuthTokenFilter extends OncePerRequestFilter {
	
	@Autowired
	private UserDetailsService7BImpl userDetailsService7B; 
	@Autowired
	private JwtUtils jwtUtils;
	
	 @Autowired
	  private TokenService tokenService;
	
	@Autowired
	private TokenRepsitory tokenRepsitory;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
	
	static class FilteredRequest extends HttpServletRequestWrapper {

        public FilteredRequest(ServletRequest request) {
            super((HttpServletRequest)request);
        }
        
        @Override
        public String getHeader(String headerName) {
        	String value = super.getHeader(headerName);
        	
        	if(headerName != null && headerName.equals("mhrHost")) {
        		if(value != null) {
        			return AESEncryptionAndDecryption.decrypt(value, CommonConstants.secretKey) ;
        		}
        	}
        	
        	if(headerName != null && headerName.equals("mhrDbName")) {
        		if(value != null) {
        			return AESEncryptionAndDecryption.decrypt(value, CommonConstants.secretKey) ;
        		}
        	}
        	
        	if(headerName != null && headerName.equals("mhrSchemaName")) {
        		if(value != null) {
        			return AESEncryptionAndDecryption.decrypt(value, CommonConstants.secretKey) ;
        		}
        	}
        	
        	if(headerName != null && headerName.equals("echHost")) {
        		if(value != null) {
        			return AESEncryptionAndDecryption.decrypt(value, CommonConstants.secretKey) ;
        		}
        	}
        	
        	if(headerName != null && headerName.equals("echDbName")) {
        		if(value != null) {
        			return AESEncryptionAndDecryption.decrypt(value, CommonConstants.secretKey) ;
        		}
        	}
        	
        	if(headerName != null && headerName.equals("echSchemaName")) {
        		if(value != null) {
        			return AESEncryptionAndDecryption.decrypt(value, CommonConstants.secretKey) ;
        		}
        	}
        	
			return value;
        }

    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = parseJwt(request);
			
			   System.out.println("Into the authtokenfileter-----");
			   
			   System.out.println("JWT ==================== " + jwt);


			   Optional<Token> token = tokenService.findByAccessToken(jwt);

				    System.out.println("Token Present = " + token.isPresent());

				
			   
//			   Optional<Token> token =
//					   tokenRepsitory.findByAccessToken(jwt);

			   System.out.println("Token Present ============ " + token.isPresent());
			   
			   System.out.println("Request URI = " + request.getRequestURI());

			   if(token.isPresent()) {
			       System.out.println("Revoked = " + token.get().getRevoked());
			       System.out.println("LoggedOut = " + token.get().getLoggedOut());
			   }
			   
			   
			System.out.println(" ======= END OF THE LOGS FOR NOW JUST ADDED=============");
			
		
			
			
		//	if (jwt != null && jwtUtils.validateJwtToken(jwt) ) {
				
				//New changes for login
				if (jwt != null
				        && jwtUtils.validateJwtToken(jwt)
				        && jwtUtils.isTokenActive(jwt)				) {
				
				String username = jwtUtils.getUserNameFromJwtToken(jwt);
				String loginType = jwtUtils.getLoginTypeFromJwtToken(jwt);
				UserDetails userDetails;
				if ("7B".equals(loginType)) {

					userDetails = userDetailsService7B.loadUserByUsername(username);

				} else {

				    // Preserve the existing Echawdi authentication flow.
					userDetails = userDetailsService.loadUserByUsername(username);
				}

				UsernamePasswordAuthenticationToken authentication =
				        new UsernamePasswordAuthenticationToken(
				        		userDetails,
				                null,
				                userDetails.getAuthorities()
				        );

			//	UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			//	UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			
				e.printStackTrace();
			    logger.error("Cannot set user authentication", e);
			//logger.error("Cannot set user authentication: {}", e.getMessage());
		}
		
		// Added for content security header 22/062026
		//response.setHeader("Referrer-Policy", "no-referrer");
		response.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");

		
		filterChain.doFilter(new FilteredRequest(request), response);
	}
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
	    String path = request.getServletPath();

	    return "/api/7b/signup".equals(path)
	            || "/api/7b/login".equals(path);
	}
	
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}
		return null;
	}
}
