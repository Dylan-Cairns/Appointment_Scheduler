package model;

public class Address {
    private int addressID;
    private String addressLine1;
    private String addressLine2;
    private City city;
    private String postalCode;
    private String phone;

    public Address(int addressID, String addressLine1, String addressLine2, City city, String postalCode, String phone) {
        this.addressID = addressID;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public Address(String addressLine1, String addressLine2, City city, String postalCode, String phone) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public int getAddressID() {
        return addressID;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public City getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
    }
}
