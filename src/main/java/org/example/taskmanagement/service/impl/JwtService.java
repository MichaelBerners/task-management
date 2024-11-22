package org.example.taskmanagement.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  @Value("${token.spring.key}")
  private String jwtSigningKey;

  /**
   * Генерация токена
   *
   * @param userDetails данные пользователя
   * @return токен
   */
  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("email", userDetails.getUsername());
    claims.put("password", userDetails.getPassword());
    claims.put("role", userDetails.getAuthorities());

    String token = Jwts.builder().claims(claims).subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 100_000 * 60 * 24))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();

    return token;

  }

  /**
   * Получение ключа для подписи токена
   * @return ключ
   */
  private Key getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);

    return Keys.hmacShaKeyFor(keyBytes);
  }

  /**
   * Извлечение email из токена
   *
   * @param token токен
   * @return имя пользователя
   */

  public String extractEmail(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Извлечение данных из токена
   *
   * @param token           токен
   * @param claimsResolvers функция извлечения данных
   * @param <T>             тип данных
   * @return данные
   */

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
    Claims claims = extractAllClaims(token);

    return claimsResolvers.apply(claims);
  }

  /**
   * Извлечение всех данных из токена
   *
   * @param token токен
   * @return данные
   */

  public Claims extractAllClaims(String token) {
    return Jwts
        .parser()
        .setSigningKey(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  /**
   * Проверка токена на просроченность
   *
   * @param token токен
   * @return true, если токен просрочен
   */

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String email = extractEmail(token);

    return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  /**
   * Проверка токена на просроченность
   *
   * @param token токен
   * @return true, если токен просрочен
   */

  private boolean isTokenExpired(String token) {

    return extractExpiration(token).before(new Date(System.currentTimeMillis()));
  }

  /**
   * Извлечение даты истечения токена
   *
   * @param token токен
   * @return дата истечения
   */

  public Date extractExpiration(String token) {

    return extractClaim(token, Claims::getExpiration);
  }
}