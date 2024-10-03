/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;
import java.sql.Date;

// line 63 "model.ump"
// line 174 "model.ump"
public class Order
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Order Attributes
  private int orderID;
  private String orderNumber;
  private Customer customerDetails;
  private Status orderStatus;

  //Order Associations
  private List<Game> games;
  private PaymentInfo paymentInfo;
  private PurchaseHistory purchaseHistory;
  private Customer customer;
  private List<Employee> employees;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Order(int aOrderID, String aOrderNumber, Customer aCustomerDetails, Status aOrderStatus, PaymentInfo aPaymentInfo, PurchaseHistory aPurchaseHistory, Customer aCustomer)
  {
    orderID = aOrderID;
    orderNumber = aOrderNumber;
    customerDetails = aCustomerDetails;
    orderStatus = aOrderStatus;
    games = new ArrayList<Game>();
    if (aPaymentInfo == null || aPaymentInfo.getOrder() != null)
    {
      throw new RuntimeException("Unable to create Order due to aPaymentInfo. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    paymentInfo = aPaymentInfo;
    boolean didAddPurchaseHistory = setPurchaseHistory(aPurchaseHistory);
    if (!didAddPurchaseHistory)
    {
      throw new RuntimeException("Unable to create order due to purchaseHistory. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create order due to customer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    employees = new ArrayList<Employee>();
  }

  public Order(int aOrderID, String aOrderNumber, Customer aCustomerDetails, Status aOrderStatus, int aPaymentinfoIDForPaymentInfo, String aCardNumberForPaymentInfo, Date aExpiryDateForPaymentInfo, int aCvvForPaymentInfo, String aBillingAddressForPaymentInfo, Customer aCustomerForPaymentInfo, PurchaseHistory aPurchaseHistory, Customer aCustomer)
  {
    orderID = aOrderID;
    orderNumber = aOrderNumber;
    customerDetails = aCustomerDetails;
    orderStatus = aOrderStatus;
    games = new ArrayList<Game>();
    paymentInfo = new PaymentInfo(aPaymentinfoIDForPaymentInfo, aCardNumberForPaymentInfo, aExpiryDateForPaymentInfo, aCvvForPaymentInfo, aBillingAddressForPaymentInfo, aCustomerForPaymentInfo, this);
    boolean didAddPurchaseHistory = setPurchaseHistory(aPurchaseHistory);
    if (!didAddPurchaseHistory)
    {
      throw new RuntimeException("Unable to create order due to purchaseHistory. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create order due to customer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    employees = new ArrayList<Employee>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setOrderID(int aOrderID)
  {
    boolean wasSet = false;
    orderID = aOrderID;
    wasSet = true;
    return wasSet;
  }

  public boolean setOrderNumber(String aOrderNumber)
  {
    boolean wasSet = false;
    orderNumber = aOrderNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setCustomerDetails(Customer aCustomerDetails)
  {
    boolean wasSet = false;
    customerDetails = aCustomerDetails;
    wasSet = true;
    return wasSet;
  }

  public boolean setOrderStatus(Status aOrderStatus)
  {
    boolean wasSet = false;
    orderStatus = aOrderStatus;
    wasSet = true;
    return wasSet;
  }

  public int getOrderID()
  {
    return orderID;
  }

  public String getOrderNumber()
  {
    return orderNumber;
  }

  public Customer getCustomerDetails()
  {
    return customerDetails;
  }

  public Status getOrderStatus()
  {
    return orderStatus;
  }
  /* Code from template association_GetMany */
  public Game getGame(int index)
  {
    Game aGame = games.get(index);
    return aGame;
  }

  public List<Game> getGames()
  {
    List<Game> newGames = Collections.unmodifiableList(games);
    return newGames;
  }

  public int numberOfGames()
  {
    int number = games.size();
    return number;
  }

  public boolean hasGames()
  {
    boolean has = games.size() > 0;
    return has;
  }

  public int indexOfGame(Game aGame)
  {
    int index = games.indexOf(aGame);
    return index;
  }
  /* Code from template association_GetOne */
  public PaymentInfo getPaymentInfo()
  {
    return paymentInfo;
  }
  /* Code from template association_GetOne */
  public PurchaseHistory getPurchaseHistory()
  {
    return purchaseHistory;
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_GetMany */
  public Employee getEmployee(int index)
  {
    Employee aEmployee = employees.get(index);
    return aEmployee;
  }

  public List<Employee> getEmployees()
  {
    List<Employee> newEmployees = Collections.unmodifiableList(employees);
    return newEmployees;
  }

  public int numberOfEmployees()
  {
    int number = employees.size();
    return number;
  }

  public boolean hasEmployees()
  {
    boolean has = employees.size() > 0;
    return has;
  }

  public int indexOfEmployee(Employee aEmployee)
  {
    int index = employees.indexOf(aEmployee);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGames()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Game addGame(int aGameID, String aTitle, String aDescription, String aCategoryName, double aPrice, int aStock, String aImage, boolean aArchived, boolean aManagerApproval, Employee aEmployee, Wishlist aWishlist, Category aCategory, GameArchive aGameArchive)
  {
    return new Game(aGameID, aTitle, aDescription, aCategoryName, aPrice, aStock, aImage, aArchived, aManagerApproval, aEmployee, aWishlist, this, aCategory, aGameArchive);
  }

  public boolean addGame(Game aGame)
  {
    boolean wasAdded = false;
    if (games.contains(aGame)) { return false; }
    Order existingOrder = aGame.getOrder();
    boolean isNewOrder = existingOrder != null && !this.equals(existingOrder);
    if (isNewOrder)
    {
      aGame.setOrder(this);
    }
    else
    {
      games.add(aGame);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGame(Game aGame)
  {
    boolean wasRemoved = false;
    //Unable to remove aGame, as it must always have a order
    if (!this.equals(aGame.getOrder()))
    {
      games.remove(aGame);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGameAt(Game aGame, int index)
  {  
    boolean wasAdded = false;
    if(addGame(aGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGames()) { index = numberOfGames() - 1; }
      games.remove(aGame);
      games.add(index, aGame);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGameAt(Game aGame, int index)
  {
    boolean wasAdded = false;
    if(games.contains(aGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGames()) { index = numberOfGames() - 1; }
      games.remove(aGame);
      games.add(index, aGame);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGameAt(aGame, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setPurchaseHistory(PurchaseHistory aPurchaseHistory)
  {
    boolean wasSet = false;
    if (aPurchaseHistory == null)
    {
      return wasSet;
    }

    PurchaseHistory existingPurchaseHistory = purchaseHistory;
    purchaseHistory = aPurchaseHistory;
    if (existingPurchaseHistory != null && !existingPurchaseHistory.equals(aPurchaseHistory))
    {
      existingPurchaseHistory.removeOrder(this);
    }
    purchaseHistory.addOrder(this);
    wasSet = true;
    return wasSet;
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
      existingCustomer.removeOrder(this);
    }
    customer.addOrder(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfEmployees()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addEmployee(Employee aEmployee)
  {
    boolean wasAdded = false;
    if (employees.contains(aEmployee)) { return false; }
    employees.add(aEmployee);
    if (aEmployee.indexOfOrder(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aEmployee.addOrder(this);
      if (!wasAdded)
      {
        employees.remove(aEmployee);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeEmployee(Employee aEmployee)
  {
    boolean wasRemoved = false;
    if (!employees.contains(aEmployee))
    {
      return wasRemoved;
    }

    int oldIndex = employees.indexOf(aEmployee);
    employees.remove(oldIndex);
    if (aEmployee.indexOfOrder(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aEmployee.removeOrder(this);
      if (!wasRemoved)
      {
        employees.add(oldIndex,aEmployee);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addEmployeeAt(Employee aEmployee, int index)
  {  
    boolean wasAdded = false;
    if(addEmployee(aEmployee))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEmployees()) { index = numberOfEmployees() - 1; }
      employees.remove(aEmployee);
      employees.add(index, aEmployee);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveEmployeeAt(Employee aEmployee, int index)
  {
    boolean wasAdded = false;
    if(employees.contains(aEmployee))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEmployees()) { index = numberOfEmployees() - 1; }
      employees.remove(aEmployee);
      employees.add(index, aEmployee);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addEmployeeAt(aEmployee, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=games.size(); i > 0; i--)
    {
      Game aGame = games.get(i - 1);
      aGame.delete();
    }
    PaymentInfo existingPaymentInfo = paymentInfo;
    paymentInfo = null;
    if (existingPaymentInfo != null)
    {
      existingPaymentInfo.delete();
    }
    PurchaseHistory placeholderPurchaseHistory = purchaseHistory;
    this.purchaseHistory = null;
    if(placeholderPurchaseHistory != null)
    {
      placeholderPurchaseHistory.removeOrder(this);
    }
    Customer placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removeOrder(this);
    }
    ArrayList<Employee> copyOfEmployees = new ArrayList<Employee>(employees);
    employees.clear();
    for(Employee aEmployee : copyOfEmployees)
    {
      aEmployee.removeOrder(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "orderID" + ":" + getOrderID()+ "," +
            "orderNumber" + ":" + getOrderNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "customerDetails" + "=" + (getCustomerDetails() != null ? !getCustomerDetails().equals(this)  ? getCustomerDetails().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "orderStatus" + "=" + (getOrderStatus() != null ? !getOrderStatus().equals(this)  ? getOrderStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "paymentInfo = "+(getPaymentInfo()!=null?Integer.toHexString(System.identityHashCode(getPaymentInfo())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "purchaseHistory = "+(getPurchaseHistory()!=null?Integer.toHexString(System.identityHashCode(getPurchaseHistory())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null");
  }
}