package com.probity.echawdi.serviceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.probity.echawdi.config.DatabaseContextHolder;
import com.probity.echawdi.dao.GramAdarshTaktaDTO;
import com.probity.echawdi.dao.GramAdarshaTaktaSaveDTO;
import com.probity.echawdi.dao.VillageCropAreaDTO;
import com.probity.echawdi.entity.CropMaster;
import com.probity.echawdi.entity.GramAdarshTaktaEntity;
import com.probity.echawdi.entity.VillageCropArea;
import com.probity.echawdi.exception.DuplicateRecordException;
import com.probity.echawdi.mapper.GramAdarshaTaktaMapper;
import com.probity.echawdi.repository.CropMasterRepository;
import com.probity.echawdi.repository.Form14Repository;
import com.probity.echawdi.repository.Form1AbstractRepository;
import com.probity.echawdi.repository.Form7AreaRepository;
import com.probity.echawdi.repository.GramAdarshTaktaRepository;
import com.probity.echawdi.repository.LandRevenueDemandRepository;
import com.probity.echawdi.service.GramAdarshTaktaService;
import com.probity.echawdi.utils.CommonConstants;
import com.probity.echawdi.utils.DataSourceKeeper;

@Service
public class GramAdarshTaktaServiceImpl implements GramAdarshTaktaService {
	
		@Autowired
		private DataSourceKeeper dataSourceKeeper;
		
	 	@Autowired
	    private LandRevenueDemandRepository landRevenuerepo;
	 	
	 	@Autowired
	 	private Form7AreaRepository form7AreaRepo;
	 	
	 	@Autowired
	 	private	Form1AbstractRepository form1AbstractRepo;
	 	
	 	@Autowired
		private Form14Repository form14Repository;
	 	
	 	
	 	@Autowired
	    private GramAdarshTaktaRepository gramAdarshaTaktaRepository;

	    @Autowired
	    private GramAdarshaTaktaMapper gramAdarshaTaktaMapper;
	    
	   // private final GramAdarshaTaktaMapper gramAdarshaTaktaMapper = Mappers.getMapper(GramAdarshaTaktaMapper.class);

	 	
	    @Autowired
	    private  CropMasterRepository cropMasterRepository;
	    
	   
	 	
	 	
	 	
