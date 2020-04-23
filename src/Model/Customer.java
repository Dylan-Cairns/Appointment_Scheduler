package Model;

public class Customer {
    private int customerID;
    private String customerName;
    private Address address;
    private City city;
    private Country country;

    public Customer(int customerID, String customerName, Address address, City city, Country country) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.city = city;
        this.country = country;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Address getAddress() {
        return address;
    }

    public City getCity() {
        return city;
    }

    public Country getCountry() {
        return country;
    }
}
