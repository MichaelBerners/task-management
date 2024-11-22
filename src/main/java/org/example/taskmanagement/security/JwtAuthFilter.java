package org.example.taskmanagement.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.example.taskmanagement.service.TaskUserService;
import org.example.taskmanagement.service.impl.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * первое куда мы попадаем в этот фильтр
 */
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  public static final String BEARER_PREFIX = "Bearer ";

  public static final String HEADER_NAME = "Authorization";

  private final JwtService jwtService;

  private final TaskUserService taskUserService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    /**
     * HEADER_NAME — название заголовка, где ожидается JWT (обычно Authorization).
     * BEARER_PREFIX — префикс, который обычно сопровождает JWT-токен (например, "Bearer ").
     * Если заголовок пустой или не начинается с "Bearer ", фильтр передаёт управление следующему
     * в цепочке через filterChain.doFilter(request, response).
     */
    String authHeader = request.getHeader(HEADER_NAME);
    if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, BEARER_PREFIX)) {
      filterChain.doFilter(request, response);
      return;
    }

    String jwt = authHeader.substring(BEARER_PREFIX.length());
    /**
     * jwtService.extractEmail(jwt) — метод, который извлекает email из токена
     * (обычно с помощью подписи токена и библиотек JWT).
     */
    String userEmail = jwtService.extractEmail(jwt);
    /**
     * Проверяется, что email не пустой.
     * Убеждается, что в SecurityContextHolder ещё нет аутентификации
     * (чтобы избежать избыточной обработки).
     */
    if (
        StringUtils.isNotEmpty(userEmail)
            && SecurityContextHolder.getContext().getAuthentication() == null
    ) {
      /**
       * taskUserService.loadUserByUsername(userEmail) загружает информацию о
       * пользователе (из БД или другого источника).
       * jwtService.isTokenValid(jwt, userDetails) проверяет валидность токена
       * (например, подпись, срок действия и соответствие пользователю).
       */
      UserDetails userDetails = taskUserService.loadUserByUsername(userEmail);
      if (jwtService.isTokenValid(jwt, userDetails)) {
        /**
         * SecurityContextHolder.createEmptyContext() создаёт новый пустой контекст.
         * UsernamePasswordAuthenticationToken — объект, представляющий аутентификацию.
         * userDetails — основной объект аутентификации.
         * null — вместо пароля (пароль не нужен, так как уже есть JWT).
         * userDetails.getAuthorities() — список ролей/прав доступа пользователя.
         * authToken.setDetails добавляет дополнительные данные о запросе
         * (например, IP-адрес, user-agent).
         * Устанавливает этот контекст в SecurityContextHolder, чтобы другие части приложения могли
         * идентифицировать пользователя.
         */
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
      }
    }
    /**
     * После аутентификации передаёт обработку запроса следующему фильтру в цепочке.
     * Важно, чтобы метод фильтра заканчивался вызовом этого метода, иначе запрос "застрянет".
     */
    filterChain.doFilter(request, response);
  }
  /**
   * 1. UsernamePasswordAuthenticationToken
   * Представляет аутентификацию, используя имя пользователя, пароль (или null) и список прав доступа.
   * В данном случае он используется, чтобы "подтвердить", что пользователь успешно аутентифицирован с помощью токена.
   * 2. SecurityContextHolder
   * Это центральный компонент Spring Security, где хранится текущая аутентификация.
   * После того как токен проверен, аутентификация добавляется в контекст, и дальнейшие запросы к защищённым ресурсам используют эти данные.
   */
}
