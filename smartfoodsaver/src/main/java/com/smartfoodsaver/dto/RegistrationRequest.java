package com.smartfoodsaver.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistrationRequest {

  @NotBlank @Size(max = 50)
  private String name;

  @NotBlank @Email @Size(max = 100)
  private String email;

  @NotBlank @Size(min = 6, max = 20)
  private String password;

  @NotBlank @Size(min = 6, max = 20)
  private String confirmPassword;

  // getters/setters
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }
  public String getConfirmPassword() { return confirmPassword; }
  public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
}
