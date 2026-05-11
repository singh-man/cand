package com.neueda.test.atm.service.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neueda.test.atm.entity.ATMCashDetails;

/**
 * 
 * @author Anubhav.Anand
 *
 */
@Repository
public interface ATMRepository extends JpaRepository<ATMCashDetails, Long> {

}
