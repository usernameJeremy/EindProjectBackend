package nl.example.boodschappenbezorgapp.DTO;


import lombok.*;
import nl.example.boodschappenbezorgapp.Enum.DeliveryRequestEnum;

@Getter
@Setter

public class GroceryListDto {


    private String name;
    private String address;
    private String bezorginstructies;
    private int dateTime;
    private String products;
    private DeliveryRequestEnum status;
}