/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package group15.gameStore.model;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.*;

// line 38 "model.ump"
// line 148 "model.ump"
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Employee extends Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Employee Attributes
  private boolean isActive;
  private boolean isManager;

  //Employee Associations
  @ManyToMany
  @JoinTable(
    name = "employee_category", // Custom join table name
    joinColumns = @JoinColumn(name = "employeeID"), // Join column in the Customer entity
    inverseJoinColumns = @JoinColumn(name = "categoryID") // Join column in the Order entity
  )
  private List<Category> categories;

  @ManyToMany
  @JoinTable(
    name = "employee_order", // Custom join table name
    joinColumns = @JoinColumn(name = "employeeID"), // Join column in the Customer entity
    inverseJoinColumns = @JoinColumn(name = "orderID") // Join column in the Order entity
  )
  private List<Order> orders;

  @ManyToMany
  @JoinTable(
    name = "employee_game", // Custom join table name
    joinColumns = @JoinColumn(name = "employeeID"), // Join column in the Customer entity
    inverseJoinColumns = @JoinColumn(name = "gameID") // Join column in the Order entity
  )
  private List<Game> games;

  @ManyToMany
  @JoinTable(
    name = "employee_wishlist", // Custom join table name
    joinColumns = @JoinColumn(name = "employeeID"), // Join column in the Customer entity
    inverseJoinColumns = @JoinColumn(name = "wishlistID") // Join column in the Order entity
  )
  private List<Wishlist> wishlists;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Employee(int aUserID, String aUsername, String aPassword, String aEmail, boolean aIsActive, boolean aIsManager)
  {
    super(aUserID, aUsername, aPassword, aEmail);
    isActive = aIsActive;
    isManager = aIsManager;
    categories = new ArrayList<Category>();
    orders = new ArrayList<Order>();
    games = new ArrayList<Game>();
    wishlists = new ArrayList<Wishlist>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsActive(boolean aIsActive)
  {
    boolean wasSet = false;
    isActive = aIsActive;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsManager(boolean aIsManager)
  {
    boolean wasSet = false;
    isManager = aIsManager;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsActive()
  {
    return isActive;
  }

  public boolean getIsManager()
  {
    return isManager;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsActive()
  {
    return isActive;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsManager()
  {
    return isManager;
  }
  /* Code from template association_GetMany */
  public Category getCategory(int index)
  {
    Category aCategory = categories.get(index);
    return aCategory;
  }

  public List<Category> getCategories()
  {
    List<Category> newCategories = Collections.unmodifiableList(categories);
    return newCategories;
  }

  public int numberOfCategories()
  {
    int number = categories.size();
    return number;
  }

  public boolean hasCategories()
  {
    boolean has = categories.size() > 0;
    return has;
  }

  public int indexOfCategory(Category aCategory)
  {
    int index = categories.indexOf(aCategory);
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
  /* Code from template association_GetMany_clear */
  protected void clear_games()
  {
    games.clear();
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCategories()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addCategory(Category aCategory)
  {
    boolean wasAdded = false;
    if (categories.contains(aCategory)) { return false; }
    categories.add(aCategory);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCategory(Category aCategory)
  {
    boolean wasRemoved = false;
    if (categories.contains(aCategory))
    {
      categories.remove(aCategory);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCategoryAt(Category aCategory, int index)
  {  
    boolean wasAdded = false;
    if(addCategory(aCategory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCategories()) { index = numberOfCategories() - 1; }
      categories.remove(aCategory);
      categories.add(index, aCategory);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCategoryAt(Category aCategory, int index)
  {
    boolean wasAdded = false;
    if(categories.contains(aCategory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCategories()) { index = numberOfCategories() - 1; }
      categories.remove(aCategory);
      categories.add(index, aCategory);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCategoryAt(aCategory, index);
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

  public void delete()
  {
    categories.clear();
    orders.clear();
    games.clear();
    wishlists.clear();
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "isActive" + ":" + getIsActive()+ "," +
            "isManager" + ":" + getIsManager()+ "]";
  }
}