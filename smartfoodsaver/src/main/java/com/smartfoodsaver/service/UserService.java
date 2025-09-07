package com.smartfoodsaver.service;

import com.smartfoodsaver.dto.RegistrationRequest;
import com.smartfoodsaver.model.User;
import com.smartfoodsaver.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository users;
  private final PasswordEncoder encoder;

  public UserService(UserRepository users, PasswordEncoder encoder) {
    this.users = users;
    this.encoder = encoder;
  }

  @Transactional
  public void register(RegistrationRequest req) {
    if (!req.getPassword().equals(req.getConfirmPassword())) {
      throw new IllegalArgumentException("Passwords do not match");
    }
    if (users.existsByEmail(req.getEmail())) {
      throw new IllegalArgumentException("Email already in use");
    }
    User u = new User();
    u.setName(req.getName());
    u.setEmail(req.getEmail().toLowerCase());
    u.setPasswordHash(encoder.encode(req.getPassword()));
    users.save(u);
  }

  // Used by Spring Security to authenticate from DB
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    System.out.println("### Loading user: " + email);
    User u = users.findByEmail(email.toLowerCase())
      .orElseThrow(() -> new UsernameNotFoundException("No user " + email));
    return new org.springframework.security.core.userdetails.User(
      u.getEmail(),
      u.getPasswordHash(),
      List.of(new SimpleGrantedAuthority(u.getRole()))
    );
  }
}
