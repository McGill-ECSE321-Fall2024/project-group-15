/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package group15.gameStore.model;

import jakarta.persistence.*;

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