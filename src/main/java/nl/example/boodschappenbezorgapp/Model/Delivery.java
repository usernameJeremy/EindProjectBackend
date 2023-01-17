package nl.example.boodschappenbezorgapp.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import nl.example.boodschappenbezorgapp.Enum.DeliveryRequestEnum;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Delivery {


    @Id
    private String address;

    @Enumerated(EnumType.STRING)
    private DeliveryRequestEnum status;

    @ManyToOne
    private Account account;
    @OneToOne
    @JsonIgnore
    private GroceryList grocerylist;

    public Delivery(String address, DeliveryRequestEnum status, GroceryList grocerylist) {
        this.address = address;
        this.status = status;
        this.grocerylist = grocerylist;
    }
}