	 	//Areabelow and greater method
	    @Override
	    public GramAdarshTaktaDTO getKhatedarCountAndBelowArea(String ccode,
		         String revenueYear, HttpServletRequest request,Locale locale) {
	    	
	    	dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
	    	
	    	
	    	
	    	System.out.println("request : "+request.getHeader("echDbName"));
	    	
	        Object[] result = (Object[]) landRevenuerepo.getLandRevenueSummary(
	        		request.getHeader("echHost"),
					request.getHeader("echDbName"),
					request.getHeader("echSchemaName"),
					CommonConstants.echUser,
					CommonConstants.echPassword,
	        		ccode,
	        		revenueYear );
	        
	        if (result == null || result.length != 5) {
	    	    throw new EntityNotFoundException("Expected exactly 3 columns in result");
	    	}
	       
	        
	        int index = 0;
	        for (Object value : result) {
	            System.out.println("Column " + index + ": " + value);
	            index++;
	        }

	        
	         
	        GramAdarshTaktaDTO dto = new GramAdarshTaktaDTO();
	        try {
	        	 dto.setAreaBelow2023(result[0] != null ? ((Number) result[0]).doubleValue() : 0);
	 	        dto.setAreaAbove2023(result[1] != null ? ((Number) result[1]).doubleValue() : 0);
	 	        dto.setKhataCountWithName(result[2] != null ? ((Number) result[2]).longValue() : 0);
	 	        dto.setKhataCountBelow2023(result[3] != null ? ((Number) result[3]).longValue() : 0);
	 	        dto.setKhataCountAbove2023(result[4] != null ? ((Number) result[4]).longValue() : 0);
	 	      	
	        }catch (ClassCastException e) {
	            throw new IllegalStateException("Unexpected data format from database", e);
	        }
	        
	        
	        // Validate the data
	        if (dto.isAllFieldsZero()) {
	        	throw new EntityNotFoundException("No meaningful land data found — both BelowArea and GreaterArea are zero.");
    
	        }


	       

	        return dto;

	    }
	    
	    
	    
	    
	    //Akrushak and potkharab area method
	    @Override
	    public GramAdarshTaktaDTO getAkrushakAndPothkhrabAreaByccode(String ccode,
		         HttpServletRequest request,Locale locale) {
	    	
	    	 	  	
	        Object[] result = (Object[]) form7AreaRepo.getAkrushakAndPotKharabArea(
	        		request.getHeader("mhrHost"),
	        		request.getHeader("mhrDbName"),
	        		request.getHeader("mhrSchemaName"),
	        		CommonConstants.mhrorUser,
	        		CommonConstants.mhrorPassword,
	        		ccode,
	        		CommonConstants.DefaultDataPort);
	        
	        
	        
	        

	        
	        int index = 0;
	        for (Object value : result) {
	            System.out.println("Column " + index + ": " + value);
	            index++;
	        }
	        System.out.println("Printing result using for-each loop:");

	        if (result == null || result.length != 2) {
	    	    throw new EntityNotFoundException("Expected exactly 2 columns in result");
	    	}
	       

	        GramAdarshTaktaDTO dto = new GramAdarshTaktaDTO();
	        try {
	        	dto.setPotKharabArea(result[0] != null ? ((Number) result[0]).doubleValue() : 0.0);
	  	        dto.setAkrushakArea(result[1] != null ? ((Number) result[1]).doubleValue() : 0.0);
	  	        	
	        }catch (ClassCastException e) {
	            throw new IllegalStateException("Unexpected data format from database", e);
	        }

	       
	        
	       
	        
	      

	       
	        
	        // Validate the data
	        if (dto.isAllFieldsZero()) {
	        	throw new EntityNotFoundException("No meaningful land data found — both Akrushak and Pothkharab area are zero.");
    
	        }

	       

	        return dto;

	    }
	    
	    
	    //Dummy Method for Tax Calculations.(Niyat , snakirn, rojgarhami, shikshankar)
	    @Override
	    public GramAdarshTaktaDTO getAllTaxCalculationBycCode(String ccode,
		         /*String revenueYear,*/ HttpServletRequest request,Locale locale) {
	    	
	    	dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
	    	
	    	
	    	
	    	System.out.println("request : "+request.getHeader("echDbName"));
	    	
	        Object[] result = (Object[]) landRevenuerepo.getAllTaxOfLandByCcode(
	        		request.getHeader("echHost"),
					request.getHeader("echDbName"),
					CommonConstants.DefaultDataPort,
					request.getHeader("echSchemaName"),
					CommonConstants.echUser,
					CommonConstants.echPassword,
	        		ccode/*,
	        		revenueYear*/ );
	        
	       // Object[] result = (Object[]) landRevenuerepo.getAllTaxOfLandByCcodeStatic( );
	        
	        System.out.println("Printing result using for-each loop:");
	        
	        // Check if the result is null or does not have the expected number of columns
//	        if (result == null || result.length < 3) {
//	            throw new EntityNotFoundException("No result returned from database or expected result is in more than 2 coloumns");
//	        }
//	        
	        int index = 0;
	        for (Object value : result) {
	            System.out.println("Column " + index + ": " + value);
	            index++;
	        }

	        GramAdarshTaktaDTO dto = new GramAdarshTaktaDTO();
	        try {
	        	
	        	dto.setBidumalaNiyatCurrent(result[0] != null ? ((Number) result[0]).doubleValue() : 0.0);
	  	        dto.setAkrushakCurrent(result[1] != null ? ((Number) result[1]).doubleValue() : 0.0);
	  	        dto.setSankirnCurrent(result[2] != null ? ((Number) result[2]).doubleValue() : 0.0);
	  	        dto.setEducationalCessCurrent(result[3] != null ? ((Number) result[3]).doubleValue() : 0.0);
	  	        dto.setEmployeeGuaranteeSchemeCurrent(result[4] != null ? ((Number) result[4]).doubleValue() : 0.0);
	  	        dto.setTotalAmountCurrent(result[5] != null ? ((Number) result[5]).doubleValue() : 0.0);
	  	        
	  	        dto.setBidumalaNiyatPrevious(result[6] != null ? ((Number) result[6]).doubleValue() : 0.0);
	  	        dto.setAkrushakPrevious(result[7] != null ? ((Number) result[7]).doubleValue() : 0.0);
	  	        dto.setSankirnPrevious(result[8] != null ? ((Number) result[8]).doubleValue() : 0.0);
	  	        dto.setEducationalCessPrevious(result[9] != null ? ((Number) result[9]).doubleValue() : 0.0);
	  	        dto.setEmployeeGuaranteeSchemePrevious(result[10] != null ? ((Number) result[10]).doubleValue() : 0.0);
	  	        dto.setTotalAmountPrevious(result[11] != null ? ((Number) result[11]).doubleValue() : 0.0);
	  	   
	  	        	
	        }catch (ClassCastException e) {
	            throw new IllegalStateException("Unexpected data format from database", e);
	        }

	        
	        // Validate the data
	        if (dto.isAllFieldsZero()) {
	            throw new EntityNotFoundException("All data fields are zero.");
	        }

	       

	        return dto;

	    }
	    
	    
	    // Gaothan area and VanJaminiche kshetra API Dynamic completed
	    @Override
	    public GramAdarshTaktaDTO getForestAndGaothanKshetraByCcode(String cCode, 
	    		HttpServletRequest request, Locale locale) {
	    	
	    	dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
	    	

	    	Object[] result = (Object[]) form1AbstractRepo.getForestAndGaothanKshetraByCcode(
	    	       
	    			request.getHeader("echHost"),
	    			request.getHeader("echDbName"),
	    			request.getHeader("echSchemaName"),
	    			CommonConstants.echUser,
	    			CommonConstants.echPassword,
	    			cCode
	        		 );
	    	
	    
	    	
	    	if (result == null || result.length != 2) {
	    	    throw new EntityNotFoundException("Expected exactly 2 columns in result");
	    	}
	    	
	    	int index = 0;
	    	for(Object value : result) {
	    		System.out.println("coloumn : "+index+" : "+value);
	    	}

	    	
		        
	    	 GramAdarshTaktaDTO dto = new GramAdarshTaktaDTO();
		        try {
		            dto.setVanJaminArea(result[0] != null ? ((Number) result[0]).doubleValue() : 0.0);
		            dto.setVillageSite(result[1] != null ? ((Number) result[1]).doubleValue() : 0.0);
		        } catch (ClassCastException e) {
		            throw new IllegalStateException("Unexpected data format from database", e);
		        }

		       
	    	    
		     // Validate the data
		        if (dto.isAllFieldsZero()) {
		        	throw new EntityNotFoundException("No meaningful land data found — both VanJaminArea and VillageSite are zero.");

		        }

		       

		        return dto;
	    }
	    
	    
	 	//Get Bindumala and Dumala Area
	    @Override
	    public GramAdarshTaktaDTO getBindumalaAndDumalaAreasBycCode(String ccode,
		         HttpServletRequest request,Locale locale) {
	    	
	    	//need to change the repo.method name and add query
	       /* Object[] result = (Object[]) landRevenuerepo.getBindumalaAndDumalaArea(
	        		CommonConstants.AugDatamhrHost,
	        		request.getHeader("mhrDbName"),
	        		request.getHeader("mhrSchemaName"),
	        		CommonConstants.mhrorUser,
	        		CommonConstants.mhrorPassword,
	        		ccode,
	        		CommonConstants.AugDataPort);*/
	        
	    	
	        
	        Double binArea = landRevenuerepo.getBindumalaAreaDynamic( 
	        		request.getHeader("mhrHost"),
	        		request.getHeader("mhrDbName"),
	        		request.getHeader("mhrSchemaName"),
	        		CommonConstants.mhrorUser,
	        		CommonConstants.mhrorPassword,
	        		ccode,
	        		CommonConstants.DefaultDataPort
	        		);
	        
	        
	        
	        System.out.println("BindumalaArea ------------------      : "+binArea);
	        System.out.println("BindumalaArea ------------------      : "+request.getHeader("mhrDbName"));
	        System.out.println("BindumalaArea ------------------      : "+request.getHeader("mhrSchemaName"));
		       
	        
	        Double dumArea = landRevenuerepo.getDumalaAreaDynamic(
	        		request.getHeader("mhrHost"),
	        		request.getHeader("mhrDbName"),
	        		request.getHeader("mhrSchemaName"),
	        		CommonConstants.mhrorUser,
	        		CommonConstants.mhrorPassword,
	        		ccode,
	        		CommonConstants.DefaultDataPort
	        		
	        		);
	        
//	        if (result == null || result.length != 3) {
//	    	    throw new EntityNotFoundException("Expected exactly 3 columns in result");
//	    	}
	       
	        
	        
	       Double totalArea = ( (binArea != null ? binArea : 0 ) + (dumArea != null ? dumArea : 0 ));

	        
	         
	        GramAdarshTaktaDTO dto = new GramAdarshTaktaDTO();
	        try {
	        	
	        
		        
	        	
	        	 dto.setBindumalaArea(binArea != null ? binArea : 0);
	        	 dto.setDumalaArea(dumArea != null ? dumArea : 0);
	        	 dto.setTotalArea(totalArea != null ? totalArea : 0);
	        	 
//	        	 dto.setBindumalaArea(result[0] != null ? ((Number) result[0]).doubleValue() : 0);
//	 	         dto.setBindumalaArea(result[1] != null ? ((Number) result[1]).doubleValue() : 0);
//	 	         dto.setTotalArea(result[2] != null ? ((Number) result[2]).doubleValue() : 0);
	 	      	
	        }catch (ClassCastException e) {
	            throw new IllegalStateException("Unexpected data format from database", e);
	        }
	        
	        
	        // Validate the data
	        if (dto.isAllFieldsZero()) {
	        	throw new EntityNotFoundException("No meaningful land data found — both BelowArea and GreaterArea are zero.");
    
	        }
	        
	        return dto;

	    }
	    
	    
	    // JalSinchan Api
	    @Override
	    public GramAdarshTaktaDTO getJalsinchanWellCount(String cCode, 
	    		HttpServletRequest request, Locale locale) {
	    	
	    	dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
	    	
	    	
	    	
	    	Object[] jalSinchanResource =(Object[]) form14Repository.getTotalWellsUsedForIrrigation(
	    			request.getHeader("echHost"),
	    			request.getHeader("echDbName"),
	    			request.getHeader("echSchemaName"),
	    			CommonConstants.echUser,
	    			CommonConstants.echPassword,
	    			cCode,
	    			'Y'
	        		 );
	    	
	    
	    	DatabaseContextHolder.restoreCtx();
	    	
		        
	    	 GramAdarshTaktaDTO dto = new GramAdarshTaktaDTO();
		     try {
		        	
		    	 			Integer inUsed = (jalSinchanResource[0] instanceof Number) ? ((Number) jalSinchanResource[0]).intValue() : null;
	                        Integer notInUsed = (jalSinchanResource[1] instanceof Number) ? ((Number) jalSinchanResource[1]).intValue() : null;
	    	               
						    if (inUsed == null && notInUsed == null) {
						        throw new IllegalStateException("Resource data missing: both values are null");
						    }

						    dto.setInUsedWell(inUsed != null ? inUsed : 0);
						    dto.setNotInUsedWell(notInUsed != null ? notInUsed : 0);
							            
		      } catch (ClassCastException e) {
		            throw new IllegalStateException("Unexpected data format from database", e);
		    }

		       
	    	    
		     // Validate the data
//		        if (dto.isAllFieldsZero()) {
//		        	throw new EntityNotFoundException("No meaningful land data found — both VanJaminArea and VillageSite are zero.");
//
//		        }

		       

		        return dto;
	    }
	    
	    
	    
