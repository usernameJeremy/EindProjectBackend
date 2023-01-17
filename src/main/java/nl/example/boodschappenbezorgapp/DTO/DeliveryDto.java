package nl.example.boodschappenbezorgapp.DTO;

import lombok.*;
import nl.example.boodschappenbezorgapp.Enum.DeliveryRequestEnum;
import nl.example.boodschappenbezorgapp.Model.Account;
import nl.example.boodschappenbezorgapp.Model.GroceryList;

@Getter
@Setter

public class DeliveryDto {

    private String address;
    private DeliveryRequestEnum status;
    private Account account;
    private GroceryList grocerylist;

}
