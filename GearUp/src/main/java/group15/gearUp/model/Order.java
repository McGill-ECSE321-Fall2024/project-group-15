/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package group15.gearUp.model;

import jakarta.persistence.*;
import java.util.*;

// line 70 "model.ump"
// line 169 "model.ump"
@Entity
@Table(name="`Order`")
public class Order
{

  ///------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, Order> ordersByOrderID = new HashMap<Integer, Order>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Order Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int orderID;
  private String orderNumber;
  
  @Enumerated(EnumType.STRING)
  private Status orderStatus;
  private double price;

  //Order Associations
  @OneToMany
  @JoinTable(
    name = "order_Equipment", // Custom join table name
    joinColumns = @JoinColumn(name = "orderID"), // Join column in the Customer entity
    inverseJoinColumns = @JoinColumn(name = "EquipmentID") // Join column in the Order entity
  )
  private List<Equipment> equipment;

  @ManyToOne
  private Customer customer;

  // Hibernate default constructor
  @SuppressWarnings("unused")
  public Order() {
  }

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Order(String aOrderNumber, Status aOrderStatus, double aPrice, Customer aCustomer)
  {
    orderNumber = aOrderNumber;
    orderStatus = aOrderStatus;
    price = aPrice;
    customer = aCustomer;
    if (!setOrderID(orderID))
    {
      throw new RuntimeException("Cannot create due to duplicate orderID. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    equipment = new ArrayList<Equipment>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  public Customer getCustomer()
  {
    return customer;
  }
  public boolean setOrderID(int aOrderID)
  {
    boolean wasSet = false;
    int anOldOrderID = getOrderID();
    if (anOldOrderID == aOrderID) {
      return true;
    }
    if (hasWithOrderID(aOrderID)) {
      return wasSet;
    }
    orderID = aOrderID;
    wasSet = true;
    ordersByOrderID.remove(anOldOrderID);
    ordersByOrderID.put(aOrderID, this);
    return wasSet;
  }

  public boolean setOrderNumber(String aOrderNumber)
  {
    boolean wasSet = false;
    orderNumber = aOrderNumber;
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

  public boolean setPrice(double aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public int getOrderID()
  {
    return orderID;
  }
  /* Code from template attribute_GetUnique */
  public static Order getWithOrderID(int aOrderID)
  {
    return ordersByOrderID.get(aOrderID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithOrderID(int aOrderID)
  {
    return getWithOrderID(aOrderID) != null;
  }

  public String getOrderNumber()
  {
    return orderNumber;
  }

  public Status getOrderStatus()
  {
    return orderStatus;
  }

  public double getPrice()
  {
    return price;
  }
  /* Code from template association_GetMany */
  public Equipment getEquipment(int index)
  {
    Equipment aEquipment = equipment.get(index);
    return aEquipment;
  }

  public List<Equipment> getEquipments()
  {
    List<Equipment> newEquipments = Collections.unmodifiableList(equipment);
    return newEquipments;
  }

  public int numberOfEquipments()
  {
    int number = equipment.size();
    return number;
  }

  public boolean hasEquipments()
  {
    boolean has = equipment.size() > 0;
    return has;
  }

  public int indexOfEquipment(Equipment aEquipment)
  {
    int index = equipment.indexOf(aEquipment);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfEquipments()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addEquipment(Equipment aEquipment)
  {
    boolean wasAdded = false;
    if (equipment.contains(aEquipment)) { return false; }
    equipment.add(aEquipment);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeEquipment(Equipment aEquipment)
  {
    boolean wasRemoved = false;
    if (equipment.contains(aEquipment))
    {
      equipment.remove(aEquipment);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addEquipmentAt(Equipment aEquipment, int index)
  {  
    boolean wasAdded = false;
    if(addEquipment(aEquipment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEquipments()) { index = numberOfEquipments() - 1; }
      equipment.remove(aEquipment);
      equipment.add(index, aEquipment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveEquipmentAt(Equipment aEquipment, int index)
  {
    boolean wasAdded = false;
    if(equipment.contains(aEquipment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEquipments()) { index = numberOfEquipments() - 1; }
      equipment.remove(aEquipment);
      equipment.add(index, aEquipment);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addEquipmentAt(aEquipment, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ordersByOrderID.remove(getOrderID());
    equipment.clear();
  }


  public String toString()
  {
    return super.toString() + "["+
            "orderID" + ":" + getOrderID()+ "," +
            "orderNumber" + ":" + getOrderNumber()+ "," +
            "price" + ":" + getPrice()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "orderStatus" + "=" + (getOrderStatus() != null ? !getOrderStatus().equals(this)  ? getOrderStatus().toString().replaceAll("  ","    ") : "this" : "null");
  }
}