	 // getMahasulMafAndKamakariJaminiData Api
	    @Override
	    public GramAdarshTaktaDTO getMahasulMafAndKamakariJaminiData(String cCode, 
	    		HttpServletRequest request, Locale locale) {
	    	
	    	dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
	    	
	    	
	    	
	    	 Double result = form1AbstractRepo.fetchMahasulMafOrKamakariJamini(
	                 request.getHeader("echHost"),
	                 request.getHeader("echDbName"),
	                 request.getHeader("echSchemaName"),
	                 CommonConstants.echUser,
	                 CommonConstants.echPassword,
	                 cCode
	         );
	    	
	    
	    	 
	    	 
	    	 
	    	DatabaseContextHolder.restoreCtx();
	    	
		        
	    	 GramAdarshTaktaDTO dto = new GramAdarshTaktaDTO();
	    	 try {
		        	
		    	 	   
					dto.setKamAkariArea(result != null ? result : 0.0);
					
					System.out.println("result of kamarari area : "+result);
							            
		      } catch (ClassCastException e) {
		            throw new IllegalStateException("Unexpected data format from database", e);
		    }

		       
	    	    
		     // Validate the data
//		        if (dto.isAllFieldsZero()) {
//		        	throw new EntityNotFoundException("No meaningful land data found — both VanJaminArea and VillageSite are zero.");
//
//		        }

		       

		        return dto;
	    }
	    
	    
	    
