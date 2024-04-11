package com.example.microservice.accounts.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservice.accounts.constants.AccountsConstants;
import com.example.microservice.accounts.dto.AccountsDto;
import com.example.microservice.accounts.dto.CustomerDto;
import com.example.microservice.accounts.entity.Accounts;
import com.example.microservice.accounts.entity.Customer;
import com.example.microservice.accounts.exception.CustomerAlreadyExistsException;
import com.example.microservice.accounts.exception.ResourceNotFoundException;
import com.example.microservice.accounts.mapper.AccountsMapper;
import com.example.microservice.accounts.mapper.CustomerMapper;
import com.example.microservice.accounts.repository.AccountsRepository;
import com.example.microservice.accounts.repository.CustomerRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountsService {
	
	@Autowired
	private AccountsRepository accountsRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
	public void createAccount(CustomerDto customerDto) {
		// TODO Auto-generated method stub
		Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
	    Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()) {
        	throw new CustomerAlreadyExistsException("Customer already registered with Mobile Number" + customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
	}

	private Accounts createNewAccount(Customer customer) {
		// TODO Auto-generated method stub
		Accounts newAccount = new Accounts();
		newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
		newAccount.setAccountNumber(randomAccNumber);
		newAccount.setAccountType(AccountsConstants.SAVINGS);
		newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
	}

	@Override
	public CustomerDto fetchAccount(String mobileNumber) {
		// TODO Auto-generated method stub
		Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
				()-> new ResourceNotFoundException("Customer","mobileNumber","mobile"));
		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
				()-> new ResourceNotFoundException("Account","customerId",customer.getCustomerId().toString()));
		CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
		customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
		return customerDto;
	}

	@Override
	public boolean updateAccount(CustomerDto customerDto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAccount(String mobileNumber) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
