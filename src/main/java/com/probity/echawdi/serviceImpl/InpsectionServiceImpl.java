package com.probity.echawdi.serviceImpl;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.AkrushakRateDaoInspection;
import com.probity.echawdi.dao.EHakkaTypeFiveDTO;
import com.probity.echawdi.dao.EchawdinspectionRequest;
import com.probity.echawdi.dao.EhakkaInspectionRequest;
import com.probity.echawdi.dao.FerarInspectionRequest;
import com.probity.echawdi.dao.FerarInspectionResponse;
import com.probity.echawdi.dao.FerfarDataRequestDto;
import com.probity.echawdi.dao.InspectionReportMasterDao;
import com.probity.echawdi.dao.MutationRegisterDTO;
import com.probity.echawdi.dao.NirankDao;
import com.probity.echawdi.dao.RevenueTargetResponseDTO;
import com.probity.echawdi.dao.TalathiRemarkInspectionDto;
import com.probity.echawdi.dao.VasuliInspectionDTO;
import com.probity.echawdi.dao.Warg2fherfarDao;
import com.probity.echawdi.entity.EChawdiTypeMaster;
import com.probity.echawdi.entity.EHakkaInspection;
import com.probity.echawdi.entity.EHakkaTypeMaster;
import com.probity.echawdi.entity.EchawdiInspection;
import com.probity.echawdi.entity.FerarInspection;
import com.probity.echawdi.entity.FerfarData;
import com.probity.echawdi.entity.FerfarType;
import com.probity.echawdi.entity.FerfarTypeMaster;
import com.probity.echawdi.entity.InspectionReportMaster;
import com.probity.echawdi.entity.RemarkType;
import com.probity.echawdi.entity.TalathiRemarkInspection;
import com.probity.echawdi.exception.DuplicateApplicationException;
import com.probity.echawdi.exception.DuplicateRemarkException;
import com.probity.echawdi.exception.InvalidRemarkTypeException;
import com.probity.echawdi.exception.MessageResponse;
import com.probity.echawdi.exception.MutationDataException;
import com.probity.echawdi.exception.ResourceNotFoundException;
import com.probity.echawdi.exception.VasuliDataException;
import com.probity.echawdi.payload.Response;
import com.probity.echawdi.repository.AkrushakDarRepository;
import com.probity.echawdi.repository.EHakkaTypeMasterRepository;
import com.probity.echawdi.repository.EchawdiInspectionRepository;
import com.probity.echawdi.repository.EchawdiTypeMasterRepository;
import com.probity.echawdi.repository.EhakkaInspectionRepository;
import com.probity.echawdi.repository.FerarInspectionRepository;
import com.probity.echawdi.repository.FerfarDataRepository;
import com.probity.echawdi.repository.FerfarTypeMasterRepository;
import com.probity.echawdi.repository.FerfarTypeRepository;
import com.probity.echawdi.repository.InspectionReportMasterRepository;
import com.probity.echawdi.repository.LandRevenueDemandPreRepository;
import com.probity.echawdi.repository.LandRevenueDemandRepository;
import com.probity.echawdi.repository.MutRegisterRepository;
import com.probity.echawdi.repository.NirankRepository;
import com.probity.echawdi.repository.RemarkTypeRepository;
import com.probity.echawdi.repository.TalathiRemarkInspectionRepository;
import com.probity.echawdi.service.InspectionService;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.CommonConstants.DateFormats;
import com.probity.echawdi.utils.CommonConstants.FileName;
import com.probity.echawdi.utils.DataSourceKeeper;
import com.probity.echawdi.utils.StaticPropertyFetcher;



@Service
public class InpsectionServiceImpl implements InspectionService{
	
	@Autowired
	private MutRegisterRepository mutRegisterRepo;
	@Autowired
	private AkrushakDarRepository akrushakDarRepository;
	@Autowired
	private DataSourceKeeper dataSourceKeeper;
	
	@Autowired
	private LandRevenueDemandRepository landRevenueDemandRepo;
	
	@Autowired
	private FerfarTypeMasterRepository ferfarTypeMasterRepository;
	
	@Autowired
	private LandRevenueDemandPreRepository  landRevenueDemandRepoPre;
	
	@Autowired
	private FerfarDataRepository ferfarDataRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private FerfarTypeRepository ferfarTypeRepository;

	@Autowired
    private FerarInspectionRepository ferarRepo;
    
    @Autowired
    private EhakkaInspectionRepository EhakkaRepo;
 
    @Autowired
    private EchawdiInspectionRepository EchawdiRepo;

    @Autowired
    private RemarkTypeRepository remarkRepo;
	
	@Autowired
	private EHakkaTypeMasterRepository  ehakkaTypeRepository;
	
	
	@Autowired
	private EchawdiTypeMasterRepository  echawdiTypeRepository;

	@Autowired
	private NirankRepository nirankRepository;

@Autowired	
private TalathiRemarkInspectionRepository	talathiRemarkInspectionRepository;
@Autowired
private InspectionReportMasterRepository inspectionReportMasterRepository;

	@Override
	public List<MutationRegisterDTO> FetchAllAdeshNumber(String ccode, HttpServletRequest request, Locale locale) {
		
		try {
		
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"));
		
		
		System.out.println("mhrhost----------:"+request.getHeader("mhrHost"));
		System.out.println("mhrDbName----------:"+request.getHeader("mhrDbName"));
		System.out.println("mhrSchemaName----------:"+request.getHeader("mhrSchemaName"));
		
		
		
		List<Object[]> mutData = mutRegisterRepo.FetchAllAdeshNumberandMutationDate(
				request.getHeader("mhrHost"),
	            request.getHeader("mhrDbName"),
	            request.getHeader("mhrSchemaName"),
				CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, 
				ccode, 
				CommonConstants.DefaultDataPort);
		
		 List<MutationRegisterDTO> result = new ArrayList<>();
		
		
	        for (Object[] row : mutData) {
	        	
	            MutationRegisterDTO mutDto = new MutationRegisterDTO();

	            mutDto.setMutNo(row[0] != null ? ((Number) row[0]).intValue() : null);
	            mutDto.setMutType(row[1] != null ? ((Number) row[1]).intValue() : null);

	            mutDto.setMutDate(row[2] != null ? ((java.sql.Date) row[2]).toLocalDate() : null);

	            mutDto.setSurveyHissaNo(row[3] != null ? row[3].toString() : null);

	            mutDto.setIsRemarkSubmitted(false);

	            result.add(mutDto);
	        }

	        return result;
	} catch (Exception ex) {
        // Use custom exception 
        throw new MutationDataException("error.fetch.mutation.list", ex);
    }

		
		
		
}
	

