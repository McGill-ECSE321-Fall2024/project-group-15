/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package group15.gameStore.model;

import jakarta.persistence.*;
import java.util.*;

// line 10 "model.ump"
// line 132 "model.ump"
@Entity
public class Customer extends Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes
  private String address;
  private String phoneNumber;
 //Customer Associations
//  @OneToMany
//  @JoinTable(
//    name = "customer_paymentInfo", // Custom join table name
//    joinColumns = @JoinColumn(name = "customerID"), // Join column in the Customer entity
//    inverseJoinColumns = @JoinColumn(name = "paymentinfoID") // Join column in the Order entity
//  )
//  private List<PaymentInfo> paymentInfos;

//  @OneToMany
//  @JoinTable(
//    name = "customer_review", // Custom join table name
//    joinColumns = @JoinColumn(name = "customerID"), // Join column in the Customer entity
//    inverseJoinColumns = @JoinColumn(name = "reviewID") // Join column in the Order entity
//  )
//  private List<Review> reviews;

//  @ManyToMany
//  @JoinTable(
//    name = "customer_wishlist",
//    joinColumns = @JoinColumn(name = "customerID"),
//    inverseJoinColumns = @JoinColumn(name = "wishlistID")
//  )
//  private List<Wishlist> wishlists;

//  @OneToMany
//  @JoinTable(
//    name = "customer_orders", // Custom join table name
//    joinColumns = @JoinColumn(name = "customerID"), // Join column in the Customer entity
//    inverseJoinColumns = @JoinColumn(name = "orderID") // Join column in the Order entity
//  )
//  private List<Order> orders;

 // Hibernate default constructor
 @SuppressWarnings("unused")
 private Customer() {
 }

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(String aUsername, String aPassword, String aEmail, String aAddress, String aPhoneNumber)
  {
    super(aUsername, aPassword, aEmail);
    address = aAddress;
    phoneNumber = aPhoneNumber;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAddress(String aAddress)
  {
    boolean wasSet = false;
    address = aAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhoneNumber(String aPhoneNumber)
  {
    boolean wasSet = false;
    phoneNumber = aPhoneNumber;
    wasSet = true;
    return wasSet;
  }

  public String getAddress()
  {
    return address;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }


  public String toString()
  {
    return super.toString() + "["+
            "address" + ":" + getAddress()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ ",";
  }
}