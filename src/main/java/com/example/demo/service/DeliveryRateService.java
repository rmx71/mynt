package com.example.demo.service;


import com.example.demo.model.DeliveryRate;
import com.example.demo.model.Parcel;
import com.example.demo.model.VoucherItem;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Slf4j
@Service
public class DeliveryRateService {
    @Autowired
    private WebClient webClient;

    @Autowired
    private KieContainer kieContainer;

    @Value("${api.key}")
    private String apiKey;

    public DeliveryRate compute(Parcel parcel) {
        DeliveryRate deliveryRate = new DeliveryRate();
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.setGlobal("deliveryRate", deliveryRate);
        kieSession.insert(parcel);
        kieSession.fireAllRules();
        kieSession.dispose();

        if (deliveryRate.getTotalDeliveryRate() == null) {
            return deliveryRate;
        }

        float discountFromVoucher = getDiscountFromVoucher(parcel.getDiscountCode());
        deliveryRate.setDiscountFromVoucher(discountFromVoucher);

        deliveryRate.setTotalDeliveryRate(deliveryRate.getTotalDeliveryRate() - discountFromVoucher);
        return deliveryRate;
    }

    private float getDiscountFromVoucher(String discountCode) {
        float discountFromVoucher = 0.0f;
        VoucherItem voucherItem = webClient
                .get()
                .uri(uriBuilder->uriBuilder.path("/voucher/{voucher}")
                        .queryParam("key",apiKey)
                        .build(discountCode))
                .retrieve()
                .bodyToMono(VoucherItem.class)
                .timeout(Duration.ofSeconds(5))
                .doOnError(Exception.class, ex -> {
                    log.debug("Connect timeout occurred: " + ex.getMessage());
                }).onErrorReturn(VoucherItem.builder().discount(discountFromVoucher).build()).block();
        return voucherItem.getDiscount();
    }
}
