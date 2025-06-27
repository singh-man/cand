package com.zinkworks.atmmachine.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Manish.Singh
 */
@Entity
@Table(name = "atm")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public final class ATM {

    @Id
    public final Long id = 1L;

    @NonNull
    private Integer note5; // 20

    @NonNull
    private Integer note10; // 30

    @NonNull
    private Integer note20; // 30

    @NonNull
    private Integer note50; // 10

}
