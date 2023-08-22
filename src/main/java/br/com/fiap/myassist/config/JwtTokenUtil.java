package br.com.fiap.myassist.config;

import br.com.fiap.myassist.entity.Usuario;
import br.com.fiap.myassist.enums.RoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenUtil {

    @Value("${token.security.key}")
    private String jwtSecurityKey;

    @Value("${token.security.expiration-time}")
    private Duration jwtExpirationTime;

    private Key getChaveAssinatura() {
        return Keys.hmacShaKeyFor(jwtSecurityKey.getBytes());
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getChaveAssinatura())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String gerarToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", ((Usuario)userDetails).getRoles())
                .claim("id", ((Usuario)userDetails).getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTime.toMillis()))
                .signWith(getChaveAssinatura(), SignatureAlgorithm.HS256)
                .compact();

    }

    @SuppressWarnings("unchecked")
    public UserDetails getUsuarioAutenticado(String token) {
        var claims = getClaims(token);
        var role = claims.get("roles", List.class).stream().findAny().orElse(RoleEnum.USER.name());
        return Usuario.builder().login(claims.getSubject())
                .id(claims.get("id", Integer.class))
                .role(RoleEnum.valueOf((String) role))
                .build();
    }

}
