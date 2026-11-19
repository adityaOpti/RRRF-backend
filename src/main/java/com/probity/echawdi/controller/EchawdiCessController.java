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

import com.probity.echawdi.dao.EchawadiCessDao;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.EchawadiCessService;
import com.probity.echawdi.utils.CommonConstants;

@RestController
@RequestMapping("api/echawadiCess")
public class EchawdiCessController {
	
	@Autowired
	EchawadiCessService echawadiCessService;
	
	@Autowired
	private MessageSource messageSource;
	
	
	@GetMapping("/getEchawadiCessData")
	@ResponseStatus(HttpStatus.OK)
	public Response getAllEchawadiCessDs(@RequestParam(value = "pageNo", defaultValue = CommonConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = CommonConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = CommonConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = CommonConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
			Locale locale) {
		return echawadiCessService.getAllEchawadiCessData(pageNo, pageSize, sortBy, sortDir, locale);
	}
	
	@GetMapping("/getEchawadiCessById")
	@ResponseStatus(HttpStatus.OK)
	public EchawadiCessDao getEchawadiById(@RequestParam long id,Locale locale) {
		return echawadiCessService.getEchawadiCessById(id, locale) ;
	}
	
	@PostMapping("/saveEchawadiCess")
	@ResponseStatus(HttpStatus.CREATED)
	public MessageResponse createEchawadiCess(@Valid @RequestBody EchawadiCessDao echawadiCessDao, Locale locale) {
		return echawadiCessService.save(echawadiCessDao, locale) ;
		
	}
	@PutMapping("/editEchawadiCess")
	@ResponseStatus(HttpStatus.OK)
	public MessageResponse updateEchawadiCess(@Valid @RequestBody EchawadiCessDao echawadiCessDao, Locale locale) {
		try {
			echawadiCessService.updateEchawadiCess(echawadiCessDao, locale);
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.successmessage", null, locale)).build();
		}
		catch (Exception ex) {
			throw new ExchangeException("error.form.editrecord");
		}
		}
		
		@DeleteMapping("/discardMenu")
		@ResponseStatus(HttpStatus.IM_USED)
		public MessageResponse deleteEchawadiCess(@RequestParam long id ,Locale locale){
			try {
				echawadiCessService.deleteEchawadiCessById(id, locale);
				return MessageResponse.builder().status("SUCCESS").message(
						messageSource.getMessage("error.form.deletemessage", null, locale)).build();	
			}
			catch (Exception ex) {
				throw new ExchangeException("error.form.deleterecord");
			}
		
	}

}