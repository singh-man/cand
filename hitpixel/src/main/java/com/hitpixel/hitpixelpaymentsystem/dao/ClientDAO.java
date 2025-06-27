package com.hitpixel.hitpixelpaymentsystem.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ClientDAO {
    private Long id;

    @JsonProperty("client")
    private String name;

    private String status;

    @JsonProperty("billing-interval")
    private String billingInterval;

    private String email;

    @JsonProperty("fees-type")
    private String feesType;

    private Double fees;
}