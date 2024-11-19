
package com.bank_app.controller;

import com.bank_app.models.Account;
import com.bank_app.service.AccountService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Fetch all accounts
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    // Add a new account
    @PostMapping
    public Account addAccount(@RequestBody Account account) {


        // Call the service to add the new account
        return accountService.addAccount(account.getAccount_name(), account.getAccountType(), account.getCustomer_id());
    }
}
