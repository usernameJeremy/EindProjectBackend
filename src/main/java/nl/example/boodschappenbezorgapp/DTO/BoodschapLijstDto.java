package nl.example.boodschappenbezorgapp.DTO;


import lombok.*;


@Getter
@Setter


public class BoodschapLijstDto {

    private Long id;
    private String name;
    private String address;
    private String products;
    private String bezorginstructies;
    private int dateTime;
}
