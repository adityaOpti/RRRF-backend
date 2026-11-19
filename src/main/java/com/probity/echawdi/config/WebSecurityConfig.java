

package com.probity.echawdi.config;

/*import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.probity.echawdi.exception.CustomAccessDeniedHandler;
import com.probity.echawdi.serviceImpl.UserDetailsServiceImpl;
import com.probity.echawdi.utils.AuthEntryPointJwt;
import com.probity.echawdi.utils.AuthTokenFilter;
import com.probity.echawdi.utils.JwtUtils;*/

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.probity.echawdi.exception.CustomAccessDeniedHandler;
import com.probity.echawdi.serviceImpl.UserDetailsServiceImpl;
import com.probity.echawdi.utils.AuthEntryPointJwt;
import com.probity.echawdi.utils.AuthTokenFilter;
import com.probity.echawdi.utils.JwtUtils;
import org.springframework.http.HttpMethod;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    
    @Autowired
    private JwtUtils jwtUtils;

   
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;
    
    
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    
    
    
    
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }
    
    public void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
        		.userDetailsService((UserDetailsService)this.userDetailsService)
        		.passwordEncoder(this.passwordEncoder());
    }
   
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
	@Autowired
    public PasswordEncoder passwordEncoder() {
        return new com.probity.echawdi.utils.PasswordEncoder();
        
    }
	
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList(
        		"http://localhost:3000",
                "http://localhost:8000",
                "http://localhost:9095",
                "http://115.124.110.193:443",
                "http://localhost:9091",
                "http://localhost:9091/inspection",
                "https://echawadi.mahabhumi.gov.in"
                
        ));

        configuration.setAllowedMethods(Arrays.asList(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS",
                "PATCH"
        ));

        configuration.setAllowedHeaders(Arrays.asList("*"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return  source;
    }
    
//	protected void configure(final HttpSecurity http) throws Exception {
//     http
//     .cors()
//     .and()
//     .csrf().disable()
//     .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
//     .and()
//     .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//     .and()
//     .authorizeRequests().antMatchers("/api/**").permitAll()
//     .antMatchers("/auth/**").permitAll()
//     .antMatchers("/portal/**").permitAll()
//     .antMatchers("/api/**").permitAll()
//     .antMatchers("/api/**").hasAnyRole("TALATHI","COLLECTOR","DYSLR")
////     .antMatchers("/api/**").hasRole("COLLECTOR")
//     .anyRequest().authenticated();
//     http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//	}
//	
	
	
	
	 
	    /*
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	            .cors()
	            .and()
	            .csrf().disable()
	            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
	            .and()
	            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            .and()
	            .authorizeRequests()
	            
	            .antMatchers("/authenticateUserByUsernameAndPassword").permitAll()  // 🔥 Login URL
	            .antMatchers("/auth/**").permitAll()
	            .antMatchers("/portal/**").permitAll()
	            .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
	            
	            .antMatchers("/api/**").authenticated()
	            
	            .anyRequest().authenticated();

	        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	        http.addFilterAfter(roleCheckFilter(), AuthTokenFilter.class);  // Role check after auth
	    }
	    */
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http
	    .headers()
	    //HSTS FIX
        .httpStrictTransportSecurity()
        	.maxAgeInSeconds(31536000)
            .includeSubDomains(true)
        .and()
	     .contentSecurityPolicy( "default-src 'self'; " +                                           //Allow everything only from your server
	    		 	"script-src 'self'; " +
			        "style-src 'self' 'unsafe-inline'; " +                                        //Allow CSS + inline styles
	    		    "img-src 'self' data:; " +                                                      //Allow images from serve0r + base64
	    		    "connect-src 'self' http://localhost:8000 http://localhost:9091 http://localhost:9091/inspection http://localhost:8091 http://115.124.110.193:443 https://echawadi.mahabhumi.gov.in;" +             //Allow API calls only to these
	    		    "font-src 'self'; " +                                                           //Only allow fonts from your server
	    		    "object-src 'none'; " +                                                         //Block plugins (Flash, etc.)
	    		    "frame-ancestors 'none'; " +                                                   //No iframe embedding (Prevent from clickjacking)
	    		    "base-uri 'self';")
	 .and()
	 .frameOptions().deny()
	 .and()
	        .cors()
	        .and()
	        .csrf().disable()
	        .exceptionHandling()
	            .authenticationEntryPoint(unauthorizedHandler)
	            .accessDeniedHandler(accessDeniedHandler())   // 🔥 IMPORTANT
	        .and()
	        .sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()
	        .authorizeRequests()
	        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
	        .antMatchers("/api/7b/signup").permitAll()
	        .antMatchers(
	                "/api/landRevenue/getDropDownsDivision",
	                "/api/landRevenue/getDropDownsDistrict",
	                "/api/landRevenue/getDropDownsTaluka",
	                "/api/landRevenue/getDropDownsVillage"
	            ).permitAll()
	        .antMatchers("/authenticateUserByUsernameAndPassword").permitAll()
	        .antMatchers("/auth/**").permitAll()
	        .antMatchers("/portal/**").permitAll()
	        .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
	      //new line added for 
            .antMatchers("/getForm1AbstractReportDyslrListModified/**").permitAll()
	        .antMatchers("/api/**").authenticated()
	        .anyRequest().authenticated();

	    // ✅ FILTER ORDER (IMPORTANT)
	    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	    http.addFilterAfter(roleCheckFilter(), AuthTokenFilter.class);
	}
	    // ========== ROLE CHECK FILTER ==========
	    @Bean
	    public OncePerRequestFilter roleCheckFilter() {
	        return new OncePerRequestFilter() {

	            @Override
	            protected void doFilterInternal(HttpServletRequest request,
	                                            HttpServletResponse response,
	                                            FilterChain filterChain)
	                                            throws ServletException, IOException {
	            	
	            	

	            	  if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
	            	        filterChain.doFilter(request, response);
	            	        return;
	            	    }

	            		

		            	System.out.println("Inside rolechecker here----------------------");
	            		String requestURI = request.getRequestURI();
	                
		                if (isPublicUrl(requestURI)) {
		                    filterChain.doFilter(request, response);
		                    return;
		                }
	               
	            	
	            	
	               

	                try {
	                    String jwt = getJwtFromRequest(request);

//	                    if (jwt == null || !jwtUtils.validateJwtToken(jwt)) {
//	                        filterChain.doFilter(request, response);
//	                        return;
//	                    }
	                    
	                    
	                    boolean var = jwt == null || !jwtUtils.validateJwtToken(jwt);
	                    System.out.println("if conditon vadidate wali----------"+var);
	                   
	                    if (jwt == null || !jwtUtils.validateJwtToken(jwt)) {
	                    	
	                    	
	                    	System.out.println("inside validate if condition--------------");

	                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	                        response.setContentType("application/json");

	                        response.getWriter().write(
	                            "{ \"message\": \"Session Expired. Please login again.\" }"
	                        );

	                        return;
	                    }

	                    
	                   
	                    String username = jwtUtils.getUserNameFromJwtToken(jwt);
	                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

	                    List<String> userRoles = userDetails.getAuthorities()
	                            .stream()
	                            .map(GrantedAuthority::getAuthority)
	                            .collect(Collectors.toList());
	                    
	                    
	                    

	                    String cleanUri = requestURI.replace("/echawdi", "");
	                    
	                    System.out.println("clean url-----------"+cleanUri+"UserRole ye hai------------"+userRoles);
	                    
	                    boolean var2 = !hasRequiredRole(userRoles, cleanUri);
	                    
	                    System.out.println("has role wali condition----------: "+var2);
	                    
	                    

	                    if (!hasRequiredRole(userRoles, cleanUri)) {
	                    	
	                    	System.out.println("into hasrole if condition-------------------");
	                    	
//	                        throw new org.springframework.security.access.AccessDeniedException(
//	                        
//		         	           "Access Denied for role: " + userRoles
//	                          
//	                        );
	                    	
	                    	customAccessDeniedHandler.handle(
	                    		    request,
	                    		    response,
	                    		    new AccessDeniedException(
	                    		        "Access Denied for role: " + userRoles
	                    		    )
	                    		);
	                    		return;
	                    	
	               
	        	            
	        	            
	                    }

	                    
	                    
//	                    UsernamePasswordAuthenticationToken authentication =
//	                            new UsernamePasswordAuthenticationToken(
//	                                    userDetails,
//	                                    null,
//	                                    userDetails.getAuthorities()
//	                            );
//
//	                    authentication.setDetails(
//	                            new WebAuthenticationDetailsSource().buildDetails(request)
//	                    );
//
//	                    SecurityContextHolder.getContext().setAuthentication(authentication);

	                } catch (Exception e) {
	                   // logger.error("Role Check Error for URI [{}]: {}", request.getRequestURI(), e.getMessage(), e);

	                	logger.error("Role Check Error for URI [{}]: {}");
	                    throw e; // 🔥 VERY IMPORTANT (do not swallow)
	                }

	                filterChain.doFilter(request, response);
	            }
	        };
	    }
	    
	 //   old code 
