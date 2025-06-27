package com.hitpixel.hitpixelpaymentsystem.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@ToString(exclude = {"transactions"})
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client")
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String status;

    private String billingInterval;

    private String email;

    private String feesType;

    private Double fees;
}