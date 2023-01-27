package nl.example.boodschappenbezorgapp.DTO;


import lombok.*;
import nl.example.boodschappenbezorgapp.Enum.DeliveryRequestEnum;
import nl.example.boodschappenbezorgapp.Model.Account;

import java.util.Objects;

@Getter
@Setter

public class GroceryListDto {


    private String name;
    private String address;
    private String deliveryInstructions;
    private int dateTime;
    private String products;
    private Account account;
    private DeliveryRequestEnum status;

    public GroceryListDto(String name, String address, String deliveryInstructions, int dateTime, String products) {
        this.name = name;
        this.address = address;
        this.deliveryInstructions = deliveryInstructions;
        this.dateTime = dateTime;
        this.products = products;
    }

    public GroceryListDto() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroceryListDto that = (GroceryListDto) o;
        return dateTime == that.dateTime && Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(deliveryInstructions, that.deliveryInstructions) && Objects.equals(products, that.products) && Objects.equals(account, that.account) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, deliveryInstructions, dateTime, products, account, status);
    }
}
