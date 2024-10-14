/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package group15.gameStore.model;

import jakarta.persistence.*;
import java.util.*;
import java.sql.Date;

// line 24 "model.ump"
// line 141 "model.ump"
@Entity
public class Game
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, Game> gamesByGameID = new HashMap<Integer, Game>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int gameID;
  private String title;
  private String description;
  private double price;
  private int stock;
  private String image;
  private Date archivedDate;
  private boolean isApproved;

  // Hibernate default constructor
  @SuppressWarnings("unused")
  private Game() {
  }

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game(String aTitle, String aDescription, double aPrice, int aStock, String aImage, boolean aIsApproved)
  {
    title = aTitle;
    description = aDescription;
    price = aPrice;
    stock = aStock;
    image = aImage;
    archivedDate = null;
    isApproved = aIsApproved;
    if (!setGameID(gameID))
    {
      throw new RuntimeException("Cannot create due to duplicate gameID. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setGameID(int aGameID)
  {
    boolean wasSet = false;
    int anOldGameID = getGameID();
    if (anOldGameID == aGameID) {
      return true;
    }
    if (hasWithGameID(aGameID)) {
      return wasSet;
    }
    gameID = aGameID;
    wasSet = true;

    gamesByGameID.remove(anOldGameID);

    gamesByGameID.put(aGameID, this);
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

  public int getGameID()
  {
    return gameID;
  }
  /* Code from template attribute_GetUnique */
  public static Game getWithGameID(int aGameID)
  {
    return gamesByGameID.get(aGameID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithGameID(int aGameID)
  {
    return getWithGameID(aGameID) != null;
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
    gamesByGameID.remove(getGameID());
  }


  public String toString()
  {
    return super.toString() + "["+
            "gameID" + ":" + getGameID()+ "," +
            "title" + ":" + getTitle()+ "," +
            "description" + ":" + getDescription()+ "," +
            "price" + ":" + getPrice()+ "," +
            "stock" + ":" + getStock()+ "," +
            "image" + ":" + getImage()+ "," +
            "isApproved" + ":" + getIsApproved()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "archivedDate" + "=" + (getArchivedDate() != null ? !getArchivedDate().equals(this)  ? getArchivedDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}