//	    private boolean isPublicUrl(String uri) {
//	        return uri.equals("/authenticateUserByUsernameAndPassword") ||
//	               uri.startsWith("/auth/") ||
//	               uri.startsWith("/portal/") ||
//	               uri.startsWith("/swagger-ui/") ||
//	               uri.startsWith("/v3/api-docs/");
//	    }

	    //new code 
	    private boolean isPublicUrl(String uri) {

	        String cleanUri = uri.replace("/echawdi", "");

	        return cleanUri.equals("/authenticateUserByUsernameAndPassword") ||
	        		cleanUri.equals("/api/7b/signup") ||
	               cleanUri.startsWith("/auth/") ||
	               cleanUri.startsWith("/portal/") ||
	               cleanUri.startsWith("/swagger-ui/") ||
	               cleanUri.startsWith("/v3/api-docs/") ||
	               cleanUri.equals("/auth/getForm1AbstractReportDyslrListModified")||

		          // 7B signup APIs
		         cleanUri.equals("/api/7b/signup")||
		         cleanUri.equals("/api/7b/otp/send") ||
		         cleanUri.equals("/api/7b/otp/verify") ||
		
		         // Signup form dropdown APIs
		         cleanUri.equals("/api/landRevenue/getDropDownsDivision") ||
		         cleanUri.equals("/api/landRevenue/getDropDownsDistrict") ||
		         cleanUri.equals("/api/landRevenue/getDropDownsTaluka") ||
		         cleanUri.equals("/api/landRevenue/getDropDownsVillage") ;
	    }

	   
	    private String getJwtFromRequest(HttpServletRequest request) {
	        String bearerToken = request.getHeader("Authorization");
	        
	        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
	            return bearerToken.substring(7);
	        }
	        return null;
	    }

	    private boolean hasRequiredRole(List<String> userRoles, String requestURI) {
	        String uri = requestURI.toLowerCase();
	         
	        
	        // 🔥 DYSLR ONLY URLs  
	        if (uri.contains("/dyslr/") || 
	      //      (uri.contains("/reports/") && uri.contains("village-form-1")) ||
//	            (uri.contains("/reports/") && uri.contains("Form1ODC")) ||
//	            (uri.contains("/reports/") && uri.contains("form1Dyslr")) ||
//	            (uri.contains("/reports/") && uri.contains("Form1ODC-Difference")) ||
	           // (uri.contains("/reports/") && uri.contains("Dyslr-Entry-Record")) ||
	           // (uri.contains("/reports/") && uri.contains("Dyslr-Delete-Record")) ||
	            
	        		
	        	
	            uri.contains("/reports/village-form-1") ||
	            uri.contains("/reports/Form1ODC") ||
	            uri.contains("/api/form1Dyslr") ||
	            uri.contains("/reports/Form1ODC-Difference") ||
	            uri.contains("/reports/Dyslr-Entry-Record") ||
	            uri.contains("/Dyslr-Delete-Record") ||
	           
                uri.contains("/form/dyslr-form-akarband-changes/table-form") ||

	            uri.contains("/review/")) {
	        	
	        	System.out.println("Im here in RoleDyslr rolechecker ---------------");
	        	
	            return userRoles.contains("ROLE_DYSLR");
	        }
	        
	        
	       


          
	            // 🔥 TALATHI ONLY URLs 
	            if (uri.contains("/talathi/") ||   
	                (uri.contains("/api/") && uri.contains("landRevenue")) ||
	                (uri.contains("/reports/") && uri.contains("form8")) ||
	                (uri.contains("/reports/") && uri.contains("form7")) ||
	                (uri.contains("/api/") && uri.contains("form15")) ||
	                (uri.contains("/api/") && uri.contains("reports")) ||
	            //    (uri.contains("/api/") && uri.contains("form1")) ||
	            //    uri.contains("getForm1Data") ||
	                (uri.contains("/homepage")) ||
	                uri.contains("/village/save") ||
	                uri.contains("/village/update") ||
	                (uri.contains("/restservice/") && uri.contains("getnirankandcompleted"))) {
	            	
	            	System.out.println("Im here in ROLE_TALATHI rolechecker ---------------");
	            	
	            	System.out.println("ROLE_TALATHI rolechecker condition ---------------"+userRoles.contains("ROLE_TALATHI"));
	            	
	            	// ← FIXED parentheses
	                return userRoles.contains("ROLE_TALATHI");
	            }
	            
	            
	            
	            
//	            if (
//	                (uri.contains("/api/") && uri.contains("form1")) ||
//	                uri.contains("getForm1Data") 
//	               ) {
//	            	
//	            	
//	            	
//	            	// ← FIXED parentheses
//	                return userRoles.contains("ROLE_TALATHI") || userRoles.contains("ROLE_DYSLR");
//	            }
//	        
	            
	         // 🔥 TALATHI & DYSLR URLs 
	            if (
	            	    (uri.contains("/api/") && uri.contains("form1")) ||
	            	    uri.contains("/api/form1/getForm1Data") ||
	            	    uri.contains("/api/restservice/getAllVillageForm") ||
	            	    uri.contains("/api/form17NoKaJaPa/getReportForm17NoKaJaPa") ||
	            	    uri.contains("/api/form1/form1OdcDiff") ||
	            	    uri.contains("/api/landRevenue/getLandRevenueDemandDetails")
	            	) {
	            	
	            	System.out.println("Im here in ROLE_TALATHI  & ROLE_DYSLR rolechecker ---------------");
	            	
	            	System.out.println("ROLE_TALATHI  & ROLE_DYSLR rolechecker condition ---------------"+userRoles.contains("ROLE_TALATHI"));

	            	    return userRoles.contains("ROLE_TALATHI")
	            	            || userRoles.contains("ROLE_DYSLR") || userRoles.contains("ROLE_TAH");
	            	}
	    
//	        // 🔥 DYSLR ONLY URLs  
//	        if (uri.contains("/dyslr/") || 
//	            (uri.contains("/reports/") && uri.contains("dyslr")) ||
//	            (uri.contains("/api/") && uri.contains("form1")) ||
//	            (uri.contains("/api/") && uri.contains("form1Dyslr")) ||
//	            uri.contains("/review/")) {
//	            return userRoles.contains("ROLE_DYSLR");
//	        }
	            
	         
	        
	        // 🔥 COLLECTOR ONLY URLs
	        if (uri.contains("/collector/") || 
	            (uri.contains("/reports/") && uri.contains("collector")) ||
	            uri.contains("/approval/collector") ||
	            uri.contains("/approve") ||
	            uri.contains("/reject")) {
	        	
	        	System.out.println("Im here in ROLE_COLLECTOR rolechecker ---------------");
	        	
	            return userRoles.contains("ROLE_COLLECTOR");
	        }
	        
	        System.out.println("Mai toh sbse nichhheee-------------in rolecheckker--------");
	        
	        return true;
	    }

	    @Bean
	    public org.springframework.security.web.access.AccessDeniedHandler accessDeniedHandler() {
	        return (request, response, accessDeniedException) -> {

	            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	            response.setContentType("application/json;charset=UTF-8");

	            String responseBody = String.format(
	                "{" +
	                "\"status\":403," +
	                "\"error\":\"Forbidden\"," +
	                "\"messageMarathi\":\"प्रवेश नाकारला! तुम्हाला या पृष्ठावर प्रवेश नाही.\"," +
	                "\"messageEnglish\":\"Access Denied! You don't have permission to access this page.\"," +
	                "\"requestedUrl\":\"%s\"," +
	                "\"timestamp\":\"%s\"" +
	                "}",
	                request.getRequestURI(),
	                new java.util.Date().toString()
	            );

	            response.getWriter().write(responseBody);
	        };
	    }
}