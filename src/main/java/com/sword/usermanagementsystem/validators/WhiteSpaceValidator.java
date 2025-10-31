package com.sword.usermanagementsystem.validators;

import org.apache.commons.lang3.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/*This is a validator class, it tells the validation framework how to check the custom annotation class we created,
@WhiteSpaceConstraint*/

/*ConstraintValidator<A,T> is a generic interface. A is the annotation we wish to validate (the custom class we created),
and T is the type of the field we are validating, in this case, a String, since we are checking usernames and passwords.*/

/* The validation framework (like Hibernate Validator or Spring Boot’s validation system) does this automatically:
1. Scans your entities or DTOs for validation annotations (like @WhiteSpaceConstraint).
2. Sees the @Constraint(validatedBy = WhiteSpaceValidator.class) line from your annotation.
3. Instantiates the WhiteSpaceValidator class.
4. Calls its isValid() method for each annotated field (i.e. private String username in the DTO).*/
public class WhiteSpaceValidator implements ConstraintValidator<WhiteSpaceConstraint,String> {

    //instance variable for the method initialize at the bottom
    private boolean canContainWhitespaces = false;

    /*isValid runs once for each validated field (i.e. username) and decides if it passes or fails validation*/
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context){

        //StringUtils is a helper class with static methods for working with strings.
        //.isBlank checks for null, empty, or only whitespace
        // this if statement skips validation if String is null or empty
        if (value == null || value.isBlank()){
            return true; // let @NotBlank handle emptiness
        }

        // Only run the no-space allowed rule if it's been decided that spaces are NOT allowed
        if(!canContainWhitespaces){
            return value.equals(value.trim()) && !value.contains(" ");
        }

        // If spaces ARE allowed, always return true
        return true;
    }


    /* When your validator is first created, Spring passes the annotation instance to this method. That lets you read
    the canContainSpaces flag the developer set on the annotation (in UserDTO).

    You read the configuration from the annotation, you store it in a field (this.canContainWhitespaces), then isValid()
    uses that stored value to decide how strict to be.*/
    @Override
    public void initialize(WhiteSpaceConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.canContainWhitespaces = constraintAnnotation.canContainSpaces();
    }
}
