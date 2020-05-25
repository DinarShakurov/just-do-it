package ru.shakurov.spring_webapp.validation;

import ru.shakurov.spring_webapp.forms.GoalCreatingForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GoalCreatingFormValidator implements ConstraintValidator<ValidGoalCreatingForm, GoalCreatingForm> {

    @Override
    public void initialize(ValidGoalCreatingForm constraintAnnotation) {
    }

    @Override
    public boolean isValid(GoalCreatingForm form, ConstraintValidatorContext context) {
        return (form.getDay() + form.getHour() + form.getMinute()) >= 1;
    }
}
