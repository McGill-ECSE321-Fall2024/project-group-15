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

  private static Map<Integer, PaymentInfo> paymentinfosByPaymentinfoID = new HashMap<Integer, PaymentInfo>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PaymentInfo Attributes
  @Id
  @GeneratedValue
  private int paymentinfoID;
  private String cardNumber;
  private Date expiryDate;
  private int cvv;
  private String billingAddress;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PaymentInfo(String aCardNumber, Date aExpiryDate, int aCvv, String aBillingAddress)
  {
    cardNumber = aCardNumber;
    expiryDate = aExpiryDate;
    cvv = aCvv;
    billingAddress = aBillingAddress;
    if (!setPaymentinfoID(paymentinfoID))
    {
      throw new RuntimeException("Cannot create due to duplicate paymentinfoID. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPaymentinfoID(int aPaymentinfoID)
  {
    boolean wasSet = false;
    int anOldPaymentinfoID = getPaymentinfoID();
    if (anOldPaymentinfoID == aPaymentinfoID) {
      return true;
    }
    if (hasWithPaymentinfoID(aPaymentinfoID)) {
      return wasSet;
    }
    paymentinfoID = aPaymentinfoID;
    wasSet = true;
    paymentinfosByPaymentinfoID.remove(anOldPaymentinfoID);
    paymentinfosByPaymentinfoID.put(aPaymentinfoID, this);
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

  public int getPaymentinfoID()
  {
    return paymentinfoID;
  }
  /* Code from template attribute_GetUnique */
  public static PaymentInfo getWithPaymentinfoID(int aPaymentinfoID)
  {
    return paymentinfosByPaymentinfoID.get(aPaymentinfoID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithPaymentinfoID(int aPaymentinfoID)
  {
    return getWithPaymentinfoID(aPaymentinfoID) != null;
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
    paymentinfosByPaymentinfoID.remove(getPaymentinfoID());
  }


  public String toString()
  {
    return super.toString() + "["+
            "paymentinfoID" + ":" + getPaymentinfoID()+ "," +
            "cardNumber" + ":" + getCardNumber()+ "," +
            "cvv" + ":" + getCvv()+ "," +
            "billingAddress" + ":" + getBillingAddress()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "expiryDate" + "=" + (getExpiryDate() != null ? !getExpiryDate().equals(this)  ? getExpiryDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}