	    //save Api  
	   /* @Override
	    public GramAdarshaTaktaSaveDTO save(
	    		GramAdarshaTaktaSaveDTO gramAdarshaTaktaDTO, 
	    		HttpServletRequest request,
	    	     Locale locale
	    		) {

	    	dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
	   
	        GramAdarshTaktaEntity gramAdarshaTakta = gramAdarshaTaktaMapper.toEntity(gramAdarshaTaktaDTO);

	        // 🔁 Set the parent reference in each crop area
	        if (gramAdarshaTakta.getCropAreas() != null && !gramAdarshaTakta.getCropAreas().isEmpty()) {
	        	
	        	 for (VillageCropArea crop : gramAdarshaTakta.getCropAreas()) {
	        	        crop.setGramAdarshaTakta(gramAdarshaTakta);
	        	    }
	        }

	        
	        gramAdarshaTakta = gramAdarshaTaktaRepository.save(gramAdarshaTakta);

	        return gramAdarshaTaktaMapper.toDto(gramAdarshaTakta);
	        
	    }*/
	    
	    
	    
	    
	    @Override
	    public GramAdarshaTaktaSaveDTO save(
	            GramAdarshaTaktaSaveDTO dto,
	            HttpServletRequest request,
	            Locale locale
	    ) {
	        // Set dynamic datasource (if applicable)
	        /*dataSourceKeeper.setDataSourceEchwadi(
	                request.getHeader("echDbName"),
	                request.getHeader("echSchemaName")
	        );*/
	    	
	    	
	    	// Check if cccode already exists
	        String exists = gramAdarshaTaktaRepository.findByCccode(dto.getCcode());
	        
	        if (exists != null && exists.equals(dto.getCcode())) {
	            throw new DuplicateRecordException("record.duplicate.cccode");
	        }

	        // Map DTO to Entity
	        GramAdarshTaktaEntity entity = gramAdarshaTaktaMapper.toEntity(dto);
	        entity.setActiveFlag('Y');
	        entity.setCreateDtTm(LocalDateTime.now());
	        entity.setUpdateDtTm(LocalDateTime.now());

	        // Handle cropAreas
	        if (entity.getCropAreas() != null && !entity.getCropAreas().isEmpty()) {
	            List<VillageCropAreaDTO> dtoCropAreas = dto.getCropAreas();

	            for (int i = 0; i < entity.getCropAreas().size(); i++) {
	                VillageCropArea cropArea = entity.getCropAreas().get(i);
	                VillageCropAreaDTO cropDTO = dtoCropAreas.get(i);

	                cropArea.setGramAdarshaTakta(entity);
	                cropArea.setCcode(entity.getCcode());
	                cropArea.setActiveFlag('Y');
	                cropArea.setCreateDtTm(LocalDateTime.now());
	                cropArea.setUpdateDtTm(LocalDateTime.now());
	                

	                
	                CropMaster crop; // Declare only once

	             // Prefer cropName if provided
	             if (cropDTO.getCropName() != null && !cropDTO.getCropName().trim().isEmpty()) {
	                 String normalizedCropName = normalizeCropName(cropDTO.getCropName());

	                 // Check if already exists
	                 crop = cropMasterRepository.findByCropName(normalizedCropName);

	                 if (crop == null) {
	                     try {
	                         CropMaster newCrop = new CropMaster();
	                         newCrop.setCropName(normalizedCropName);
	                         crop = cropMasterRepository.save(newCrop);
	                     } catch (DataIntegrityViolationException e) {
	                         // Race condition: someone else inserted it
	                         crop = cropMasterRepository.findByCropName(normalizedCropName);
	                         if (crop == null) {
	                             throw new RuntimeException("Duplicate crop creation failed: " + normalizedCropName);
	                         }
	                     }
	                 }

	             } else if (cropDTO.getCropId() != null) {
	                 crop = cropMasterRepository.findById(cropDTO.getCropId())
	                         .orElseThrow(() -> new RuntimeException("Invalid cropId: " + cropDTO.getCropId()));
	             } else {
	                 throw new IllegalArgumentException("Either cropName or cropId must be provided.");
	             }

	             cropArea.setCrop(crop); // ✅ Set the resolved crop

	              
	            }
	        }

	        // Save entire hierarchy
	        GramAdarshTaktaEntity saved = gramAdarshaTaktaRepository.save(entity);
	        return gramAdarshaTaktaMapper.toDto(saved);
	    }

