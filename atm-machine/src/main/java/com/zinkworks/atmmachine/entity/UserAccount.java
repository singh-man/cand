package com.zinkworks.atmmachine.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Manish.Singh
 */
@Entity
@Table(name = "user_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {

    @Id
    private long accountNumber;

    private int pin;

    private double openingBalance;

    private double overDraft;

}