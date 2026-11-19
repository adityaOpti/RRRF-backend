package com.probity.echawdi.serviceImpl;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.probity.echawdi.dao.MenuDao;
import com.probity.echawdi.entity.Menu;
import com.probity.echawdi.entity.Users;
import com.probity.echawdi.exception.ExchangeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.NoContent;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.MenuRepository;
import com.probity.echawdi.repository.RoleMenuRepository;
import com.probity.echawdi.repository.RoleRepository;
import com.probity.echawdi.service.MenuService;


/**
 * @author Kumar Harpude
 */

@Component
public class MenuServiceImpl  implements MenuService{

	@Autowired
	private MessageSource messageSource;
//	@Autowired
//	private UsersRepository usersRepository;
	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private RoleMenuRepository roleMenuRepository;


	@Override
	public Response getAllMenuData(int pageNo, int pageSize, String sortBy, String sortDir, Locale locale) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Menu> menu = menuRepository.findAllByActiveFlag('Y',pageable);
		List<Menu> menus=menu.getContent();
		Response menuResponse =new Response();
		menuResponse.setMenuData(menus.stream().map(menuR -> toDao(menuR,null)).collect(Collectors.toList()));
		return menuResponse;
	}

	@Override
	public MenuDao getMenuById(long id, Locale locale) {
		Menu menu=menuRepository.getById(id);
		return toDao(menu,null);
	}

	@Override
	public MessageResponse save(MenuDao MenuDao, Locale locale) {
		Menu menu =new Menu();
		BeanUtils.copyProperties(MenuDao, menu);
		menuRepository.save(menu);
		return  MessageResponse.builder().message("Data Saved").status("Success").build();
	}			


	@Override
	public void updateMenu(MenuDao menuDao, Locale locale) {
		Menu menu=menuRepository.findById(Long.valueOf(String.valueOf(menuDao.getId())))
				.orElseThrow(() -> 
				new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "Menu", menuDao.getId()));
		if(menu !=null) {
			BeanUtils.copyProperties(menuDao, menu);
			menuRepository.save(menu);
		}else {
			throw new ExchangeException("Error Occured");
		}
	}

	@Override
	public void deleteMenuById(long id, Locale locale) {
		Menu menu =menuRepository.findById(id)
				.orElseThrow(() -> 
				new ResourceNotFoundException("error.form.datanotfound", messageSource, locale, "Menu", id));
		menu.setActiveFlag('N');
		menuRepository.save(menu);
	}

	@Override
	public Response getRoleBasedMenu(String userName){
		if(userName != null) {
			Response menuResponse= new Response();
			//		--get User Info (role_id)
			Optional<Users> users = Optional.empty();
//					usersRepository.findByServarthId(userName); //modify this into object array methos then use it for below purposes
			//		--getMenuKeysfrom RoleMenu
			if(users != null) {
				List<Double> menuids=roleMenuRepository.getByRoleKey((roleRepository.findByName(users.get().getRoleName())).get().getId());
				//		--get Menus as per role
				if(menuids !=null) {
					List<Menu> menu=menuRepository.getByIdInAndRoutesIsNull(menuids);
					if(menu !=null) {
						menuResponse.setMenuData(menu.stream().map(m->toDao(m,menuids)).collect(Collectors.toList()));
						menuResponse.setMessage("Records Fetch Successfully");
						return menuResponse;
					}
					throw new NoContent("Not Found");
				}
				throw new NoContent("Not Found");
			}
			throw new NoContent("Not Found");
		}
		throw new NoContent("Not Found");
	}

	public MenuDao toDao(Menu menu,List<Double> menuIds) {
		MenuDao menuDao =new MenuDao();
		BeanUtils.copyProperties(menu, menuDao);
//		menuDao.setIcon(menu.getIcon());
		if(menu.getParentKey()==null) {
			List<Menu> menus=menuRepository.getByRoutes(menuDao.getId());
			if(menuIds != null && menus != null) {
				menuDao.setRoutes(menus.stream().map(menuu->toDao(menuu,menuIds)).collect(Collectors.toList()));
			}
		}
		return menuDao;
	}
}