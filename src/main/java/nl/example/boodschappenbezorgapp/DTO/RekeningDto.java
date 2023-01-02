package nl.example.boodschappenbezorgapp.DTO;


import lombok.*;


@Getter
@Setter

public class RekeningDto {

    private Long id;
    private String products;
    private String deliveryGuy;
    private String reciever;
    private String deliveryAddress;
    private int totalAmount;
}
