/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package group15.gameStore.model;

import java.util.*;

// line 117 "model.ump"
// line 207 "model.ump"
public class PurchaseHistory
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PurchaseHistory Associations
  private Customer customer;
  private List<Order> orders;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PurchaseHistory(Customer aCustomer)
  {
    if (aCustomer == null || aCustomer.getPurchaseHistory() != null)
    {
      throw new RuntimeException("Unable to create PurchaseHistory due to aCustomer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    customer = aCustomer;
    orders = new ArrayList<Order>();
  }

  public PurchaseHistory(int aUserIDForCustomer, String aUsernameForCustomer, String aPasswordForCustomer, String aEmailForCustomer, String aAddressForCustomer, String aPhoneNumberForCustomer, Wishlist aWishListForCustomer, boolean aIsPaymentInfoSavedForCustomer, PurchaseHistory aCustomerPurchaseHistoryForCustomer)
  {
    customer = new Customer(aUserIDForCustomer, aUsernameForCustomer, aPasswordForCustomer, aEmailForCustomer, aAddressForCustomer, aPhoneNumberForCustomer, aWishListForCustomer, aIsPaymentInfoSavedForCustomer, aCustomerPurchaseHistoryForCustomer, this);
    orders = new ArrayList<Order>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
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
  public static int minimumNumberOfOrders()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Order addOrder(int aOrderID, String aOrderNumber, Status aOrderStatus, double aPrice, Customer aCustomer)
  {
    return new Order(aOrderID, aOrderNumber, aOrderStatus, aPrice, aCustomer, this);
  }

  public boolean addOrder(Order aOrder)
  {
    boolean wasAdded = false;
    if (orders.contains(aOrder)) { return false; }
    PurchaseHistory existingPurchaseHistory = aOrder.getPurchaseHistory();
    boolean isNewPurchaseHistory = existingPurchaseHistory != null && !this.equals(existingPurchaseHistory);
    if (isNewPurchaseHistory)
    {
      aOrder.setPurchaseHistory(this);
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
    //Unable to remove aOrder, as it must always have a purchaseHistory
    if (!this.equals(aOrder.getPurchaseHistory()))
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
    Customer existingCustomer = customer;
    customer = null;
    if (existingCustomer != null)
    {
      existingCustomer.delete();
    }
    for(int i=orders.size(); i > 0; i--)
    {
      Order aOrder = orders.get(i - 1);
      aOrder.delete();
    }
  }

}