package nl.example.boodschappenbezorgapp.Model;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "grocerylist")
public class BoodschapLijst {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String address;
    private String products;
    private int totalPrice;
    private String bezorginstructies;
    private int dateTime;

    @OneToOne(mappedBy = "grocerylist")
    private BezorgVerzoek deliveryRequests;
}
