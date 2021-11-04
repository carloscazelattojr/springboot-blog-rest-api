package br.com.carlosjunior.blog.security;

import br.com.carlosjunior.blog.exception.BlogAPIException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpiratinInMs;

    public String generateToken(Authentication auth){
        String username = auth.getName();
        Date currentDate = new Date();
        Date expirateDate = new Date(currentDate.getTime() + jwtExpiratinInMs);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expirateDate)
                .signWith(SignatureAlgorithm.ES512, jwtSecret)
                .compact();

        return token;
    }

    // get username from the token
    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject()
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Invalid JWT Signature");
        } catch (MalformedJwtException e){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Invalid JWT token");
        } catch (ExpiredJwtException e){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Expired JWT Token");
        } catch (UnsupportedJwtException e){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Unsupported JWT Signature");
        } catch (IllegalArgumentException e){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"JWT claims string is empty");
        }
    }

}
