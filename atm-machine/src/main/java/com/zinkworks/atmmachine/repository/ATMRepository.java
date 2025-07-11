package com.zinkworks.atmmachine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zinkworks.atmmachine.entity.ATM;

/**
 * @author Manish.Singh
 */
@Repository
public interface ATMRepository extends JpaRepository<ATM, Long> { }