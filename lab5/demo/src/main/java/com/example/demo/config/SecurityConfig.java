package com.example.demo.config;

import com.example.demo.filter.JwtAuthenticationFilter;
import com.example.demo.jwt.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  public UserDetailsService userDetailsService(PasswordEncoder encoder) {
    return new InMemoryUserDetailsManager(
      User.withUsername("user")
          .password(encoder.encode("password"))
          .roles("USER")
          .build()
    );
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil,
                                                         UserDetailsService uds) {
    return new JwtAuthenticationFilter(jwtUtil, uds);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http,
                                         JwtAuthenticationFilter jwtFilter) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/auth/**").permitAll()
        .anyRequest().authenticated()
      )
      .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
      .httpBasic(withDefaults());

    return http.build();
  }
}
