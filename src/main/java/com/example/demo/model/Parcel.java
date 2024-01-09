package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Parcel {

    @NotNull
    @Min(value = 1, message = "Invalid length")
    private float length;

    @NotNull
    @Min(value = 1, message = "Invalid weight value")
    private float width;

    @NotNull
    @Min(value = 1, message = "Invalid height value")
    private float height;

    @NotNull
    @Min(value = 1, message = "Invalid weight value")
    private float weight;

    @Pattern(regexp="^[A-Za-z]*$", message = "Invalid discount code value")
    private String discountCode;

    @JsonIgnore
    private Float volume;

    public Float getVolume() {
        return height * width * length;
    }
}
