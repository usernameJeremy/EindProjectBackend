package nl.example.boodschappenbezorgapp.Model;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(name = "rekeningen")
public class Rekening {

@Id
@GeneratedValue
    private Long id;

    private String name;
    private String address;
    private String products;
    private String deliveryName;
    private int deliveryTime;
    private int totalAmount;

}
