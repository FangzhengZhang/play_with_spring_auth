package cat.frank.playWithAuthJWT.sercurityConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import java.util.Date;

import static cat.frank.playWithAuthJWT.sercurityConfig.SecurityConstants.JWT_Expiration;
import static cat.frank.playWithAuthJWT.sercurityConfig.SecurityConstants.JWT_SECRET;

/**
 * Set information in the token
 */
@Component
public class JWTGenerator {
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        String freeSpace = "freeSpace";
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + JWT_Expiration);
        String token = Jwts.builder()
                .setSubject(username)
                //.setSubject(freeSpace)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
        return token;
    }

    public String generateToken(String username){
        String freeSpace = "freeSpace";
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + JWT_Expiration);
        String token = Jwts.builder()
                .setSubject(username)
                //.setSubject(freeSpace)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
        return token;
    }

    /**
     * get information from the token
     * @param token
     * @return
     */
    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }
}
