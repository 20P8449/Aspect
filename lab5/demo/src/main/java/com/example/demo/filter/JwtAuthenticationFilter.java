package com.example.demo.filter;

import com.example.demo.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final UserDetailsService userDetailsService;

  public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService uds) {
    this.jwtUtil = jwtUtil;
    this.userDetailsService = uds;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain chain)
      throws ServletException, IOException {

    String token = null;
    String header = request.getHeader("Authorization");

    if (header != null && header.startsWith("Bearer ")) {
      token = header.substring(7);
    } else if (request.getCookies() != null) {
      for (Cookie c : request.getCookies()) {
        if ("JWT_TOKEN".equals(c.getName())) {
          token = c.getValue();
          break;
        }
      }
    }

    if (token != null && jwtUtil.validateToken(token)) {
      String username = jwtUtil.getUsernameFromToken(token);
      UserDetails user = userDetailsService.loadUserByUsername(username);
      var auth = new UsernamePasswordAuthenticationToken(
          user, null, user.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    chain.doFilter(request, response);
  }
}
