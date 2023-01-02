package nl.example.boodschappenbezorgapp.Model;


import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rekening {


    private Long id;
    private String products;
    private String deliveryGuy;
    private String reciever;
    private String deliveryAddress;
    private int totalAmount;


    //one to one met boodschappen lijst
}
