package com.isd.Homebanking.controllers;

import com.isd.Homebanking.dtos.AccountDTO;
import com.isd.Homebanking.dtos.ClientDTO;
import com.isd.Homebanking.models.Account;
import com.isd.Homebanking.models.AccountType;
import com.isd.Homebanking.models.Client;
import com.isd.Homebanking.services.AccountService;
import com.isd.Homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/clients")
    public List<ClientDTO> getAllClients() {
        List<ClientDTO> clients = clientService.findAllClients().stream().map(client -> new ClientDTO(client)).collect(
                Collectors.toList());
        return clients;
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable String id) {
        ClientDTO client = new ClientDTO(clientService.findClientById(id));
        return client;
    }

    @GetMapping("/clients/current")
    public ClientDTO getAll(Authentication authentication) {
        return new ClientDTO(clientService.findClientByEmail(authentication.getName()));
    }

    @PostMapping("/clients")
    public ResponseEntity<Object> register(@RequestParam String firstName, @RequestParam String lastName,
                                           @RequestParam String email, @RequestParam String password)
    {

        if (firstName.isBlank()) {
            return new ResponseEntity<>("Please complete this field with your name", HttpStatus.FORBIDDEN);
        }

        if (lastName.isBlank()) {
            return new ResponseEntity<>("Please complete this field with your last name", HttpStatus.FORBIDDEN);
        }

        if (email.isBlank()) {
            return new ResponseEntity<>("Please complete this field with your email", HttpStatus.FORBIDDEN);
        }

        if (password.isBlank()) {
            return new ResponseEntity<>("Please complete this field with your password", HttpStatus.FORBIDDEN);
        }

        if (clientService.existsClientByEmail(email)) {
            return new ResponseEntity<>("Already exists a client with this email", HttpStatus.FORBIDDEN);
        }

        Client newClient = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        Account account = new Account(generateNumber(1, 100000000), LocalDate.now(), 0.00, true, AccountType.SAVINGS);
        accountService.saveAccount(account);
        newClient.addAccount(account);
        clientService.saveClient(newClient);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public String generateNumber(int min, int max) {
        List<AccountDTO> accounts = accountService.findAllAccounts().stream().map(
                account -> new AccountDTO(account)).collect(Collectors.toList());
        Set<String> setAccounts = accounts.stream().map(accountDTO -> accountDTO.getNumber()).collect(
                Collectors.toSet());
        String aux = "VIN - ";
        long number;
        String numberCompleted;
        do {
            number = (int) ((Math.random() * (max - min)) + min);
            String formattedNumber = String.format("%03d", number);
            numberCompleted = aux + formattedNumber;
        } while (setAccounts.contains(numberCompleted));
        return numberCompleted;
    }
}
