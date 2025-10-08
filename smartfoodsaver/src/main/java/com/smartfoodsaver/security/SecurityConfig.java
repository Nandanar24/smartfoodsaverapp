package com.smartfoodsaver.security;

import com.smartfoodsaver.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http, DaoAuthenticationProvider authProvider) throws Exception {
    http
      .requestCache(c -> c.requestCache(new NullRequestCache()))
      .authenticationProvider(authProvider)
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/", "/signin", "/register", "/style.css", "/images/**","/h2-console/**", "/contact").permitAll()
        .requestMatchers(HttpMethod.POST, "/contact").permitAll()
        .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/chat").permitAll()
        .anyRequest().authenticated()
      )
      .csrf(csrf -> csrf.csrfTokenRepository(new HttpSessionCsrfTokenRepository())
        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
        .disable()
      )

      .headers(h -> h.frameOptions(f -> f.sameOrigin()))

      .formLogin(form -> form
        .loginPage("/signin")
        .loginProcessingUrl("/login")
        .failureUrl("/signin?error")
        .defaultSuccessUrl("/meal-planner", true)
        .permitAll()
      )
      .logout(logout -> logout.permitAll());
    System.out.println("### SecurityConfig active: defaultSuccessUrl -> /");
    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService(UserService userService) {
    return userService;
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider(UserService userService) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

}
