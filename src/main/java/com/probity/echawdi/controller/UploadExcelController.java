//package com.probity.echawdi.controller;
//
//import java.sql.SQLException;
//import java.util.Locale;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.probity.echawdi.entity.UploadExcel;
//import com.probity.echawdi.service.UploadExcelService;
//
//
//@RestController
//@RequestMapping("/api/uploadexcel")
//public class UploadExcelController {
//	
//	@Autowired
//	UploadExcelService uploadExcelService;
//
//	@PostMapping("/uploadfile")
//	@ResponseStatus(HttpStatus.OK)
//	public UploadExcel uploadfile(@RequestBody MultipartFile multipartFile, 
//			HttpServletRequest request,
//			Locale locale ) throws SQLException  {
//		return uploadExcelService.uploadexcelfile(multipartFile, request, locale);
//	}
//
//}
