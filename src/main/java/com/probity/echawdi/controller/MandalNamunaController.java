package com.probity.echawdi.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.dao.GovernmentPropertyRegisterDto;
import com.probity.echawdi.service.GovernmentPropertyRegisterService;

@RestController
@RequestMapping("/api/mandalNamuna")
@CrossOrigin(origins = "*")
public class MandalNamunaController {

    @Autowired
    private GovernmentPropertyRegisterService governmentPropertyRegisterService;

    @PostMapping("/save")
    public ResponseEntity<GovernmentPropertyRegisterDto> saveGovernmentProperty(
            @RequestBody GovernmentPropertyRegisterDto dto) {

        GovernmentPropertyRegisterDto response =
        		governmentPropertyRegisterService.saveGovernmentProperty(dto);

        return new ResponseEntity<GovernmentPropertyRegisterDto>(
                response, HttpStatus.CREATED);
    }
    
    
    @GetMapping("/getBySevarthId")
    public ResponseEntity<List<GovernmentPropertyRegisterDto>> getBySevarthId(
            @RequestParam("sevarthId") String sevarthId) {

        List<GovernmentPropertyRegisterDto> response =
                governmentPropertyRegisterService.getBySevarthId(sevarthId);

        return new ResponseEntity<List<GovernmentPropertyRegisterDto>>(
                response, HttpStatus.OK);
    }
}