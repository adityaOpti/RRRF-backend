/**
 * 
 */
package com.probity.echawdi.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.ApiResponse;
import com.probity.echawdi.dao.Form1AbstractDao;
import com.probity.echawdi.dao.Form1AbstractDyslrDao;
import com.probity.echawdi.dao.KhataMergeDao;
import com.probity.echawdi.entity.Form1Abstract;
import com.probity.echawdi.entity.Form1Abstract_dyslr;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.Form1AbstractDyslrRepository;
import com.probity.echawdi.repository.Form1AbstractRepository;
import com.probity.echawdi.repository.KhataMergeRepository;
import com.probity.echawdi.service.Form1AbstractService;
import com.probity.echawdi.service.LgdMapperService;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceKeeper;

/**
 * @author Bhagyashri Lolage
 *
 */

@Component
public class Form1AbstractServiceImpl  implements Form1AbstractService{

	@Autowired
	private Form1AbstractRepository form1AbstractRepository;
	@Autowired
	private Form1AbstractDyslrRepository form1AbstractDyslrRepository;
	@Autowired
	private LgdMapperService lgdMapperService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	@Autowired
	private KhataMergeRepository khataMergeRepository;

	@Override
	public Form1AbstractDao getForm1AbstractData(String cCode, Locale locale,HttpServletRequest request) {
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
		Form1Abstract form1abstractdtl=form1AbstractRepository.findBycCode(cCode);
		DatabaseContextHolder.restoreCtx();
		if(form1abstractdtl!=null) {
			return toDao(form1abstractdtl);
		}else {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}
	
	//****DYSLR****

//	@Override
//	public Form1AbstractDyslrDao getForm1AbstractDyslrData(String cCode, Locale locale, HttpServletRequest request) {
//	    dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
//	    
//	    System.out.println("API cCode: [" + cCode + "]");
//	    System.out.println("Header servarthId: [" + request.getHeader("servarthId") + "]");
//	    
//	    List<Object[]> ccodeslist = khataMergeRepository.getccodeDetails(
//	            request.getHeader("mhrHost"), request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"), 
//	            CommonConstants.mhrorUser, CommonConstants.mhrorPassword, request.getHeader("servarthId"));
//	            
//	    Response response = new Response();
//	    
//	    response.setKhataMergeData(ccodeslist.stream().map(r -> {
//	        KhataMergeDao km = new KhataMergeDao();
//	        km.setcCode(r[0] != null ? r[0].toString().trim() : "");
//	        km.setServarthId(r[1] != null ? r[1].toString().trim() : "");
//	        km.setVillageName(r[2] != null ? r[2].toString().trim() : "");
//	        return km;
//	    }).collect(Collectors.toList()));
//	    
//	    System.out.println("Total Allowed cCodes from DB: " + response.getKhataMergeData().size());
//	    for(KhataMergeDao r : response.getKhataMergeData()) {
//	        System.out.println("Allowed cCode: [" + r.getcCode() + "]");
//	    }
//	    
//	    boolean isAuthorized = response.getKhataMergeData().stream()
//	            .anyMatch(r -> r.getcCode() != null && r.getcCode().equals(cCode != null ? cCode.trim() : ""));
//	    
//	    System.out.println("Is User Authorized? : " + isAuthorized);
//
//	    if (isAuthorized) {    
//	        Form1Abstract_dyslr form1abstractdyslrdtl = form1AbstractDyslrRepository.findBycCode(cCode.trim());
//	        DatabaseContextHolder.restoreCtx();
//	        
//	        if (form1abstractdyslrdtl != null) {
//	            return toDao(form1abstractdyslrdtl);
//	        } else {
//	            throw new ResourceNotFoundException("error.form.getData", messageSource, locale);
//	        }
//	    } else {
//	        throw new ResourceNotFoundException("error.form.unautorized", messageSource, locale);
//	    }
//	}
	
	
	@Override
	public Form1AbstractDyslrDao getForm1AbstractDyslrData(
	        String cCode,
	        Locale locale,
	        HttpServletRequest request) {

	    try {
	        System.out.println("---- FORM1 DATA FETCH START ----");
	        System.out.println("Target cCode: [" + (cCode != null ? cCode.trim() : "NULL") + "]");
	        System.out.println("Header echDbName: [" + request.getHeader("echDbName") + "]");
	        System.out.println("Header echSchemaName: [" + request.getHeader("echSchemaName") + "]");

	        dataSourceKeeper.setDataSourceEchwadi(
	                request.getHeader("echDbName"),
	                request.getHeader("echSchemaName"));
	        System.out.println("Data source context successfully switched to Echwadi.");

	       
	        System.out.println("Executing repository lookup for cCode...");
	        Form1Abstract_dyslr entity =
	                form1AbstractDyslrRepository.findBycCode(cCode.trim());

	        DatabaseContextHolder.restoreCtx();
	        System.out.println("Database context restored back to default.");

	        if (entity == null) {
	            System.out.println("FETCH FAILED: No record found in DB for cCode: [" + cCode.trim() + "]");
	            throw new ResourceNotFoundException(
	                    "error.form.getData",
	                    messageSource,
	                    locale);
	        }

	        System.out.println("FETCH SUCCESS: Record found. Converting to DAO now...");

	     
	        return convertToDao(entity);

	    } catch (Exception e) {
	        System.out.println("EXCEPTION ENCOUNTERED in getForm1AbstractDyslrData!");
	        e.printStackTrace();
	        throw e;
	    }
	}
	
	private Form1AbstractDyslrDao convertToDao(Form1Abstract_dyslr entity) {

		Form1AbstractDyslrDao dao = new Form1AbstractDyslrDao();

	    dao.setId(entity.getId());

	    dao.setDistrictCode(entity.getDistrictCode());
	    dao.setTalukaCode(entity.getTalukaCode());
	    dao.setCCode(entity.getcCode());

	    dao.setTenureCode1(entity.getTenureCode1());
	    dao.setTenureCode2(entity.getTenureCode2());
	    dao.setTenureCode3(entity.getTenureCode3());

	    dao.setNonOccupationGov(entity.getNonOccupationGov());

	    dao.setRevenueOrLeaseholdLandUnderSpecialAgreement(
	            entity.getRevenueOrLeaseholdLandUnderSpecialAgreement());

	    dao.setDumala(entity.getDumala());

	    dao.setUnOccupied(entity.getUnOccupied());

	    dao.setAssignedForSpecialUse(entity.getAssignedForSpecialUse());

	    dao.setPotKharab(entity.getPotKharab());

	    dao.setRiversNalas(entity.getRiversNalas());

	    dao.setNalas(entity.getNalas());

	    dao.setForest(entity.getForest());

	    dao.setKuran(entity.getKuran());

	    dao.setFreePastureCattleStand(
	            entity.getFreePastureCattleStand());

	    dao.setVillageSite(entity.getVillageSite());

	    dao.setTank(entity.getTank());

	    dao.setBurialGround(entity.getBurialGround());

	    dao.setRailways(entity.getRailways());

	    dao.setPotKharabAssignedRoadsWaterCourses(
	            entity.getPotKharabAssignedRoadsWaterCourses());

	    dao.setRoadsAndPath(entity.getRoadsAndPath());

	    dao.setPipeLineCanel(entity.getPipeLineCanel());

	    dao.setCantonmentLandMilitaryCamp(
	            entity.getCantonmentLandMilitaryCamp());

	    dao.setSchools(entity.getSchools());

	    dao.setDharmshalas(entity.getDharmshalas());

	    dao.setSrNoForNonAgricultureUse(
	            entity.getSrNoForNonAgricultureUse());

	    dao.setPotKharabArea(entity.getPotKharabArea());

	    dao.setPotKharabAssessment(
	            entity.getPotKharabAssessment());

	    dao.setNalaAreaH(entity.getNalaAreaH());

	    dao.setRiverAreaH(entity.getRiverAreaH());

	    dao.setForm3NetCultiArea(
	            entity.getForm3NetCultiArea());

	    dao.setForm3Assessment(
	            entity.getForm3Assessment());

	    dao.setTenure1Area(entity.getTenure1Area());

	    dao.setTenure1Assessment(
	            entity.getTenure1Assessment());

	    dao.setTenure2Area(entity.getTenure2Area());

	    dao.setTenure2Assessment(
	            entity.getTenure2Assessment());

	    dao.setTenure3Area(entity.getTenure3Area());

	    dao.setTenure3Assessment(
	            entity.getTenure3Assessment());

	    dao.setTenure4Area(entity.getTenure4Area());

	    dao.setTenure4Assessment(
	            entity.getTenure4Assessment());

	    return dao;
	}

//	//Dyslr///
//	@Override
//	public Form1AbstractDyslrDao getForm1AbstractDyslrReport(Integer districtCode,Integer talukaCode,String cCode,Locale locale,HttpServletRequest request) {
//		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
//
//		/*List<Object[]> ccodeslist=khataMergeRepository.getccodeDetails(
//				request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
//				request.getHeader("servarthId"));
//		Response response =new  Response();
//		response.setKhataMergeData(ccodeslist.stream().map(r -> {
//			KhataMergeDao km=new KhataMergeDao();
//			km.setcCode(r[0].toString());
//			km.setServarthId(r[1].toString());
//			km.setVillageName(r[2].toString());
//			return km;
//		}).collect(Collectors.toList()));
//		
//		
//		String ccodes=null;
//		for(KhataMergeDao r : response.getKhataMergeData()) {
//			if(r.getcCode().equals(cCode)) {
//			ccodes=r.getcCode();
//			break;
//			}else{
//				ccodes="0";
//				}}*/
//		
//		if(cCode.equals(cCode)) {	
//		List<Form1Abstract_dyslr> formAbstractdyslrLst= new ArrayList<>();
//		List<Object[]> liveDataLst= new ArrayList<>();
//		
//		System.out.println("I am here above the form1AbstractQueryDyslr-----------------: ");
//		
//		liveDataLst=form1AbstractRepository.fetchLiveData(request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
//				request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
//				cCode) ;
//		Form1Abstract_dyslr form1AbstractRdyslr = form1AbstractDyslrRepository.findAllBycCodeAndActiveFlag(cCode, 'Y');
//		
//		System.out.println("formAbstractObject printed here--------------------- : "+form1AbstractRdyslr);
//		
//		DatabaseContextHolder.restoreCtx();
//		if(!liveDataLst.isEmpty()) {
//			formAbstractdyslrLst=liveDataLst.stream().map((liveData)->{
//				Form1Abstract_dyslr form1Abstractdyslr=new Form1Abstract_dyslr();	
//				form1Abstractdyslr.setcCode(liveData[0].toString());          
//				form1Abstractdyslr.setTenure1Area(BigDecimal.valueOf(Double.valueOf(liveData[1]==null ? "0": liveData[1].toString())));
//				form1Abstractdyslr.setTenure1Assessment(BigDecimal.valueOf(Double.valueOf(liveData[2]==null ? "0": liveData[2].toString())));
//				form1Abstractdyslr.setTenure2Area(BigDecimal.valueOf(Double.valueOf(liveData[3]==null ? "0": liveData[3].toString())));
//				form1Abstractdyslr.setTenure2Assessment(BigDecimal.valueOf(Double.valueOf(liveData[4]==null ? "0": liveData[4].toString())));
//				form1Abstractdyslr.setTenure3Area(BigDecimal.valueOf(Double.valueOf(liveData[5]==null ? "0": liveData[5].toString())));
//				form1Abstractdyslr.setTenure3Assessment(BigDecimal.valueOf(Double.valueOf(liveData[6]==null ? "0": liveData[6].toString())));
//				form1Abstractdyslr.setTenure4Area(BigDecimal.valueOf(Double.valueOf(liveData[7]==null ? "0": liveData[7].toString())));
//				form1Abstractdyslr.setTenure4Assessment(BigDecimal.valueOf(Double.valueOf(liveData[8]==null ? "0": liveData[8].toString())));
//				form1Abstractdyslr.setPotKharabArea(BigDecimal.valueOf(Double.valueOf(liveData[9]==null ? "0": liveData[9].toString())));
//				form1Abstractdyslr.setPotKharabAssessment(BigDecimal.valueOf(Double.valueOf(liveData[10]==null ? "0": liveData[10].toString())));
//				form1Abstractdyslr.setRiverAreaH(BigDecimal.valueOf(Double.valueOf(liveData[11]==null ? "0": liveData[11].toString())));
//				form1Abstractdyslr.setNalaAreaH(BigDecimal.valueOf(Double.valueOf(liveData[12]==null ? "0": liveData[12].toString())));
//				form1Abstractdyslr.setForm3NetCultiArea(BigDecimal.valueOf(Double.valueOf(liveData[13]==null ? "0": liveData[13].toString())));
//				form1Abstractdyslr.setForm3Assessment(BigDecimal.valueOf(Double.valueOf(liveData[14]==null ? "0": liveData[14].toString())));
//				form1Abstractdyslr.setUnOccupied(BigDecimal.valueOf(Double.valueOf(liveData[15]==null ? "0": liveData[15].toString())));
//				
//				
//				return form1Abstractdyslr;
//			}).collect(Collectors.toList());
//			
//			System.out.println("I am here before before  the if(form1AbstractRdyslr!=null) condition ------------ : ");
//
//			if(form1AbstractRdyslr!=null){
//				
//				System.out.println("I am here into the if(form1AbstractRdyslr!=null) condition ------------ : ");
//				
//				BeanUtils.copyProperties(form1AbstractRdyslr,formAbstractdyslrLst.get(0),"tenure1Area","tenure1Assessment","tenure2Area","tenure2Assessment","tenure3Area","tenure3Assessment","tenure4Area","tenure4Assessment",
//						"potKharabArea","potKharabAssessment","riverAreaH","nalaAreaH","form3NetCultiArea","form3Assessment","unOccupied");
//			}
//			return toDao(formAbstractdyslrLst.get(0));
//
//		}else {
//			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
//		}}else {
//			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
//		}
//	}
//


	@Override
	public void updateForm1Abstract(@Valid Form1AbstractDao form1AbstractDao, Locale locale,HttpServletRequest request) {
		boolean flag=false;
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form1Abstract form1Abstract = new Form1Abstract();
		if (form1AbstractDao.getId() != null) {
				form1Abstract = form1AbstractRepository.findById(form1AbstractDao.getId())
						.orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"Form1Abstract", form1AbstractDao.getId()));
				flag=true;
		}else {
			if(form1AbstractRepository.existsBycCode(form1AbstractDao.getcCode())) {
				throw new ResourceNotFoundException("error.form.already",messageSource,locale);
			}else {
				flag=true;
			}
		}
		if(flag) {
			BeanUtils.copyProperties(form1AbstractDao, form1Abstract);	
			form1AbstractRepository.save(form1Abstract);
		}
		DatabaseContextHolder.restoreCtx();
	}
	
	
	//****DYSLR****

	@Override
	public void updateForm1AbstractDyslr(
	        Form1AbstractDyslrDao form1AbstractDyslrDao,
	        Locale locale,
	        HttpServletRequest request) {
		
		System.out.println("Here into  updateForm1AbstractDyslr method  -----------  : ");

	    boolean flag = false;

	    dataSourceKeeper.setDataSourceEchwadi(
	            request.getHeader("echDbName"),
	            request.getHeader("echSchemaName"));

	    Form1Abstract_dyslr form1AbstractDyslr = new Form1Abstract_dyslr();

	    if (form1AbstractDyslrDao.getId() != null) {

	        form1AbstractDyslr = form1AbstractDyslrRepository
	                .findById(form1AbstractDyslrDao.getId())
	                .orElseThrow(() -> new ResourceNotFoundException(
	                        "error.form.datanotfound",
	                        messageSource,
	                        locale,
	                        "Form1Abstract_dyslr",
	                        form1AbstractDyslrDao.getId()));

	        flag = true;

	    } else {

	        if (form1AbstractDyslrRepository
	                .existsBycCode(form1AbstractDyslrDao.getCCode())) {

	            throw new ResourceNotFoundException(
	                    "error.form.already",
	                    messageSource,
	                    locale);

	        }

	        flag = true;
	    }

	    if (flag) {

	        BeanUtils.copyProperties(
	                form1AbstractDyslrDao,
	                form1AbstractDyslr);
	        
	        System.out.println("Here into before calling the save method-----------: ");
	        
	        form1AbstractDyslr.setActiveFlag('Y');

	        form1AbstractDyslrRepository.save(form1AbstractDyslr);
	        	        
	        System.out.println("Here into before calling the updateDyslrFlag-----------: ");
	        
	        ApiResponse response =	lgdMapperService.updateDyslrFlag(request);
	        
	        System.out.println("Here is the response from updateDyslrFlag-----------: "+response);

	    }

	    DatabaseContextHolder.restoreCtx();
	}


	@Override
	public void deleteForm1AbstractById(long id, Locale locale,HttpServletRequest request) {
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
		Form1Abstract form1Abstract =form1AbstractRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.form.datanotfound",messageSource,locale,"Form1Abstract",id));
		form1Abstract.setActiveFlag('N');
		form1AbstractRepository.save(form1Abstract);
		DatabaseContextHolder.restoreCtx();
	}

	@Override
	public Form1AbstractDao getForm1AbstractReport(Integer districtCode,Integer talukaCode,String cCode,Locale locale,HttpServletRequest request) {
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
		List<Form1Abstract> formAbstractLst= new ArrayList<>();
		List<Object[]> liveDataLst= new ArrayList<>();
		liveDataLst=form1AbstractRepository.fetchLiveData(request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
				request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
				cCode) ;
		Form1Abstract form1AbstractR =form1AbstractRepository.findAllBycCodeAndActiveFlag(cCode,'Y');
		DatabaseContextHolder.restoreCtx();
		if(!liveDataLst.isEmpty()) {
			formAbstractLst=liveDataLst.stream().map((liveData)->{
				Form1Abstract form1Abstract=new Form1Abstract();	
				form1Abstract.setcCode(liveData[0].toString());
				form1Abstract.setTenure1Area(BigDecimal.valueOf(Double.valueOf(liveData[1]==null ? "0": liveData[1].toString())));
				form1Abstract.setTenure1Assessment(BigDecimal.valueOf(Double.valueOf(liveData[2]==null ? "0": liveData[2].toString())));
				form1Abstract.setTenure2Area(BigDecimal.valueOf(Double.valueOf(liveData[3]==null ? "0": liveData[3].toString())));
				form1Abstract.setTenure2Assessment(BigDecimal.valueOf(Double.valueOf(liveData[4]==null ? "0": liveData[4].toString())));
				form1Abstract.setTenure3Area(BigDecimal.valueOf(Double.valueOf(liveData[5]==null ? "0": liveData[5].toString())));
				form1Abstract.setTenure3Assessment(BigDecimal.valueOf(Double.valueOf(liveData[6]==null ? "0": liveData[6].toString())));
				form1Abstract.setTenure4Area(BigDecimal.valueOf(Double.valueOf(liveData[7]==null ? "0": liveData[7].toString())));
				form1Abstract.setTenure4Assessment(BigDecimal.valueOf(Double.valueOf(liveData[8]==null ? "0": liveData[8].toString())));
				form1Abstract.setPotKharabArea(BigDecimal.valueOf(Double.valueOf(liveData[9]==null ? "0": liveData[9].toString())));
				form1Abstract.setPotKharabAssessment(BigDecimal.valueOf(Double.valueOf(liveData[10]==null ? "0": liveData[10].toString())));
				form1Abstract.setRiverAreaH(BigDecimal.valueOf(Double.valueOf(liveData[11]==null ? "0": liveData[11].toString())));
				form1Abstract.setNalaAreaH(BigDecimal.valueOf(Double.valueOf(liveData[12]==null ? "0": liveData[12].toString())));
				form1Abstract.setForm3NetCultiArea(BigDecimal.valueOf(Double.valueOf(liveData[13]==null ? "0": liveData[13].toString())));
				form1Abstract.setForm3Assessment(BigDecimal.valueOf(Double.valueOf(liveData[14]==null ? "0": liveData[14].toString())));
				form1Abstract.setUnOccupied(BigDecimal.valueOf(Double.valueOf(liveData[15]==null ? "0": liveData[15].toString())));
				return form1Abstract;
			}).collect(Collectors.toList());

			if(form1AbstractR!=null){
				BeanUtils.copyProperties(form1AbstractR,formAbstractLst.get(0),"tenure1Area","tenure1Assessment","tenure2Area","tenure2Assessment","tenure3Area","tenure3Assessment","tenure4Area","tenure4Assessment",
						"potKharabArea","potKharabAssessment","riverAreaH","nalaAreaH","form3NetCultiArea","form3Assessment","unOccupied");
			}
			return toDao(formAbstractLst.get(0));

		}else {
			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
		}}else {
			throw new ResourceNotFoundException("error.form.unautorized",messageSource,locale);
		}
	}

	public Form1AbstractDao toDao(Form1Abstract form1Abstract) {
		Form1AbstractDao form1AbstractDao=new Form1AbstractDao();
			BeanUtils.copyProperties(form1Abstract, form1AbstractDao);
		return form1AbstractDao;
	}

	public Form1Abstract toEntiy(Form1AbstractDao form1AbstractDao) {
		Form1Abstract form1Abstract=new Form1Abstract();
		BeanUtils.copyProperties(form1AbstractDao, form1Abstract);
		return form1Abstract;
	}
	
	
	//****DYSLR****

	public Form1AbstractDyslrDao toDao(Form1Abstract_dyslr form1AbstractDyslr) {
		Form1AbstractDyslrDao form1AbstractDyslrDao=new Form1AbstractDyslrDao();
			BeanUtils.copyProperties(form1AbstractDyslr, form1AbstractDyslrDao);
		return form1AbstractDyslrDao;
	}
		
		public Form1Abstract_dyslr toEntiy(Form1AbstractDyslrDao form1AbstractDyslrDao) {
			Form1Abstract_dyslr form1AbstractDyslr=new Form1Abstract_dyslr();
			BeanUtils.copyProperties(form1AbstractDyslrDao, form1AbstractDyslr);
		return form1AbstractDyslr;
		}
}



