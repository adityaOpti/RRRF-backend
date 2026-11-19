package com.probity.echawdi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.probity.echawdi.config.WithDatabase;
import com.probity.echawdi.dao.AkrushakRateDaoInspection;
import com.probity.echawdi.dao.ApiResponse;
import com.probity.echawdi.dao.EchawdinspectionRequest;
import com.probity.echawdi.dao.EhakkaInspectionRequest;
import com.probity.echawdi.dao.FerarInspectionRequest;
import com.probity.echawdi.dao.FerarInspectionResponse;
import com.probity.echawdi.dao.FerfarDataRequestDto;
import com.probity.echawdi.dao.Form1Dao;
import com.probity.echawdi.dao.InspectionReportMasterDao;
import com.probity.echawdi.dao.MutationRegisterDTO;
import com.probity.echawdi.dao.RevenueTargetResponseDTO;
import com.probity.echawdi.dao.TalathiRemarkInspectionDto;
import com.probity.echawdi.dao.VasuliInspectionDTO;
import com.probity.echawdi.dao.Warg2fherfarDao;
import com.probity.echawdi.exception.DuplicateApplicationException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.MutationDataException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.service.Form1DyslrService;
import com.probity.echawdi.service.Form1Service;
import com.probity.echawdi.service.InspectionService;
import com.probity.echawdi.utils.DataSourceType;

@RestController
@RequestMapping("/api/inpsection")
public class InspectionController {
	
	@Autowired
	private InspectionService inspectionService;
	
	@Autowired
	private Form1Service form1Service;
	
	@Autowired
	private Form1DyslrService form1DyslrService;
	
	
	
	
	//1
	@GetMapping("/getAdeshNumberFerfar")
	public ResponseEntity<?> getMutation(@RequestParam String ccode, HttpServletRequest request, Locale locale) {
	    
	        List<MutationRegisterDTO> list = inspectionService.FetchAllAdeshNumber(ccode, request, locale);
	        return ResponseEntity.ok(list);
	    
	}
	
	
	//2
	@GetMapping("/getKalamFerfar")
	public ResponseEntity<?> getMutationForKalam(@RequestParam String ccode, HttpServletRequest request, Locale locale) {
	    
	        List<MutationRegisterDTO> list = inspectionService.FetchAllAdeshNumberForKalam(ccode, request, locale);
	        return ResponseEntity.ok(list);
	    
	}
	
	
	//3
	@GetMapping("/getItarFerfar")
	public ResponseEntity<?> getMutationForItar(@RequestParam String ccode, HttpServletRequest request, Locale locale) {
	    
	        List<MutationRegisterDTO> list = inspectionService.FetchAllAdeshNumberForItar(ccode, request, locale);
	        return ResponseEntity.ok(list);
	    
	}
	
	//4
	@GetMapping("/getAkrushakDar")
    public ResponseEntity<List<AkrushakRateDaoInspection>> getRates(@RequestParam String ccode,
                                                             HttpServletRequest request,
                                                             Locale locale) {
		List<AkrushakRateDaoInspection> data = inspectionService.getAkrushakRate(ccode, request, locale);
		
		if (data.isEmpty()) {
		            return ResponseEntity.notFound().build();
		        }
		        return ResponseEntity.ok(data);
    
	}
	
	//5
	@GetMapping("/getVasuliForDemandFor0029")
	public ResponseEntity<?> getVasuliForDemand(@RequestParam String ccode, @RequestParam String revenueYear , HttpServletRequest request, Locale locale) 
	{
	    
	 		
	        List<VasuliInspectionDTO> list = inspectionService.getVasuliForDemand(ccode,revenueYear, request, locale);
	        return ResponseEntity.ok(list);
	    
	}

