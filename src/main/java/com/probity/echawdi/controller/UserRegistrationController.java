
package com.probity.echawdi.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.probity.echawdi.dao.SignupRequest;
import com.probity.echawdi.dao.SignupResponse;
import com.probity.echawdi.serviceImpl.UserRegistrationService7B;

@RestController
@RequestMapping("/api/7b")
@Validated
public class UserRegistrationController {

    @Autowired
    private UserRegistrationService7B registrationService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(

            @RequestParam @NotBlank String mhrDbName,
            @RequestParam @NotBlank String mhrSchemaName,
            @RequestParam @NotBlank String mhrhost,

            @Valid @RequestBody SignupRequest request) {

        String userId = registrationService.signup(request);

        SignupResponse response = new SignupResponse(
                true,
                "7B registration successful",
                userId
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

