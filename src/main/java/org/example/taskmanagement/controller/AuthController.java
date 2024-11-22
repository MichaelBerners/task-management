package org.example.taskmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.domain.request.SignInRequest;
import org.example.taskmanagement.domain.request.TaskUserRequest;
import org.example.taskmanagement.domain.response.JwtAuthenticationResponse;
import org.example.taskmanagement.domain.response.TaskUserResponse;
import org.example.taskmanagement.service.TaskUserService;
import org.example.taskmanagement.service.impl.AuthenticationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationService authenticationService;

  private final TaskUserService taskUserService;

  @PostMapping("/sign-in")
  @Operation(summary = "аутентификация, авторизация / получение токена")
  public ResponseEntity<String> signIn(@RequestBody SignInRequest sign) {
    JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.signIn(sign);
    return ResponseEntity.ok()
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtAuthenticationResponse.token())
        .body("Success!");
  }

  @PostMapping("/sign-up")
  @Operation(summary = "регистрация пользователя (с ролью User - по умолчанию)")
  public ResponseEntity<TaskUserResponse> signUp(@RequestBody TaskUserRequest taskUserRequest) {
    return ResponseEntity.ok(taskUserService.save(taskUserRequest));
  }
}