	//6
	 @GetMapping("/getVasuliForCollectedFor0029")
		public ResponseEntity<?> getVasuliForCollected(@RequestParam String ccode, @RequestParam String revenueYear , HttpServletRequest request, Locale locale) 
	 {
		    
		 		
		        List<VasuliInspectionDTO> list = inspectionService.getVasuliForCollected(ccode,revenueYear, request, locale);
		        return ResponseEntity.ok(list);
		    
		}
 
	 //7
	 @GetMapping("/getVasuliForEgsAndEduCess")
	 public ResponseEntity<?> getVasuliForEgsAndEduCess(
	            @RequestParam String revenueYear,
	            @RequestParam String ccode,
	            HttpServletRequest request,
	            Locale locale
	    ) {
		 VasuliInspectionDTO res = inspectionService.getVasuliForEgsAndEduCess(revenueYear, ccode, request, locale);
		 return ResponseEntity.ok(res);
	    }
 
 
	 //8 other ferfar
	@GetMapping("/getTemplateFerfar")
	public ResponseEntity<?> getMutationForOtherFerfar(@RequestParam String ccode, HttpServletRequest request, Locale locale) {
	    
	        List<MutationRegisterDTO> list = inspectionService.FetchAllAdeshNumberForOther(ccode, request, locale);
	        return ResponseEntity.ok(list);
	    
	}
 
	//9
	@WithDatabase(DataSourceType.echmst)
	@GetMapping("/getTargetAndSankirnDemandForInspection")
	public ResponseEntity<?> getTargetAndSankirnDemand(
	            @RequestParam String revenueYear,
	            @RequestParam String ccode,
	            HttpServletRequest request,
	            Locale locale
	    ) {
		 RevenueTargetResponseDTO res = inspectionService.getTargetAndSankirnDemand(revenueYear, ccode, request, locale);
		 return ResponseEntity.ok(res);
	  }
		
 
	//10
	
 	@GetMapping("/getTantrikFerfarForInspection")
	public ResponseEntity<?> getTantrikFerfar(@RequestParam String ccode, HttpServletRequest request, Locale locale) throws MutationDataException 
 	{
	    
	        List<MutationRegisterDTO> list = inspectionService.FetchAllTantrikFerfar(ccode, request, locale);
	        return ResponseEntity.ok(list);
	    
	}



 	//11
 	@GetMapping("/getReEntryFerfarDetails")
 	public ResponseEntity<?> getReEntryFerfarDetails(@RequestParam String ccode,
                                                        HttpServletRequest request,
                                                        Locale locale) {
	  
	  List<MutationRegisterDTO> list = inspectionService.getReEntryFerfarDetails(ccode, request, locale);
     return ResponseEntity.ok(list);
	  

 	}

 	//12
	 @GetMapping("/getEhakkaTrutiApplication")
	 public ResponseEntity<?> getEhakkaTrutiApplication(@RequestParam String ccode,
	                                                           HttpServletRequest request,
	                                                           Locale locale) {
		  
		  List<MutationRegisterDTO> list = inspectionService.getEhakkaTrutiApplication(ccode, request, locale);
	        return ResponseEntity.ok(list);
		  

	 }
	 
	 
	 
	//13
	@GetMapping("/getStayFerfarForInspection")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getStayFerfar(@RequestParam String ccode, HttpServletRequest request, Locale locale) throws MutationDataException {
		    
		        List<MutationRegisterDTO> list = inspectionService.FetchAllStayFerfar(ccode, request, locale);
		        return ResponseEntity.ok(list);
		       
		    
	}
	  	


	//14
	@WithDatabase(DataSourceType.echmst)
	@PostMapping("/saveFerfarRemark")
	public MessageResponse saveFerfarData(HttpServletRequest request, @RequestBody FerfarDataRequestDto ferfarDataRequestDto ,Locale locale) {
	  //ferfarDataService.saveFerfarData(ferfarDataRequestDto);
		return inspectionService.saveFerfarData(request,ferfarDataRequestDto,locale);
	
	}

