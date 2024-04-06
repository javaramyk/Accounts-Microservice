package com.example.microservice.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(name="Accounts",description="Schema to hold Account Information")
public class AccountsDto {

	@NotEmpty(message="AccountNumber can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
	@Schema(description="Account Number of Spring Account",example="3456789120")
	private Long accountNumber;
	
	@NotEmpty(message="AccountType can not be a null or empty")
	@Schema(description="Account Type of Spring Account",example="Savings")
	private String accountType;
	
    @NotEmpty(message = "BranchAddress can not be a null or empty")
	@Schema(description="Spring Account Branch Address",example="521 MLKD")
	private String branchAddress;
}
