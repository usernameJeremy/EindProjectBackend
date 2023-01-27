package nl.example.boodschappenbezorgapp.DTO;

import lombok.*;
import nl.example.boodschappenbezorgapp.Model.Account;
import nl.example.boodschappenbezorgapp.Model.Authority;

import java.util.Objects;
import java.util.Set;



public class UserDto {



    public String username;
    public String password;
    public Boolean enabled;

    public String apikey;
    public String email;
    public Set<Authority> authorities;

    public Account account;

    public UserDto(String username, String password, Boolean enabled, String apikey, String email) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.apikey = apikey;
        this.email = email;
    }

    public UserDto() {
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(username, userDto.username) && Objects.equals(password, userDto.password) && Objects.equals(enabled, userDto.enabled) && Objects.equals(apikey, userDto.apikey) && Objects.equals(email, userDto.email) && Objects.equals(authorities, userDto.authorities) && Objects.equals(account, userDto.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, enabled, apikey, email, authorities, account);
    }
}