	   //15
	  @GetMapping("/getWarg2FherfarData")
	  public ResponseEntity<?> getWargferfar(@RequestParam String ccode,HttpServletRequest request, Locale locale) {
	  List<Warg2fherfarDao> data = inspectionService.fetchWarg2FherfarData(ccode,request, locale);
	
	  if (data.isEmpty()) {
	      return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                           .body("Warg 2 data not found");
	  }
	
	  return ResponseEntity.ok(data);
	}

	  	
	  
	  	//Below 30 Days - E-hakka Pralambit
		@GetMapping("/getPendingApplicationsForTalathiUnderThirtyDays")
		@ResponseStatus(HttpStatus.OK)
		public ResponseEntity<List<MutationRegisterDTO>> getPendingApplicationsForTalathiUnderThirtyDays(
		        @RequestParam String ccode,
		        @RequestParam(required = false) String districtCode,
		        @RequestParam(required = false) String talukaCode,
		        HttpServletRequest request,
		        Locale locale) throws MutationDataException {

		    List<MutationRegisterDTO> list = inspectionService.getPendingApplicationsForTalathiUnderThirtyDays(ccode, districtCode, talukaCode, request, locale);
		    return ResponseEntity.ok(list);
		}

		
		//30 Days to 90 Days - E-hakka Pralambit
		@GetMapping("/getPendingApplicationsForTalathiInMidPendingRange")
		@ResponseStatus(HttpStatus.OK)
		public ResponseEntity<List<MutationRegisterDTO>> getPendingApplicationsForTalathiInMidPendingRange(
		        @RequestParam String ccode,
		        @RequestParam(required = false) String districtCode,
		        @RequestParam(required = false) String talukaCode,
		        HttpServletRequest request,
		        Locale locale) throws MutationDataException {

		    List<MutationRegisterDTO> list = inspectionService.getPendingApplicationsForTalathiInMidPendingRange(ccode, districtCode, talukaCode, request, locale);
		    return ResponseEntity.ok(list);
		}
	
		
		
		// Above 180 Days
		 @GetMapping("/getPendingApplicationsOver180Days")
		    public ResponseEntity<?> getPendingApplicationsOver180Days(@RequestParam String ccode,@RequestParam String districtCode , @RequestParam String talukaCode, 
		                                                           HttpServletRequest request,
		                                                           Locale locale) {
			  
			  List<MutationRegisterDTO> list = inspectionService.getPendingApplicationsOver180Days(ccode,districtCode,talukaCode, request, locale);
		        return ResponseEntity.ok(list);
			  

		    }

		 //Above 90 to 180 days
		 @GetMapping("/getPendingApplications90To180Days")
		    public ResponseEntity<?> getPendingApplications90To180Days(@RequestParam String ccode,@RequestParam String districtCode , @RequestParam String talukaCode, 
		                                                           HttpServletRequest request,
		                                                           Locale locale) {
			  
			  List<MutationRegisterDTO> list = inspectionService.getPendingApplications90To180Days(ccode,districtCode,talukaCode, request, locale);
		        return ResponseEntity.ok(list);
			 

		}
		 
		 
			@GetMapping("/form1OdcDiff")
			@ResponseStatus(HttpStatus.OK)
			public List<Form1Dao> form1OdcDiff(@RequestParam String cCode,Locale locale,HttpServletRequest request){
				return form1Service.form1OdcDiff(cCode,locale,request);
			}
			

			@GetMapping("/getFormDyslrDeleteEntries")
			@ResponseStatus(HttpStatus.OK)
			public Response getFormDyslrDeleteEntries(
					@RequestParam String cCode,
					HttpServletRequest request,
					Locale locale){
				return form1DyslrService.getFormDyslrDeleteEntries(cCode,request,locale);
			}
			
			
			
			//All Save Api's 
			
			// Save api with remark and duplicate updation saveFerfarForInspection - shubham : 10 Feb 2026
			
