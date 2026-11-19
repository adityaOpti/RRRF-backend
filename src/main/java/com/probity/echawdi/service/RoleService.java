/**
 * 
 */
package com.probity.echawdi.service;


import java.util.Locale;

import com.probity.echawdi.dao.RoleDao;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;

import net.bytebuddy.asm.Advice.Local;

/**
 * @author Kumar Harpude
 *
 */
public interface RoleService {

	Response getAllRoleData(int pageNo, int pageSize, String sortBy, String sortDir,Local locale);

	RoleDao getRoleDataById(long id,Locale locale);
	
	MessageResponse save(RoleDao roleDao,Locale local);
	
	void updateRole(RoleDao roleDao,Locale locale);

	void deleteRoleById(long id, Locale locale);
}