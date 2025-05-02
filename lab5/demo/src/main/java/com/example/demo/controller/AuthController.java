package com.example.demo.controller;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthenticationManager authManager;
  private final JwtUtil jwtUtil;

  public AuthController(AuthenticationManager authManager,
                        JwtUtil jwtUtil) {
    this.authManager = authManager;
    this.jwtUtil = jwtUtil;
  }

  @PostMapping("/login")
  public JwtResponse login(@RequestBody LoginRequest req,
                           HttpServletResponse resp) {
    Authentication auth = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            req.getUsername(), req.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(auth);

    String token = jwtUtil.generateToken(req.getUsername());
    // — return in HTTP-only cookie
    Cookie cookie = new Cookie("JWT_TOKEN", token);
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    cookie.setMaxAge((int)(jwtUtil.getJwtExpirationMs() / 1000));
    resp.addCookie(cookie);

    // — also return in JSON body
    return new JwtResponse(token);
  }
}
