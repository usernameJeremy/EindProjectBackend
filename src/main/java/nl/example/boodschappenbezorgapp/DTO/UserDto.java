package nl.example.boodschappenbezorgapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.example.boodschappenbezorgapp.Model.Account;
import nl.example.boodschappenbezorgapp.Model.Authority;

import javax.persistence.GeneratedValue;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {



    public String username;
    public String password;
    public Boolean enabled;

    public String apikey;
    public String email;
    public Set<Authority> authorities;

    public Account account;



}