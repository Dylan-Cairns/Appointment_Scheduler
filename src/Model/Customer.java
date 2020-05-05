package Model;

public class Customer {
    private int customerID;
    private String customerName;
    private Address address;

    //complete constructor, used for downloading complete customer objects from the database
    public Customer(int customerID, String customerName, Address address) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
    }

    //constructor without Id, for use when creating a temp customer to insert into the DB
    public Customer(String customerName, Address address) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
    }

    //constructor without address, for use in appointments screens
    public Customer(int customerID, String customerName) {
        this.customerID = customerID;
        this.customerName = customerName;
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

    @Override
    public String toString() { return this.getCustomerName(); }
}

