package com.probity.echawdi.service;

import java.util.Locale;

import org.springframework.stereotype.Service;

import com.probity.echawdi.dao.RoleMenuDao;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.payload.Response;

import net.bytebuddy.asm.Advice.Local;

@Service
public interface RoleMenuService {
	Response getAllRoleMenuData(int pageNo, int pageSize, String sortBy, String sortDir,Local locale);

	MessageResponse save(RoleMenuDao roleMenuDao,Locale locale);

}