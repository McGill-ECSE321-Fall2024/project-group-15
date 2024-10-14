/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package group15.gameStore.model;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.*;

// line 70 "model.ump"
// line 169 "model.ump"
@Entity
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
  @GeneratedValue
  private int orderID;
  private String orderNumber;
  
  @Enumerated(EnumType.STRING)
  private Status orderStatus;
  private double price;

  //Order Associations
  @OneToMany
  @JoinTable(
    name = "order_game", // Custom join table name
    joinColumns = @JoinColumn(name = "orderID"), // Join column in the Customer entity
    inverseJoinColumns = @JoinColumn(name = "gameID") // Join column in the Order entity
  )
  private List<Game> games;


  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Order(String aOrderNumber, Status aOrderStatus, double aPrice)
  {
    orderNumber = aOrderNumber;
    orderStatus = aOrderStatus;
    price = aPrice;
    if (!setOrderID(orderID))
    {
      throw new RuntimeException("Cannot create due to duplicate orderID. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    games = new ArrayList<Game>();
  }

  //------------------------
  // INTERFACE
  //------------------------

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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGames()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addGame(Game aGame)
  {
    boolean wasAdded = false;
    if (games.contains(aGame)) { return false; }
    games.add(aGame);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGame(Game aGame)
  {
    boolean wasRemoved = false;
    if (games.contains(aGame))
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

  public void delete()
  {
    ordersByOrderID.remove(getOrderID());
    games.clear();
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