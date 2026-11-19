//package com.probity.echawdi.serviceImpl;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//
//import com.probity.echawdi.dao.UsersDao;
//import com.probity.echawdi.repository.UsersRepository;
//import com.probity.echawdi.service.UsersService;
//
///**
// * @author Ashish Dahale
// *
// */
//
//@Component
//public class UsersServiceImpl implements UsersService{
//
//	@Autowired
//	private UsersRepository usersRepository;
//	
//	@Override
//	public ResponseEntity<Object> findByUserNameAndPassword(String username,String password){
//		List<Object[]> lstUserDtl =usersRepository.findByUserNameAndPassword(username, password);
//		if(lstUserDtl != null && lstUserDtl.size()>0) {
//		   List<UsersDao> lstUr = lstUserDtl.stream().map( (object) -> {
//		UsersDao usersDao = new UsersDao();
//		usersDao.setServarthId(object[0].toString());
//		usersDao.setDesg(object[1].toString());
//		usersDao.setDistrictCode(object[3].toString());
//		usersDao.setDistrictName(object[4].toString());
//		usersDao.setTalukaCode(object[5].toString());
//		usersDao.setTalukaName(object[6].toString());
//		return usersDao;
//		}).collect(Collectors.toList());
//		return new ResponseEntity<>(lstUr.get(0), HttpStatus.ACCEPTED);
//		}
//		return null;
//		}
//}
