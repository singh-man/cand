package com.hitpixel.hitpixelpaymentsystem.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
public class TransactionDAO {

    @JsonProperty("orderid")
    private Long id;

    @JsonProperty("datetime")
    @JsonFormat(pattern = "dd-MM-YYYY HH:mm")
    private Date orderDate;

    @JsonProperty("ordername")
    private String orderName;

    private String currency;

    @JsonProperty("cardtype")
    private String cardType;

    private String status;

    private Double amount;

    @JsonProperty("client")
    private String clientName;
}