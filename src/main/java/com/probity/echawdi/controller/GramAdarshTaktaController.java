package com.probity.echawdi.controller;


import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.config.WithDatabase;
import com.probity.echawdi.dao.GramAdarshTaktaDTO;
import com.probity.echawdi.dao.GramAdarshaTaktaSaveDTO;
import com.probity.echawdi.service.GramAdarshTaktaService;
import com.probity.echawdi.utils.DataSourceType;



@RestController
@RequestMapping("api/gramAdarshTakta")
public class GramAdarshTaktaController {
	
	@Autowired
	private GramAdarshTaktaService gramAdarshTaktaService;

	
	//(1)Greater area and below area
	@GetMapping("/getKhatedarCountAndBelowArea")
	public ResponseEntity<GramAdarshTaktaDTO> getKhatedarCountAndBelowArea( @RequestParam String ccode,
	        @RequestParam String revenueYear, HttpServletRequest request,Locale locale) {
		
		
		return  ResponseEntity.ok(gramAdarshTaktaService.getKhatedarCountAndBelowArea(ccode,revenueYear,request, locale));
	    
	}
	
	
	//(2)potkhrabarea and akrushak area
	@GetMapping("/getAkrushakAndPothkhrabAreaByccode")
	public ResponseEntity<GramAdarshTaktaDTO> getAkrushakAndPothkhrabAreaByccode( @RequestParam String ccode,
	         HttpServletRequest request,Locale locale) {
		
		
		return  ResponseEntity.ok(gramAdarshTaktaService.getAkrushakAndPothkhrabAreaByccode(ccode,request, locale));
	    
	}
	
	
	//(3)All Tax calculation
	@GetMapping("/getAllTaxCalculationBycCode")
	public ResponseEntity<GramAdarshTaktaDTO> getAllTaxCalculationBycCode( @RequestParam String ccode,
	       /* @RequestParam String revenueYear,*/ HttpServletRequest request,Locale locale) {
		
		
		return  ResponseEntity.ok(gramAdarshTaktaService.getAllTaxCalculationBycCode(ccode/*,revenueYear*/,request, locale));
	    
	}
	
	//(4)vanjminArea and Gaothan Area
	  @GetMapping("/getForestAndGaothanKshetra")
	    public ResponseEntity<GramAdarshTaktaDTO> getForestAndGaothanKshetra(
	            @RequestParam String cCode,
	            HttpServletRequest request,
	            Locale locale) {
		  
		  return  ResponseEntity.ok(gramAdarshTaktaService.getForestAndGaothanKshetraByCcode(cCode, request, locale));
		    

	    }
	  
	  
	  	//(5)for bindumala and dumala areas
		@GetMapping("/getBindumalaAndDumalaAreas")
		public ResponseEntity<GramAdarshTaktaDTO> getBindumalaAndDumalaAreasBycCode( @RequestParam String ccode,
		        HttpServletRequest request,Locale locale) {
			
			return  ResponseEntity.ok(gramAdarshTaktaService.getBindumalaAndDumalaAreasBycCode(ccode,request, locale));
		    
		}
	  
	  
		//(6)jalSinchan
		  @GetMapping("/getJalsinchanWellCountByccode")
		    public ResponseEntity<GramAdarshTaktaDTO> getJalsinchanWellCount(
		            @RequestParam String cCode,
		            HttpServletRequest request,
		            Locale locale) {
			  
			  return  ResponseEntity.ok(gramAdarshTaktaService.getJalsinchanWellCount(cCode, request, locale));
			    

		    }
		  
		  
		//(7)getMahasulMafOrKamakariJaminiData
		  @GetMapping("/getMahasulMafOrKamakariJaminiData")
		    public ResponseEntity<GramAdarshTaktaDTO> getMahasulMafOrKamakariJaminiData(
		            @RequestParam String cCode,
		            HttpServletRequest request,
		            Locale locale) {
			  
			  return  ResponseEntity.ok(gramAdarshTaktaService.getMahasulMafAndKamakariJaminiData(cCode, request, locale));
			    
		    }
		  
		  
		  ////API for Save all The records to the table
	    @PostMapping("/saveGramAdarshTaktaData")
		    @WithDatabase(DataSourceType.echmst)
		    public ResponseEntity<GramAdarshaTaktaSaveDTO> saveGramAdarshaTakta(@RequestBody GramAdarshaTaktaSaveDTO gramAdarshaTaktaDTO,
		    		HttpServletRequest request,
		            Locale locale
		    		
		    		) {
		        GramAdarshaTaktaSaveDTO savedGramAdarshaTakta = gramAdarshTaktaService.save(gramAdarshaTaktaDTO,request, locale);
		        return ResponseEntity.ok(savedGramAdarshaTakta);
		    }
		
		  
		    //API for getAll The records from the table
		    @GetMapping("/getAllGramAdarshTakaDataBycCode/{ccode}")
		    @WithDatabase(DataSourceType.echmst)
		    public ResponseEntity<List<GramAdarshaTaktaSaveDTO>> getGramAdarshaTaktaByCcodeAndRevenueYear(
		    		@PathVariable String ccode
		       
		    ) {
		        List<GramAdarshaTaktaSaveDTO> dtos = gramAdarshTaktaService.getByCcodeAndRevenueYear(ccode);
		        return ResponseEntity.ok(dtos);
		    }
		    
		    //API for kuran area and bagayat area
		    @GetMapping("/getKuranAndBagayatAreaByccode")
		    public ResponseEntity<GramAdarshTaktaDTO> getkuranAndBgayatArea(
		            @RequestParam String cCode,
		            HttpServletRequest request,
		            Locale locale) {
			  
			  return  ResponseEntity.ok(gramAdarshTaktaService.getkuranAreaAndBagayatArea(cCode, request, locale));
			    

		    }
		  
}
