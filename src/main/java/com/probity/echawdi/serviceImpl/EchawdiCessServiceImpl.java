package com.probity.echawdi.serviceImpl;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.probity.echawdi.dao.EchawadiCessDao;
import com.probity.echawdi.entity.EchawadiCess;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.EchawadiCessRepository;
import com.probity.echawdi.service.EchawadiCessService;

@Component
public class EchawdiCessServiceImpl implements EchawadiCessService{

	@Autowired
	private EchawadiCessRepository echawadiCessRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	
	
	@Override
	public Response getAllEchawadiCessData(int pageNo, int pageSize, String sortBy, String sortDir, Locale locale) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<EchawadiCess> echawadiCess = echawadiCessRepository.findAllByActiveFlag('Y',pageable);
		List<EchawadiCess> echawadiCess1=echawadiCess.getContent();
		Response echawadiCessResponse =new Response();
		echawadiCessResponse.setEchawadiCessData(echawadiCess1.stream().map(echawadiCessR -> toDao(echawadiCessR)).collect(Collectors.toList()));
		return echawadiCessResponse;
	}

	@Override
	public EchawadiCessDao getEchawadiCessById(long id, Locale locale) {
		EchawadiCess echawadiCess=echawadiCessRepository.getById(id);
		return toDao(echawadiCess);
	}

	@Override
	public MessageResponse save(EchawadiCessDao echawadiCessDao, Locale locale) {
		EchawadiCess echawadiCess =new EchawadiCess();
		BeanUtils.copyProperties(echawadiCessDao, echawadiCess);
		echawadiCessRepository.save(echawadiCess);
		return  MessageResponse.builder().message("Data Saved").status("Success").build();
	}

	@Override
	public void updateEchawadiCess(EchawadiCessDao echawadiCessDao, Locale locale) {
		EchawadiCess echawadiCess=echawadiCessRepository.findById(echawadiCessDao.getId())
				.orElseThrow(() -> 
				new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "EchawadiCess", echawadiCessDao.getId()));
		if(echawadiCess !=null) {
			BeanUtils.copyProperties(echawadiCessDao, echawadiCess);
			echawadiCessRepository.save(echawadiCess);
		}else {
			throw new ExchangeException("Error Occured");
		}
	}

	@Override
	public void deleteEchawadiCessById(long id, Locale locale) {
		EchawadiCess echawadiCess =echawadiCessRepository.findById(id)
			.orElseThrow(() -> 
			new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "Menu", id));
		echawadiCess.setActiveFlag('N');
		echawadiCessRepository.save(echawadiCess);
	}
	
	
	public EchawadiCessDao toDao(EchawadiCess echawadiCess) {
		EchawadiCessDao echawadiCessDao =new EchawadiCessDao();
		BeanUtils.copyProperties(echawadiCess, echawadiCessDao);
		return echawadiCessDao;
	}

		
	}


