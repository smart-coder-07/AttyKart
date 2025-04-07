package com.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService extends OncePerRequestFilter {


    public static final String SECRET ="5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";


    private long jwtExpirationMillis = 1000 * 60 * 60;  // 1 hour

    public String generateToken(String userName,String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_"+role);
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 6000 * 3000))
               // .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Validate the JWT token (check if it has expired and the signature is correct)
    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // Extract username from the JWT token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Extract expiration date from the JWT token
    private Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract claims from the JWT token (subject, expiration, etc.)
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    // For simplicity, you can use this to extract the claims and get specific values
    public String extractClaim(String token, String claimKey) {
        Claims claims = extractClaims(token);
        return claims.get(claimKey, String.class);  // Return the claim as a String (could be different type based on your needs)
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix

            String username = this.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (this.validateToken(token, username)) {
                    // Extract the role from the JWT claim
                    String role = extractClaim(token, "role");  // This should be "ROLE_USER" or "ROLE_ADMIN"

                    // Create authorities from the role
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);

                    // Create the authentication token with the role
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, List.of(authority));
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the authentication context
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
