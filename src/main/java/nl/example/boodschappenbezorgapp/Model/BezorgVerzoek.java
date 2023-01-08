package nl.example.boodschappenbezorgapp.Model;

import lombok.*;
import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity

public class BezorgVerzoek {



    @Id
    @GeneratedValue
    private Long id;

    private String Supermarkt;
    private String BezorgInstructies;
    private int dateOfDelivery;
    private String BezorgVerzoek;
    private boolean bezorgd;



    @ManyToOne
    private Account account;

    @OneToOne
    private BoodschapLijst grocerylist;
}
