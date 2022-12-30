package nl.example.boodschappenbezorgapp.DTO;

import lombok.*;
import nl.example.boodschappenbezorgapp.Model.BoodschapLijst;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BezorgVerzoekDto {


    private Long id;
    private String Supermarkt;
    private String BezorgInstructies;
    private int dateOfDelivery;
    private String BezorgVerzoek;
    private String deliveryGuyName;
    private boolean bezorgd;

    private BoodschapLijst groceryList;
}
