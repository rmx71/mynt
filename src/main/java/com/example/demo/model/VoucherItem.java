package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class VoucherItem {

    private String code;
    private float discount;
    private Date expiry;
}
