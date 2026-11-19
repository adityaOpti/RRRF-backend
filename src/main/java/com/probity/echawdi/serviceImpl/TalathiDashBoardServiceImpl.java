package com.probity.echawdi.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.Form7KhataDao;
import com.probity.echawdi.dao.KhataMergeDao;
import com.probity.echawdi.dao.LandRevenueDemandDao;
import com.probity.echawdi.dao.TalathiDashBoardDao;
import com.probity.echawdi.entity.TalathiDashBoard;
import com.probity.echawdi.exception.FieldValidationException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.KhataMergeRepository;
import com.probity.echawdi.repository.LandRevenueDemandRepository;
import com.probity.echawdi.repository.TalathiDashBoardRepository;
import com.probity.echawdi.service.TalathiDashBoardService;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceKeeper;

@Component
public class TalathiDashBoardServiceImpl implements TalathiDashBoardService {

	@Autowired
	private LandRevenueDemandRepository landRevenueDemandRepository;
	@Autowired
	private TalathiDashBoardRepository talathiDashBoardRepository;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private KhataMergeRepository khataMergeRepository;
	
	
	@Override
	public Response getDashBoardDetails(HttpServletRequest request,String cCode,String revenueYear) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		List<Object[]> ccodeslist=khataMergeRepository.getccodeDetails(
				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				request.getHeader("servarthId"));
		Response response =new  Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km=new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));
		
		System.out.println("____________________________________Outer Loop_____________________________________________");
		
		String ccodes=null;
		for(KhataMergeDao r : response.getKhataMergeData()) {
			if(r.getcCode().equals(cCode)) {
			ccodes=r.getcCode();
			break;
			}else{
				ccodes="0";
				}}
		
		//if(ccodes.equals(cCode)) {	
		if(true) {	
		List<Object[]> totalDemandDetails = landRevenueDemandRepository.getDashBoardTotalDemandDetails(
				request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
				cCode,revenueYear);
		
		//DemandGenerated Count
		List <Object []> demandGenerated = landRevenueDemandRepository.lrdDataUpdated(
				request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
				cCode, revenueYear );
		System.out.println("_-------------------ddddddddddddddddd------------------------------"+"=========================");
		for (Object[] row : demandGenerated) {
		  		    for (Object obj : row) {
		       		        if (obj instanceof Number) {
		          		            System.out.print(obj + "\t");
		        }
		    }
		    System.out.println(); // Move to the next line after printing each row
		}
		
		DatabaseContextHolder.restoreCtx();
		Response response1 =new Response();
		if(totalDemandDetails!=null) {
			response1.setLandRevenueDemandData(totalDemandDetails.stream().map((object) -> {
				LandRevenueDemandDao landRevenueDemandDao=new LandRevenueDemandDao();
				landRevenueDemandDao.setKhataNo(object[0] == null ? "0" : object[0].toString());
				landRevenueDemandDao.setNetReceived(BigDecimal.valueOf(Double.valueOf(object[1] == null ? "0" : object[1].toString())));
				landRevenueDemandDao.setNetAmount(BigDecimal.valueOf(Double.valueOf(object[2] == null ? "0" : object[2].toString())));
				landRevenueDemandDao.setTotalOfWithuotSankirn(BigDecimal.valueOf(Double.valueOf(object[1] == null ? "0" : object[1].toString())).subtract(BigDecimal.valueOf(Double.valueOf(object[3] == null ? "0" : object[3].toString()))));
				landRevenueDemandDao.setTotalOfWithSankirn(BigDecimal.valueOf(Double.valueOf(object[3] == null ? "0" : object[3].toString())));
				landRevenueDemandDao.setDemandGeneratedKhataNo(BigDecimal.valueOf(Double.valueOf(object[4] == null ? "0" : object[4].toString())));
				return landRevenueDemandDao;
			}). collect(Collectors.toList()));
//			return response1;
		}
		//System.out.println("____________________________________Outer Loop_____________________________________________");
		if (demandGenerated != null) {
			
			//System.out.println("_-------------------------------------------------"+"=========================");
			for (Object[] row : demandGenerated) {
			  		    for (Object obj : row) {
			       		        if (obj instanceof Number) {
			          		            System.out.print(obj + "\t");
			        }
			    }
			    System.out.println(); // Move to the next line after printing each row
			}
			
			response1.setDemandGenerated(demandGenerated.stream().map((object) -> {
				LandRevenueDemandDao landRevenueDemandDao=new LandRevenueDemandDao();
				
				//System.out.println("__________________________in Loop - "+object[0].toString()+"_______________________________________________________");

				landRevenueDemandDao.setDemandGenerated(BigDecimal.valueOf(Double.valueOf(object[0] == null ? "0" : object[0].toString())));
				//System.out.println("_____________________________in Loop22____________________________________________________");

				return landRevenueDemandDao;
			}). collect(Collectors.toList()));
		}
		return response1;
	}else {
		throw new ResourceNotFoundException("error.form.unautorized");
	}
	}

	@Override
	public Response getDashBoardKhataNoDetails(HttpServletRequest request, String cCode, String talukaCode) 
	{
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		
		System.out.println("I am here in getDashBoardKhataNoDetails method-------------------------");
		
		System.out.println("mhrHost: " + request.getHeader("mhrHost"));
		System.out.println("mhrDbName: " + request.getHeader("mhrDbName"));
		System.out.println("mhrSchemaName: " + request.getHeader("mhrSchemaName"));
		System.out.println("mhrorUser: " + CommonConstants.mhrorUser);
		System.out.println("mhrorPassword: " +
		        (CommonConstants.mhrorPassword != null ? "PRESENT" : "NULL"));
		System.out.println("servarthId: " + request.getHeader("servarthId"));
		
		List<Object[]> ccodeslist=khataMergeRepository.getccodeDetails(
				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				request.getHeader("servarthId"));
		
		
		System.out.println("ccodeslist size: " +
		        (ccodeslist != null ? ccodeslist.size() : "NULL"));
		
		Response response =new  Response();
		response.setKhataMergeData(ccodeslist.stream().map(r -> {
			KhataMergeDao km=new KhataMergeDao();
			km.setcCode(r[0].toString());
			km.setServarthId(r[1].toString());
			km.setVillageName(r[2].toString());
			return km;
		}).collect(Collectors.toList()));
		
		
		String ccodes=null;
		for(KhataMergeDao r : response.getKhataMergeData()) {
			if(r.getcCode().equals(cCode)) {
			ccodes=r.getcCode();
			break;
			}else{
				ccodes="0";
				}}
		
		System.out.println("ccodes: " + ccodes);
		System.out.println("cCode: " + cCode);
	
	//	if(ccodes.equals(cCode)) {
		if(true) {
			//Static Data DashBoard
			List<Object[]> khataNoDetailsAug = landRevenueDemandRepository.getDashBoardKhataNoDetails(
					cCode, talukaCode);
			
			
			
//		List<Object[]> khataNoDetails = landRevenueDemandRepository.getDashBoardKhataNoDetails( request.getHeader("mhrHost"),request.getHeader("mhrDbName")
//															,request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,cCode);
		
			
			System.out.println("AugDatamhrHost: " + CommonConstants.AugDatamhrHost);
			System.out.println("mhrDbName: " + request.getHeader("mhrDbName"));
			System.out.println("mhrSchemaName: " + request.getHeader("mhrSchemaName"));
			System.out.println("mhrorUser: " + CommonConstants.mhrorUser);
			System.out.println("mhrorPassword: " + CommonConstants.mhrorPassword);
			System.out.println("cCode: " + cCode);
			System.out.println("AugDataPort: " + CommonConstants.AugDataPort);	
			
			
			
			List<Object[]>  khataNoDetails = landRevenueDemandRepository.getDashBoardKhataNoDetailsAug(CommonConstants.AugDatamhrHost,request.getHeader("mhrDbName")
				,request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,cCode,CommonConstants.AugDataPort);
		
		//vasuli patra khatedar

		List<Object[]> khatedarPatra = landRevenueDemandRepository.getKhatedarPatraSankhya(request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,
				CommonConstants.echPassword,cCode,CommonConstants.currentYear);
		
		
		
		DatabaseContextHolder.restoreCtx();
		Response response1 =new Response();
		if(khataNoDetails!=null) {
			response1.setForm7KhataData(khataNoDetails.stream().map((object) ->{
				Form7KhataDao form7KhataDao=new Form7KhataDao();
				//form7KhataDao.setKhataNo(object[0].toString());
				form7KhataDao.setForm7khataCount(object[0].toString());
				form7KhataDao.setPin(object[1].toString());
				return form7KhataDao;
			}).collect(Collectors.toList()));	

		}
		if(khataNoDetailsAug!=null) {
			response1.setForm7KhataDataAug(khataNoDetailsAug.stream().map((object) ->{
				Form7KhataDao form7KhataDao=new Form7KhataDao();
				//form7KhataDao.setKhataNo(object[0].toString());
				form7KhataDao.setForm7khataCount(object[0].toString());
				form7KhataDao.setPin(object[1].toString());
				return form7KhataDao;
			}).collect(Collectors.toList()));	

		}
		

		if(khatedarPatra!=null) {
			response1.setKhatedarPatra(khatedarPatra.stream().map((object) ->{
				Form7KhataDao form7KhataDao=new Form7KhataDao();
				form7KhataDao.setNetAmount1(object[0].toString());

				return form7KhataDao;
			}).collect(Collectors.toList()));	

	}
		
		return response1;
		}else {
			throw new ResourceNotFoundException("error.form.unautorized");
		}
	}
	
	
	
	
	
	
	/*
	 * old api 22 Aug 2025
	 * @Override
	public Response getVillageTarget(HttpServletRequest request,String cCode,String revenueYear,Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
			List<Object[]> ccodeslist=khataMergeRepository.getccodeDetails(
					request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
					request.getHeader("servarthId"));
			Response response =new  Response();
			response.setKhataMergeData(ccodeslist.stream().map(r -> {
				KhataMergeDao km=new KhataMergeDao();
				km.setcCode(r[0].toString());
				km.setServarthId(r[1].toString());
				km.setVillageName(r[2].toString());
				return km;
			}).collect(Collectors.toList()));
			
			
			String ccodes=null;
			for(KhataMergeDao r : response.getKhataMergeData()) {
				if(r.getcCode().equals(cCode)) {
				ccodes=r.getcCode();
				break;
				}else{
					ccodes="0";
					}}
			
			if(ccodes.equals(cCode)) {	
			List<TalathiDashBoard> talathiDashBoard = talathiDashBoardRepository.findBycCodeAndRevenueYearAndActiveFlag(cCode,revenueYear,'Y');
		DatabaseContextHolder.restoreCtx();
		if(Collections.isEmpty(talathiDashBoard)) {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}	 
		Response talathiResponse =new  Response();
		talathiResponse.setTalathiDashBoardData(talathiDashBoard.stream().map(talathiDashBoardData->toDao(talathiDashBoardData)).collect(Collectors.toList()));
		return talathiResponse;
		}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}*/
	
	
	
	@Override
	public Response getDashboardLrDataUpdated(HttpServletRequest request,String cCode,String revenueYear) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));

		
		//DemandGenerated Count
		/*List <Object []> demandGenerated = landRevenueDemandRepository.lrdDataUpdated(
				request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
				cCode, revenueYear );*/
		
		
		
		/*List <Object []>*/ Long demandGenerated = landRevenueDemandRepository.lrdDataUpdatede(
				request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
				cCode, revenueYear );
		
		
		System.out.println("long Value----- : "+demandGenerated);
		
		
	
		//DatabaseContextHolder.restoreCtx();
		
		
		long safeDemandGenerated = (demandGenerated != null) ? demandGenerated : 0L;

		System.out.println("long Value----- : " + safeDemandGenerated);

		Response response1 = new Response();
		response1.setDemandGeneratedd(safeDemandGenerated);
		
		
