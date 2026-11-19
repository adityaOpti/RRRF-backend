package com.probity.echawdi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.dao.ApiResponseDecryptExternal;
import com.probity.echawdi.dao.FerfarApiRequest;
import com.probity.echawdi.dao.ImageDataForSatBara;
import com.probity.echawdi.dao.SatBaraApiRequest;
import com.probity.echawdi.service.ExternalApiService;

/*-------- http://115.124.110.193:8091/echawdi/api/callExternalSatBaraApi?lgdCode=535327&pin=125 ----------*/

@RestController
@RequestMapping("/api")
public class ExternalApiSatBaraController {

    private final ExternalApiService externalApiService;

    public ExternalApiSatBaraController(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    
    @PostMapping("/callExternalSatBaraApi")
    public ResponseEntity<ApiResponseDecryptExternal<ImageDataForSatBara>> callExternal(
            @RequestBody SatBaraApiRequest request) {

        return ResponseEntity.ok(
                externalApiService.callExternalApi(
                        request.getLgd_code(),
                        request.getPin(),
                        request.getPin1(),
                        request.getPin2(),
                        request.getPin3(),
                        request.getPin4(),
                        request.getPin5(),
                        request.getPin6(),
                        request.getPin7(),
                        request.getPin8()
                       
                )
        );
    }
    
    
    
    @PostMapping("/callExternalFerfarApi")
    public ResponseEntity<ApiResponseDecryptExternal<ImageDataForSatBara>> callExternal(
            @RequestBody FerfarApiRequest request) {

        return ResponseEntity.ok(
                externalApiService.callExternalApiFerfar(
                        request.getLgd_code(),
                        request.getMut_no()
                       
                )
        );
    }
    
    
    
  
}
