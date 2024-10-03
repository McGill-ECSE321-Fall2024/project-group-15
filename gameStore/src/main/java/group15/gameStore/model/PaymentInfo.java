/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.sql.Date;

// line 48 "model.ump"
// line 164 "model.ump"
public class PaymentInfo
{

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
  private Order order;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PaymentInfo(int aPaymentinfoID, String aCardNumber, Date aExpiryDate, int aCvv, String aBillingAddress, Customer aCustomer, Order aOrder)
  {
    paymentinfoID = aPaymentinfoID;
    cardNumber = aCardNumber;
    expiryDate = aExpiryDate;
    cvv = aCvv;
    billingAddress = aBillingAddress;
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create paymentInfo due to customer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (aOrder == null || aOrder.getPaymentInfo() != null)
    {
      throw new RuntimeException("Unable to create PaymentInfo due to aOrder. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    order = aOrder;
  }

  public PaymentInfo(int aPaymentinfoID, String aCardNumber, Date aExpiryDate, int aCvv, String aBillingAddress, Customer aCustomer, int aOrderIDForOrder, String aOrderNumberForOrder, Customer aCustomerDetailsForOrder, Status aOrderStatusForOrder, PurchaseHistory aPurchaseHistoryForOrder, Customer aCustomerForOrder)
  {
    paymentinfoID = aPaymentinfoID;
    cardNumber = aCardNumber;
    expiryDate = aExpiryDate;
    cvv = aCvv;
    billingAddress = aBillingAddress;
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create paymentInfo due to customer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    order = new Order(aOrderIDForOrder, aOrderNumberForOrder, aCustomerDetailsForOrder, aOrderStatusForOrder, this, aPurchaseHistoryForOrder, aCustomerForOrder);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPaymentinfoID(int aPaymentinfoID)
  {
    boolean wasSet = false;
    paymentinfoID = aPaymentinfoID;
    wasSet = true;
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
  /* Code from template association_GetOne */
  public Order getOrder()
  {
    return order;
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
    Customer placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removePaymentInfo(this);
    }
    Order existingOrder = order;
    order = null;
    if (existingOrder != null)
    {
      existingOrder.delete();
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
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "order = "+(getOrder()!=null?Integer.toHexString(System.identityHashCode(getOrder())):"null");
  }
}