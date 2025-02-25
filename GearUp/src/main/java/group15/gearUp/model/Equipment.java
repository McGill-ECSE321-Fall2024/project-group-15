/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package group15.gearUp.model;

import jakarta.persistence.*;
import java.util.*;
import java.sql.Date;

// line 24 "model.ump"
// line 141 "model.ump"
@Entity
public class Equipment
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, Equipment> EquipmentsByEquipmentID = new HashMap<Integer, Equipment>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Equipment Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int EquipmentID;
  private String title;
  private String description;
  private double price;
  private int stock;
  private String image;
  private Date archivedDate;
  private boolean isApproved;
  @ManyToOne
  private Manager manager;

  // Hibernate default constructor
  @SuppressWarnings("unused")
public Equipment() {
  }

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Equipment(String aTitle, String aDescription, double aPrice, int aStock, String aImage, boolean aIsApproved, Manager aManager)
  {
    title = aTitle;
    description = aDescription;
    price = aPrice;
    stock = aStock;
    image = aImage;
    archivedDate = null;
    isApproved = aIsApproved;
    manager = aManager;
    if (!setEquipmentID(EquipmentID))
    {
      throw new RuntimeException("Cannot create due to duplicate EquipmentID. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  public Manager getManager()
  {
    return manager;
  }
  public boolean setEquipmentID(int aEquipmentID)
  {
    boolean wasSet = false;
    int anOldEquipmentID = getEquipmentID();
    if (anOldEquipmentID == aEquipmentID) {
      return true;
    }
    if (hasWithEquipmentID(aEquipmentID)) {
      return wasSet;
    }
    EquipmentID = aEquipmentID;
    wasSet = true;

    EquipmentsByEquipmentID.remove(anOldEquipmentID);

    EquipmentsByEquipmentID.put(aEquipmentID, this);
    return wasSet;
  }

  public boolean setTitle(String aTitle)
  {
    boolean wasSet = false;
    title = aTitle;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
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

  public boolean setStock(int aStock)
  {
    boolean wasSet = false;
    stock = aStock;
    wasSet = true;
    return wasSet;
  }

  public boolean setImage(String aImage)
  {
    boolean wasSet = false;
    image = aImage;
    wasSet = true;
    return wasSet;
  }

  public boolean setArchivedDate(Date aArchivedDate)
  {
    boolean wasSet = false;
    archivedDate = aArchivedDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsApproved(boolean aIsApproved)
  {
    boolean wasSet = false;
    isApproved = aIsApproved;
    wasSet = true;
    return wasSet;
  }

  public int getEquipmentID()
  {
    return EquipmentID;
  }
  /* Code from template attribute_GetUnique */
  public static Equipment getWithEquipmentID(int aEquipmentID)
  {
    return EquipmentsByEquipmentID.get(aEquipmentID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithEquipmentID(int aEquipmentID)
  {
    return getWithEquipmentID(aEquipmentID) != null;
  }

  public String getTitle()
  {
    return title;
  }

  public String getDescription()
  {
    return description;
  }

  public double getPrice()
  {
    return price;
  }

  public int getStock()
  {
    return stock;
  }

  public String getImage()
  {
    return image;
  }

  public Date getArchivedDate()
  {
    return archivedDate;
  }

  public boolean getIsApproved()
  {
    return isApproved;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsApproved()
  {
    return isApproved;
  }

  public void delete()
  {
    EquipmentsByEquipmentID.remove(getEquipmentID());
  }


  public String toString()
  {
    return super.toString() + "["+
            "EquipmentID" + ":" + getEquipmentID()+ "," +
            "title" + ":" + getTitle()+ "," +
            "description" + ":" + getDescription()+ "," +
            "price" + ":" + getPrice()+ "," +
            "stock" + ":" + getStock()+ "," +
            "image" + ":" + getImage()+ "," +
            "isApproved" + ":" + getIsApproved()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "archivedDate" + "=" + (getArchivedDate() != null ? !getArchivedDate().equals(this)  ? getArchivedDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}