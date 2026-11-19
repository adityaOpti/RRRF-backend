package com.probity.echawdi.serviceImpl;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.probity.echawdi.dao.RoleMenuDao;
import com.probity.echawdi.entity.RoleMenu;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.RoleMenuRepository;
import com.probity.echawdi.service.RoleMenuService;

import net.bytebuddy.asm.Advice.Local;

public class RoleMenuServiceImpl implements RoleMenuService{

	@Autowired
	private RoleMenuRepository roleMenuRepository;

	@Override
	public MessageResponse save(RoleMenuDao roleMenuDao,Locale locale) {
		if(roleMenuDao.getMenuKey() !=null && roleMenuDao.getRoleKey() != null ) {
			RoleMenu roleMenu =new RoleMenu();
			BeanUtils.copyProperties(roleMenuDao, roleMenu);
			roleMenuRepository.save(roleMenu);
			return MessageResponse.builder().status("Success").message("Data Saved").build();
		}
		return MessageResponse.builder().status("Failure").message("Error While Save").build();
	}

	@Override
	public Response getAllRoleMenuData(int pageNo, int pageSize, String sortBy, String sortDir,Local locale) {
		Sort sort= sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
		Response roleMenuData = new Response();
		Page<RoleMenu> roleMenuP =roleMenuRepository.findAllByActiveFlag('Y', pageable);
		List<RoleMenu> roleMenu=roleMenuP.getContent();
		roleMenuData.setRoleMenuData(roleMenu.stream().map(roleMenuR->toDao(roleMenuR)).collect(Collectors.toList()));
		return roleMenuData;
	}

	public RoleMenuDao toDao(RoleMenu roleMenu) {
		RoleMenuDao roleMenuDao=new RoleMenuDao();
		BeanUtils.copyProperties(roleMenu, roleMenuDao);
		return roleMenuDao;
	}
}