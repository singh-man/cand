package com.hitpixel.hitpixelpaymentsystem.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Data
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
public class Transaction implements Serializable {

    @Id
    @Column(nullable = false)
    private Long id;

    private Date orderDate;

    private String orderName;

    private String currency;

    private String cardType;

    private String status;

    private Double amount;

    private Long clientId;

    private String clientName;

    private Boolean isBillGenerated;

}