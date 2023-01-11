package nl.example.boodschappenbezorgapp.Model;

import lombok.*;
import nl.example.boodschappenbezorgapp.Enum.DeleveryReqeustEnum;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity

public class DeliveryRequest {



    @Id
    @GeneratedValue
    private Long id;

    private String Supermarkt;
    private String BezorgInstructies;
    private int dateOfDelivery;
    private String BezorgVerzoek;
    private DeleveryReqeustEnum requestStatus;



    @ManyToOne
    private Account account;

    @OneToOne
    private GroceryList grocerylist;
}