	    /**
	     * Normalizes Marathi crop name by trimming and removing all spaces.
	     * Example: "  ज्वा री " → "ज्वारी"
	     */
	    private String normalizeCropName(String cropName) {
	        if (cropName == null) return null;

	        return cropName
	                .trim()
	                .replaceAll("\\s+", "");  // Removes all spaces inside and outside
	    }


	    
	   

	    @Override
	    public List<GramAdarshaTaktaSaveDTO> getByCcodeAndRevenueYear(String ccode) {
	        List<GramAdarshTaktaEntity> entities = gramAdarshaTaktaRepository.findByCcodeAndRevenueYear(ccode);
	        return gramAdarshaTaktaMapper.toDtoList(entities);
	    }
	    
	    @Override
		public GramAdarshTaktaDTO getkuranAreaAndBagayatArea(String cCode, HttpServletRequest request, Locale locale) {
			// TODO Auto-generated method stub
	    	dataSourceKeeper.setDataSourceEchwadi(request.getHeader("echDbName"),request.getHeader("echSchemaName"));
	    	dataSourceKeeper.setDataSourceEchwadi(request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"));

			 List<Object[]> result= form7AreaRepo.getKuranAndBgayatArea(
					 
				 
					 
		       request.getHeader("mhrHost"),request.getHeader("mhrDbName"),request.getHeader("mhrSchemaName"),CommonConstants.mhrorUser,CommonConstants.mhrorPassword ,
						request.getHeader("echHost"),request.getHeader("echDbName"),request.getHeader("echSchemaName"),CommonConstants.echUser,CommonConstants.echPassword,
						cCode	
		        		 );
			 
			// Print result to console
			    System.out.println("Result from getKuranAndBgayatArea:");
			    for (Object[] row : result) {
			        System.out.println(Arrays.toString(row));
			    }

			    GramAdarshTaktaDTO dto = new GramAdarshTaktaDTO();

			    if (result != null && !result.isEmpty()) {
			        Object[] row = result.get(0);

			        dto.setKuran((row[0] instanceof Number) ? ((Number) row[0]).intValue() : 0);
			        dto.setSumOfBgayatArea((row[1] instanceof Number) ? ((Number) row[1]).doubleValue() : 0.0);

			    } else {
			        // Optionally default values if result is empty
			        dto.setKuran(0);
			        dto.setSumOfBgayatArea(0.0);
			    }

			    return dto;

		}
	  
	    
	    
}