/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package group15.gameStore.model;

import java.util.*;
import java.sql.Date;

// line 10 "model.ump"
// line 133 "model.ump"
public class Customer extends Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes
  private String address;
  private String phoneNumber;
  private Wishlist wishList;
  private boolean isPaymentInfoSaved;
  private PurchaseHistory customerPurchaseHistory;

  //Customer Associations
  private List<PaymentInfo> paymentInfos;
  private List<Review> reviews;
  private Wishlist wishlist;
  private List<Order> orders;
  private PurchaseHistory purchaseHistory;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(int aUserID, String aUsername, String aPassword, String aEmail, String aAddress, String aPhoneNumber, Wishlist aWishList, boolean aIsPaymentInfoSaved, PurchaseHistory aCustomerPurchaseHistory, PurchaseHistory aPurchaseHistory)
  {
    super(aUserID, aUsername, aPassword, aEmail);
    address = aAddress;
    phoneNumber = aPhoneNumber;
    wishList = aWishList;
    isPaymentInfoSaved = aIsPaymentInfoSaved;
    customerPurchaseHistory = aCustomerPurchaseHistory;
    paymentInfos = new ArrayList<PaymentInfo>();
    reviews = new ArrayList<Review>();
    orders = new ArrayList<Order>();
    if (aPurchaseHistory == null || aPurchaseHistory.getCustomer() != null)
    {
      throw new RuntimeException("Unable to create Customer due to aPurchaseHistory. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    purchaseHistory = aPurchaseHistory;
  }

  public Customer(int aUserID, String aUsername, String aPassword, String aEmail, String aAddress, String aPhoneNumber, Wishlist aWishList, boolean aIsPaymentInfoSaved, PurchaseHistory aCustomerPurchaseHistory)
  {
    super(aUserID, aUsername, aPassword, aEmail);
    address = aAddress;
    phoneNumber = aPhoneNumber;
    wishList = aWishList;
    isPaymentInfoSaved = aIsPaymentInfoSaved;
    customerPurchaseHistory = aCustomerPurchaseHistory;
    paymentInfos = new ArrayList<PaymentInfo>();
    reviews = new ArrayList<Review>();
    orders = new ArrayList<Order>();
    purchaseHistory = new PurchaseHistory(this);
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

  public boolean setWishList(Wishlist aWishList)
  {
    boolean wasSet = false;
    wishList = aWishList;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsPaymentInfoSaved(boolean aIsPaymentInfoSaved)
  {
    boolean wasSet = false;
    isPaymentInfoSaved = aIsPaymentInfoSaved;
    wasSet = true;
    return wasSet;
  }

  public boolean setCustomerPurchaseHistory(PurchaseHistory aCustomerPurchaseHistory)
  {
    boolean wasSet = false;
    customerPurchaseHistory = aCustomerPurchaseHistory;
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

  public Wishlist getWishList()
  {
    return wishList;
  }

  public boolean getIsPaymentInfoSaved()
  {
    return isPaymentInfoSaved;
  }

  public PurchaseHistory getCustomerPurchaseHistory()
  {
    return customerPurchaseHistory;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsPaymentInfoSaved()
  {
    return isPaymentInfoSaved;
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
  public Review getReview(int index)
  {
    Review aReview = reviews.get(index);
    return aReview;
  }

  public List<Review> getReviews()
  {
    List<Review> newReviews = Collections.unmodifiableList(reviews);
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

  public int indexOfReview(Review aReview)
  {
    int index = reviews.indexOf(aReview);
    return index;
  }
  /* Code from template association_GetOne */
  public Wishlist getWishlist()
  {
    return wishlist;
  }

  public boolean hasWishlist()
  {
    boolean has = wishlist != null;
    return has;
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
  public PurchaseHistory getPurchaseHistory()
  {
    return purchaseHistory;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPaymentInfos()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public PaymentInfo addPaymentInfo(int aPaymentinfoID, String aCardNumber, Date aExpiryDate, int aCvv, String aBillingAddress)
  {
    return new PaymentInfo(aPaymentinfoID, aCardNumber, aExpiryDate, aCvv, aBillingAddress, this);
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
  public Review addReview(int aReviewID, Rating aRating, String aDescription, Game aGame)
  {
    return new Review(aReviewID, aRating, aDescription, aGame, this);
  }

  public boolean addReview(Review aReview)
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

  public boolean removeReview(Review aReview)
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
  public boolean addReviewAt(Review aReview, int index)
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

  public boolean addOrMoveReviewAt(Review aReview, int index)
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
  /* Code from template association_SetOptionalOneToOne */
  public boolean setWishlist(Wishlist aNewWishlist)
  {
    boolean wasSet = false;
    if (wishlist != null && !wishlist.equals(aNewWishlist) && equals(wishlist.getCustomer()))
    {
      //Unable to setWishlist, as existing wishlist would become an orphan
      return wasSet;
    }

    wishlist = aNewWishlist;
    Customer anOldCustomer = aNewWishlist != null ? aNewWishlist.getCustomer() : null;

    if (!this.equals(anOldCustomer))
    {
      if (anOldCustomer != null)
      {
        anOldCustomer.wishlist = null;
      }
      if (wishlist != null)
      {
        wishlist.setCustomer(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrders()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Order addOrder(int aOrderID, String aOrderNumber, Status aOrderStatus, double aPrice, PurchaseHistory aPurchaseHistory)
  {
    return new Order(aOrderID, aOrderNumber, aOrderStatus, aPrice, this, aPurchaseHistory);
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
      Review aReview = reviews.get(i - 1);
      aReview.delete();
    }
    Wishlist existingWishlist = wishlist;
    wishlist = null;
    if (existingWishlist != null)
    {
      existingWishlist.delete();
    }
    for(int i=orders.size(); i > 0; i--)
    {
      Order aOrder = orders.get(i - 1);
      aOrder.delete();
    }
    PurchaseHistory existingPurchaseHistory = purchaseHistory;
    purchaseHistory = null;
    if (existingPurchaseHistory != null)
    {
      existingPurchaseHistory.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "address" + ":" + getAddress()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "," +
            "isPaymentInfoSaved" + ":" + getIsPaymentInfoSaved()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "wishList" + "=" + (getWishList() != null ? !getWishList().equals(this)  ? getWishList().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "customerPurchaseHistory" + "=" + (getCustomerPurchaseHistory() != null ? !getCustomerPurchaseHistory().equals(this)  ? getCustomerPurchaseHistory().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "wishlist = "+(getWishlist()!=null?Integer.toHexString(System.identityHashCode(getWishlist())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "purchaseHistory = "+(getPurchaseHistory()!=null?Integer.toHexString(System.identityHashCode(getPurchaseHistory())):"null");
  }
}