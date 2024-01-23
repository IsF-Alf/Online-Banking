package com.isd.Homebanking.controllers;

import com.isd.Homebanking.dtos.AccountDTO;
import com.isd.Homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAllAccounts() {
        List<AccountDTO> accounts = accountService.findAllAccounts().stream().map(
                account -> new AccountDTO(account)).collect(Collectors.toList());
        return accounts;
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> getAccount(Authentication authentication, @PathVariable Long id) {
        Client client = (clientService.findClientByEmail(authentication.getName()));
        Set<Long> accountsId = client.getAccounts().stream().map(account -> account.getId()).collect(
                Collectors.toSet());
        Account account = accountService.findAccountById(id);
        if (!accountsId.contains(id)) {
            return new ResponseEntity<>("The authenticated client does not have ownership of this account",
                    HttpStatus.FORBIDDEN);
        }
        if (account != null) {
            return new ResponseEntity<>(new AccountDTO(account), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getAll(Authentication authentication) {
        Client client = (clientService.findClientByEmail(authentication.getName()));
        List<AccountDTO> accounts = client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(
                Collectors.toList());
        return accounts;
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication, @RequestParam AccountType accountType) {
        Client client = (clientService.findClientByEmail(authentication.getName()));
        if (!clientService.existsClientByEmail(authentication.getName())) {
            return new ResponseEntity<>("The client was not found", HttpStatus.NOT_FOUND);
        }
        if (client.getAccounts().size() >= 3) {
            return new ResponseEntity<>("You have reached the limit of created accounts", HttpStatus.FORBIDDEN);
        } else {
            Account account = new Account(generateNumber(), LocalDate.now(), 0.00,true, accountType);
            accountService.saveAccount(account);
            client.addAccount(account);
            clientService.saveClient(client);
            return new ResponseEntity<>("Account created successfully", HttpStatus.CREATED);
        }
    }

    @PatchMapping("/clients/current/accounts")
    public ResponseEntity<Object> deleteAccount(Authentication authentication, @RequestParam Long id) {
        Client client = clientService.findClientByEmail(authentication.getName());
        Account account = accountService.findById(id);
        if (account == null) {
            return new ResponseEntity<>("The account doesn't exist",
                    HttpStatus.FORBIDDEN);
        }
        if (account.getBalance() != 0) {
            return new ResponseEntity<>("You can not delete an account with a balance greater than zero",
                    HttpStatus.FORBIDDEN);
        }
        if (!account.getActive()) {
            return new ResponseEntity<>("The account is inactive",
                    HttpStatus.FORBIDDEN);
        }
        if (!account.getClient().equals(client)) {
            return new ResponseEntity<>("The account doesn't belong to the authenticated client",
                    HttpStatus.FORBIDDEN);
        } else {
            account.setActive(false);
            account.getTransaction().forEach(transaction -> transaction.setActive(false));
            accountService.saveAccount(account);
            return new ResponseEntity<>("Account deleted successfully", HttpStatus.CREATED);
        }
    }

    public String generateNumber() {
        String numberGenerator;
        do {
            numberGenerator = AccountUtils.generateNumber();
        } while (accountService.existsAccountByNumber(numberGenerator));
        return numberGenerator;
    }
}
