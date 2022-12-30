package nl.example.boodschappenbezorgapp.DTO;


import nl.example.boodschappenbezorgapp.Model.BezorgVerzoek;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoodschapLijstDto {

    private Long id;
    private String name;
    private String address;
    private String products;
    private int totalPrice;
    private String bezorginstructies;
    private int dateTime;

    private BezorgVerzoek deliveryRequest;
}
