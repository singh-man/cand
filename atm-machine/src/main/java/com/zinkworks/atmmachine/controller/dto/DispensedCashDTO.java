package com.zinkworks.atmmachine.controller.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Manish.Singh
 */
public class DispensedCashDTO {

    @Getter
    @Setter
    private Integer note5 = Integer.valueOf(0); // 20

    @Getter
    @Setter
    private Integer note10 = Integer.valueOf(0); // 30

    @Getter
    @Setter
    private Integer note20 = Integer.valueOf(0); // 30

    @Getter
    @Setter
    private Integer note50 = Integer.valueOf(0); // 10

    public double getMoneyCount() {
        return note5.intValue() * 5 + note10.intValue() * 10 + note20.intValue() * 20 + note50.intValue() * 50;
    }

}
