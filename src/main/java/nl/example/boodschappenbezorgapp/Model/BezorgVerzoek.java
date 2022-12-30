package nl.example.boodschappenbezorgapp.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity

public class BezorgVerzoek {

    //Bezorg verzoek: Ontvanger,

    @Id
    @GeneratedValue
    private Long id;

    private String Supermarkt;
    private String BezorgInstructies;
    private int dateOfDelivery;
    private String BezorgVerzoek;
    private boolean bezorgd;

}
