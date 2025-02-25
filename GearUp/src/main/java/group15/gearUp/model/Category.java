/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package group15.gearUp.model;

import jakarta.persistence.*;
import java.util.*;

// line 109 "model.ump"
// line 192 "model.ump"
@Entity
public class Category
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, Category> categorysByCategoryID = new HashMap<Integer, Category>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Category Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int categoryID;
  private String name;

  //Category Associations
  @ManyToMany
  @JoinTable(
    name = "category_Equipment",
    joinColumns = @JoinColumn(name = "categoryID"),
    inverseJoinColumns = @JoinColumn(name = "EquipmentID")
  )
  private List<Equipment> equipment;

  // Hibernate default constructor
  @SuppressWarnings("unused")
  private Category() {
  }

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Category(String aName)
  {
    name = aName;
    if (!setCategoryID(categoryID))
    {
      throw new RuntimeException("Cannot create due to duplicate categoryID. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    equipment = new ArrayList<Equipment>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCategoryID(int aCategoryID)
  {
    boolean wasSet = false;
    int anOldCategoryID = getCategoryID();
    if (anOldCategoryID == aCategoryID) {
      return true;
    }
    if (hasWithCategoryID(aCategoryID)) {
      return wasSet;
    }
    categoryID = aCategoryID;
    wasSet = true;
    categorysByCategoryID.remove(anOldCategoryID);
    categorysByCategoryID.put(aCategoryID, this);
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public int getCategoryID()
  {
    return categoryID;
  }
  /* Code from template attribute_GetUnique */
  public static Category getWithCategoryID(int aCategoryID)
  {
    return categorysByCategoryID.get(aCategoryID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithCategoryID(int aCategoryID)
  {
    return getWithCategoryID(aCategoryID) != null;
  }

  public String getName()
  {
    return name;
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
    categorysByCategoryID.remove(getCategoryID());
    equipment.clear();
  }


  public String toString()
  {
    return super.toString() + "["+
            "categoryID" + ":" + getCategoryID()+ "," +
            "name" + ":" + getName()+ "]";
  }
}