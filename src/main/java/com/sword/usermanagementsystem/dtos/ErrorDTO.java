package com.sword.usermanagementsystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/*The purpose of this class is to pass clean and structured info about an error when one happens. It is accessed in
 GlobalExceptionHandler class to send a JSON response to client (i.e. postman, swagger, etc.) */

/* Basically, for us, this means that if the user tries to register a teacher or student user but enters a whitespace
  somewhere in either/both the username and password, then GlobalExceptionHandler accesses this class to send back...
{
  "message": "Validation failed",
  "modelErrors": {
    "username": "Username cannot be blank",
    "password": "Field cannot contain whitespace"
  }
}

Here, you can see that the field String message is the message "Validation failed", and Map<String, String> modelErrors
sends the first String in <String,String> as "username", and the second String as "Username cannot be blank". Then this
is sent another time, but for the password, and it's error message. "password": "Field cannot contain whitespace".

The message "Validation failed" comes from the GlobalExceptionHandler class. The message for username and password fields
will come from either/both the @NotBlank(message = "Username cannot be blank") in UserDTO, and/or the line
String message() default "Field cannot contain whitespace." in the WhiteSpaceConstraint annotation class. Basically,
it's from whether the placement of the whitespace or if it's one of the other options like null or empty, triggering one
of, or both of @NotBlank in the UserDTO or .isBlank in WhiteSpaceValidator. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {
    private String message;
    private Map<String, String> modelErrors;
}
