package org.example.taskmanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.domain.request.SignInRequest;
import org.example.taskmanagement.domain.response.JwtAuthenticationResponse;
import org.example.taskmanagement.service.TaskUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final TaskUserService taskUserService;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  public JwtAuthenticationResponse signIn(SignInRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.email(),
            request.password()
        ));
    UserDetails userDetails = taskUserService.loadUserByUsername(request.email());
    String token = jwtService.generateToken(userDetails);

    return new JwtAuthenticationResponse(token);
  }
}
