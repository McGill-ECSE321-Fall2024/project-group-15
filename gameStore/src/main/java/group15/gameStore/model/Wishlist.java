/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package group15.gameStore.model;

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
  @GeneratedValue
  private int wishListId;
  private String wishListName;

  //Wishlist Associations
  @OneToMany
  @JoinTable(
    name = "wishlist_game", // Custom join table name
    joinColumns = @JoinColumn(name = "wishlistID"), // Join column in the Customer entity
    inverseJoinColumns = @JoinColumn(name = "gameID") // Join column in the Order entity
  )
  private List<Game> games;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Wishlist(String aWishListName)
  {
    wishListName = aWishListName;
    if (!setWishListId(wishListId))
    {
      throw new RuntimeException("Cannot create due to duplicate wishListId. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    games = new ArrayList<Game>();
  }

  //------------------------
  // INTERFACE
  //------------------------

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
    wishlistsByWishListId.remove(getWishListId());
    games.clear();
  }


  public String toString()
  {
    return super.toString() + "["+
            "wishListId" + ":" + getWishListId()+ "," +
            "wishListName" + ":" + getWishListName()+ "]";
  }
}