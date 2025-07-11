package com.zinkworks.atmmachine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zinkworks.atmmachine.entity.UserAccount;


/**
 * @author Manish.Singh
 */
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> { }