package com.probity.echawdi.serviceImpl;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.probity.echawdi.dao.RoleDao;
import com.probity.echawdi.entity.Role;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.RoleRepository;
import com.probity.echawdi.service.RoleService;

import net.bytebuddy.asm.Advice.Local;


/**
 * @author Kumar Harpude
 *
 */



@Component
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	MessageSource messageSource;
	
	
	public RoleDao toDao(Role role) {
		RoleDao roleDao =new RoleDao();
		BeanUtils.copyProperties(role, roleDao);
		return roleDao;
	}
	

	@Override
	public Response getAllRoleData(int pageNo, int pageSize, String sortBy, String sortDir, Local locale) {
		Sort sort= sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Role> role = roleRepository.findAllByActiveFlag('Y',pageable);
		List<Role> roles=role.getContent();
		Response roleResponse =new Response();
		roleResponse.setRoleData(roles.stream().map(roleR -> toDao(roleR)).collect(Collectors.toList()));
		return roleResponse;
	}


	@Override
	public RoleDao getRoleDataById(long id, Locale locale) {
		Role role=roleRepository.getById(id);
		return toDao(role);
	}


	@Override
	public MessageResponse save(RoleDao roleDao, Locale local) {
		Role role =new Role();
		BeanUtils.copyProperties(roleDao, role);
		role.setName(roleDao.getName());
		if(role.getName()!=null) {
			roleRepository.save(role);
			return  MessageResponse.builder().message("Data Saved").status("Success").build();
		}else {
			return  MessageResponse.builder().message("Error").status("FAILURE").build();
		}
	}			
	
	
	@Override
	public void updateRole(RoleDao roleDao, Locale locale) {
		Role role=roleRepository.findById(roleDao.getId())
				.orElseThrow(() -> 
				new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "Role", roleDao.getId()));
		if(role !=null) {
			BeanUtils.copyProperties(roleDao, role);
			roleRepository.save(role);
		}else {
			throw new ExchangeException("Error Occured");
		}
	}


	@Override
	public void deleteRoleById(long id, Locale locale) {
		Role role= roleRepository.findById(id)
				.orElseThrow(() -> 
				new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "Role", id));
			role.setActiveFlag('N');
			roleRepository.save(role);
		
	}
	
	
	
	
	
	
	
	
		
		

	

}
