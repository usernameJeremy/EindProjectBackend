package nl.example.boodschappenbezorgapp.Model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class BoodschapLijst {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String address;
    private String products;
    private String bezorginstructies;
    private int dateTime;




    @OneToOne(mappedBy = "grocerylist")
    private BezorgVerzoek deliveryRequest;

    @OneToOne(mappedBy = "grocerylist")
    private Rekening rekening;

}