	@Override
	public List<MutationRegisterDTO> FetchAllAdeshNumberForKalam(String ccode, HttpServletRequest request, Locale locale) {
		
		try {
		
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"));
		
			
		List<Object[]> mutData = mutRegisterRepo.FetchAllAdeshNumberandMutationDateForKalam(
				request.getHeader("mhrHost"),
	            request.getHeader("mhrDbName"),
	            request.getHeader("mhrSchemaName"),
				CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, 
				ccode, 
				CommonConstants.DefaultDataPort);
		
		 List<MutationRegisterDTO> result = new ArrayList<>();
		
		
	        for (Object[] row : mutData) {
	        	
	            MutationRegisterDTO mutDto = new MutationRegisterDTO();

	            mutDto.setMutNo(row[0] != null ? ((Number) row[0]).intValue() : null);
	            mutDto.setMutType(row[1] != null ? ((Number) row[1]).intValue() : null);

	            mutDto.setMutDate(row[2] != null ? ((java.sql.Date) row[2]).toLocalDate() : null);

	            mutDto.setSurveyHissaNo(row[3] != null ? row[3].toString() : null);

	            mutDto.setIsRemarkSubmitted(false);


	            result.add(mutDto);
	        }

	        return result;
	} catch (Exception ex) {
        // Use custom exception 
        throw new MutationDataException("error.fetch.mutation.list", ex);
    }
	
 }
	
	
	@Override
	public List<MutationRegisterDTO> FetchAllAdeshNumberForItar(String ccode, HttpServletRequest request, Locale locale) {
		
		try {
			
			System.out.println("mhrhost----------:"+request.getHeader("mhrHost"));
			System.out.println("mhrDbName----------:"+request.getHeader("mhrDbName"));
			System.out.println("mhrSchemaName----------:"+request.getHeader("mhrSchemaName"));
			
		
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"));
		
		
		
			
		List<Object[]> mutData = mutRegisterRepo.FetchAllAdeshNumberandMutationDateForItar(
				request.getHeader("mhrHost"),
	            request.getHeader("mhrDbName"),
	            request.getHeader("mhrSchemaName"),
				CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, 
				ccode, 
				CommonConstants.DefaultDataPort);
		
		 List<MutationRegisterDTO> result = new ArrayList<>();
		
		
	        for (Object[] row : mutData) {
	        	
	            MutationRegisterDTO mutDto = new MutationRegisterDTO();

	            mutDto.setMutNo(row[0] != null ? ((Number) row[0]).intValue() : null);
	            mutDto.setMutDate(row[1] != null ? LocalDate.parse(row[1].toString()) : null);
	            mutDto.setSurveyHissaNo(row[2] != null ? row[2].toString() : null);

	            mutDto.setIsRemarkSubmitted(false);

	            result.add(mutDto);
	        }

	        return result;
	} catch (Exception ex) {
        // Use custom exception 
        throw new MutationDataException("error.fetch.mutation.list", ex);
    }
	
 }
	
	
	 @Override
	    public List<AkrushakRateDaoInspection> getAkrushakRate(String ccode, HttpServletRequest request, Locale locale) {
	    	
			dataSourceKeeper.setDataSourceEchwadi(request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"));

			String mhrHost = request.getHeader("mhrHost");
			String mhrDbName = request.getHeader("mhrDbName");
			String mhrSchemaName = request.getHeader("mhrSchemaName");

			System.out.println("mhrHost: " + mhrHost);
			System.out.println("mhrDbName: " + mhrDbName);
			System.out.println("mhrSchemaName: " + mhrSchemaName);
			
	        List<Object[]> rawResults = akrushakDarRepository.fetchAkruShakRate(
		             request.getHeader("mhrHost"),
		             request.getHeader("mhrDbName"),
		             request.getHeader("mhrSchemaName"),
		             CommonConstants.mhrorUser,
		             CommonConstants.mhrorPassword,
		             CommonConstants.DefaultDataPort,
		             ccode
	        );
	        
	        for (Object[] row : rawResults) {
	            for (Object column : row) {
	                System.out.print(column + " ");
	            }
	            System.out.println(); // Move to the next line after each row
	        }
	        
	        

	        List<AkrushakRateDaoInspection> resultList = new ArrayList<>();

	        for (Object[] row : rawResults) {
	            BigDecimal t2_rate = (BigDecimal) row[0]; 
	            String t2_flag = (String) row[1];         
	            String t2_name = (String) row[2];         
	            String t1_flag = (String) row[3];         
	            String piase_ccode = (String) row[4]; 
	            System.out.println("Paise flag------: "+t1_flag);
	            String rate_ccode = (String) row[5];     

	            boolean hasRate = t2_rate != null;
	            boolean hasPaise = (t1_flag != null && (t1_flag.contains("5") || t1_flag.contains("10")));

	            if (hasRate || hasPaise) {
	            	AkrushakRateDaoInspection dto = new AkrushakRateDaoInspection();

	                dto.setRate(t2_rate);
	                dto.setName(t2_name);
	                dto.setFlag(t2_flag);

	                // Handle Nagarparishad vs Mahanagarpalika logic
	                if (t2_name != null && t2_name.contains("नगरपरिषद")) {
	                    dto.setNprate(t2_rate);
	                    dto.setMnparate(null);
	                } else {
	                    dto.setMnparate(t2_rate);
	                    dto.setNprate(null);
	                }

	                // 5 and 10 paise flags
	                dto.setFivepaise((t1_flag != null && t1_flag.contains("5")) ? 5 : 0);
	                dto.setTenpaise((t1_flag != null && t1_flag.contains("10")) ? 10 : 0);

	                // Declaration flag
	                dto.setDeclaration((t2_flag != null && t2_flag.equalsIgnoreCase("Y")) ? "Y" : "N");

	                resultList.add(dto);
	            }
	        }

	        return resultList;
	    }
	

	
	    @Override
		public List<VasuliInspectionDTO> getVasuliForDemand(String ccode, String revenueYear, 
		                                                                HttpServletRequest request, Locale locale) {
		    try {
		       
		        
		        System.out.println("dbName :"+request.getHeader("echDbName"));

		        System.out.println("schemaName :"+request.getHeader("echSchemaName"));
		        
		        System.out.println("hostname :"+request.getHeader("echHost"));
		        
		        System.out.println("ccode: " + ccode);
		        System.out.println("revenueYear: " + revenueYear);

		        // Fetch data from repository
		        List<Object[]> vasuliData = landRevenueDemandRepo.findByCcodeForDemand(
		                request.getHeader("echHost"),
		                request.getHeader("echDbName"),
		                request.getHeader("echSchemaName"),
		                CommonConstants.echUser,
		                CommonConstants.echPassword,
		                ccode,
		                revenueYear
		        );
		        
		        
		        System.out.println("Fetched vasuliData size: " + vasuliData.size());
		        if (vasuliData.isEmpty()) {
		            System.out.println("No data found.");
		        }

		        
		        for (Object[] row : vasuliData) {
		            for (Object col : row) {
		                System.out.print(col + "\t");
		            }
		            System.out.println(); // new line after each row
		        }


		        List<VasuliInspectionDTO> result = new ArrayList<>();

		        for (Object[] row : vasuliData) {
		        	
		        	
		        	try {
		        		
		        		 VasuliInspectionDTO dto = new VasuliInspectionDTO();
		 	            int idx = 0;
		    
		 	            dto.setCcode((String) row[idx++]); // ccode

		 	            
		 	            
		 	            // Bindumala
		 	            dto.setBinJmDemand((Double) row[idx++]);
		 	            dto.setBinGpDemand((Double) row[idx++]);
		 	            dto.setBinZpDemand((Double) row[idx++]);
		 	            dto.setBinTotalDemand((Double) row[idx++]);

		 	            // Akrushik
		 	            dto.setAkJmDemand((Double) row[idx++]);
		 	            dto.setAkGpDemand((Double) row[idx++]);
		 	            dto.setAkZpDemand((Double) row[idx++]);
		 	            dto.setAkTotalDemand((Double) row[idx++]);

		 	            // Sankirn
		 	            dto.setSanJmDemand((Double) row[idx++]);
		 	            dto.setSanGpDemand((Double) row[idx++]);
		 	            dto.setSanZpDemand((Double) row[idx++]);
		 	            dto.setSanTotalDemand((Double) row[idx++]);
		 	            
		 	           
		 	            // Total
		 	           dto.setTotalDemand((Double) row[idx++]);
		 	            
		 	            
		 	            
		 	            result.add(dto);
		        		
		        	}catch(Exception e) {
		        		System.out.println("Error at Demand Vasuli Method Inspeciton : "+e.getMessage());
		        	}
		        	
		           
		        }

		        return result;

		    } catch (Exception ex) {
		    	throw new VasuliDataException("error.fetch.demand.vasuli.list", ex);
		    }
		   
		}
		
		
		@Override
		public List<VasuliInspectionDTO> getVasuliForCollected(String ccode, String revenueYear, 
		                                                                HttpServletRequest request, Locale locale) {
		    try {
		       
		        
		        System.out.println("dbName :"+request.getHeader("echDbName"));

		        System.out.println("schemaName :"+request.getHeader("echSchemaName"));
		        
		        System.out.println("hostname :"+request.getHeader("echHost"));
		        
		        System.out.println("ccode: " + ccode);
		        System.out.println("revenueYear: " + revenueYear);

		        // Fetch data from repository
		        List<Object[]> vasuliData = landRevenueDemandRepo.findByCcodeForCollected(
		                request.getHeader("echHost"),
		                request.getHeader("echDbName"),
		                request.getHeader("echSchemaName"),
		                CommonConstants.echUser,
		                CommonConstants.echPassword,
		                ccode,
		                revenueYear
		        );
		        
		        
		        System.out.println("Fetched vasuliData size: " + vasuliData.size());
		        if (vasuliData.isEmpty()) {
		            System.out.println("No data found.");
		        }

		        
		        for (Object[] row : vasuliData) {
		            for (Object col : row) {
		                System.out.print(col + "\t");
		            }
		            System.out.println(); // new line after each row
		        }


		        List<VasuliInspectionDTO> result = new ArrayList<>();

		        for (Object[] row : vasuliData) {
		        	
		        	try {
		        		
		        		VasuliInspectionDTO dto = new VasuliInspectionDTO();
			            
			            int i = 0;

			            dto.setCcode((String) row[i++]);

			            // Bindumala
			            dto.setBinJmCollected((Double) row[i++]);
			            dto.setBinGpCollected((Double) row[i++]);
			            dto.setBinZpCollected((Double) row[i++]);
			            dto.setBinTotalCollected((Double) row[i++]);

			            // Akrushik
			            dto.setAkJmCollected((Double) row[i++]);
			            dto.setAkGpCollected((Double) row[i++]);
			            dto.setAkZpCollected((Double) row[i++]);
			            dto.setAkTotalCollected((Double) row[i++]);

			            // Sankirn
			            dto.setSanJmCollected((Double) row[i++]);
			            dto.setSanGpCollected((Double) row[i++]);
			            dto.setSanZpCollected((Double) row[i++]);
			            dto.setSanTotalCollected((Double) row[i++]);


			            
			            // Overall
			            dto.setTotalCollected((Double) row[i++]);
			            
			            result.add(dto);
		        		
		        	}catch(Exception e) {
		        		System.out.println("error at collected method :"+e.getMessage());
		        	}
		            
		        }

		        return result;

		    } catch (Exception ex) {
		        throw new VasuliDataException("error.fetch.collected.vasuli.list", ex);
		    }
		}
		
		
		//For Vasuli API 0045
		
		@Override
		public VasuliInspectionDTO getVasuliForEgsAndEduCess(String revenueYear, String ccode, HttpServletRequest request, Locale locale) {


		    VasuliInspectionDTO dto = new VasuliInspectionDTO();
		    try {
		        // Fetch current year summary correctly
		        Object[] currentYear = (Object[]) landRevenueDemandRepo.getCurrentYearSummary(
		        		request.getHeader("echHost"),
		        		request.getHeader("echDbName"),
		        		request.getHeader("echSchemaName"),
		                CommonConstants.echUser,
		                CommonConstants.echPassword,
		                ccode,
		                revenueYear
		        );
		       // System.out.println("Current Year Result: " + Arrays.toString(currentYear));

		        // Fetch previous year summary correctly
		        Object[] previousYear = (Object[]) landRevenueDemandRepoPre.getPreviousYearSummary(
		        		request.getHeader("echHost"),
		        		request.getHeader("echDbName"),
		        		request.getHeader("echSchemaName"),
		                CommonConstants.echUser,
		                CommonConstants.echPassword,
		                ccode
		        );
		       
		       // System.out.println("Previous Year Result: " + Arrays.toString(previousYear));

		        // Set previous year values 
		        if (previousYear != null) {
		            dto.setPrevEgsDemand(previousYear[0] != null ? ((Number) previousYear[0]).longValue() : 0L);
		            dto.setPrevEgsCollected(previousYear[1] != null ? ((Number) previousYear[1]).longValue() : 0L);
		            dto.setPrevEduCessDemand(previousYear[2] != null ? ((Number) previousYear[2]).longValue() : 0L);
		            dto.setPrevEduCessCollected(previousYear[3] != null ? ((Number) previousYear[3]).longValue() : 0L);
		        }

		        // Set current year values 
		        if (currentYear != null) {
		            dto.setCurrEgsDemand(currentYear[0] != null ? ((Number) currentYear[0]).longValue() : 0L);
		            dto.setCurrEgsCollected(currentYear[1] != null ? ((Number) currentYear[1]).longValue() : 0L);
		            dto.setCurrEduCessDemand(currentYear[2] != null ? ((Number) currentYear[2]).longValue() : 0L);
		            dto.setCurrEduCessCollected(currentYear[3] != null ? ((Number) currentYear[3]).longValue() : 0L);
		        }

		        // Calculate totals
		        dto.setTotalEgsDemand(dto.getPrevEgsDemand() + dto.getCurrEgsDemand());
		        dto.setTotalEgsCollected(dto.getPrevEgsCollected() + dto.getCurrEgsCollected());
		        dto.setTotalEduCessDemand(dto.getPrevEduCessDemand() + dto.getCurrEduCessDemand());
		        dto.setTotalEduCessCollected(dto.getPrevEduCessCollected() + dto.getCurrEduCessCollected());


		    } catch (Exception e) {
		        System.err.println("Error fetching tax summary: " + e.getMessage());
		        e.printStackTrace();
		       
		    }

		    return dto;
		}
		
		
		
		//API FOR TARGET AND SANKIRN TOTAL
		@Override
		public RevenueTargetResponseDTO getTargetAndSankirnDemand(String ccode, String revenueYear, 
		                                                                HttpServletRequest request, Locale locale) {
		   
		       
		        
		        System.out.println("dbName :"+request.getHeader("echDbName"));

		        System.out.println("schemaName :"+request.getHeader("echSchemaName"));
		        
		        System.out.println("hostname :"+request.getHeader("echHost"));
		        
		        System.out.println("ccode: " + ccode);
		        
		        System.out.println("revenueYear: " + revenueYear);

		   //     try {
		        	
		        	// Fetch data from repository
			        Object[] result = (Object[]) landRevenueDemandRepo.findByCcodeForSankirnCollected(
			                request.getHeader("echHost"),
			                request.getHeader("echDbName"),
			                request.getHeader("echSchemaName"),
			                CommonConstants.echUser,
			                CommonConstants.echPassword,
			                ccode,
			               revenueYear
			        );
			        
			        //System.out.println(Arrays.toString(result));

			        
			          if (result == null) {
			            return new RevenueTargetResponseDTO(0.0, 0.0, 0.0, 0.0, null);
			        }

			        // Map query result → DTO
			        Double totalJmSankirn = result[0] != null ? ((Number) result[0]).doubleValue() : 0.0;
			        Double totalGpSankirn = result[1] != null ? ((Number) result[1]).doubleValue() : 0.0;
			        Double totalZpSankirn = result[2] != null ? ((Number) result[2]).doubleValue() : 0.0;
			        Double grandTotal     = result[3] != null ? ((Number) result[3]).doubleValue() : 0.0;
			        String annualVillageTarget = result[4] != null ? result[4].toString() : null;

			        return new RevenueTargetResponseDTO(
			                totalJmSankirn,
			                totalGpSankirn,
			                totalZpSankirn,
			                grandTotal,
			                annualVillageTarget
			        );
		       
		        
		   
//		    } catch (Exception ex) {
//		        throw new VasuliDataException("error.fetch.collected.vasuli.list", ex);
//		    }
		}
		
		
		//Other ferfar
		@Override
		public List<MutationRegisterDTO> FetchAllAdeshNumberForOther(String ccode, HttpServletRequest request, Locale locale) {
			
			try {
			
			dataSourceKeeper.setDataSourceEchwadi(request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"));
			
				
			List<Object[]> mutData = mutRegisterRepo.fetchMutationSurveyHissaForOtherFerfar(
					request.getHeader("mhrHost"),
		            request.getHeader("mhrDbName"),
		            request.getHeader("mhrSchemaName"),
					CommonConstants.mhrorUser,
					CommonConstants.mhrorPassword, 
					ccode, 
					CommonConstants.DefaultDataPort);
			
			 List<MutationRegisterDTO> result = new ArrayList<>();
			
			
		        for (Object[] row : mutData) {
		        	
		            MutationRegisterDTO mutDto = new MutationRegisterDTO();

	                if (row[0] != null) {
	                    mutDto.setMutNo(((Number) row[0]).intValue());
	                }

	                if (row[1] != null) {
	                    mutDto.setMutType(((Number) row[1]).intValue());
	                }

	                if (row[2] != null) {
	                    mutDto.setMutDate(((java.sql.Date) row[2]).toLocalDate());
	                }

	                if (row[3] != null) {
	                    mutDto.setSurveyHissaNo(row[3].toString());
	                }

	                mutDto.setIsRemarkSubmitted(false);


		            result.add(mutDto);
		        }

		        return result;
		} catch (Exception ex) {
	        // Use custom exception 
	        throw new MutationDataException("error.fetch.mutation.list", ex);
	    }
		
	 }
		
		
	
		@Override
		public List<MutationRegisterDTO> getReEntryFerfarDetails(String ccode, HttpServletRequest request, Locale locale) {
			
			try {
			
			dataSourceKeeper.setDataSourceEchwadi(request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"));
			
			

			
			List<Object[]> mutData = mutRegisterRepo.fetchReEntryFerfarDetails(
					request.getHeader("mhrHost"),
		            request.getHeader("mhrDbName"),
		            request.getHeader("mhrSchemaName"),
					CommonConstants.mhrorUser,
					CommonConstants.mhrorPassword, 
					CommonConstants.DefaultDataPort);
			
			 List<MutationRegisterDTO> result = new ArrayList<>();
			
			
		        for (Object[] row : mutData) {
		        	
		            MutationRegisterDTO mutDto = new MutationRegisterDTO();

		            mutDto.setMutNo(row[0] != null ? ((Number) row[0]).intValue() : null);

		            mutDto.setMutDate(row[1] != null ? ((java.sql.Date) row[1]).toLocalDate() : null);
		            mutDto.setMutType(row[2] != null ? ((Number) row[2]).intValue() : null);
		            mutDto.setMutName(row[3] != null ? row[3].toString() : null);

		            mutDto.setSurveyHissaNo(row[4] != null ? row[4].toString() : null);

		            mutDto.setIsRemarkSubmitted(false);

		            result.add(mutDto);
		        }

		        return result;
		} catch (Exception ex) {
	        // Use custom exception 
	        throw new MutationDataException("error.fetch.mutation.list", ex);
	    }

	}

		
		 @Override
		 public List<MutationRegisterDTO> FetchAllTantrikFerfar(String ccode, HttpServletRequest request, Locale locale) throws MutationDataException {

		        try {
		            dataSourceKeeper.setDataSourceEchwadi(
		                    request.getHeader("mhrDbName"), 
		                    request.getHeader("mhrSchemaName"));

		            List<Object[]> mutData = mutRegisterRepo.fetchalltantrikMutationData(
		                    request.getHeader("mhrHost"),
		                    request.getHeader("mhrDbName"),
		                    request.getHeader("mhrSchemaName"),
		                    CommonConstants.mhrorUser,
		                    CommonConstants.mhrorPassword, 
		                    ccode,
		                    CommonConstants.DefaultDataPort);

		            List<MutationRegisterDTO> result = new ArrayList<>();

		            for (Object[] row : mutData) {
			        	
			            MutationRegisterDTO mutDto = new MutationRegisterDTO();

			            mutDto.setMutNo(row[0] != null ? ((Number) row[0]).intValue() : null);

			            mutDto.setMutDate(row[1] != null ? ((java.sql.Date) row[1]).toLocalDate() : null);
			            mutDto.setMutType(row[2] != null ? ((Number) row[2]).intValue() : null);
			            mutDto.setMutName(row[3] != null ? row[3].toString() : null);

			            mutDto.setSurveyHissaNo(row[4] != null ? row[4].toString() : null);

			            mutDto.setIsRemarkSubmitted(false);


			            result.add(mutDto);
			        }


		            return result;

		        } catch (Exception ex) {
		            // Use custom exception 
		            throw new MutationDataException("error.fetch.mutation.list", ex);
		        }
		    }
		
	

		// Helper method
		private Double toDouble(Object obj) {
		    if (obj == null) return 0d;
		    if (obj instanceof Number) return ((Number) obj).doubleValue();
		    return Double.parseDouble(obj.toString());
		}
	
	
	@Override
	public List<MutationRegisterDTO> getEhakkaTrutiApplication(String ccode, HttpServletRequest request, Locale locale) {
		
		try {
		
		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"));
		
	
		
		List<Object[]> mutData = mutRegisterRepo.fetchEhkkaInpsection(
				CommonConstants.HostEhakka,
				CommonConstants.mhrorUser,
				CommonConstants.mhrorPassword, 
				CommonConstants.DefaultDataPort
				);
		
		 List<MutationRegisterDTO> result = new ArrayList<>();
		 
		
		
		
	        for (Object[] row : mutData) {
	        	
	            MutationRegisterDTO mutDto = new MutationRegisterDTO();

      
	            mutDto.setApplicationId(row[0] != null ? new BigInteger(row[0].toString()) : null);
	            mutDto.setAppDate(row[1] != null ? LocalDate.parse(row[1].toString()) : null);
	            mutDto.setRejReason(row[2] != null ? (row[2].toString()) : null);
	         	mutDto.setIsRemarkSubmitted(false);

	            result.add(mutDto);
	        }

	        return result;
	} catch (Exception ex) {
        // Use custom exception 
        throw new MutationDataException("error.fetch.mutation.list", ex);
    }

}
	
	
	 	@Override
	    public List<MutationRegisterDTO> FetchAllStayFerfar(String ccode, HttpServletRequest request, Locale locale)
	            throws MutationDataException {

	        try {
	            // Set dynamic datasource
	            dataSourceKeeper.setDataSourceEchwadi(request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"));

	            List<Object[]> mutData = mutRegisterRepo.fetchAllStayFerfarMutationData(
	                    request.getHeader("mhrHost"),
	                    request.getHeader("mhrDbName"),
	                    request.getHeader("mhrSchemaName"),
	                    CommonConstants.mhrorUser,
	                    CommonConstants.mhrorPassword,
	                    ccode,
	                    CommonConstants.DefaultDataPort
	            );

	            System.out.println("Fetched rows: " + mutData.size());

	            List<MutationRegisterDTO> result = new ArrayList<>();

	            for (Object[] row : mutData) {

	                MutationRegisterDTO mutDto = new MutationRegisterDTO();

	                // Correct indices
	                mutDto.setMutNo(row[0] != null ? ((Number) row[0]).intValue() : null);
	                mutDto.setStayOrderDate(row[1] != null ? LocalDate.parse(row[1].toString()) : null);

	                // Compute isRemarkSubmitted from stay_remark (row[2])
	                mutDto.setIsRemarkSubmitted(row[2] != null && !row[2].toString().trim().isEmpty());
		            mutDto.setSurveyHissaNo(row[3] != null ? row[3].toString() : null);

	                result.add(mutDto);
	            }

	            return result;

	        } catch (Exception ex) {
	            throw new MutationDataException("error.fetch.mutation.list", ex);
	        }
	    }

	
	 	@Override
		public MessageResponse saveFerfarData(HttpServletRequest request, @Valid FerfarDataRequestDto ferfarDataRequestDto,
				Locale locale) {
						//dataSourceKeeper.setDataSourceEchwadi(request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"));

						 //working  
						FerfarTypeMaster ferfarType = ferfarTypeMasterRepository.findById(ferfarDataRequestDto.getFerfarTypeId())
						        .orElseThrow(() -> new IllegalArgumentException("Invalid ferfarTypeId: " + ferfarDataRequestDto.getFerfarTypeId()));


								System.out.println("my used schema name is " +ferfarType);
//
//						List<Object[]> f17=form17Repository.findByKhataNo(
//								CommonConstants.AugDatamhrHost,request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword,
//								form17Dao.getcCode(),form17Dao.getKhataNo(),CommonConstants.AugDataPort
//								);
//						
						
						FerfarData data = new FerfarData();

						data.setDistrictCode(ferfarDataRequestDto.getDistrictCode());
						data.setTalukaCode(ferfarDataRequestDto.getTalukaCode());
						data.setCircleCode(ferfarDataRequestDto.getCircleCode());
						data.setVillageCode(ferfarDataRequestDto.getVillageCode());
						data.setCcode(ferfarDataRequestDto.getCcode());
						data.setFerfarType(ferfarType); // set the full entity
						//data.setFerfarType(ferfarDataRequestDto.getFerfarType()); // set the full entity

						data.setMutNo(ferfarDataRequestDto.getMutNo());
						data.setMutDate(ferfarDataRequestDto.getMutDate());
						data.setDocumentReference(ferfarDataRequestDto.getDocumentReference());
						data.setRemark(ferfarDataRequestDto.getRemark());

//						data.setStatus("PENDING");
//						data.setIsVerified(false);
//						data.setActiveFlag(true);

						data.setCreatedBy(ferfarDataRequestDto.getCreatedBy());
						//data.setCreatedDate(LocalDateTime.now());
						data.setUpdatedBy(ferfarDataRequestDto.getUpdatedBy());
						
						//data.setUpdatedDate(LocalDateTime.now());

						ferfarDataRepository.save(data);
						DatabaseContextHolder.restoreCtx();
						return MessageResponse.builder().status("SUCCESS").message(
								messageSource.getMessage("error.form.savedata", null, locale)).build();	
								
			}
	 	
	/* 	
	 	
		@Override
		public List<Warg2fherfarDao> fetchWarg2FherfarData(String ccode, HttpServletRequest request, Locale locale) {
			// TODO Auto-generated method stub


				dataSourceKeeper.setDataSourceEchwadi(request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"));

				String mhrHost = request.getHeader("mhrHost");
				String mhrDbName = request.getHeader("mhrDbName");
				String mhrSchemaName = request.getHeader("mhrSchemaName");

				System.out.println("mhrHost: " + mhrHost);

		        List<Object[]> resultList = mutRegisterRepo.fetchWarg2FherfarData(
		        request.getHeader("mhrHost"),
		        request.getHeader("mhrDbName"),
		        request.getHeader("mhrSchemaName"),
		        CommonConstants.mhrorUser,
		        CommonConstants.mhrorPassword,
		        CommonConstants.DefaultDataPort
		       // ccode
		        );
		        
		       
		        if (resultList != null && !resultList.isEmpty()) {
		            Object[] row = resultList.get(0); 

		            //old
		            Warg2fherfarDao dto = new Warg2fherfarDao();
//		            dto.setMutNo((Integer) row[0]);
//		            dto.setMutDate((LocalDate) row[1]);
//		            dto.setMutType((Integer) row[2]);
//		            dto.setMutName((String) row[3]);
//			           dto.setCcode((String)row[4]);
//			            //return dto;
//			            return List.of(dto);
		            dto.setCcode(row[0] != null ? row[0].toString() : null);

		            dto.setMutType(row[1] != null ? Integer.parseInt(row[1].toString()) : null);

		            dto.setMutNo(row[2] != null ? Integer.parseInt(row[2].toString()) : null);

		      //      dto.setMutDate(row[3] != null ? ((Date) row[3]).toLocalDate() : null); // or parse if String
		            
//		            dto.setMutDate(
//		            	    row[3] != null ? ((java.sql.Date) row[3]).toLocalDate() : null
//		            	);
		            
//		            
//		            dto.setMutDate(
//		            	    row[3] != null
//		            	        ? ((java.sql.Timestamp) row[3]).toLocalDateTime().toLocalDate()
//		            	        : java.time.LocalDate.of(1900, 1, 1)
//		            	);
		            
		            
		            
		            dto.setMutDate(
		            	    row[3] != null
		            	        ? (row[3] instanceof java.sql.Timestamp
		            	            ? ((java.sql.Timestamp) row[3]).toLocalDateTime().toLocalDate() // Handle Timestamp
		            	            : ((java.sql.Date) row[3]).toLocalDate()) // Handle Date
		            	        : java.time.LocalDate.of(1900, 1, 1) // Default date
		            	);



		            
		            dto.setMutName(row[4] != null ? row[4].toString() : null);
		            //  return List.of(dto);
		            return Collections.singletonList(dto); // I used this bcz project compile on above java 8 and on server its run on java 8 only.


		        }
				return null;

			}
		
		*/
	 	
	 	public List<Warg2fherfarDao> fetchWarg2FherfarData(String ccode, HttpServletRequest request, Locale locale) {

	 		try {
	 		
	 		dataSourceKeeper.setDataSourceEchwadi(request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"));
	 		
	 			
	 		List<Object[]> mutData = mutRegisterRepo.fetchWarg2FherfarData(
	 				request.getHeader("mhrHost"),
	 	            request.getHeader("mhrDbName"),
	 	            request.getHeader("mhrSchemaName"),
	 				CommonConstants.mhrorUser,
	 				CommonConstants.mhrorPassword, 
	 				CommonConstants.DefaultDataPort);
	 		
	 		 List<Warg2fherfarDao> result = new ArrayList<>();
	 		
	 		
	 	        for (Object[] row : mutData) {
	 	        	
	 	        	Warg2fherfarDao mutDto = new Warg2fherfarDao();

//	 	           mutDto.setMutNo(row[0] != null ? ((Number) row[0]).intValue() : null);
//	 	            mutDto.setMutType(row[1] != null ? ((Number) row[1]).intValue() : null);
	 	//
//	 	            mutDto.setMutDate(row[2] != null ? ((java.sql.Date) row[2]).toLocalDate() : null);
	 	//
//	 	            mutDto.setSurveyHissaNo(row[3] != null ? row[3].toString() : null);
	 	//
//	 	            mutDto.setIsRemarkSubmitted(false);
	 	        	mutDto.setCcode(row[0] != null ? row[0].toString() : null);

	 	            mutDto.setMutType(row[1] != null ? ((Number) row[1]).intValue() : null);
	 	            mutDto.setMutNo(row[2] != null ? ((Number) row[2]).intValue() : null);

	 	            mutDto.setMutDate(row[3] != null ? ((java.sql.Date) row[3]).toLocalDate() : null);
	 	            mutDto.setMutName(row[4] != null ? row[4].toString() : null);

	 	            mutDto.setSurveyHissaNo(row[5] != null ? row[5].toString() : null);



	 	            result.add(mutDto);
	 	        }

	 	        return result;
	 	} catch (Exception ex) {
	 	    // Use custom exception 
	 	    throw new MutationDataException("error.fetch.mutation.list", ex);
	 	}

	 	}

		
		@Override
		public List<MutationRegisterDTO> getPendingApplicationsForTalathiUnderThirtyDays(String ccode, String districtCode, String talukaCode, HttpServletRequest request, Locale locale) throws MutationDataException {

		    try {
		     

		        List<Object[]> mutData = mutRegisterRepo.fetchPendingApplicationsForTalathiUnderThirtyDays(
		        		CommonConstants.HostEhakka,
						CommonConstants.mhrorUser,
						CommonConstants.mhrorPassword, 
						CommonConstants.DefaultDataPort,
						//ccode,
						districtCode,
						talukaCode
						
		        );

		        List<MutationRegisterDTO> result = new ArrayList<>();

		        if (mutData == null || mutData.isEmpty()) {
		            System.out.println("No data returned from DB.");
		        }

		        for (Object[] row : mutData) {
		            MutationRegisterDTO mutDto = new MutationRegisterDTO();

		            if (row[0] != null) mutDto.setApplicationId(new BigInteger(row[0].toString()));
		            if (row[1] != null) {
		                if (row[1] instanceof java.sql.Date) {
		                    mutDto.setAppDate(((java.sql.Date) row[1]).toLocalDate());
		                } else {
		                    mutDto.setAppDate(LocalDate.parse(row[1].toString()));
		                }
		            }

		            mutDto.setIsRemarkSubmitted(false);
		            result.add(mutDto);
		        }

		        return result;

		    } catch (Exception ex) {
		        ex.printStackTrace(); 
		        throw new MutationDataException("error.fetch.mutation.list", ex);
		    }
		}
		
		
		
		@Override
		public List<MutationRegisterDTO> getPendingApplicationsForTalathiInMidPendingRange(String ccode, String districtCode, String talukaCode, HttpServletRequest request, Locale locale) throws MutationDataException {

		    try {
		       
		    	//dataSourceKeeper.setDataSourceEchwadi(request.getHeader("mhrDbName"), request.getHeader("mhrSchemaName"));
				
		    	
		        List<Object[]> mutData = mutRegisterRepo.fetchPendingApplicationsForTalathiInMidPendingRange(
		        		CommonConstants.HostEhakka,
						CommonConstants.mhrorUser,
						CommonConstants.mhrorPassword, 
						CommonConstants.DefaultDataPort,
						//ccode,
						districtCode,
						talukaCode
						
		        );

		        List<MutationRegisterDTO> result = new ArrayList<>();

		        if (mutData == null || mutData.isEmpty()) {
		            System.out.println("No data returned from DB.");
		        }

		        for (Object[] row : mutData) {
		            MutationRegisterDTO mutDto = new MutationRegisterDTO();

		            if (row[0] != null) mutDto.setApplicationId(new BigInteger(row[0].toString()));
		            if (row[1] != null) {
		                if (row[1] instanceof java.sql.Date) {
		                    mutDto.setAppDate(((java.sql.Date) row[1]).toLocalDate());
		                } else {
		                    mutDto.setAppDate(LocalDate.parse(row[1].toString()));
		                }
		            }

		            mutDto.setIsRemarkSubmitted(false);
		            result.add(mutDto);
		        }

		        return result;

		    } catch (Exception ex) {
		        ex.printStackTrace(); 
		        throw new MutationDataException("error.fetch.mutation.list", ex);
		    }
		}
		
		
		@Override
		public List<MutationRegisterDTO> getPendingApplicationsOver180Days(String ccode, String districtCode,
				String talukaCode, HttpServletRequest request, Locale locale) 

		{
			try {
				
					
						List<Object[]> mutData = mutRegisterRepo.fetchPendingApplicationsOver180Days(
								CommonConstants.HostEhakka,
								CommonConstants.mhrorUser,
								CommonConstants.mhrorPassword, 
								CommonConstants.DefaultDataPort,
								//ccode,
								districtCode,
								talukaCode
						);
				
				 List<MutationRegisterDTO> result = new ArrayList<>();
				 
				
				
				
			        for (Object[] row : mutData) {
			        	
			            MutationRegisterDTO mutDto = new MutationRegisterDTO();

		      
			            mutDto.setApplicationId(row[0] != null ? new BigInteger(row[0].toString()) : null);
			            mutDto.setAppDate(row[1] != null ? LocalDate.parse(row[1].toString()) : null);
			            mutDto.setRejReason(row[2] != null ? (row[2].toString()) : null);
			         	mutDto.setIsRemarkSubmitted(false);

			            result.add(mutDto);
			        }

			        return result;
			} catch (Exception ex) {
		        // Use custom exception 
		        throw new MutationDataException("error.fetch.mutation.list", ex);
		    }
		}
		

		@Override
		public List<MutationRegisterDTO> getPendingApplications90To180Days(String ccode, String districtCode,
				String talukaCode, HttpServletRequest request, Locale locale) 

		{
			try {
				
			
						List<Object[]> mutData = mutRegisterRepo.fetchPendingApplications90To180Days(
								CommonConstants.HostEhakka,
								CommonConstants.mhrorUser,
								CommonConstants.mhrorPassword, 
								CommonConstants.DefaultDataPort,
							//	ccode,
								districtCode,
								talukaCode
						);
				
				 List<MutationRegisterDTO> result = new ArrayList<>();
				 
				
				
				
			        for (Object[] row : mutData) {
			        	
			            MutationRegisterDTO mutDto = new MutationRegisterDTO();

		      
			            mutDto.setApplicationId(row[0] != null ? new BigInteger(row[0].toString()) : null);
			            mutDto.setAppDate(row[1] != null ? LocalDate.parse(row[1].toString()) : null);
			          //  mutDto.setRejReason(row[2] != null ? (row[2].toString()) : null);
			         	mutDto.setIsRemarkSubmitted(false);

			            result.add(mutDto);
			        }

			        return result;
			} catch (Exception ex) {
		        // Use custom exception 
		        throw new MutationDataException("error.fetch.mutation.list", ex);
		    }
		}

		
		//All save apis
		@Override
		public FerarInspectionResponse saveInspection(FerarInspectionRequest req, MultipartFile file) {

			
			System.out.println("Into the InspectionSave method ----------------- : "+req.toString()
			+" here is the file --------------- : "+file);
			
		  
			if (ferarRepo.existsByMutNo(req.getMutNo())) {
		        throw new DuplicateApplicationException(
		                "Mutation Number  already exists: " + req.getMutNo());
		        
		    }
			
		    // Step 4: Find remark type automatically
		    RemarkType remarkType = remarkRepo.findByRemarkTypeName(req.getRemarkType());
		    if (remarkType == null) {
		        throw new InvalidRemarkTypeException("Remark type not found: " + req.getRemarkType());
		    }

		    
		    FerarInspection entity = new FerarInspection();
		    
		    boolean abx = file != null;
		    System.out.println("file is null or not--------------- : "+abx);
		    
		    if(file != null) {
		    	 // Step 5: Create DB entity
			   
		    	 //  store full physical path in DB
			    String file0029 = getFilename(file,  req);
			    
			    entity.setDoc_path(file0029);

			    
		    }
		    	
		    	
		    	
		    	entity.setTalukaCode(Integer.valueOf(req.getTalukaCode()));
		    	entity.setDistrictCode(Integer.valueOf(req.getDistrictCode()));

			    entity.setCcode(req.getCcode());
			    entity.setRevenue_year(req.getRevenueYear());
			    entity.setMutNo(req.getMutNo());
			 
		        
		        
		        FerfarType ferfarType = ferfarTypeRepository
		                .findById(Long.valueOf(req.getFerfar_type()))
		                .orElseThrow(() ->
		                        new ResourceNotFoundException("Invalid ferfar type or ferfar type not found with provided type_id")
		                );

			    
		        entity.setRemark(req.getRemark());
		        
		        entity.setFerfarType(ferfarType);
			    
			    entity.setCreateDtTm(LocalDateTime.now());
			    entity.setUpdateDtTm(LocalDateTime.now());
			 
			    
			    // auto remark type
			    entity.setRemarkType(remarkType);

			    // active flag auto-set
			    entity.setActive_flag("Y");
			    
			    entity.setIsRemarkSubmitted('Y');
		    
		   

		    System.out.println("Into the InspectionSave method before saving  ----------------- : "+req.toString());
		    // Step 6: Save DB record
		    FerarInspection saved = ferarRepo.save(entity);

		    return FerarInspectionResponse.builder()
		            .id(saved.getId())
		            .remarkType(remarkType.getRemarkTypeName())
		            .documentPath(saved.getDoc_path())   // return stored path
		            .build();
		}
		
		
		
		
	/*	@Override
		public FerarInspectionResponse saveEhakka(EhakkaInspectionRequest req, MultipartFile file) {

		  
		    // Step 4: Find remark type automatically
		    RemarkType remarkType = remarkRepo.findByRemarkTypeName(req.getRemarkType());
		    if (remarkType == null) {
		        throw new InvalidRemarkTypeException("Remark type not found: " + req.getRemarkType());
		    }

		    
		    EHakkaInspection entity = new EHakkaInspection();
		    
		    if(file != null) {
		    	 // Step 5: Create DB entity
			   
		    	 //  store full physical path in DB
			    String ehakkaTypeDocPath = getEhakkaFilename(file,  req);
		    	
		    	//entity.setTalukaCode(req.getTalukaCode());
		    	//entity.setDistrictCode(req.getDistrictCode());
		    	
		    	entity.setTalukaCode(Integer.valueOf(req.getTalukaCode()));
		    	entity.setDistrictCode(Integer.valueOf(req.getDistrictCode()));

			    entity.setCcode(req.getCcode());
			    
			    entity.setRevenueYear(req.getRevenueYear());
			    
			    entity.setRemark(req.getRemark());
			    
			    entity.getApplicationId();
			 
		        
		        
			    EHakkaTypeMaster eHakkaType = ehakkaTypeRepository
		                .findById(Long.valueOf(req.getEhakkaType()))
		                .orElseThrow(() ->
		                        new ResourceNotFoundException("Invalid Ehakka_Type or Ehakka_Type not found")
		                );

			   	entity.setEHakkaType(eHakkaType);
			    
			    entity.setCreateDtTm(LocalDateTime.now());
			    entity.setUpdateDtTm(LocalDateTime.now());
			 
			    entity.setDocPath(ehakkaTypeDocPath);
			    
			    entity.setActiveFlag("Y");
			   
			    entity.setTypeOfRemark(remarkType);
			    
		    }
		    // Step 6: Save DB record
		    EHakkaInspection saved = EhakkaRepo.save(entity);

		    return FerarInspectionResponse.builder()
		            .id(saved.getId())
		            .remarkType(remarkType.getRemarkTypeName())
		            .documentPath(saved.getDocPath())   // return stored path
		            .build();
		}*/

		
		
		@Override
		public FerarInspectionResponse saveEhakka(EhakkaInspectionRequest req) {

		  
			if (EhakkaRepo.existsByApplicationId(req.getApplicationid())) {
		        throw new DuplicateApplicationException(
		                "Application ID already exists: " + req.getApplicationid());
		        
		    }
			
		    // Step 4: Find remark type automatically
		    RemarkType remarkType = remarkRepo.findByRemarkTypeName(req.getRemarkType());
		    if (remarkType == null) {
		        throw new InvalidRemarkTypeException("Remark type not found: " + req.getRemarkType());
		    }

		    
		    EHakkaInspection entity = new EHakkaInspection();
		    
		
		    	 // Step 5: Create DB entity
			   
		    	 //  store full physical path in DB
			  //  String ehakkaTypeDocPath = getEhakkaFilename(file,  req);
		    	
		    	//entity.setTalukaCode(req.getTalukaCode());
		    	//entity.setDistrictCode(req.getDistrictCode());
		    	
		    	entity.setTalukaCode(Integer.valueOf(req.getTalukaCode()));
		    	entity.setDistrictCode(Integer.valueOf(req.getDistrictCode()));

			    entity.setCcode(req.getCcode());
			    
			    entity.setRevenueYear(req.getRevenueYear());
			    
			    entity.setRemark(req.getRemark());
			    
			   // entity.getApplicationId();
			    
			    entity.setApplicationId(req.getApplicationid());
			 
		        
		        
			    EHakkaTypeMaster eHakkaType = ehakkaTypeRepository
		                .findById(Long.valueOf(req.getEhakkaType()))
		                .orElseThrow(() ->
		                        new ResourceNotFoundException("Invalid Ehakka_Type or Ehakka_Type not found")
		                );

			   	entity.setEHakkaType(eHakkaType);
			    
			    entity.setCreateDtTm(LocalDateTime.now());
			    entity.setUpdateDtTm(LocalDateTime.now());
			 
			  //  entity.setDocPath(ehakkaTypeDocPath);
			    
			    entity.setActiveFlag("Y");
			   
			    entity.setTypeOfRemark(remarkType);
			    entity.setIsRemarkSubmitted('Y');
		    
		    // Step 6: Save DB record
		    EHakkaInspection saved = EhakkaRepo.save(entity);

		    return FerarInspectionResponse.builder()
		            .id(saved.getId())
		            .remarkType(remarkType.getRemarkTypeName())
		            .documentPath(saved.getDocPath())   // return stored path
		            .build();
		}

		
		
		@Override
		public FerarInspectionResponse saveEchawdiDataForInpsection(EchawdinspectionRequest req, 
				HttpServletRequest request) {

			dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
			
				System.out.println("request.getHeader(\"echDbName\") : --- "+request.getHeader("echDbName"));
				System.out.println("request.getHeader(\"echSchemaName\") : --- "+request.getHeader("echSchemaName"));
		
				EchawdiInspection entity = new EchawdiInspection();
		    
		 
		    	entity.setTalukaCode(Integer.valueOf(req.getTalukaCode()));
		    	entity.setDistrictCode(Integer.valueOf(req.getDistrictCode()));

			    entity.setCcode(req.getCcode());
			    
			    entity.setRevenueYear(req.getRevenueYear());
			    
			   	        
			    EChawdiTypeMaster echawdiType = echawdiTypeRepository
		                .findById(Long.valueOf(req.getEChawdiType()))
		                .orElseThrow(() ->
		                        new ResourceNotFoundException("Invalid EchawdiType or EchawdiType not found")
		                );
			    
			    
			    /*Modification change */
			    
			    Integer districtCode = Integer.valueOf(req.getDistrictCode());
			    Integer talukaCode = Integer.valueOf(req.getTalukaCode());
			    
			    boolean exists = EchawdiRepo
			            .existsByEchawdiTypeAndDistrictCodeAndTalukaCodeAndCcodeAndRevenueYearAndActiveFlag(
			                    echawdiType,
			                    districtCode,
			                    talukaCode,
			                    req.getCcode(),
			                    req.getRevenueYear(),
			                    "Y"
			            );

			    if (exists) {
			        return FerarInspectionResponse.builder()
			                .id(null)
			                .remark("Echawdi type already exists for this village and year")
			                .build();
			    }
			    
			    /*Modification change End */

			    

			   	entity.setEchawdiType(echawdiType);
			    
			    entity.setCreateDtTm(LocalDateTime.now());
			    entity.setUpdateDtTm(LocalDateTime.now());
			    
			    entity.setRemark(req.getRemark());
			 
			   
			    
			    entity.setActiveFlag("Y");
			   
			   
			    
		    
		    // Step 6: Save DB record
			    EchawdiInspection saved = EchawdiRepo.save(entity);

		    return FerarInspectionResponse.builder()
		            .id(saved.getId())
		            .remark(saved.getRemark())
		            .build();
		}

		
		
		
		
		public String getEhakkaFilename(MultipartFile file,
				EhakkaInspectionRequest req) {
			String newFileName = new SimpleDateFormat(DateFormats.FILE_NAME_DATE_FORMAT).format(new Date());
			String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'),
					file.getOriginalFilename().length());
			boolean flag = false;
			if (!file.getOriginalFilename().isEmpty()
					&& extension.toLowerCase().matches(CommonConstants.Matcher.validFileMatcher)) {
				if (extension.toLowerCase().matches(CommonConstants.Matcher.imageMatcher)) {
					try {
						BufferedImage image = ImageIO.read(file.getInputStream());
						image.getData();
						flag = true;
					} catch (Exception e) {
						e.printStackTrace();
						DatabaseContextHolder.restoreCtx();
						return null;
					}
				} else if (extension.toLowerCase().matches(CommonConstants.Matcher.pdfMather)) {
					try {
						PDDocument document = PDDocument.load(file.getInputStream());
						document.getDocument();
						flag = true;
					} catch (Exception e) {
						e.printStackTrace();
						DatabaseContextHolder.restoreCtx();
						return null;
					}
				} else {
					DatabaseContextHolder.restoreCtx();
					return null;
				}
				if (flag) {
					try {
					String basePath = StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "EHAKKA_INPSEITON");
					
					String fullPath = Paths.get(basePath,
							req.getRevenueYear(), 
							req.getDistrictCode(),
							req.getTalukaCode(),
							req.getCcode()).toString();

						 
				    // Create directory if not exists
					File dir = new File(fullPath);
						
						
						if (!dir.exists()) {
							dir.mkdirs();
						}
						BufferedOutputStream outputStream = new BufferedOutputStream(
								new FileOutputStream(new File(dir, newFileName + extension)));
						outputStream.write(file.getBytes());
						outputStream.flush();
						outputStream.close();
						outputStream.close();
						return newFileName + extension;
					} catch (FileNotFoundException e) {
						DatabaseContextHolder.restoreCtx();
						e.printStackTrace();
						return null;
					} catch (IOException e) {
						DatabaseContextHolder.restoreCtx();
						e.printStackTrace();
						return null;
					}
				}
			}
			return null;
		}
		
		
		
		
		public String getFilename(MultipartFile file,
				FerarInspectionRequest req) {
			String newFileName = new SimpleDateFormat(DateFormats.FILE_NAME_DATE_FORMAT).format(new Date());
			String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'),
					file.getOriginalFilename().length());
			boolean flag = false;
			if (!file.getOriginalFilename().isEmpty()
					&& extension.toLowerCase().matches(CommonConstants.Matcher.validFileMatcher)) {
				if (extension.toLowerCase().matches(CommonConstants.Matcher.imageMatcher)) {
					try {
						BufferedImage image = ImageIO.read(file.getInputStream());
						image.getData();
						flag = true;
					} catch (Exception e) {
						e.printStackTrace();
						DatabaseContextHolder.restoreCtx();
						return null;
					}
				} else if (extension.toLowerCase().matches(CommonConstants.Matcher.pdfMather)) {
					try {
						PDDocument document = PDDocument.load(file.getInputStream());
						document.getDocument();
						flag = true;
					} catch (Exception e) {
						e.printStackTrace();
						DatabaseContextHolder.restoreCtx();
						return null;
					}
				} else {
					DatabaseContextHolder.restoreCtx();
					return null;
				}
				if (flag) {
					try {
						
					
					
						
					String basePath = StaticPropertyFetcher.getPropertyValue(FileName.FILEPATH_CONFIG, "INSPECTION_FERFAR");
					
					
					
					String fullPath = Paths.get(basePath,
							req.getRevenueYear(), 
							req.getDistrictCode(),
							req.getTalukaCode(),
							req.getCcode()).toString();

						 
				    // Create directory if not exists
					File dir = new File(fullPath);
						
						
						if (!dir.exists()) {
							dir.mkdirs();
						}
						BufferedOutputStream outputStream = new BufferedOutputStream(
								new FileOutputStream(new File(dir, newFileName + extension)));
						outputStream.write(file.getBytes());
						outputStream.flush();
						outputStream.close();
						outputStream.close();
						return newFileName + extension;
					} catch (FileNotFoundException e) {
						DatabaseContextHolder.restoreCtx();
						e.printStackTrace();
						return null;
					} catch (IOException e) {
						DatabaseContextHolder.restoreCtx();
						e.printStackTrace();
						return null;
					}
				}
			}
			return null;
		}

		
		
			@Override
		   public List<EHakkaTypeFiveDTO> getEHakkaApplicationCountData(
		           HttpServletRequest request,
		           String ccode,
		           String districtCode,
		           String talukaCode,
		           Long eHakkaType) {

		       List<EHakkaTypeFiveDTO> response = new ArrayList<>();

		       List<Long> typesToFetch = new ArrayList<>();
		       if (eHakkaType == null) {
		           typesToFetch.add(1L);
		           typesToFetch.add(2L);
		           typesToFetch.add(3L);
		           typesToFetch.add(4L);
		       } else {
		           if (eHakkaType < 1 || eHakkaType > 4) {
		               throw new IllegalArgumentException("Invalid eHakkaType id");
		           }
		           typesToFetch.add(eHakkaType);
		       }

		       for (Long type : typesToFetch) {
		           List<Object[]> data = mutRegisterRepo.fetchEHakkaApplicationData(
		                 /*  request.getHeader("echHost"),
		                   CommonConstants.echUser,
		                   CommonConstants.echPassword,
		                   CommonConstants.DefaultDataPort,
		                   ccode,
		                   Integer.valueOf(districtCode),
		                   Integer.valueOf(talukaCode),
		                   type */
		        		   
		        		   ccode,
		                   districtCode,
		                   talukaCode,
		                   type,
		                   CommonConstants.DefaultDataPort
		           );

		           for (Object[] row : data) {
		               EHakkaTypeFiveDTO dto = new EHakkaTypeFiveDTO();
		               
		               dto.setApplicationId(row[0] != null ? new BigInteger(row[0].toString()) : null);
		              // dto.setApplicationId(row[0] != null ? ((Long) row[0]) : 0);
		               if (row.length > 1) dto.setRemark((String) row[1]);
		               if (row.length > 2) dto.setRemarkType((String) row[2]);
		               if (row.length > 3) dto.setEhakkatype((String) row[3]);
		               response.add(dto);
		           }
		       }

		       return response;
		   }

//		   
//		 @Override
//		   public List<EHakkaTypeFiveDTO> getEHakkaTypeFiveData(
//			        HttpServletRequest request, 
//		             String ccode,
//		           String districtCode,
//		           String talukaCode,
//		           Long eHakkaType) {
//
//		       if (eHakkaType == null || eHakkaType != 5) {
//		           throw new IllegalArgumentException("Invalid type id");
//		       }
//
//		       List<Object[]> data = mutRegisterRepo.fetchEHakkaTypeFiveData(
//		              /* request.getHeader("echHost"),
//		               CommonConstants.echUser,
//		               CommonConstants.echPassword,
//		               CommonConstants.DefaultDataPort,
//		               ccode,
//		               Integer.valueOf(districtCode),
//		               Integer.valueOf(talukaCode) */
//		    		   
//		    		   ccode,
//		    	       districtCode,
//		    	       talukaCode,
//		    	       CommonConstants.DefaultDataPort
//		    		   
//		    		   
//		       );
//
//		       List<EHakkaTypeFiveDTO> response = new ArrayList<>();
//
//		       for (Object[] row : data) {
//		    	   EHakkaTypeFiveDTO dto = new EHakkaTypeFiveDTO();
//		           dto.setApplicationId(((Number) row[0]).intValue());
//		           dto.setRemark((String) row[1]);           
//		           dto.setRemarkType((String) row[2]);
//		           if (row.length > 3) {
//		               dto.setEhakkatype((String) row[3]);
//		           }           response.add(dto);
//		       }
//
//		       return response;
//		   }


@Override
public List<EHakkaTypeFiveDTO> getEHakkaTypeFiveData(
        HttpServletRequest request,
        String ccode,
        String districtCode,
        String talukaCode) {

    List<Object[]> data = mutRegisterRepo.fetchEHakkaTypeFiveData(
            ccode,
            districtCode,
            talukaCode,
            CommonConstants.DefaultDataPort
    );

    if (data == null || data.isEmpty()) {
        return Collections.emptyList();
    }

    List<EHakkaTypeFiveDTO> response = new ArrayList<>();

    for (Object[] row : data) {
        if (row == null) continue;

        EHakkaTypeFiveDTO dto = new EHakkaTypeFiveDTO();
        
        
       

        if (row.length > 0 && row[0] != null) {
       
          //  dto.setApplicationId((Long) row[0] );
        	dto.setApplicationId(((BigInteger) row[0]));

        }

        if (row.length > 1) {
            dto.setRemark((String) row[1]);
        }

        if (row.length > 2) {
            dto.setRemarkType((String) row[2]);
        }

        if (row.length > 3) {
            dto.setEhakkatype((String) row[3]);
        }

        response.add(dto);
    }

    return response;
}

		
		 
		 @Override
			public List<FerarInspectionResponse> getAdeshFherfarCount(String ccode, Integer districtCode, Integer talukaCode,
					String revenueYear, Character activeFlag, Integer ferFarType, HttpServletRequest request, Locale locale) {
				// TODO Auto-generated method stub
//				dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
//			       
//			    dataSourceKeeper.setDataSourceEchwadi(
//			            request.getHeader("echDbName"),
//			            request.getHeader("echSchemaName")
//			    );
			 
			 	
			 	System.out.println("Ccode------------: "+ccode);
			 	System.out.println("districtCode------------: "+districtCode);
			 	System.out.println("talukaCode------------: "+talukaCode);
			 	System.out.println("revenueYear------------: "+revenueYear);
			 	System.out.println("activeFlag------------: "+activeFlag);
			 	System.out.println("ferFarType------------: "+ferFarType);
				
			 	List<Object[]> resultList ;
				
				 
			 	if(ferFarType != 0) {
			 		
			 		
			 		 
			 		 resultList = ferarRepo.getAdeshFerfarMutNo(
					            ccode,
					            districtCode,
					            talukaCode,
					            revenueYear,
					            activeFlag ,
					            ferFarType
					    );
			 		
			 	}else {
			 		
			 		 resultList = ferarRepo.getAdeshFerfarMutNoWithoutFerfarType(
					            ccode,
					            districtCode,
					            talukaCode,
					            revenueYear,
					            activeFlag 
					           
					    );
			 		
			 	}

			   

			    List<FerarInspectionResponse> responseList = new ArrayList<>();

			    if (resultList != null && !resultList.isEmpty()) {
			        for (Object[] row : resultList) {

			        	FerarInspectionResponse dto = new FerarInspectionResponse();

			            // mut_no
			            dto.setMutNo(
			                    row[0] != null ? ((Number) row[0]).intValue() : null
			            );

			            dto.setRemark(
			                    row[1] != null ? row[1].toString() : null
			            );

			            dto.setTypeOfRemark(
			                    row[2] != null ? ((Number) row[2]).longValue() : null
			            );

			            dto.setFerfarType( row[3] != null ? ((Number) row[3]).longValue() : null);
			           

			            
			            responseList.add(dto);
			        }
			    }

			    return responseList;
			
			}
		 
		 
		 
		 //dyslr method
		 @Override
			public Long getDyslrAkarbandDeletedCount(String ccode, 
					HttpServletRequest request, Locale local) {
				try {
		            Long count = landRevenueDemandRepo.getDyslrAkarbandDeletedCount(
		            		 request.getHeader("echHost"),        
		                     request.getHeader("echDbName"),       
		                     request.getHeader("echSchemaName"),  
		                     CommonConstants.echUser,              
		                     CommonConstants.echPassword,          
		                     ccode
		                   //  activeFlag     
		                                    );
		            
		            System.out.println(+count);

		            return count == null ? 0L : count;

		        } finally {
		            DatabaseContextHolder.restoreCtx();
		            
		        }
			}
		 
		 
		 
		 	@Override
		    public MutationRegisterDTO getNirankCompletedCounts() {
		        Object result = nirankRepository.getNirankCompletedCounts();
		        Object[] row = (Object[]) result;

		        MutationRegisterDTO dto = new MutationRegisterDTO();
		        dto.setNirankYesCount(row[0] == null ? 0L : ((Number) row[0]).longValue());
		        dto.setCompletedYesCount(row[1] == null ? 0L : ((Number) row[1]).longValue());
		        dto.setCompletedNoNirankNoCount(row[2] == null ? 0L : ((Number) row[2]).longValue());

		        return dto;
		    }

			
			
		 	@Override
		 	public Response getNirankAndCompleted(String cCode, String revenueYear, HttpServletRequest request, Locale locale) {

		 	List<Object[]> nirank =
		 	nirankRepository.findBycCodeAndRevenueYearAndActiveFlag(cCode, revenueYear, 'Y');

		 	System.out.println("nirank size = " + nirank.size());


		 	Response nirankResponse = new Response();
		 	nirankResponse.setNirank(
		 	nirank.stream().map(object -> {
		 	    NirankDao nirankDao = new NirankDao();
		 	    nirankDao.setcCode(object[0].toString());
		 	    nirankDao.setFormId(object[1].toString());
		 	    nirankDao.setIsNirank((Character) object[2]);
		 	    nirankDao.setIsCompleted((Character) object[3]);
		 	    nirankDao.setDistrictCode(object[4].toString());
		 	    nirankDao.setTalukaCode(object[5].toString());
		 	    return nirankDao;
		 	}).collect(Collectors.toList())
		 	);


		 	DatabaseContextHolder.restoreCtx();

		 	return nirankResponse;

		 	}
		 	
		 	
			@Override
		    public VasuliInspectionDTO getVasuliSummary(
		            String ccode,
		            String revenueYear,
		            HttpServletRequest request,
		            Locale locale) {

		        VasuliInspectionDTO dto = new VasuliInspectionDTO();

		        try {

		            String host = request.getHeader("echHost");
		            String dbName = request.getHeader("echDbName");
		            String schema = request.getHeader("echSchemaName");

		            List<Object[]> demandList = landRevenueDemandRepo.getTotalDemand(
		                    host, dbName, schema,
		                    CommonConstants.echUser,
		                    CommonConstants.echPassword,
		                    ccode, revenueYear);

		            List<Object[]> collectedList = landRevenueDemandRepo.getTotalCollected(
		                    host, dbName, schema,
		                    CommonConstants.echUser,
		                    CommonConstants.echPassword,
		                    ccode, revenueYear);

		            Object[] demandRow = (demandList != null && !demandList.isEmpty())
		                    ? demandList.get(0) : null;

		            Object[] collectedRow = (collectedList != null && !collectedList.isEmpty())
		                    ? collectedList.get(0) : null;

		            System.out.println("Demand Row: " + java.util.Arrays.toString(demandRow));
		            System.out.println("Collected Row: " + java.util.Arrays.toString(collectedRow));

		            if (demandRow != null && demandRow.length >= 3) {
		                dto.setTotalDemand(
		                        demandRow[0] != null ? ((Number) demandRow[0]).doubleValue() : 0.0);
		                dto.setTotalEgsDemand(
		                        demandRow[1] != null ? ((Number) demandRow[1]).longValue() : 0L);
		                dto.setTotalEduCessDemand(
		                        demandRow[2] != null ? ((Number) demandRow[2]).longValue() : 0L);
		            }

		            if (collectedRow != null && collectedRow.length >= 3) {
		                dto.setTotalCollected(
		                        collectedRow[0] != null ? ((Number) collectedRow[0]).doubleValue() : 0.0);
		                dto.setTotalEgsCollected(
		                        collectedRow[1] != null ? ((Number) collectedRow[1]).longValue() : 0L);
		                dto.setTotalEduCessCollected(
		                        collectedRow[2] != null ? ((Number) collectedRow[2]).longValue() : 0L);
		            }

		        } catch (Exception e) {
		            throw new RuntimeException("Error fetching Vasuli Summary", e);
		        }

		        return dto;
		    }
		 	
			
			@Override
			public List<EchawdinspectionRequest> FetchAllEchawadiRemark(String ccode, Integer districtCode, Integer talukaCode, HttpServletRequest request) {
				
				dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"), request.getHeader("echSchemaName"));
				
				
				// TODO Auto-generated method stub
				   List<Object[]> echawadiRemark = EchawdiRepo.fetchEchawadiRemark(ccode, districtCode, 
						   talukaCode, request.getHeader("echDbName"), 
						   request.getHeader("echSchemaName"),
						   CommonConstants.echUser,
						   CommonConstants.echPassword
						   );
			            


			       List<EchawdinspectionRequest> result = new ArrayList<>();

			       for (Object[] row : echawadiRemark) {

			    	   EchawdinspectionRequest echawdinspectionRequest = new EchawdinspectionRequest();

			           // Correct indices
			         
			           echawdinspectionRequest.setEChawdiType(row[0] != null ? ((Number) row[0]).intValue() : 0);

			           echawdinspectionRequest.setRemark(row[1] != null ? row[1].toString() : null);

			           result.add(echawdinspectionRequest);
			       }

			       return result;

			   
			}

			@Override
			public Long getMaganiDurustiKhatedarCount(String ccode, String revenueYear, Character activeFlag,
					HttpServletRequest request, Locale local) {
				try {
		            Long count = landRevenueDemandRepo.MaganiDurustiKhatedarCount(
		            		 request.getHeader("echHost"),        
		                     request.getHeader("echDbName"),       
		                     request.getHeader("echSchemaName"),  
		                     CommonConstants.echUser,              
		                     CommonConstants.echPassword,          
		                     ccode,
		                     activeFlag,     
		                     revenueYear               );
		            
		            System.out.println(+count);

		            return count == null ? 0L : count;

		        } finally {
		            DatabaseContextHolder.restoreCtx();
		            
		        }
			}
			

			//dyslr durusti count
			@Override
			public Long getDyslrDurustiCount(String ccode, 
					HttpServletRequest request, Locale local) {
				try {
			        Long count = landRevenueDemandRepo.getDyslrDurustiCount(
			        		 request.getHeader("echHost"),        
			                 request.getHeader("echDbName"),       
			                 request.getHeader("echSchemaName"),  
			                 CommonConstants.echUser,              
			                 CommonConstants.echPassword,          
			                 ccode
			               //  activeFlag     
			                                );
			        
			        System.out.println(+count);

			        return count == null ? 0L : count;

			    } finally {
			        DatabaseContextHolder.restoreCtx();
			        
			    }
			}


@Override
public InspectionReportMasterDao saveReport(
        InspectionReportMasterDao req,
        HttpServletRequest request,
        Locale locale) {

    dataSourceKeeper.setDataSourceEchwadi(
            request.getHeader("echDbName"),
            request.getHeader("echSchemaName"));

    try {
        if (inspectionReportMasterRepository.existsByCcode(req.getCcode())) {
            throw new ResourceNotFoundException("Ccode already exists");
        }

        InspectionReportMaster entity = new InspectionReportMaster();
        entity.setCcode(req.getCcode());
        entity.setDistrictCode(req.getDistrictCode());
        entity.setTalukaCode(req.getTalukaCode());
        entity.setRevenueYear(req.getRevenueYear());
        entity.setCreatedUserId(req.getCreatedUserId());
        entity.setInspOfficerUsername(req.getInspOfficerUsername());
        
 entity.setStatus(
            	    req.getStatus() != null ? req.getStatus() : "OPEN"
            	);
            	
            	
        InspectionReportMaster saved =
                inspectionReportMasterRepository.save(entity);

        InspectionReportMasterDao response = new InspectionReportMasterDao();
        response.setId(saved.getId());
        response.setCcode(saved.getCcode());

        return response;

    } catch (DataIntegrityViolationException ex) {
        throw new ResourceNotFoundException("Ccode already exists");
    }
}





public String saveRemark(TalathiRemarkInspectionDto dto ,HttpServletRequest request,
        Locale locale) {

	dataSourceKeeper.setDataSourceEchwadi(
            request.getHeader("echDbName"),
            request.getHeader("echSchemaName"));
	
	

    // Find master record using ccode
    InspectionReportMaster master = inspectionReportMasterRepository
            .findByCcode(dto.getCcode())
            .orElseThrow(() -> 
                new ResourceNotFoundException("Ccode not found in master table"));

    if ("COMPLETED".equalsIgnoreCase(master.getStatus())) {

        throw new ResourceNotFoundException(
                "Inspection already completed. Remark save not allowed.");
    }
    
    
    TalathiRemarkInspection remark = new TalathiRemarkInspection();

    
    remark.setInspectionReportMaster(master);

    // optional direct ccode column
    remark.setCcode(master.getCcode());

    remark.setDistrictCode(master.getDistrictCode());
    remark.setTalukaCode(master.getTalukaCode());
    remark.setRevenueYear(master.getRevenueYear());
   

    remark.setRemark(dto.getRemark());
    remark.setDesignation(dto.getDesignation());
   remark.setInspectionOfficerUsername(dto.getInspectionOfficerUsername());

    remark.setActiveFlag("Y");
    remark.setIsRemarkSubmitted("Y");

    remark.setCreateDtTm(new Date());

    talathiRemarkInspectionRepository.save(remark);

    return "Remark saved successfully";
}


@Override
public List<TalathiRemarkInspectionDto> getTalathiRemarkInspection(String ccode, String revenueYear,
		HttpServletRequest request, Locale local) {


	
	 List<Object[]> list = talathiRemarkInspectionRepository.getTalathiRemarkInspection(
			 request.getHeader("echHost"),
	            request.getHeader("echDbName"),
	            request.getHeader("echSchemaName"),
	            CommonConstants.echUser,
	            CommonConstants.echPassword,
	            ccode,
	            revenueYear);
	
	
   /* List<Object[]> list = talathiRemarkInspectionRepository.getTalathiRemarkInspection(
            request.getHeader("echHost"),
            request.getHeader("echDbName"),
            request.getHeader("echSchemaName"),
            CommonConstants.echUser,
            CommonConstants.echPassword,
            ccode,
            revenueYear);*/

    List<TalathiRemarkInspectionDto> responseList = new ArrayList<>();

    for (Object[] obj : list) {

        TalathiRemarkInspectionDto dto = new TalathiRemarkInspectionDto();
        
       // dto.setCreateDtTm(obj[0] != null ? (java.sql.Date) obj[0] : null); 
        
        dto.setCreateDtTm(obj[0] != null ? new Date(((Timestamp) obj[0]).getTime()) : null);
        dto.setRemark(obj[1] != null ? obj[1].toString() : null);
        dto.setDesignation(obj[2] != null ? obj[2].toString() : null);
        dto.setInspectionOfficerUsername(obj[3] != null ? obj[3].toString() : null);
        dto.setCcode(obj[4] != null ? obj[4].toString() : null);
        dto.setRevenueYear(obj[5] != null ? obj[5].toString() : null);
        responseList.add(dto);
    }

    return responseList;

 
}



}