//	    toDao 
//		Form1AbstractTypes form1AbstractTypes=form1AbstractTypesRepository.getById(form1Abstract.getLandType());
//		String concetter = form1AbstractTypes.getLandType().concat("-" +form1AbstractTypes.getLandSubType1())
//				.concat(form1AbstractTypes.getLandSubType2() == null ? "" : ("/" +form1AbstractTypes.getLandSubType2()))
//				.concat(form1AbstractTypes.getLandSubType3() == null ? "" : ("/" +form1AbstractTypes.getLandSubType3()));
//		String concetter1 = form1AbstractTypes.getLandTypeMarathi().concat("-" +form1AbstractTypes.getLandSubType1Marathi())
//				.concat(form1AbstractTypes.getLandSubType2Marathi() == null ? "" : ("/" +form1AbstractTypes.getLandSubType2Marathi()))
//				.concat(form1AbstractTypes.getLandSubType3Marathi() == null ? "" : ("/" +form1AbstractTypes.getLandSubType3Marathi()));
//		form1AbstractDao.setLandTypeName(concetter);
//		form1AbstractDao.setLandTypeNameMarathi(concetter1);

//	@Autowired
//	private Form1AbstractTypesRepository form1AbstractTypesRepository;


//	@Override
//	public MessageResponse save(Form1AbstractDao form1AbstractDao,Locale locale) {
//		if(form1AbstractDao.getcCode() != null) {
//			//if(!(form1AbstractRepository.existsBycCodeAndLandType(form1AbstractDao.getcCode(),form1AbstractDao.getLandType()))) {
//				Form1Abstract form1Abstract = toEntiy(form1AbstractDao);
//				form1AbstractRepository.save(form1Abstract);
//				return MessageResponse.builder().status("SUCCESS").message(messageSource.getMessage("error.form.savedata", null, locale)).build();	
////			}
////			throw new ExchangeException("error.form.already");
//		}
//		else {
//			throw new FieldValidationException("error.form.errorwhilesave");
//		}
//	}

