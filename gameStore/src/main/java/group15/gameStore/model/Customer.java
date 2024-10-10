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
  private boolean isPaymentInfoSaved;

 //Customer Associations
 @OneToMany
 @JoinTable(
   name = "customer_paymentInfo", // Custom join table name
   joinColumns = @JoinColumn(name = "customerID"), // Join column in the Customer entity
   inverseJoinColumns = @JoinColumn(name = "paymentinfoID") // Join column in the Order entity
 )
 private List<PaymentInfo> paymentInfos;

 @OneToMany
 @JoinTable(
   name = "customer_review", // Custom join table name
   joinColumns = @JoinColumn(name = "customerID"), // Join column in the Customer entity
   inverseJoinColumns = @JoinColumn(name = "reviewID") // Join column in the Order entity
 )
 private List<Review> reviews;

 @ManyToMany
 @JoinTable(
   name = "customer_wishlist",
   joinColumns = @JoinColumn(name = "customerID"),
   inverseJoinColumns = @JoinColumn(name = "wishlistID")
 )
 private List<Wishlist> wishlists;

 @OneToMany
 @JoinTable(
   name = "customer_orders", // Custom join table name
   joinColumns = @JoinColumn(name = "customerID"), // Join column in the Customer entity
   inverseJoinColumns = @JoinColumn(name = "orderID") // Join column in the Order entity
 )
 private List<Order> orders;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(int aUserID, String aUsername, String aPassword, String aEmail, String aAddress, String aPhoneNumber, boolean aIsPaymentInfoSaved)
  {
    super(aUserID, aUsername, aPassword, aEmail);
    address = aAddress;
    phoneNumber = aPhoneNumber;
    isPaymentInfoSaved = aIsPaymentInfoSaved;
    paymentInfos = new ArrayList<PaymentInfo>();
    reviews = new ArrayList<Review>();
    wishlists = new ArrayList<Wishlist>();
    orders = new ArrayList<Order>();
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

  public boolean setIsPaymentInfoSaved(boolean aIsPaymentInfoSaved)
  {
    boolean wasSet = false;
    isPaymentInfoSaved = aIsPaymentInfoSaved;
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

  public boolean getIsPaymentInfoSaved()
  {
    return isPaymentInfoSaved;
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
  /* Code from template association_GetMany */
  public Wishlist getWishlist(int index)
  {
    Wishlist aWishlist = wishlists.get(index);
    return aWishlist;
  }

  public List<Wishlist> getWishlists()
  {
    List<Wishlist> newWishlists = Collections.unmodifiableList(wishlists);
    return newWishlists;
  }

  public int numberOfWishlists()
  {
    int number = wishlists.size();
    return number;
  }

  public boolean hasWishlists()
  {
    boolean has = wishlists.size() > 0;
    return has;
  }

  public int indexOfWishlist(Wishlist aWishlist)
  {
    int index = wishlists.indexOf(aWishlist);
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPaymentInfos()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addPaymentInfo(PaymentInfo aPaymentInfo)
  {
    boolean wasAdded = false;
    if (paymentInfos.contains(aPaymentInfo)) { return false; }
    paymentInfos.add(aPaymentInfo);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePaymentInfo(PaymentInfo aPaymentInfo)
  {
    boolean wasRemoved = false;
    if (paymentInfos.contains(aPaymentInfo))
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
  /* Code from template association_AddUnidirectionalMany */
  public boolean addReview(Review aReview)
  {
    boolean wasAdded = false;
    if (reviews.contains(aReview)) { return false; }
    reviews.add(aReview);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeReview(Review aReview)
  {
    boolean wasRemoved = false;
    if (reviews.contains(aReview))
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWishlists()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addWishlist(Wishlist aWishlist)
  {
    boolean wasAdded = false;
    if (wishlists.contains(aWishlist)) { return false; }
    wishlists.add(aWishlist);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWishlist(Wishlist aWishlist)
  {
    boolean wasRemoved = false;
    if (wishlists.contains(aWishlist))
    {
      wishlists.remove(aWishlist);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWishlistAt(Wishlist aWishlist, int index)
  {  
    boolean wasAdded = false;
    if(addWishlist(aWishlist))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWishlists()) { index = numberOfWishlists() - 1; }
      wishlists.remove(aWishlist);
      wishlists.add(index, aWishlist);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWishlistAt(Wishlist aWishlist, int index)
  {
    boolean wasAdded = false;
    if(wishlists.contains(aWishlist))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWishlists()) { index = numberOfWishlists() - 1; }
      wishlists.remove(aWishlist);
      wishlists.add(index, aWishlist);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWishlistAt(aWishlist, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrders()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addOrder(Order aOrder)
  {
    boolean wasAdded = false;
    if (orders.contains(aOrder)) { return false; }
    orders.add(aOrder);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrder(Order aOrder)
  {
    boolean wasRemoved = false;
    if (orders.contains(aOrder))
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
    paymentInfos.clear();
    reviews.clear();
    wishlists.clear();
    orders.clear();
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "address" + ":" + getAddress()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "," +
            "isPaymentInfoSaved" + ":" + getIsPaymentInfoSaved()+ "]";
  }
}