package com.sword.usermanagementsystem.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
//OncePerRequestFilter guarantees that Spring only runs this filter once per request
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    /*Note: A chain is a sequence/pipeline of filters that every request coming in to the controllers
    (every HTTP request) must pass through before actually reaching the controllers. Basically, this JwtFilter is adding
    itself as another filter to that long list of filters that must be checked before reaching the controllers.*/
    @Override
    protected void doFilterInternal(HttpServletRequest request, //info about HTTP request (headers, URL, method, etc.)
                                    HttpServletResponse response, //what will be sent back to client if filter stops chain
                                    FilterChain filterChain) //continues request if validation succeeds
            throws ServletException, IOException{

        String path = request.getRequestURI(); //gets the request path (i.e. http://localhost8081/users/login/student)

                /* Checks if the path contains "/login" or "/register", and if it does, skip token validation
                 (since users don't yet have a token when registering or logging in, but they would AFTER having
                 logged in and using another request, like getAllUsers) */
        if(path.contains("/login") || path.contains("/register")){
            filterChain.doFilter(request,response); //If it does have /login or /register, this line tells Spring "I'm done checking".
            return;
        }

                /* Retrieves Authorization header from HTTP request (the thing containing the token that the user is
                 entering to validate themselves */
        String header = request.getHeader("Authorization");

        if(header == null || !header.startsWith("Bearer ")){ //If header is missing or doesn't start with "Bearer ", reject request
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header"); //Sends 401 error and stops processing
            return; //Prevents anonymous access to protected endpoints
        }

        String token = header.substring(7); //Extracts token string, removes "Bearer " prefix to get actual JWT
        if(!jwtUtil.validateToken(token)){ //Calls validation method we created to see if token is valid
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token"); //If not, 401 + this message, and stop the chain
            return;
        }

        /* If the token passes all checks, the filter allows the request to continue to the next filter or your controller
        endpoint. From here, your app processes the request normally. */
        filterChain.doFilter(request,response);
        //This line is literally saying "I've done my job in this filter, now pass the request to the next filter in the chain
    }
}
