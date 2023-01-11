package nl.example.boodschappenbezorgapp.DTO;


import lombok.*;

@Getter
@Setter

public class GroceryListDto {

    private Long id;
    private String name;
    private String address;
    private String bezorginstructies;
    private int dateTime;
    private String products;
}
