package com.bank_app.service;

import com.bank_app.models.Account;
import com.bank_app.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Fetch all accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Add a new account to the database
    public Account addAccount(String accountName, String accountType, Integer customerId) {
        Account newAccount = new Account();

        // Set fields for the new account
        newAccount.setAccount_name(Optional.ofNullable(accountName).orElse("Base_account"));
        newAccount.setAccountType(Optional.ofNullable(accountType).orElse("Savings"));
        newAccount.setAccountNumber(generateAccountNumber()); // Generate a unique account number
        newAccount.setBalance(BigDecimal.ZERO); // Initialize balance to 0.00
        newAccount.setCustomer_id(customerId); // Set customer ID
        newAccount.setCreatedAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime()); // Set created at timestamp
        newAccount.setUpdatedAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime()); // Set updated at timestamp

        // Save the new account to the database
        return accountRepository.save(newAccount);
    }

    // Helper method to generate a unique account number
    private String generateAccountNumber() {
        // A more robust solution could involve using UUID or ensuring uniqueness via database
        // This method generates an account number based on current timestamp and random number
        long timestamp = System.currentTimeMillis();
        int randomNum = (int) (Math.random() * 10000);  // Generate a 4-digit random number
        return "ACC" + timestamp + randomNum;  // Create a unique account number
    }
}
