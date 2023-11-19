package com.okayjava.html.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.okayjava.html.model.AuthResponse;
import com.okayjava.html.model.UserCredentials;

import javax.servlet.http.HttpSession;

@RestController
public class AuthenticationController {

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody UserCredentials credentials, HttpSession session) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserCredentials> request = new HttpEntity<>(credentials, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
        
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            try {
                AuthResponse authResponse = new ObjectMapper().readValue(response.getBody().trim(), AuthResponse.class);
                session.setAttribute("ACCESS_TOKEN", authResponse.getAccess_token()); // Store the token in the session
                return ResponseEntity.ok().body("Authentication Successful");
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error parsing JSON", e);
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