			@CrossOrigin(origins = "*")
			@PostMapping(
			    value = "/saveFerfarForInspection",
			    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
			)
			public ResponseEntity<ApiResponse<?>> save(
			        @RequestParam("data") String json,
			        @RequestParam(value = "File", required = false ) MultipartFile file 
			) {
			    try {
			        ObjectMapper mapper = new ObjectMapper();
			        FerarInspectionRequest request =
			                mapper.readValue(json, FerarInspectionRequest.class);

			        FerarInspectionResponse response =
			                inspectionService.saveInspection(request, file);

			        ApiResponse<FerarInspectionResponse> api =
			                ApiResponse.<FerarInspectionResponse>builder()
			                        .status(HttpStatus.CREATED.value())
			                        .message("Record saved successfully")
			                        .data(response)
			                        .build();

			        return ResponseEntity.status(HttpStatus.CREATED).body(api);

			    } catch (DuplicateApplicationException ex) {

			        ApiResponse<String> errorResponse =
			                ApiResponse.<String>builder()
			                        .status(HttpStatus.CONFLICT.value())
			                        .message(ex.getMessage())
			                        .data(null)
			                        .build();

			        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);

			    } catch (Exception ex) {

			        ApiResponse<String> errorResponse =
			                ApiResponse.<String>builder()
			                        .status(HttpStatus.BAD_REQUEST.value())
			                        .message("Invalid request data")
			                        .data(null)
			                        .build();

			        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
			    }
			}
			
			
			/* 10 Feb 2026 Working controller -- Tushar
			 * 
			 * @CrossOrigin("*")
		  	@PostMapping(
		  	    value = "/saveFerfarForInspection",
		  	    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
		  	)
		  	public ResponseEntity<ApiResponse<FerarInspectionResponse>> save(
		  	        @RequestParam("data") String json,
		  	        @RequestParam("File") MultipartFile file
		  	) throws JsonProcessingException {
				
				
		  	    ObjectMapper mapper = new ObjectMapper();
		  	    FerarInspectionRequest request = mapper.readValue(json, FerarInspectionRequest.class);

		  	    FerarInspectionResponse response = inspectionService.saveInspection(request, file);

		  	    ApiResponse<FerarInspectionResponse> api = ApiResponse.<FerarInspectionResponse>builder()
		  	            .status(HttpStatus.CREATED.value())
		  	            .message("Record saved successfully")
		  	            .data(response)
		  	            .build();

		  	    return new ResponseEntity<>(api, HttpStatus.CREATED);
		  	}*/
		  	
		  	
		  	
		  /*	@CrossOrigin("*")
		  	@PostMapping(
		  	    value = "/saveEHakkaForInspection",
		  	    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
		  	)
		  	public ResponseEntity<ApiResponse<FerarInspectionResponse>> saveEhakka(
		  	        @RequestParam("data") String json,
		  	        @RequestParam("File") MultipartFile file
		  	) throws JsonProcessingException {

		  	    ObjectMapper mapper = new ObjectMapper();
		  	  EhakkaInspectionRequest request = mapper.readValue(json, EhakkaInspectionRequest.class);

		  	    FerarInspectionResponse response = inspectionService.saveEhakka(request, file);

		  	    ApiResponse<FerarInspectionResponse> api = ApiResponse.<FerarInspectionResponse>builder()
		  	            .status(HttpStatus.CREATED.value())
		  	            .message("Record saved successfully")
		  	            .data(response)
		  	            .build();

		  	    return new ResponseEntity<>(api, HttpStatus.CREATED);
		  	}*/
		  	
		  	
		  //working 	
	/*	  	
			@PostMapping(value = "/saveEHakkaForInspection")
		  	public ResponseEntity<ApiResponse<FerarInspectionResponse>> saveEchawdiDataForInpsection(
		  	      @RequestBody EhakkaInspectionRequest req
	 	       
		  	) {

		  	    FerarInspectionResponse response = inspectionService.saveEhakka(req);

		  	    ApiResponse<FerarInspectionResponse> api = ApiResponse.<FerarInspectionResponse>builder()
		  	            .status(HttpStatus.CREATED.value())
		  	            .message("Record saved successfully")
		  	            .data(response)
		  	            .build();

		  	    return new ResponseEntity<>(api, HttpStatus.CREATED);
		  	}
		  	
		*/
			@PostMapping(value = "/saveEHakkaForInspection")
			public ResponseEntity<ApiResponse<?>> saveEchawdiDataForInpsection(@RequestBody EhakkaInspectionRequest req) {
			    try {
			        FerarInspectionResponse response = inspectionService.saveEhakka(req);
			        
			        ApiResponse<FerarInspectionResponse> api = ApiResponse.<FerarInspectionResponse>builder()
			                .status(HttpStatus.CREATED.value())
			                .message("Record saved successfully")
			                .data(response)
			                .build();

			        return new ResponseEntity<>(api, HttpStatus.CREATED);
			        
			    } catch (DuplicateApplicationException ex) {
			        ApiResponse<String> errorResponse = ApiResponse.<String>builder()
			                .status(HttpStatus.CONFLICT.value())
			                .message(ex.getMessage())
			                .data(null)
			                .build();
			        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
			    }
			    
			    
			}
			
			
		  	@PostMapping(value = "/saveEchawdiDataForInspection")
		  	public ResponseEntity<ApiResponse<FerarInspectionResponse>> saveEchawdiDataForInpsection(
		  	      @RequestBody EchawdinspectionRequest req,
		  	      HttpServletRequest request		
	 	       
		  	) {

		  	    FerarInspectionResponse response = inspectionService.saveEchawdiDataForInpsection(req,request );
		  	    
		  	    
		  	    
		  	  // Check if it's an error response -- modification part done by shubham 
			    if (response.getId() == null) {
			        ApiResponse<FerarInspectionResponse> api = ApiResponse.<FerarInspectionResponse>builder()
			                .status(HttpStatus.CONFLICT.value())
			                .message(response.getRemark())
			                .data(null)
			                .build();
			        return new ResponseEntity<>(api, HttpStatus.CONFLICT);
			    }

		  	    

		  	    ApiResponse<FerarInspectionResponse> api = ApiResponse.<FerarInspectionResponse>builder()
		  	            .status(HttpStatus.CREATED.value())
		  	            .message("Record saved successfully")
		  	            .data(response)
		  	            .build();

		  	    return new ResponseEntity<>(api, HttpStatus.CREATED);
		  	}

		  	
		  	//ehakkaFetchDoc 
		  	
		  	@WithDatabase(DataSourceType.echmst)
		    @GetMapping("/getEHakkaApplicationCountDetails")
			public ResponseEntity<?> getEHakkaApplicationCountDetails(HttpServletRequest request,
					@RequestParam("ccode") String ccode, @RequestParam("districtCode") String districtCode,
					@RequestParam("talukaCode") String talukaCode, @RequestParam("eHakkaType") Long eHakkaType) {
				try {
					return ResponseEntity.ok(inspectionService.getEHakkaApplicationCountData(request, ccode, districtCode,
							talukaCode, eHakkaType));
				} catch (IllegalArgumentException ex) {
					return ResponseEntity.badRequest().body("Invalid type id");
				} catch (Exception ex) {
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
							.body("Error fetching EHakka application data");
				}
			}

