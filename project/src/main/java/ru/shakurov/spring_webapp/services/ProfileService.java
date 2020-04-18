package ru.shakurov.spring_webapp.services;

import ru.shakurov.spring_webapp.dto.ProfileDto;
import ru.shakurov.spring_webapp.exceptions.BalanceException;
import ru.shakurov.spring_webapp.exceptions.DurationException;
import ru.shakurov.spring_webapp.exceptions.MoneyException;
import ru.shakurov.spring_webapp.forms.GoalCreatingForm;

public interface ProfileService {
    public ProfileDto getUserInfo(Long userId);
}
