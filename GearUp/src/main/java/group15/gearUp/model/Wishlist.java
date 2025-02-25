/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package group15.gearUp.model;

import jakarta.persistence.*;
import java.util.*;

// line 117 "model.ump"
// line 198 "model.ump"
@Entity
public class Wishlist
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, Wishlist> wishlistsByWishListId = new HashMap<Integer, Wishlist>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Wishlist Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int wishListId;
  private String wishListName;

  //Wishlist Associations
  @OneToMany
  @JoinTable(
    name = "wishlist_Equipment",
    joinColumns = @JoinColumn(name = "wishlistID"), // Join column in the Customer entity
    inverseJoinColumns = @JoinColumn(name = "EquipmentID") // Join column in the Order entity
  )
  private List<Equipment> equipment;

  @ManyToOne
  private Customer customer;

  // Hibernate default constructor
  @SuppressWarnings("unused")
  private Wishlist() {
  }

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Wishlist(String aWishListName, Customer aCustomer)
  {
    customer = aCustomer;
    wishListName = aWishListName;
    if (!setWishListId(wishListId))
    {
      throw new RuntimeException("Cannot create due to duplicate wishListId. See https://manual.umple.org?RE003ViolationofUniqueness.html");
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
  public boolean setWishListId(int aWishListId)
  {
    boolean wasSet = false;
    int anOldWishListId = getWishListId();
    if (anOldWishListId == aWishListId) {
      return true;
    }
    if (hasWithWishListId(aWishListId)) {
      return wasSet;
    }
    wishListId = aWishListId;
    wasSet = true;
    wishlistsByWishListId.remove(anOldWishListId);
    wishlistsByWishListId.put(aWishListId, this);
    return wasSet;
  }

  public boolean setWishListName(String aWishListName)
  {
    boolean wasSet = false;
    wishListName = aWishListName;
    wasSet = true;
    return wasSet;
  }

  public int getWishListId()
  {
    return wishListId;
  }
  /* Code from template attribute_GetUnique */
  public static Wishlist getWithWishListId(int aWishListId)
  {
    return wishlistsByWishListId.get(aWishListId);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithWishListId(int aWishListId)
  {
    return getWithWishListId(aWishListId) != null;
  }

  public String getWishListName()
  {
    return wishListName;
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
    wishlistsByWishListId.remove(getWishListId());
    equipment.clear();
  }


  public String toString()
  {
    return super.toString() + "["+
            "wishListId" + ":" + getWishListId()+ "," +
            "wishListName" + ":" + getWishListName()+ "]";
  }
}