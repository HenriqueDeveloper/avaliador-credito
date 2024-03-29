package br.com.henrique.avaliadorcredito.models;

public class UserDto {
  private String username;
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String email) {
    this.username = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
