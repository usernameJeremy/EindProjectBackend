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
public class Rekening {

    @Id
    @GeneratedValue
    private Long id;

    private String products;
    private String deliveryGuy;
    private String reciever;
    private String deliveryAddress;
    private int totalAmount;


    @OneToOne
    private BoodschapLijst grocerylist;
}
