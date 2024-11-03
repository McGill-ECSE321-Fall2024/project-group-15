/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package group15.gameStore.model;

import jakarta.persistence.*;
import java.util.*;
import java.sql.Date;

// line 54 "model.ump"
// line 157 "model.ump"
@Entity
public class PaymentInfo
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, PaymentInfo> paymentInfosByPaymentInfoID = new HashMap<Integer, PaymentInfo>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PaymentInfo Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int paymentInfoID;
  private String cardNumber;
  private Date expiryDate;
  private int cvv;
  private String billingAddress;
  @ManyToOne
  private Customer customer;

  // Hibernate default constructor
  @SuppressWarnings("unused")
  private PaymentInfo() {
  }

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PaymentInfo(String aCardNumber, Date aExpiryDate, int aCvv, String aBillingAddress, Customer aCustomer)
  {
    cardNumber = aCardNumber;
    expiryDate = aExpiryDate;
    cvv = aCvv;
    billingAddress = aBillingAddress;
    customer = aCustomer;
    if (!setPaymentInfoID(paymentInfoID))
    {
      throw new RuntimeException("Cannot create due to duplicate paymentInfoID. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  public Customer getCustomer() {
    return customer;
  }
  public boolean setPaymentInfoID(int aPaymentInfoID)
  {
    boolean wasSet = false;
    int anOldPaymentInfoID = getPaymentInfoID();
    if (anOldPaymentInfoID == aPaymentInfoID) {
      return true;
    }
    if (hasWithPaymentInfoID(aPaymentInfoID)) {
      return wasSet;
    }
    paymentInfoID = aPaymentInfoID;
    wasSet = true;
    paymentInfosByPaymentInfoID.remove(anOldPaymentInfoID);
    paymentInfosByPaymentInfoID.put(aPaymentInfoID, this);
    return wasSet;
  }

  public boolean setCardNumber(String aCardNumber)
  {
    boolean wasSet = false;
    cardNumber = aCardNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setExpiryDate(Date aExpiryDate)
  {
    boolean wasSet = false;
    expiryDate = aExpiryDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setCvv(int aCvv)
  {
    boolean wasSet = false;
    cvv = aCvv;
    wasSet = true;
    return wasSet;
  }

  public boolean setBillingAddress(String aBillingAddress)
  {
    boolean wasSet = false;
    billingAddress = aBillingAddress;
    wasSet = true;
    return wasSet;
  }

  public int getPaymentInfoID()
  {
    return paymentInfoID;
  }
  /* Code from template attribute_GetUnique */
  public static PaymentInfo getWithPaymentInfoID(int aPaymentInfoID)
  {
    return paymentInfosByPaymentInfoID.get(aPaymentInfoID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithPaymentInfoID(int aPaymentInfoID)
  {
    return getWithPaymentInfoID(aPaymentInfoID) != null;
  }

  public String getCardNumber()
  {
    return cardNumber;
  }

  public Date getExpiryDate()
  {
    return expiryDate;
  }

  public int getCvv()
  {
    return cvv;
  }

  public String getBillingAddress()
  {
    return billingAddress;
  }

  public void delete()
  {
    paymentInfosByPaymentInfoID.remove(getPaymentInfoID());
  }


  public String toString()
  {
    return super.toString() + "["+
            "paymentInfoID" + ":" + getPaymentInfoID()+ "," +
            "cardNumber" + ":" + getCardNumber()+ "," +
            "cvv" + ":" + getCvv()+ "," +
            "billingAddress" + ":" + getBillingAddress()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "expiryDate" + "=" + (getExpiryDate() != null ? !getExpiryDate().equals(this)  ? getExpiryDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}