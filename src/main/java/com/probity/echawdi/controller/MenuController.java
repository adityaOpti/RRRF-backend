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

import com.probity.echawdi.dao.MenuDao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.MenuService;
import com.probity.echawdi.utils.CommonConstants;

@RestController
@RequestMapping("/api/menu")
public class MenuController {


	/**
	 * @author Kumar Harpude
	 */
	
	@Autowired
	MenuService menuService;

	@Autowired
	private MessageSource messageSource;


	@GetMapping("/getMenuData")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllMenuDs(	
			@RequestParam(value = "pageNo", defaultValue = CommonConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = CommonConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = CommonConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = CommonConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
			Locale locale){
		return menuService.getAllMenuData(pageNo, pageSize, sortBy, sortDir, locale);
	}


	@GetMapping("/getMenuDataById")
	@ResponseStatus(HttpStatus.OK)
	public MenuDao getMenuById(@RequestParam long id,Locale locale){
		return menuService.getMenuById(id, locale);
	}

	@PostMapping("/saveMenu")
	@ResponseStatus(HttpStatus.CREATED)
	public MessageResponse createMenu(@Valid @RequestBody MenuDao MenuDao,Locale locale){
		return menuService.save(MenuDao, locale);
	}


	@PutMapping("/editMenu")
	@ResponseStatus(HttpStatus.OK)
	public MessageResponse updateMenu(@Valid @RequestBody MenuDao MenuDao,Locale locale){
		try {
			menuService.updateMenu(MenuDao, locale);
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.successmessage", null, locale)).build();
		}
		catch (Exception ex) {
			throw new ExchangeException("error.form.editrecord");
		}
	}

	@DeleteMapping("/discardMenu")
	@ResponseStatus(HttpStatus.IM_USED)
	public MessageResponse deleteMenu(@RequestParam long id ,Locale locale){
		try {
			menuService.deleteMenuById(id, locale);
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
		}
		catch (Exception ex) {
			throw new ExchangeException("error.form.deleterecord");
		}
	}	
	
	@GetMapping("/getRoleBasedMenu")
	public Response getRoleBasedMenu(@RequestParam ("userName") String userName) {
		return menuService.getRoleBasedMenu(userName);
	}
}