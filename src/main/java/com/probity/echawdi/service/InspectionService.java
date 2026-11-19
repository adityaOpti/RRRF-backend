package com.probity.echawdi.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.probity.echawdi.dao.AkrushakRateDaoInspection;
import com.probity.echawdi.dao.EHakkaTypeFiveDTO;
import com.probity.echawdi.dao.EchawdinspectionRequest;
import com.probity.echawdi.dao.EhakkaInspectionRequest;
import com.probity.echawdi.dao.FerarInspectionRequest;
import com.probity.echawdi.dao.FerarInspectionResponse;
import com.probity.echawdi.dao.FerfarDataRequestDto;
import com.probity.echawdi.dao.InspectionReportMasterDao;
import com.probity.echawdi.dao.MutationRegisterDTO;
import com.probity.echawdi.dao.RevenueTargetResponseDTO;
import com.probity.echawdi.dao.TalathiRemarkInspectionDto;
import com.probity.echawdi.dao.VasuliInspectionDTO;
import com.probity.echawdi.dao.Warg2fherfarDao;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.MutationDataException;
import com.probity.echawdi.payload.Response;


public interface InspectionService {
	
public List<MutationRegisterDTO>  FetchAllAdeshNumber(String ccode, HttpServletRequest request, Locale locale); 
	
	public List<MutationRegisterDTO>  FetchAllAdeshNumberForKalam(String ccode, HttpServletRequest request, Locale locale); 

	public List<MutationRegisterDTO>  FetchAllAdeshNumberForItar(String ccode, HttpServletRequest request, Locale locale); 
	
	List<AkrushakRateDaoInspection> getAkrushakRate(String ccode, HttpServletRequest request, Locale locale);

	public List<VasuliInspectionDTO>  getVasuliForDemand(String ccode,String revenueYear, HttpServletRequest request, Locale locale); 

	public List<VasuliInspectionDTO>  getVasuliForCollected(String ccode,String revenueYear, HttpServletRequest request, Locale locale); 

	public VasuliInspectionDTO getVasuliForEgsAndEduCess(String revenueYear, String ccode, HttpServletRequest request, Locale locale);

	public RevenueTargetResponseDTO getTargetAndSankirnDemand(String revenueYear, String ccode, HttpServletRequest request, Locale locale);

	//other ferfar
		public List<MutationRegisterDTO>  FetchAllAdeshNumberForOther(String ccode, HttpServletRequest request, Locale locale); 

		
		public List<MutationRegisterDTO>  getReEntryFerfarDetails(String ccode, HttpServletRequest request, Locale locale); 

		public List<MutationRegisterDTO>  FetchAllTantrikFerfar(String ccode, HttpServletRequest request, Locale locale) throws MutationDataException; 


	public List<MutationRegisterDTO>  getEhakkaTrutiApplication(String ccode, HttpServletRequest request, Locale locale); 


	//
	public List<MutationRegisterDTO>  FetchAllStayFerfar(String ccode, HttpServletRequest request, Locale locale) throws MutationDataException; 


	List<Warg2fherfarDao> fetchWarg2FherfarData(String ccode, HttpServletRequest request, Locale locale);


	MessageResponse saveFerfarData(HttpServletRequest request,@Valid  FerfarDataRequestDto ferfarDataRequestDto, Locale locale);


	List<MutationRegisterDTO> getPendingApplicationsForTalathiUnderThirtyDays(String ccode, String districtCode, String talukaCode, HttpServletRequest request, Locale locale) throws MutationDataException;

	List<MutationRegisterDTO> getPendingApplicationsForTalathiInMidPendingRange(String ccode, String districtCode, String talukaCode, HttpServletRequest request, Locale locale) throws MutationDataException;

	public List<MutationRegisterDTO> getPendingApplicationsOver180Days(String ccode, String districtCode,
			String talukaCode, HttpServletRequest request, Locale locale); 

	public List<MutationRegisterDTO> getPendingApplications90To180Days(String ccode, String districtCode,
			String talukaCode, HttpServletRequest request, Locale locale); 

	
	
	//save api for ferfars in Inspection
	FerarInspectionResponse saveInspection(FerarInspectionRequest request, MultipartFile file);
		
		
		//save api for ehakka in Inspection
		//FerarInspectionResponse saveEhakka(EhakkaInspectionRequest request, MultipartFile file);
		
		
		FerarInspectionResponse saveEhakka(EhakkaInspectionRequest req);
		
		
		//save api for echawdi in Inspection
		FerarInspectionResponse saveEchawdiDataForInpsection(EchawdinspectionRequest req,HttpServletRequest request);
		
		
		
		 List<EHakkaTypeFiveDTO> getEHakkaApplicationCountData(
		            HttpServletRequest request,
		            String ccode,
		            String districtCode,
		            String talukaCode,
		            Long eHakkaType);

		
		  List<EHakkaTypeFiveDTO> getEHakkaTypeFiveData(
		    		 HttpServletRequest request,
		            String ccode,
		            String districtCode,
		            String talukaCode
		            );
		  
		  
		  public List<FerarInspectionResponse> getAdeshFherfarCount(String ccode, Integer districtCode,
					Integer talukaCode,String revenueYear,Character activeFlag ,Integer ferFarType,HttpServletRequest request, Locale locale);

		
		  Long getDyslrAkarbandDeletedCount( String ccode,HttpServletRequest request , Locale local );
		  
		  MutationRegisterDTO getNirankCompletedCounts();
			
		  Response getNirankAndCompleted(String cCode, String revenueYear, HttpServletRequest request, Locale locale);

		  public VasuliInspectionDTO getVasuliSummary(
		            String ccode,
		            String revenueYear,
		            HttpServletRequest request,
		            Locale locale);

		public List<EchawdinspectionRequest> FetchAllEchawadiRemark(String ccode, Integer districtCode,
				Integer talukaCode,HttpServletRequest request);

		public Long getMaganiDurustiKhatedarCount(String ccode, String revenueYear, Character activeFlag,
				HttpServletRequest request, Locale locale);

		Long getDyslrDurustiCount(String ccode,HttpServletRequest request , Locale local );

//		public TalathiRemarkInspectionResponseDao saveRemark (TalathiRemarkInspectionResponseDao req,
//				HttpServletRequest request, Locale locale);
		
		InspectionReportMasterDao saveReport(InspectionReportMasterDao req ,HttpServletRequest request, Locale locale) ;


		public String saveRemark(TalathiRemarkInspectionDto dto ,HttpServletRequest request,
		        Locale locale);

		List <TalathiRemarkInspectionDto>getTalathiRemarkInspection(String ccode,String revenueYear ,HttpServletRequest request , Locale local);



	
}
