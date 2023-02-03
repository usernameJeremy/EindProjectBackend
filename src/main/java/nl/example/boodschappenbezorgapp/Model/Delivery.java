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

    @ManyToOne
    private Account account;
    @OneToOne
    @JsonIgnore
    private GroceryList grocerylist;

    public Delivery(String address, GroceryList grocerylist) {
        this.address = address;
        this.grocerylist = grocerylist;
    }
}
