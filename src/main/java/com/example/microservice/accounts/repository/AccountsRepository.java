package com.example.microservice.accounts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.microservice.accounts.entity.Accounts;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts,Long>{

	Optional<Accounts> findByCustomerId(Long customerId);
	
	void deleteByCustomerId(Long customerId);
}
