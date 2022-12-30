package nl.example.boodschappenbezorgapp.DTO;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class AccountDto {

    private Long id;
    private String username;
    private String password;
    private String name;
    private String lastName;
    private String adres;

}
