package nl.example.boodschappenbezorgapp.Model;

import lombok.*;
import nl.example.boodschappenbezorgapp.Enum.DeliveryRequestEnum;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class GroceryList {


    private String name;
    @Id
    private String address;
    private String bezorginstructies;
    private int dateTime;
    private String products;

    @Enumerated(EnumType.STRING)
    private DeliveryRequestEnum status;



    @OneToOne(mappedBy = "grocerylist")
    private Delivery deliveryRequest;

    @OneToOne(mappedBy = "grocerylist")
    private Invoice rekening;


    public void setDelivery(Delivery delivery) {
    }
}
