package nl.example.boodschappenbezorgapp.Model;

import lombok.*;
import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class GroceryList {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String address;
    private String bezorginstructies;
    private int dateTime;
    private String products;



    @OneToOne(mappedBy = "grocerylist")
    private DeliveryRequest deliveryRequest;

    @OneToOne(mappedBy = "grocerylist")
    private Rekening rekening;

}
