/**
 * 
 */
package com.probity.echawdi.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.probity.echawdi.dao.RevenueYearDao;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;

/**
 * @author Bhagyashri Lolage
 *
 */

public interface  RevenueYearService {
	
	 MessageResponse save(RevenueYearDao revenueYearDao,Locale locale,HttpServletRequest request);
//
//	 void updateRevenueYear(RevenueYearDao revenueYearDao,Locale locale);
//	 
	 void deleteRevenueYearById(long id,Locale locale,HttpServletRequest request);
    
     Response getRevenueYearData(HttpServletRequest request,Locale locale);
		
//     RevenueYearDao getRevenueYearById(long id,Locale locale);
}