
package com.probity.echawdi.controller;



import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.dao.InwardOutwardRegisterDto;
import com.probity.echawdi.service.InwardOutwardRegisterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inwardOutward")
@RequiredArgsConstructor
public class InwardOutwardRegisterController {

    private final InwardOutwardRegisterService service;

    @PostMapping("/save")
    public ResponseEntity<String> saveInwardOutwardRegister(
            @RequestBody InwardOutwardRegisterDto dto) {

        String response =
                service.saveInwardOutwardRegister(dto);

        return new ResponseEntity<String>(
                response, HttpStatus.CREATED);
    }
    
    

	@GetMapping("/getBySevarthId")
	public ResponseEntity<List<InwardOutwardRegisterDto>> getBySevarthId(
	        @RequestParam("sevarthId") String sevarthId) {
	
	    List<InwardOutwardRegisterDto> response =
	            service.getBySevarthId(sevarthId);
	
	    return new ResponseEntity<List<InwardOutwardRegisterDto>>(
	            response, HttpStatus.OK);
	}

}
