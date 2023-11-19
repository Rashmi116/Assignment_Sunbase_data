package com.okayjava.html.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

@RestController
public class CustomerController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/getCustomers")
    public ResponseEntity<?> getCustomerList(HttpSession session) {
        String token = (String) session.getAttribute("ACCESS_TOKEN");
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        String url = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return ResponseEntity.ok(response.getBody().trim());
    }

    @PostMapping("/deleteCustomer")
    public ResponseEntity<?> deleteCustomer(@RequestBody Map<String, String> payload, HttpSession session) {
        String token = (String) session.getAttribute("ACCESS_TOKEN");
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // Convert payload to URL encoded form data
        String formBody = payload.entrySet().stream()
                            .map(entry -> entry.getKey() + "=" + entry.getValue())
                            .collect(Collectors.joining("&"));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        String url = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?" + formBody;
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping("/createCustomer")
    public ResponseEntity<String> createCustomer(@RequestBody Map<String, String> customerData, HttpSession session) {
        String token = (String) session.getAttribute("ACCESS_TOKEN");
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=create";
        HttpEntity<Map<String, String>> request = new HttpEntity<>(customerData, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @PostMapping("/updateCustomer")
    public ResponseEntity<String> updateCustomer(@RequestBody Map<String, Object> customerData, HttpSession session) {
        String token = (String) session.getAttribute("ACCESS_TOKEN");
        // Extract UUID and remove it from the map
        String uuid = (String) customerData.remove("uuid");
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(customerData, headers);
        String url = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=update&uuid=" + uuid;

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

}

