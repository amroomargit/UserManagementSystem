package com.sword.usermanagementsystem.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


@Component
public class JwtUtil {

    //Generates a secret key to be used to sign and verify tokens. HS256 is the symmetric encryption algorithm we'll be using
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    //Length of time a token is valid
    private final long expirationMs = 3600000;

    //Creating the token
    public String generateToken(String username){
        return Jwts.builder() //JWT builder object created to define token's content
                .setSubject(username) //sets subject claim
                .setIssuedAt(new Date()) //timestamp of token creation
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs)) //sets token expiration date
                .signWith(key) //signs token using secret key and HS256 algorithm
                .compact(); //builds token and converts it into compact string
    }

    //Returns true or false to validate if the token passed to it is good or not
    public boolean validateToken(String token){
        //Tries to parse token using secret key, if successful, the token's signature matches (wasn't tampered with), and isn't expired
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }
        //If any problem occurs (i.e. token is expired, malformed, has invalid signature, etc) then exception is thrown
        catch(JwtException e){
            return false;
        }
    }

    //Returns username extracted from the token (basically, if token was made for "Steve", this method returns "Steve")
    public String extractUsername(String token){
        return Jwts.parserBuilder().setSigningKey(key).build() //Parsing JWT string (using secret key to verify token is authentic)
                .parseClaimsJws(token).getBody() //Retrieves the token's body (claims)
                .getSubject(); //Gets subject name (the one we set to username in generateToken method)
    }
}
