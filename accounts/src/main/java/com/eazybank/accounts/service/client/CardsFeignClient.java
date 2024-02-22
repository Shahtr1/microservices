package com.eazybank.accounts.service.client;

import com.eazybank.accounts.dto.CardsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cards") // same value we use to register with eureka server
public interface CardsFeignClient {

//    The client will wait for 30 seconds ( default ) before removing cache and calling eureka server for updated data again

    @GetMapping(value = "/api/fetch", consumes = "application/json")
    // should be same as what we have defined in the actual microservice
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestParam String mobileNumber);
}
