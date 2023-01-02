package nl.example.boodschappenbezorgapp.DTO;


import lombok.*;


@Getter
@Setter

public class BezorgVerzoekDto {

    private Long id;

    private String Supermarkt;
    private String BezorgInstructies;
    private int dateOfDelivery;
    private String BezorgVerzoek;
    private boolean bezorgd;
}