//		  	@WithDatabase(DataSourceType.echmst)
//		    @GetMapping("/getEHakkaTypeFiveDetails")
//			public ResponseEntity<?> getEHakkaTypeFiveDetails(HttpServletRequest request, @RequestParam String ccode,
//					@RequestParam String districtCode, @RequestParam String talukaCode, @RequestParam Long eHakkaType) {
//
//				try {
//					return ResponseEntity
//							.ok(inspectionService.getEHakkaTypeFiveData(request, ccode, districtCode, talukaCode, eHakkaType));
//				} catch (IllegalArgumentException ex) {
//					return ResponseEntity.badRequest().body("Invalid type id");
//				}
//
//			}
		  	
		  	@WithDatabase(DataSourceType.echmst)
		    @GetMapping("/getEHakkaTypeFiveDetails")
			public ResponseEntity<?> getEHakkaTypeFiveDetails(HttpServletRequest request, @RequestParam String ccode,
					@RequestParam String districtCode, @RequestParam String talukaCode) {

				try {
					return ResponseEntity
							.ok(inspectionService.getEHakkaTypeFiveData(request, ccode, districtCode, talukaCode));
				} catch (IllegalArgumentException ex) {
					return ResponseEntity.badRequest().body("Invalid type id");
				}

			}

		  	
		  	@WithDatabase(DataSourceType.echmst)
		  	@GetMapping("/FetchAllFerfarSavedData")
			 public ResponseEntity<?> getAdeshFerfarNumbers(
			         @RequestParam String ccode,
			         @RequestParam Integer districtCode,
			         @RequestParam Integer talukaCode,
			         @RequestParam String revenueYear,
			         @RequestParam Character activeFlag,
			         @RequestParam Integer ferFarType,
			         HttpServletRequest request,
			         Locale locale
			 ) {

			     System.out.println("Controller hit, ferFarType = " + ferFarType);

			     if ( ferFarType < 0 || ferFarType > 8) {

			         Map<String, Object> response = new HashMap<>();
			         response.put("message", "Invalid ferfar type. Allowed values are 1 to 8");
			         response.put("data", null);

			         return ResponseEntity
			                 .status(HttpStatus.BAD_REQUEST)
			                 .body(response);
			     }

			     List<FerarInspectionResponse> dtoList =
			             inspectionService.getAdeshFherfarCount(
			                     ccode, districtCode, talukaCode,
			                     revenueYear, activeFlag, ferFarType,
			                     request, locale
			             );

			     if (dtoList == null || dtoList.isEmpty()) {
			         Map<String, Object> response = new HashMap<>();
			         response.put("message", "निरक: Data not present in given fherfar");
			         response.put("data", null);
			         return ResponseEntity.ok(response);
			     }

			     return ResponseEntity.ok(dtoList);
			 }
		  	
		  	
		  	//dyslr deleted count api
		  	@GetMapping("/getDyslrAkarbandDeletedCount")
			 public ResponseEntity<MutationRegisterDTO> getDyslrAkarbandDeletedCount(
			         @RequestParam String ccode,
			         HttpServletRequest request,
			         Locale locale
			 ) {
			     Long count = inspectionService.getDyslrAkarbandDeletedCount(
			             ccode,
			             request,
			             locale
			     );

			     MutationRegisterDTO dto = new MutationRegisterDTO();
			     dto.setDyslrAkarbandDeletedCount(count != null ? count : 0L);

			     return ResponseEntity.ok(dto);
			 }
		  	

		  	//NirankCount Task
		  	@WithDatabase(DataSourceType.echmst)
		    @GetMapping("/getNirankCounts")
		    @ResponseStatus(HttpStatus.OK)
		    public MutationRegisterDTO getNirankCounts() {
		        return inspectionService.getNirankCompletedCounts();
		    }
			
			 //Nirank Echawadi
		  	 @WithDatabase(DataSourceType.echmst)
		  	 @GetMapping("/getNirankandCompleted")
		  	 public Response getNirankAndCompleted(@RequestParam String cCode,@RequestParam String revenueYear,HttpServletRequest request,Locale locale) {
		  			return inspectionService.getNirankAndCompleted(cCode,revenueYear,request,locale);
		  	 }
		  			
		  	 
		  	 //ReportVasuliApi -AP
		  	@GetMapping("/getTotalVasuliForReport")
		    public ResponseEntity<VasuliInspectionDTO> getVasuliSummary(
		            @RequestParam String ccode,
		            @RequestParam String revenueYear,
		            HttpServletRequest request,
		            Locale locale) {

		        VasuliInspectionDTO response =
		        		inspectionService.getVasuliSummary(ccode, revenueYear, request, locale);

		        return ResponseEntity.ok(response);
		    }

		  	@GetMapping("/getEchawadiRemark")
			 @WithDatabase(DataSourceType.echmst)
		  	public ResponseEntity<?> getEchawadiRemark(@RequestParam String ccode,@RequestParam("districtCode") Integer districtCode,
					@RequestParam("talukaCode") Integer talukaCode,
					HttpServletRequest request
					) {
			    
		        List<EchawdinspectionRequest> list = inspectionService.FetchAllEchawadiRemark(ccode,districtCode ,talukaCode, request);
		        return ResponseEntity.ok(list);
		    
		}
		  	
		  	@GetMapping("/getMaganiDurustiKhatedarCount")
			// public ResponseEntity<Long> getMaganiDurustiKhatedarCount(
			 public ResponseEntity<MutationRegisterDTO> getMaganiDurustiKhatedarCount(

			         @RequestParam String ccode,
			         @RequestParam String revenueYear,
			         HttpServletRequest request,
			         Locale locale
			 ) {
			     Character activeFlag = 'E';

			     Long count = inspectionService.getMaganiDurustiKhatedarCount(
			             ccode,
			             revenueYear,
			             activeFlag,
			             request,
			             locale
			     );
           System.out.println(+count);
			     //return ResponseEntity.ok(count);
           MutationRegisterDTO dto = new MutationRegisterDTO();
           dto.setMaganiDurustiKhatedarCount(count != null ? count : 0L);

           return ResponseEntity.ok(dto);
       }

		  	@GetMapping("/getDyslrDurustiCount")
			 public ResponseEntity<MutationRegisterDTO> getDyslrDurustiCount(
			         @RequestParam String ccode,
			         HttpServletRequest request,
			         Locale locale
			 ) {
			     Long count = inspectionService.getDyslrDurustiCount(
			             ccode,
			             request,
			             locale
			     );

			     MutationRegisterDTO dto = new MutationRegisterDTO();
			     dto.setDyslrDurustiCount(count != null ? count : 0L);

			     return ResponseEntity.ok(dto);
			 }

		 

		    @PostMapping("/saveInspectionOficerMasterRemark")
		      public ResponseEntity<?> saveInspectionReportMaster(
		              @RequestBody InspectionReportMasterDao requestDto,
		              HttpServletRequest request,
		              Locale locale) {

		          InspectionReportMasterDao response =
		        		  inspectionService.saveReport(requestDto, request, locale);

		       //  return ResponseEntity.ok(response);
		         
		     return ResponseEntity.status(HttpStatus.CREATED).body(" Report Data save successfully ");

		          
		      }
		  	  
		  	 @PostMapping("/saveTalathiRemark")
		     public ResponseEntity<?> saveRemark(
		             @RequestBody TalathiRemarkInspectionDto dto,HttpServletRequest request,
		              Locale locale) {

		         String response = inspectionService.saveRemark(dto,request,locale);

		        // return ResponseEntity.ok(response);
			     return ResponseEntity.status(HttpStatus.CREATED).body(" Report Data save successfully ");

		     }
		  	 
		  	 @GetMapping("/getTalathiRemarkInspection")
			 public ResponseEntity<?> getTalathiRemarkInspection(
			         @RequestParam String ccode,@RequestParam String revenueYear,
			         HttpServletRequest request,
			         Locale locale
			 ) {
			     List<TalathiRemarkInspectionDto> fillist = inspectionService.getTalathiRemarkInspection(
			    		 ccode, revenueYear, request, locale);

			     return ResponseEntity.ok(fillist);
			 }


}
