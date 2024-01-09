package com.example.demo.controller;

import com.example.demo.model.DeliveryRate;
import com.example.demo.model.Parcel;
import com.example.demo.service.DeliveryRateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DeliveryRateController {

    @Autowired
    private DeliveryRateService deliveryRateService;
    @PostMapping("/rate")
    @ResponseBody
    public ResponseEntity<DeliveryRate> compute(@Valid  @RequestBody Parcel parcel) {
        DeliveryRate response = deliveryRateService.compute(parcel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
