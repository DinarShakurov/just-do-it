package ru.shakurov.spring_webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shakurov.spring_webapp.forms.ReplenishBalanceForm;
import ru.shakurov.spring_webapp.models.User;
import ru.shakurov.spring_webapp.security.details.UserDetailsImpl;
import ru.shakurov.spring_webapp.services.MoneyService;

@Controller
@RequestMapping("/money")
public class MoneyController {
    @Autowired
    private MoneyService moneyService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/replenish")
    public String getReplenishBalancePage() {
        return "replenish_balance";
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/replenish")
    public String replenishBalance(Authentication authentication, ReplenishBalanceForm form) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        moneyService.replenishUserBalance(user.getId(), form.getMoney());
        return "redirect:/money/replenish";
    }
}
