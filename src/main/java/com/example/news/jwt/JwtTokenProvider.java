package com.example.news.jwt;

import com.example.news.entity.Author;
import com.example.news.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expired}")
    private Long expired;

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String username, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", getRoleNames(roles));

        Date now = new Date();
        Date validity = new Date(now.getTime() + expired);

        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authentication getAuth(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer_")) {
            return token.substring(7, token.length());
        }

        return null;
    }

    public List<String> getRoleNames(List<Role> roles) {
        return roles
                .stream()
                .map(role -> role.getName())
                .toList();
    }

    public boolean validateToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

        if (claims.getBody().getExpiration().before(new Date())) {
            return false;
        }

        return true;
    }
}
