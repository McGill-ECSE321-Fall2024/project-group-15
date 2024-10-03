/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;
import java.sql.Date;

// line 9 "model.ump"
// line 145 "model.ump"
public class Customer extends Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes
  private int customerID;
  private String address;
  private String phoneNumber;
  private Wishlist wishList;
  private PurchaseHistory purchaseHistory;
  private PaymentInfo savedPaymentInfo;

  //Customer Associations
  private List<PaymentInfo> paymentInfos;
  private List<Reviews> reviews;
  private List<Order> orders;
  private Cart cart;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(String aUsername, String aPassword, String aEmail, int aCustomerID, String aAddress, String aPhoneNumber, Wishlist aWishList, PurchaseHistory aPurchaseHistory, PaymentInfo aSavedPaymentInfo, Cart aCart)
  {
    super(aUsername, aPassword, aEmail);
    customerID = aCustomerID;
    address = aAddress;
    phoneNumber = aPhoneNumber;
    wishList = aWishList;
    purchaseHistory = aPurchaseHistory;
    savedPaymentInfo = aSavedPaymentInfo;
    paymentInfos = new ArrayList<PaymentInfo>();
    reviews = new ArrayList<Reviews>();
    orders = new ArrayList<Order>();
    if (aCart == null || aCart.getCustomer() != null)
    {
      throw new RuntimeException("Unable to create Customer due to aCart. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    cart = aCart;
  }

  public Customer(String aUsername, String aPassword, String aEmail, int aCustomerID, String aAddress, String aPhoneNumber, Wishlist aWishList, PurchaseHistory aPurchaseHistory, PaymentInfo aSavedPaymentInfo, int aCartIDForCart, double aTotalPriceForCart, Promotion aPromotionForCart)
  {
    super(aUsername, aPassword, aEmail);
    customerID = aCustomerID;
    address = aAddress;
    phoneNumber = aPhoneNumber;
    wishList = aWishList;
    purchaseHistory = aPurchaseHistory;
    savedPaymentInfo = aSavedPaymentInfo;
    paymentInfos = new ArrayList<PaymentInfo>();
    reviews = new ArrayList<Reviews>();
    orders = new ArrayList<Order>();
    cart = new Cart(aCartIDForCart, aTotalPriceForCart, this, aPromotionForCart);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCustomerID(int aCustomerID)
  {
    boolean wasSet = false;
    customerID = aCustomerID;
    wasSet = true;
    return wasSet;
  }

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

  public boolean setWishList(Wishlist aWishList)
  {
    boolean wasSet = false;
    wishList = aWishList;
    wasSet = true;
    return wasSet;
  }

  public boolean setPurchaseHistory(PurchaseHistory aPurchaseHistory)
  {
    boolean wasSet = false;
    purchaseHistory = aPurchaseHistory;
    wasSet = true;
    return wasSet;
  }

  public boolean setSavedPaymentInfo(PaymentInfo aSavedPaymentInfo)
  {
    boolean wasSet = false;
    savedPaymentInfo = aSavedPaymentInfo;
    wasSet = true;
    return wasSet;
  }

  public int getCustomerID()
  {
    return customerID;
  }

  public String getAddress()
  {
    return address;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public Wishlist getWishList()
  {
    return wishList;
  }

  public PurchaseHistory getPurchaseHistory()
  {
    return purchaseHistory;
  }

  public PaymentInfo getSavedPaymentInfo()
  {
    return savedPaymentInfo;
  }
  /* Code from template association_GetMany */
  public PaymentInfo getPaymentInfo(int index)
  {
    PaymentInfo aPaymentInfo = paymentInfos.get(index);
    return aPaymentInfo;
  }

  public List<PaymentInfo> getPaymentInfos()
  {
    List<PaymentInfo> newPaymentInfos = Collections.unmodifiableList(paymentInfos);
    return newPaymentInfos;
  }

  public int numberOfPaymentInfos()
  {
    int number = paymentInfos.size();
    return number;
  }

  public boolean hasPaymentInfos()
  {
    boolean has = paymentInfos.size() > 0;
    return has;
  }

  public int indexOfPaymentInfo(PaymentInfo aPaymentInfo)
  {
    int index = paymentInfos.indexOf(aPaymentInfo);
    return index;
  }
  /* Code from template association_GetMany */
  public Reviews getReview(int index)
  {
    Reviews aReview = reviews.get(index);
    return aReview;
  }

  public List<Reviews> getReviews()
  {
    List<Reviews> newReviews = Collections.unmodifiableList(reviews);
    return newReviews;
  }

  public int numberOfReviews()
  {
    int number = reviews.size();
    return number;
  }

  public boolean hasReviews()
  {
    boolean has = reviews.size() > 0;
    return has;
  }

  public int indexOfReview(Reviews aReview)
  {
    int index = reviews.indexOf(aReview);
    return index;
  }
  /* Code from template association_GetMany */
  public Order getOrder(int index)
  {
    Order aOrder = orders.get(index);
    return aOrder;
  }

  public List<Order> getOrders()
  {
    List<Order> newOrders = Collections.unmodifiableList(orders);
    return newOrders;
  }

  public int numberOfOrders()
  {
    int number = orders.size();
    return number;
  }

  public boolean hasOrders()
  {
    boolean has = orders.size() > 0;
    return has;
  }

  public int indexOfOrder(Order aOrder)
  {
    int index = orders.indexOf(aOrder);
    return index;
  }
  /* Code from template association_GetOne */
  public Cart getCart()
  {
    return cart;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPaymentInfos()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public PaymentInfo addPaymentInfo(int aPaymentinfoID, String aCardNumber, Date aExpiryDate, int aCvv, String aBillingAddress, Order aOrder)
  {
    return new PaymentInfo(aPaymentinfoID, aCardNumber, aExpiryDate, aCvv, aBillingAddress, this, aOrder);
  }

  public boolean addPaymentInfo(PaymentInfo aPaymentInfo)
  {
    boolean wasAdded = false;
    if (paymentInfos.contains(aPaymentInfo)) { return false; }
    Customer existingCustomer = aPaymentInfo.getCustomer();
    boolean isNewCustomer = existingCustomer != null && !this.equals(existingCustomer);
    if (isNewCustomer)
    {
      aPaymentInfo.setCustomer(this);
    }
    else
    {
      paymentInfos.add(aPaymentInfo);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePaymentInfo(PaymentInfo aPaymentInfo)
  {
    boolean wasRemoved = false;
    //Unable to remove aPaymentInfo, as it must always have a customer
    if (!this.equals(aPaymentInfo.getCustomer()))
    {
      paymentInfos.remove(aPaymentInfo);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPaymentInfoAt(PaymentInfo aPaymentInfo, int index)
  {  
    boolean wasAdded = false;
    if(addPaymentInfo(aPaymentInfo))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPaymentInfos()) { index = numberOfPaymentInfos() - 1; }
      paymentInfos.remove(aPaymentInfo);
      paymentInfos.add(index, aPaymentInfo);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePaymentInfoAt(PaymentInfo aPaymentInfo, int index)
  {
    boolean wasAdded = false;
    if(paymentInfos.contains(aPaymentInfo))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPaymentInfos()) { index = numberOfPaymentInfos() - 1; }
      paymentInfos.remove(aPaymentInfo);
      paymentInfos.add(index, aPaymentInfo);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPaymentInfoAt(aPaymentInfo, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReviews()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Reviews addReview(int aReviewID, rating aRating, String aDescription, Customer aCustomerDetails, Game aGame)
  {
    return new Reviews(aReviewID, aRating, aDescription, aCustomerDetails, aGame, this);
  }

  public boolean addReview(Reviews aReview)
  {
    boolean wasAdded = false;
    if (reviews.contains(aReview)) { return false; }
    Customer existingCustomer = aReview.getCustomer();
    boolean isNewCustomer = existingCustomer != null && !this.equals(existingCustomer);
    if (isNewCustomer)
    {
      aReview.setCustomer(this);
    }
    else
    {
      reviews.add(aReview);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeReview(Reviews aReview)
  {
    boolean wasRemoved = false;
    //Unable to remove aReview, as it must always have a customer
    if (!this.equals(aReview.getCustomer()))
    {
      reviews.remove(aReview);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addReviewAt(Reviews aReview, int index)
  {  
    boolean wasAdded = false;
    if(addReview(aReview))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReviews()) { index = numberOfReviews() - 1; }
      reviews.remove(aReview);
      reviews.add(index, aReview);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveReviewAt(Reviews aReview, int index)
  {
    boolean wasAdded = false;
    if(reviews.contains(aReview))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReviews()) { index = numberOfReviews() - 1; }
      reviews.remove(aReview);
      reviews.add(index, aReview);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addReviewAt(aReview, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrders()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Order addOrder(int aOrderID, String aOrderNumber, Customer aCustomerDetails, Status aOrderStatus, PaymentInfo aPaymentInfo, PurchaseHistory aPurchaseHistory)
  {
    return new Order(aOrderID, aOrderNumber, aCustomerDetails, aOrderStatus, aPaymentInfo, aPurchaseHistory, this);
  }

  public boolean addOrder(Order aOrder)
  {
    boolean wasAdded = false;
    if (orders.contains(aOrder)) { return false; }
    Customer existingCustomer = aOrder.getCustomer();
    boolean isNewCustomer = existingCustomer != null && !this.equals(existingCustomer);
    if (isNewCustomer)
    {
      aOrder.setCustomer(this);
    }
    else
    {
      orders.add(aOrder);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrder(Order aOrder)
  {
    boolean wasRemoved = false;
    //Unable to remove aOrder, as it must always have a customer
    if (!this.equals(aOrder.getCustomer()))
    {
      orders.remove(aOrder);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOrderAt(Order aOrder, int index)
  {  
    boolean wasAdded = false;
    if(addOrder(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
      orders.remove(aOrder);
      orders.add(index, aOrder);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrderAt(Order aOrder, int index)
  {
    boolean wasAdded = false;
    if(orders.contains(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
      orders.remove(aOrder);
      orders.add(index, aOrder);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOrderAt(aOrder, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=paymentInfos.size(); i > 0; i--)
    {
      PaymentInfo aPaymentInfo = paymentInfos.get(i - 1);
      aPaymentInfo.delete();
    }
    for(int i=reviews.size(); i > 0; i--)
    {
      Reviews aReview = reviews.get(i - 1);
      aReview.delete();
    }
    for(int i=orders.size(); i > 0; i--)
    {
      Order aOrder = orders.get(i - 1);
      aOrder.delete();
    }
    Cart existingCart = cart;
    cart = null;
    if (existingCart != null)
    {
      existingCart.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "customerID" + ":" + getCustomerID()+ "," +
            "address" + ":" + getAddress()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "wishList" + "=" + (getWishList() != null ? !getWishList().equals(this)  ? getWishList().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "purchaseHistory" + "=" + (getPurchaseHistory() != null ? !getPurchaseHistory().equals(this)  ? getPurchaseHistory().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "savedPaymentInfo" + "=" + (getSavedPaymentInfo() != null ? !getSavedPaymentInfo().equals(this)  ? getSavedPaymentInfo().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "cart = "+(getCart()!=null?Integer.toHexString(System.identityHashCode(getCart())):"null");
  }
}