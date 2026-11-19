package com.probity.echawdi.serviceImpl;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.probity.echawdi.dao.ApiResponseDecryptExternal;
import com.probity.echawdi.dao.ImageDataForSatBara;
import com.probity.echawdi.service.ExternalApiService;
import com.probity.echawdi.utils.AES256CBCNoPaddingUtil;

//Fianl Working Code 20/01/2025
@Service
public class ExternalApiServiceImpl implements ExternalApiService {

    @Value("${api.base-url-satbara}")
    private String baseUrlSatbara;
    
    
    @Value("${api.base-url-ferfar}")
    private String baseUrlFerfar;

    @Value("${api.bearer-token}")
    private String bearerToken;

    @Value("${api.api-key}")
    private String apiKey;

    @Value("${api.secret-key}")
    private String secretKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ExternalApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public ApiResponseDecryptExternal<ImageDataForSatBara> callExternalApi(String lgd_code, 
    																		String pin,
    																		String pin1,
    																		String pin2,
    																		String pin3,
    																		String pin4,
    																		String pin5,
    																		String pin6,
    																		String pin7,
    																		String pin8) {
        try {
        	
            String url = UriComponentsBuilder
                    .fromHttpUrl(baseUrlSatbara)
                    .queryParam("lgd_code", lgd_code)
                    .queryParam("pin", pin)
                    .queryParam("pin1", pin1)
                    .queryParam("pin2", pin2)
                    .queryParam("pin3", pin3)
                    .queryParam("pin4", pin4)
                    .queryParam("pin5", pin5)
                    .queryParam("pin6", pin6)
                    .queryParam("pin7", pin7)
                    .queryParam("pin8", pin8)
                    .toUriString();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + bearerToken);
            headers.set("API-KEY", apiKey);
            headers.set("SECRET-KEY", secretKey);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, String.class
            );

            String responseBody = response.getBody();
            if (responseBody == null || responseBody.trim().isEmpty()) {
                return new ApiResponseDecryptExternal<>("error", "Empty API response", null);
            }

            responseBody = responseBody.trim();
            String encryptedBase64;

            if (responseBody.startsWith("{") && responseBody.endsWith("}")) {
                JsonNode root = objectMapper.readTree(responseBody);
                encryptedBase64 = root.has("data") ? root.get("data").asText() : responseBody;
            } else {
                encryptedBase64 = responseBody;
            }

            String decrypted = AES256CBCNoPaddingUtil.decrypt(encryptedBase64, bearerToken);

            return handleDecryptedPayload(decrypted);

        } catch (Exception e) {
            throw new RuntimeException("Failed to process external API response", e);
        }
    }

    private ApiResponseDecryptExternal<ImageDataForSatBara> handleDecryptedPayload(String decrypted) {
        if (decrypted == null || decrypted.trim().isEmpty()) {
            return new ApiResponseDecryptExternal<>("error", "Empty decrypted payload", null);
        }

        decrypted = decrypted.trim();

        if (decrypted.startsWith("\"") && decrypted.endsWith("\"")) {
            decrypted = decrypted.substring(1, decrypted.length() - 1);
        }

        decrypted = decrypted.replace("\\/", "/");

        if (decrypted.startsWith("data:image")) {
            String[] parts = decrypted.split(",", 2);
            String mimeType = parts[0].replace("data:", "").replace(";base64", "");
            String base64Content = parts[1];
            long size = Base64.getDecoder().decode(base64Content).length;

            ImageDataForSatBara imageData = new ImageDataForSatBara(mimeType, base64Content, size);
            return new ApiResponseDecryptExternal<>("success", "Image payload", imageData);
        }

        // Fallback for plain text
        ImageDataForSatBara textData = new ImageDataForSatBara("text/plain", decrypted, decrypted.length());
        return new ApiResponseDecryptExternal<>("success", "Text payload", textData);
    }

    
    
	@Override
	public ApiResponseDecryptExternal<ImageDataForSatBara> callExternalApiFerfar(String lgd_code, String mut_no) {
		

        try {
        	
            String url = UriComponentsBuilder
                    .fromHttpUrl(baseUrlFerfar)
                    .queryParam("lgd_code", lgd_code)
                    .queryParam("mut_no", mut_no)
                    .toUriString();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + bearerToken);
            headers.set("API-KEY", apiKey);
            headers.set("SECRET-KEY", secretKey);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, String.class
            );

            String responseBody = response.getBody();
            if (responseBody == null || responseBody.trim().isEmpty()) {
                return new ApiResponseDecryptExternal<>("error", "Empty API response", null);
            }

            responseBody = responseBody.trim();
            String encryptedBase64;

            if (responseBody.startsWith("{") && responseBody.endsWith("}")) {
                JsonNode root = objectMapper.readTree(responseBody);
                encryptedBase64 = root.has("data") ? root.get("data").asText() : responseBody;
            } else {
                encryptedBase64 = responseBody;
            }

            String decrypted = AES256CBCNoPaddingUtil.decrypt(encryptedBase64, bearerToken);

            return handleDecryptedPayload(decrypted);

        } catch (Exception e) {
            throw new RuntimeException("Failed to process external API response", e);
        }
    
		
	}
	
	
	
}


    
    
    
   