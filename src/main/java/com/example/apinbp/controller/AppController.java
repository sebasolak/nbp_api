package com.example.apinbp.controller;

import com.example.apinbp.model.dtos.UserRegistrationDTO;
import com.example.apinbp.service.CurrencyService;
import com.example.apinbp.service.MailService;
import com.example.apinbp.service.SecurityContextHolderService;
import com.example.apinbp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping
public class AppController {


    private final CurrencyService currencyService;
    private final UserService userService;
    private final SecurityContextHolderService securityContextHolderService;
    private final MailService mailService;

    @Autowired
    public AppController(CurrencyService currencyService,
                         UserService userService,
                         SecurityContextHolderService securityContextHolderService,
                         MailService mailService) {
        this.currencyService = currencyService;
        this.userService = userService;
        this.securityContextHolderService = securityContextHolderService;
        this.mailService = mailService;
    }

    @GetMapping
    public List<String> selectAvailableCurrenciesRates() {
        return currencyService.selectAvailableCurrenciesRates();
    }


    @GetMapping("{code}")
    public Message getRate(@PathVariable String code) {
        return currencyService.selectRate(code);
    }

    @GetMapping("{codeFrom}/{codeTo}/{amount}")
    public Message exchangeMoney(@PathVariable String codeFrom,
                                 @PathVariable String codeTo,
                                 @PathVariable Long amount) {
        String login = securityContextHolderService.getLoginOfLoggedUser();
        return currencyService.exchangeMoney(codeFrom, codeTo, amount, login);
    }

    @PostMapping("registration")
    public void registerUserAccount(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        userService.save(userRegistrationDTO);
    }

    @GetMapping("send")
    public String sendOperationsToLoggedUser() throws MessagingException {
        String login = securityContextHolderService.getLoginOfLoggedUser();
        String email = userService.selectUserEmailByUserLogin(login);
        mailService.sendMail(email,
                "Operations list",
                userService.selectOperationsByUserLogin(login).toString(),
                true);
        return "list of operations was send";
    }

}
