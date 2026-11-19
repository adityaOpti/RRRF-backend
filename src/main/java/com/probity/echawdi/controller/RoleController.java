/**
 * 
 */
package com.probity.echawdi.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.dao.RoleDao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.RoleService;
import com.probity.echawdi.utils.CommonConstants;

import net.bytebuddy.asm.Advice.Local;



/**
 * @author Kumar Harpude
 *
 */

@RestController
@RequestMapping("/api/role")
public class RoleController {

	@Autowired
	RoleService roleService;
	
	@Autowired
	MessageSource messageSource;
	
	@GetMapping("/getRoleData")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllRoleDs(
			@RequestParam(value= "pageNo", defaultValue=CommonConstants.DEFAULT_PAGE_NUMBER, required=false) int pageNo,
			@RequestParam(value= "pageSize", defaultValue=CommonConstants.DEFAULT_PAGE_SIZE, required=false) int pageSize,
			@RequestParam(value= "sortBy", defaultValue=CommonConstants.DEFAULT_SORT_BY, required=false) String sortBy,
			@RequestParam(value= "sortDir", defaultValue=CommonConstants.DEFAULT_SORT_DIRECTION, required=false) String sortDir,
			Local locale) {
		
			return roleService.getAllRoleData(pageNo, pageSize, sortBy, sortDir, locale);
	}
	
	
	@GetMapping("/getRoleDataById")
	@ResponseStatus(HttpStatus.OK)
	public RoleDao getRoleById(@RequestParam long id,Locale locale) {
	return roleService.getRoleDataById(id,locale);
	}
	
	
	@PostMapping("/saveRole")
	@ResponseStatus(HttpStatus.CREATED)
	public MessageResponse createRole(@Valid @RequestBody RoleDao roleDao,Locale locale) {
	return roleService.save(roleDao, locale);
	}
	
	
	@PutMapping("/editRole")
	@ResponseStatus(HttpStatus.OK)
	public MessageResponse updateRole(@Valid @RequestBody RoleDao roleDao,Locale locale) {
		try {
			roleService.updateRole(roleDao,locale);
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.successmessage", null, locale)).build();
		}
		catch (Exception ex) {
			throw new ExchangeException("error.form.editrecord");
		}
	}
	
	@DeleteMapping("/discardRole")
	@ResponseStatus(HttpStatus.IM_USED)
	public MessageResponse deleteRole(@RequestParam long id,Locale locale) {
		try {
			roleService.deleteRoleById(id, locale);
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
		}
		catch (Exception ex) {
			throw new ExchangeException("error.form.deleterecord");
		}
	
	}
	
}
