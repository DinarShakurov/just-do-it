package ru.shakurov.spring_webapp.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.shakurov.spring_webapp.forms.ReplenishBalanceForm;
import ru.shakurov.spring_webapp.security.jwt.details.UserDetailsJwtBasedImpl;
import ru.shakurov.spring_webapp.services.MoneyService;

@RestController
@RequestMapping("/api/money")
public class MoneyRestController {
    @Autowired
    private MoneyService moneyService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/replenish")
    @ResponseStatus(value = HttpStatus.OK)
    public void replenishBalance(@AuthenticationPrincipal Authentication authentication,
                                 @RequestBody ReplenishBalanceForm form) {
        UserDetailsJwtBasedImpl userDetails = (UserDetailsJwtBasedImpl) authentication.getPrincipal();
        moneyService.replenishUserBalance(userDetails.getId(), form.getMoney());
    }

}
