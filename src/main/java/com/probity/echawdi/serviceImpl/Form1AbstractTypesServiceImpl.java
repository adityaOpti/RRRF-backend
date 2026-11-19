//package com.probity.echawdi.serviceImpl;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.probity.echawdi.config.DatabaseContextHolder;
//import com.probity.echawdi.dao.Form1AbstractTypesDao;
//import com.probity.echawdi.entity.Form1AbstractTypes;
//import com.probity.echawdi.repository.Form1AbstractTypesRepository;
//import com.probity.echawdi.service.Form1AbstractTypesService;
//import com.probity.echawdi.utils.DataSourceKeeper;
//
//@Component
//public class Form1AbstractTypesServiceImpl implements Form1AbstractTypesService{
//
//	@Autowired
//	private Form1AbstractTypesRepository form1AbstractTypesRepository;
//	@Autowired
//	private DataSourceKeeper dataSourceKeeper;
//	
//	@Override
//	public List<Form1AbstractTypesDao> findAllByActiveFlag(HttpServletRequest request) {
//		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
//		List<Form1AbstractTypes> form1AbstractTypes=new ArrayList<>();
//		form1AbstractTypes = form1AbstractTypesRepository.findAllByActiveFlagOrderByLandTypeCodeAsc('Y');
//		DatabaseContextHolder.restoreCtx();
//		return form1AbstractTypes.stream().map(form1Abt -> toDao(form1Abt)).collect(Collectors.toList());
//	}
//	
//	public Form1AbstractTypesDao toDao(Form1AbstractTypes form1AbstractTypes) {
//		Form1AbstractTypesDao form1AbstractTypesDao=new Form1AbstractTypesDao();
//		if(form1AbstractTypes != null) {
//			String concetter = form1AbstractTypes.getLandType().concat("-" +form1AbstractTypes.getLandSubType1())
//						      .concat(form1AbstractTypes.getLandSubType2() == null ? "" : ("/" +form1AbstractTypes.getLandSubType2()))
//							  .concat(form1AbstractTypes.getLandSubType3() == null ? "" : ("/" +form1AbstractTypes.getLandSubType3()));
//			
//			String concetter1 = form1AbstractTypes.getLandTypeMarathi().concat("-" +form1AbstractTypes.getLandSubType1Marathi())
//				      .concat(form1AbstractTypes.getLandSubType2Marathi() == null ? "" : ("/" +form1AbstractTypes.getLandSubType2Marathi()))
//					  .concat(form1AbstractTypes.getLandSubType3Marathi() == null ? "" : ("/" +form1AbstractTypes.getLandSubType3Marathi()));
//			
//			BeanUtils.copyProperties(form1AbstractTypes, form1AbstractTypesDao);
//			form1AbstractTypesDao.setLandTypeWithSubtype(concetter);
//			form1AbstractTypesDao.setLandTypeWithSubtypeMarathi(concetter1);
//		}
//	return form1AbstractTypesDao;
//	}
//}