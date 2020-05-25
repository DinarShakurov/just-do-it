package ru.shakurov.spring_webapp.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy =  GoalCreatingFormValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidGoalCreatingForm {
    String message() default "Error during creating goal";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
