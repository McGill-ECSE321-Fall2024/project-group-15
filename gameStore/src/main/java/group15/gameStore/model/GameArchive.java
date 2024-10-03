/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.sql.Date;
import java.util.*;

// line 127 "model.ump"
// line 227 "model.ump"
public class GameArchive
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GameArchive Attributes
  private int gameArchiveID;
  private Date archiveDate;

  //GameArchive Associations
  private List<Game> games;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GameArchive(int aGameArchiveID, Date aArchiveDate)
  {
    gameArchiveID = aGameArchiveID;
    archiveDate = aArchiveDate;
    games = new ArrayList<Game>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setGameArchiveID(int aGameArchiveID)
  {
    boolean wasSet = false;
    gameArchiveID = aGameArchiveID;
    wasSet = true;
    return wasSet;
  }

  public boolean setArchiveDate(Date aArchiveDate)
  {
    boolean wasSet = false;
    archiveDate = aArchiveDate;
    wasSet = true;
    return wasSet;
  }

  public int getGameArchiveID()
  {
    return gameArchiveID;
  }

  public Date getArchiveDate()
  {
    return archiveDate;
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
  /* Code from template association_AddManyToOne */
  public Game addGame(int aGameID, String aTitle, String aDescription, String aCategoryName, double aPrice, int aStock, String aImage, boolean aArchived, boolean aManagerApproval, Employee aEmployee, Wishlist aWishlist, Order aOrder, Category aCategory)
  {
    return new Game(aGameID, aTitle, aDescription, aCategoryName, aPrice, aStock, aImage, aArchived, aManagerApproval, aEmployee, aWishlist, aOrder, aCategory, this);
  }

  public boolean addGame(Game aGame)
  {
    boolean wasAdded = false;
    if (games.contains(aGame)) { return false; }
    GameArchive existingGameArchive = aGame.getGameArchive();
    boolean isNewGameArchive = existingGameArchive != null && !this.equals(existingGameArchive);
    if (isNewGameArchive)
    {
      aGame.setGameArchive(this);
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
    //Unable to remove aGame, as it must always have a gameArchive
    if (!this.equals(aGame.getGameArchive()))
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
    for(int i=games.size(); i > 0; i--)
    {
      Game aGame = games.get(i - 1);
      aGame.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "gameArchiveID" + ":" + getGameArchiveID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "archiveDate" + "=" + (getArchiveDate() != null ? !getArchiveDate().equals(this)  ? getArchiveDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}