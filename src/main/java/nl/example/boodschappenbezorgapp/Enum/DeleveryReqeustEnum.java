package nl.example.boodschappenbezorgapp.Enum;

public enum DeleveryReqeustEnum {
    AVAILABLE("AVAILABLE"), INACTIVE("INACTIVE"), COMPLETED("COMPLETED"),ACCEPTED("ACCEPTED") ;
    private final String value;
    DeleveryReqeustEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String setValue() {
        return value;
    }
}
