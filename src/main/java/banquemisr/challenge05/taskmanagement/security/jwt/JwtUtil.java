package banquemisr.challenge05.taskmanagement.security.jwt;


import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private final String secretKey;

    private final Key key;

    @Value("${jwt.access.token.expiration}")
    public long ACCESS_TOKEN_EXPIRATION;

    @Value("${jwt.refresh.token.expiration}")
    public long REFRESH_TOKEN_EXPIRATION;

    public JwtUtil(Dotenv dotenv) {

        this.secretKey = dotenv.get("JWT_SECRET", null);
        if (this.secretKey == null || this.secretKey.isEmpty()) {


            throw new IllegalStateException("Secret KEY missed");
        }

        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());

    }

    public String generateToken(String userName, String role) {
        long expiration = this.ACCESS_TOKEN_EXPIRATION;


        Map<String, Object> claims = new HashMap<>();


        if (role != null) {
            Collection<GrantedAuthority> roleCollection = Collections.singleton(
                    new SimpleGrantedAuthority("ROLE_" + role)
            );
            claims.put("roles", roleCollection);
        }

        logger.warn("generateToken claims {}", claims);

        return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + expiration)).signWith(key).compact();


    }

    public Claims extractAllClaims(String token) {

        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw e;
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUserName(String token) {
        return extractClaim(token, new Function<Claims, String>() {
            @Override
            public String apply(Claims claims) {
                return claims.getSubject();
            }
        });
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, new Function<Claims, Date>() {
            @Override
            public Date apply(Claims claims) {
                return claims.getExpiration();
            }
        });
    }

    public String extractRole(String token) {


        final Claims claims = extractAllClaims(token);


        ArrayList<Object> rolesArrayList = (ArrayList<Object>) claims.get("roles");

        Map<String, String> roleMap = (HashMap<String, String>) rolesArrayList.get(0);

        String role = roleMap.get("authority");

        return role;
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    public Boolean validateToken(String token, String userName) {


        return (extractUserName(token).equals(userName)) && !isTokenExpired(token);
    }

    public String generateRefreshToken(String userName) {
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setId(UUID.randomUUID().toString())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(key)
                .compact();
    }

    public boolean validateRefreshToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
