package nl.example.boodschappenbezorgapp.Enum;

public enum DeliveryRequestEnum {
    AVAILABLE("AVAILABLE"),
    COMPLETED("COMPLETED"),
    ACCEPTED("ACCEPTED");
    private String status;
    DeliveryRequestEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String newStatus) {
        this.status = newStatus;
    }

}
