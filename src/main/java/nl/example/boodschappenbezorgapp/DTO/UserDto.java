package nl.example.boodschappenbezorgapp.DTO;

import lombok.*;

import nl.example.boodschappenbezorgapp.Model.Authority;
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


}