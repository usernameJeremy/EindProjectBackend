package nl.example.boodschappenbezorgapp.DTO;

import lombok.*;
import nl.example.boodschappenbezorgapp.Model.User;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class AccountDto {


    private String username;
    private String name;
    private String lastName;
    private String address;

    public User user;


}
