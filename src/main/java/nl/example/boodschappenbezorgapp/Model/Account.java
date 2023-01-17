package nl.example.boodschappenbezorgapp.Model;

import lombok.*;
import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(name = "accounts")
public class Account{


// delivery heeft een bezorger.
// bezorger moet gekoppeld worden aan account
    @Id
    private String username;

    private String name;
    private String lastName;
    private String address;

    @OneToMany(mappedBy = "account")
    private List<Delivery> bezorgVerzoek;

    @OneToOne(
            targetEntity = User.class,
            mappedBy = "account",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private User user;

    public Account( String username, String name, String lastName, String address) {
        this.username = username;
        this.name = name;
        this.lastName = lastName;
        this.address = address;
    }

    public void add(Account account) {

    }
    public void remove(Account account) {
    }

    public void setDelivery(Delivery delivery) {

    }
}
