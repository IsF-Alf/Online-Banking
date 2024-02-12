package com.isd.Homebanking.controllers;

import com.isd.Homebanking.dtos.PaymentDTO;
import com.isd.Homebanking.models.*;
import com.isd.Homebanking.services.AccountService;
import com.isd.Homebanking.services.CardService;
import com.isd.Homebanking.services.ClientService;
import com.isd.Homebanking.services.TransactionService;
import com.isd.Homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardService cardService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;


    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(Authentication authentication, @RequestParam CardType type,
                                             @RequestParam CardColor color)
    {
        Client client = (clientService.findClientByEmail(authentication.getName()));
        if (client == null) {
            return new ResponseEntity<>("The entered client does not exist", HttpStatus.FORBIDDEN);
        }
        if (type == null) {
            return new ResponseEntity<>("Card type is required", HttpStatus.FORBIDDEN);
        }
        if (color == null) {
            return new ResponseEntity<>("Card type is required", HttpStatus.FORBIDDEN);
        }
        if (cardService.existsCardByTypeAndColorAndClientAndActive(type, color, client, true)) {
            return new ResponseEntity<>("You already have the same card", HttpStatus.FORBIDDEN);
        }
        Card card = new Card(generatedNumber(),(client.getFirstName().toUpperCase() + " " + client.getLastName().toUpperCase()), type,
                color,  CardUtils.generateCvv(), LocalDate.now().plusYears(5), LocalDate.now(), true);
        cardService.saveCard(card);
        client.addCard(card);
        clientService.saveClient(client);
        return new ResponseEntity<>("Card created successfully", HttpStatus.CREATED);
    }

    public String generatedNumber() {
        String numberGenerator;
        do {
            numberGenerator = CardUtils.generateNumber();
        } while (cardService.existsCardByNumber(numberGenerator));
        return numberGenerator;
    }

    @PatchMapping("/clients/current/cards")
    public ResponseEntity<Object> deleteCardToClient(Authentication authentication, @RequestParam String id) {
        Client client = clientService.findClientByEmail(authentication.getName());
        Card card = cardService.findById(id);
        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.FORBIDDEN);
        }
        if (card == null) {
            return new ResponseEntity<>("The card not exist", HttpStatus.FORBIDDEN);
        }
        if (!card.getActive()) {
            return new ResponseEntity<>("The card is inactive", HttpStatus.FORBIDDEN);
        }
        if (!card.getClient().equals(client)) {
            return new ResponseEntity<>("The card not belong to the authenticated client", HttpStatus.FORBIDDEN);
        }
        card.setActive(false);
        cardService.saveCard(card);
        return new ResponseEntity<>("Card deleted successfully", HttpStatus.CREATED);
    }

    @PostMapping("/clients/current/payments")
    public ResponseEntity<Object> payWithCard(@RequestBody PaymentDTO paymentDTO) {
        Card cardUsed = cardService.findByNumber(paymentDTO.getNumber());
        Client client = clientService.findClientByEmail(paymentDTO.getEmail());
        boolean hasCard = client.getCards().stream().anyMatch(card -> card.getId() == cardUsed.getId());
        Optional<Account> optionalAccountToBeDebited = client.getAccounts().stream().filter(
                acc -> acc.getBalance() >= paymentDTO.getAmount()).findFirst();

        if (paymentDTO.getNumber().isBlank() || !cardService.existsCardByNumber(paymentDTO.getNumber())) {
            return new ResponseEntity<>("The card number is invalid or does not exist", HttpStatus.FORBIDDEN);
        }
        if (!cardService.existsByCvv(paymentDTO.getCvv())) {
            return new ResponseEntity<>("Invalid CVV", HttpStatus.FORBIDDEN);
        }
        if (paymentDTO.getTypeCard() == null) {
            return new ResponseEntity<>("You must select a card type (DEBIT or CREDIT)", HttpStatus.FORBIDDEN);
        }
        if (cardUsed.getType() != paymentDTO.getTypeCard()) {
            return new ResponseEntity<>("The selected card type does not match the card number", HttpStatus.FORBIDDEN);
        }
        if (paymentDTO.getAmount() <= 0) {
            return new ResponseEntity<>("You must enter a valid amount", HttpStatus.FORBIDDEN);
        }
        if (paymentDTO.getDescription().isBlank()) {
            return new ResponseEntity<>("Description is required", HttpStatus.FORBIDDEN);
        }
        if (paymentDTO.getEmail().isBlank()) {
            return new ResponseEntity<>("Email is required", HttpStatus.FORBIDDEN);
        }
        if (client == null) {
            return new ResponseEntity<>("The entered email does not exist", HttpStatus.FORBIDDEN);
        }
        if (!hasCard) {
            return new ResponseEntity<>("The entered email belongs to another account", HttpStatus.FORBIDDEN);
        }
        if (cardUsed.getThruDate().isBefore(LocalDate.now())) {
            return new ResponseEntity<>("The card has expired", HttpStatus.FORBIDDEN);
        }
        if (optionalAccountToBeDebited.isPresent()) {
            Account accountToBeDebited = optionalAccountToBeDebited.get();
            Double paymentAmount = paymentDTO.getAmount();
            if (accountToBeDebited.getBalance() >= paymentAmount) {
                Double newBalance = accountToBeDebited.getBalance() - paymentAmount;
                Transaction paymentTransaction = new Transaction(TransactionType.DEBIT, paymentAmount,
                        paymentDTO.getDescription(), LocalDateTime.now(), newBalance, true);

                transactionService.saveTransaction(paymentTransaction);
                accountToBeDebited.addTransaction(paymentTransaction);
                accountToBeDebited.setBalance(newBalance);
                accountService.saveAccount(accountToBeDebited);

                return new ResponseEntity<>("Payment successful", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Insufficient funds", HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>("Insufficient funds", HttpStatus.FORBIDDEN);
        }
    }
}
