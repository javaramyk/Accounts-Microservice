package com.example.microservice.accounts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.microservice.accounts.constants.AccountsConstants;
import com.example.microservice.accounts.dto.CustomerDto;
import com.example.microservice.accounts.dto.ErrorResponseDto;
import com.example.microservice.accounts.dto.ResponseDto;
import com.example.microservice.accounts.service.IAccountsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@Tag(
	name="CRUD REST APIs for Accounts in Spring Project",
	description="CRUD REST APIs in Spring Project to CREATE,UPDATE,FETCH and DELETE account details"
	)
@RestController
@RequestMapping(path="/api",produces= {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class AccountsController {

	private IAccountsService iAccountsService;
	
	@Operation(
			summary ="Create Account REST API",
			description="REST API to create new Customer and Account inside Spring Project"
			)
	@ApiResponses({
		@ApiResponse(
				responseCode = "201",
				description="HTTP Status CREATED"
				),
		@ApiResponse(
				responseCode="500",
				description="HTTP Status Internal Server Error",
						content = @Content(
	                            schema = @Schema(implementation = ErrorResponseDto.class)
	                    )
		)
		
	})
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
		iAccountsService.createAccount(customerDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
	}
	@Operation(
			summary ="Fetch Account Details REST API",
			description=""
			
			)
	@GetMapping("/fetch")
	public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber){
		CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(customerDto);		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto){
		boolean isUpdated = iAccountsService.updateAccount(customerDto);
		if(isUpdated) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));	
		}
		else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_UPDATE));
		}
	}
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam @Pattern(regexp="(^$|[0-9]{10}",message="Mobile number must be 10 digits") String mobileNumber){
		boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
		if(isDeleted) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
		}
		else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_DELETE));
		}
	}
	
}
