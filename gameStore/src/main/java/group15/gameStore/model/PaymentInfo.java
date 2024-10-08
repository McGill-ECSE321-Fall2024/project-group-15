/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package group15.gameStore.model;
import java.util.*;
import java.sql.Date;

// line 50 "model.ump"
// line 148 "model.ump"
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
  private int paymentinfoID;
  private String cardNumber;
  private Date expiryDate;
  private int cvv;
  private String billingAddress;

  //PaymentInfo Associations
  private Customer customer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PaymentInfo(int aPaymentinfoID, String aCardNumber, Date aExpiryDate, int aCvv, String aBillingAddress, Customer aCustomer)
  {
    cardNumber = aCardNumber;
    expiryDate = aExpiryDate;
    cvv = aCvv;
    billingAddress = aBillingAddress;
    if (!setPaymentinfoID(aPaymentinfoID))
    {
      throw new RuntimeException("Cannot create due to duplicate paymentinfoID. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create paymentInfo due to customer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCustomer(Customer aCustomer)
  {
    boolean wasSet = false;
    if (aCustomer == null)
    {
      return wasSet;
    }

    Customer existingCustomer = customer;
    customer = aCustomer;
    if (existingCustomer != null && !existingCustomer.equals(aCustomer))
    {
      existingCustomer.removePaymentInfo(this);
    }
    customer.addPaymentInfo(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    paymentinfosByPaymentinfoID.remove(getPaymentinfoID());
    Customer placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removePaymentInfo(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "paymentinfoID" + ":" + getPaymentinfoID()+ "," +
            "cardNumber" + ":" + getCardNumber()+ "," +
            "cvv" + ":" + getCvv()+ "," +
            "billingAddress" + ":" + getBillingAddress()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "expiryDate" + "=" + (getExpiryDate() != null ? !getExpiryDate().equals(this)  ? getExpiryDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null");
  }
}