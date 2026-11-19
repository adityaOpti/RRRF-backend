package com.probity.echawdi.service;

import java.util.Locale;

import org.springframework.stereotype.Service;

import com.probity.echawdi.dao.MenuDao;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;

/**
 * @author Kumar Harpude
 */

@Service
public interface MenuService {
	
	Response getAllMenuData(int pageNo, int pageSize, String sortBy, String sortDir,Locale locale);

	MenuDao getMenuById(long id, Locale locale);
	
	MessageResponse save(MenuDao MenuDao,Locale locale);
		
	void updateMenu(MenuDao MenuDao,Locale locale);
	
	void deleteMenuById(long id,Locale locale);
	
	Response getRoleBasedMenu(String userName);

}