//	@Override
//	public List<Form1AbstractDao> getForm1AbstractDetails(Integer districtCode,Integer talukaCode,String cCode,Locale locale) {
//		List<Form1Abstract> form1Abstract= form1AbstractRepository.findAllBycCodeAndDistrictCodeAndTalukaCodeAndActiveFlagOrderByLandTypeAsc(cCode,districtCode,talukaCode,'Y');
//		if(Collections.isEmpty(form1Abstract)) {
//			throw new ResourceNotFoundException("error.form.getData",messageSource,locale);
//		}
//		else {
//			return form1Abstract.stream().map(form1Ab -> toDao(form1Ab)).collect(Collectors.toList());
//		}
//	}


//	List<AbstractOccupantResponse> abstractOccupantResponses=new ArrayList<AbstractOccupantResponse>();
//			AbstractOccupantResponse occupant1=getOccupantHeads1(districtCode,talukaCode,cCode);
//			abstractOccupantResponses.add(occupant1);
//			AbstractOccupantResponse occupant2=getOccupantHeads2(districtCode,talukaCode,cCode);
//			abstractOccupantResponses.add(occupant2);
//			AbstractOccupantResponse govtless=govermentLesses(districtCode,talukaCode,cCode);
//			abstractOccupantResponses.add(govtless);
//			AbstractOccupantResponse potkharaba=potKharaba(districtCode,talukaCode,cCode);
//			abstractOccupantResponses.add(potkharaba);
//			abstractResponse.setForm1AbstractData(form1AbstractLst.stream().map(form1Abstract ->toDao(form1Abstract)).collect(Collectors.toList()));
//			abstractResponse.setAbstractOccupantResponse(abstractOccupantResponses);

