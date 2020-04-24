package Model;

public class Customer {
    private int customerID;
    private String customerName;
    private Address address;

    public Customer(int customerID, String customerName, Address address) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
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
}

