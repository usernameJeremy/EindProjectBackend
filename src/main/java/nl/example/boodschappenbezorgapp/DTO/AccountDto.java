package nl.example.boodschappenbezorgapp.DTO;

import lombok.*;
import nl.example.boodschappenbezorgapp.Model.User;

import java.util.Objects;


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

    public AccountDto( String username, String name, String lastName, String address) {
        this.username = username;
        this.name = name;
        this.lastName = lastName;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDto that = (AccountDto) o;
        return Objects.equals(username, that.username) && Objects.equals(name, that.name) && Objects.equals(lastName, that.lastName) && Objects.equals(address, that.address) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, name, lastName, address, user);
    }
}
