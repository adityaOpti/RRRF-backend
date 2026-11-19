//package com.probity.echawdi.controller;
//
//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URLConnection;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.FileCopyUtils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.client.RestTemplate;
//
//import com.probity.echawdi.config.DatabaseContextHolder;
//import com.probity.echawdi.config.WithDatabase;
//import com.probity.echawdi.entity.DivisionDistrictTalukaCount;
//import com.probity.echawdi.entity.Form1;
//import com.probity.echawdi.entity.Form14;
//import com.probity.echawdi.payload.Response;
//import com.probity.echawdi.repository.DivisionDistrictTalukaCountRepository;
//import com.probity.echawdi.repository.Form14Repository;
//import com.probity.echawdi.repository.Form1Repository;
//import com.probity.echawdi.repository.MVillageMapperRepository;
//import com.probity.echawdi.utils.CommonConstants.FileName;
//import com.probity.echawdi.utils.DataSourceKeeper;
//import com.probity.echawdi.utils.DataSourceType;
//import com.probity.echawdi.utils.StaticPropertyFetcher;
//
//@RequestMapping("/auth")
//public class OneTimeActivityController {
//
//	@Autowired
//	private DivisionDistrictTalukaCountRepository divisionDistrictTalukaCountRepository; 
//	@Autowired
//	private MVillageMapperRepository mVillageMapperRepository;
//	@Autowired
//	private DataSourceKeeper dataSourceKeeper;
//	@Autowired
//	private Form1Repository form1Repository;
//	@Autowired
//	private Form14Repository form14Repository;
//	@Autowired
//	private RestTemplate restTemplate;
//
//}