//	liveData.stream().map((o)->{
//	}).collect(Collectors.toList());


//	public AbstractOccupantResponse getOccupantHeads1(Integer districtCode,Integer talukaCode,String cCode){
//		AbstractOccupantResponse abstractOccupantResponse=new AbstractOccupantResponse();
//		String occupantClass1= form1AbstractRepository.getOcupantClass1Data(cCode);
//		abstractOccupantResponse.setOccupantType("Occupant Class-1");
//		if(!occupantClass1.equals("null,null")) {
//			String[] spliter=occupantClass1.split(",");
//			List<String> oc1 = Arrays.asList(spliter);
//			abstractOccupantResponse.setArea(BigDecimal.valueOf(Double.valueOf(oc1.get(0).equals("null") ? "0" : oc1.get(0))));
//			abstractOccupantResponse.setAssessment(BigDecimal.valueOf(Double.valueOf(oc1.get(1).equals("null") ? "0" : oc1.get(1))));
//		}
//		abstractOccupantResponse.setArea(BigDecimal.ZERO);
//		abstractOccupantResponse.setAssessment(BigDecimal.ZERO);
//		return abstractOccupantResponse;
//	}
//
//	public AbstractOccupantResponse getOccupantHeads2(Integer districtCode,Integer talukaCode,String cCode){
//		AbstractOccupantResponse abstractOccupantResponse=new AbstractOccupantResponse();
//		String occupantClass2= form1AbstractRepository.getOcupantClass2Data(cCode);
//		abstractOccupantResponse.setOccupantType("Occupant Class-2");
//		if(!occupantClass2.equals("null,null")) {
//			String[] spliter2=occupantClass2.split(",");
//			List<String> oc2 = Arrays.asList(spliter2);
//			abstractOccupantResponse.setArea(BigDecimal.valueOf(Double.valueOf(oc2.get(0).toString().equals("null") ? "0" : oc2.get(0))));
//			abstractOccupantResponse.setAssessment(BigDecimal.valueOf(Double.valueOf(oc2.get(1).equals("null") ? "0" : oc2.get(1))));
//		}
//		abstractOccupantResponse.setArea(BigDecimal.ZERO);
//		abstractOccupantResponse.setAssessment(BigDecimal.ZERO);
//		return abstractOccupantResponse;
//	}
//
//	public AbstractOccupantResponse govermentLesses(Integer districtCode,Integer talukaCode,String cCode){
//		AbstractOccupantResponse abstractOccupantResponse=new AbstractOccupantResponse();
//		String govermentLessess= form1AbstractRepository.getGovermentLessess(cCode);
//		abstractOccupantResponse.setOccupantType("Goverment Lessess");
//		if(!govermentLessess.equals("null,null")) {
//			String[] spliter3=govermentLessess.split(",");
//			List<String> govles = Arrays.asList(spliter3);
//			abstractOccupantResponse.setArea(BigDecimal.valueOf(Double.valueOf(govles.get(0).equals(null) ? "0" : govles.get(0))));
//			abstractOccupantResponse.setAssessment(BigDecimal.valueOf(Double.valueOf(govles.get(1).equals(null) ? "0" : govles.get(1))));
//		}
//		abstractOccupantResponse.setArea(BigDecimal.ZERO);
//		abstractOccupantResponse.setAssessment(BigDecimal.ZERO);
//
//
//		return abstractOccupantResponse;
//	}
//
//	public AbstractOccupantResponse potKharaba(Integer districtCode,Integer talukaCode,String cCode){
//
//		AbstractOccupantResponse abstractOccupantResponse=new AbstractOccupantResponse();
//		String potkharabaDetails= form1AbstractRepository.getPotkharabaDetails(cCode);
//		abstractOccupantResponse.setOccupantType("Potkhraba");
//		if(!potkharabaDetails.equals("null,null")) {
//			String[] spliter4=potkharabaDetails.split(",");
//			List<String> potkharaba = Arrays.asList(spliter4);
//			abstractOccupantResponse.setPotkharabaAH(BigDecimal.valueOf(Double.valueOf(potkharaba.get(1).equals(null) ? "0" : potkharaba.get(1))));
//			abstractOccupantResponse.setPotkharabaBH(BigDecimal.valueOf(Double.valueOf(potkharaba.get(2).equals(null) ? "0" : potkharaba.get(2))));
//		}
//		abstractOccupantResponse.setArea(BigDecimal.ZERO);
//		abstractOccupantResponse.setAssessment(BigDecimal.ZERO);
//
//		return abstractOccupantResponse;
//	}


//form1Abstract.setNalaAssessment(BigDecimal.valueOf(Double.valueOf(liveData[12].toString())));
//form1Abstract.setRiverAssessment(BigDecimal.valueOf(Double.valueOf(liveData[14].toString())));