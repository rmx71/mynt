package com.example.demo.model;

import lombok.Data;

@Data
public class DeliveryRate {

    private Float totalDeliveryRate;
    private String rule;
    private String weight;
    private String volume;
    private Float discountFromVoucher;
}