//		Response response1 =new Response();
//	
//		
//		response1.setDemandGeneratedd(demandGenerated != null ? demandGenerated : 0L);
//		
		return response1;
}
	
	
	
	//new api target 8 sept 2025
	@Override
	public Response getVillageTarget(HttpServletRequest request, String cCode, String revenueYear, Locale locale) {
	    dataSourceKeeper.setDataSourceEchwadi(
	        request.getHeader("echDbName"),
	        request.getHeader("echSchemaName")
	    );

//	    List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(
//	        request.getHeader("mhrHost"),
//	        request.getHeader("mhrDbName"),
//	        request.getHeader("mhrSchemaName"),
//	        CommonConstants.mhrorUser,
//	        CommonConstants.mhrorPassword,
//	        request.getHeader("servarthId")
//	    );
//
//	    List<KhataMergeDao> khataList = ccodeslist.stream().map(r -> {
//	        KhataMergeDao km = new KhataMergeDao();
//	        km.setcCode(r[0].toString());
//	        km.setServarthId(r[1].toString());
//	        km.setVillageName(r[2].toString());
//	        return km;
//	    }).collect(Collectors.toList());
//
//	    boolean isAuthorized = khataList.stream().anyMatch(k -> k.getcCode().equals(cCode));
//	    if (!isAuthorized) {
//	        throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
//	    }

	    List<TalathiDashBoard> talathiDashBoard =
	        talathiDashBoardRepository.findBycCodeAndRevenueYearAndActiveFlag(cCode, revenueYear, 'Y');

	    DatabaseContextHolder.restoreCtx();

	    Response talathiResponse = new Response();

	    if (talathiDashBoard == null || talathiDashBoard.isEmpty()) {
	        talathiResponse.setTalathiDashBoardData(new ArrayList<>());
	        talathiResponse.setMessage("Target is not set for this village");
	        talathiResponse.setAnnualVillageTarget("0");
	    } else {
	        talathiResponse.setTalathiDashBoardData(
	            talathiDashBoard.stream()
	                .map(this::toDao)
	                .collect(Collectors.toList())
	        );

	        TalathiDashBoard dashBoard = talathiDashBoard.get(0);
	        String target = dashBoard.getAnnualVillageTarget();
	        String targetValue = (target != null) ? target.toString() : "0";

	        System.out.println("DEBUG: Target from DB = " + targetValue);

	        talathiResponse.setAnnualVillageTarget(targetValue);
	        talathiResponse.setMessage("Target data retrieved successfully");
	    }

	    return talathiResponse;
	}

	/*@Override
	public Response getVillageTarget(HttpServletRequest request, String cCode, String revenueYear, Locale locale) {
	    dataSourceKeeper.setDataSourceEchwadi(
	        request.getHeader("echDbName"),
	        request.getHeader("echSchemaName")
	    );

	    List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(
	        request.getHeader("mhrHost"),
	        request.getHeader("mhrDbName"),
	        request.getHeader("mhrSchemaName"),
	        CommonConstants.mhrorUser,
	        CommonConstants.mhrorPassword,
	        request.getHeader("servarthId")
	    );

	    Response response = new Response();
	    List<KhataMergeDao> khataList = ccodeslist.stream().map(r -> {
	        KhataMergeDao km = new KhataMergeDao();
	        km.setcCode(r[0].toString());
	        km.setServarthId(r[1].toString());
	        km.setVillageName(r[2].toString());
	        return km;
	    }).collect(Collectors.toList());

	    response.setKhataMergeData(khataList);

	    // Check authorization
	    boolean isAuthorized = khataList.stream().anyMatch(k -> k.getcCode().equals(cCode));

	    if (!isAuthorized) {
	        throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
	    }

	    // Get dashboard data
	    List<TalathiDashBoard> talathiDashBoard =
	        talathiDashBoardRepository.findBycCodeAndRevenueYearAndActiveFlag(cCode, revenueYear, 'Y');

	    // Restore DB context
	    DatabaseContextHolder.restoreCtx();

	    // Prepare response
	    Response talathiResponse = new Response();

	    if (talathiDashBoard == null || talathiDashBoard.isEmpty()) {
	        // No data found, return empty list and message = "0"
	        talathiResponse.setTalathiDashBoardData(new ArrayList<>()); 
	        talathiResponse.setMessage("Target is not set for this village "); 
	        talathiResponse.setTarget(0);
	        return talathiResponse;
	    }

	    // Data found, map and return
	    talathiResponse.setTalathiDashBoardData(
	        talathiDashBoard.stream()
	            .map(this::toDao)
	            .collect(Collectors.toList())
	    );
	    
	    talathiResponse.setTarget(0); // 

	    return talathiResponse;
	}*/


	@Override
	public MessageResponse save(TalathiDashBoardDao talathiDashBoardDao,HttpServletRequest request, Locale locale) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		if(talathiDashBoardDao.getCCode() != null && talathiDashBoardDao.getAnnualVillageTarget()!=null) {
			if(!talathiDashBoardRepository.existsBycCodeAndRevenueYear(talathiDashBoardDao.getCCode(),talathiDashBoardDao.getRevenueYear())) {
			TalathiDashBoard talathiDashBoard = toEntity(talathiDashBoardDao);
			talathiDashBoardRepository.save(talathiDashBoard);
			DatabaseContextHolder.restoreCtx();
			return MessageResponse.builder().status("SUCCESS").message(
					messageSource.getMessage("error.form.savedata", null, locale)).build();	
		}
		}
		throw new FieldValidationException("error.form.errorwhilesave");
	}
	
	@Override
	public void updateTalathiDashboard(TalathiDashBoardDao talathiDashBoardDao,Locale locale,HttpServletRequest request)
	{
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		TalathiDashBoard talathiDashBoard = talathiDashBoardRepository.findById(talathiDashBoardDao.getId())
				.orElseThrow(() -> new ResourceNotFoundException("TalathiDashBoard"));
		BeanUtils.copyProperties(talathiDashBoardDao, talathiDashBoard);
		talathiDashBoardRepository.save(talathiDashBoard);
		DatabaseContextHolder.restoreCtx();
	}

	
	private TalathiDashBoard toEntity(TalathiDashBoardDao talathiDashBoardDao) {
		TalathiDashBoard talathiDashBoard = new TalathiDashBoard();
		BeanUtils.copyProperties(talathiDashBoardDao,talathiDashBoard);
		return talathiDashBoard;
	}
	
	private TalathiDashBoardDao toDao(TalathiDashBoard talathiDashBoard){
		TalathiDashBoardDao talathiDashBoardDao = new TalathiDashBoardDao();
		BeanUtils.copyProperties(talathiDashBoard, talathiDashBoardDao);
		return talathiDashBoardDao;
	}
}