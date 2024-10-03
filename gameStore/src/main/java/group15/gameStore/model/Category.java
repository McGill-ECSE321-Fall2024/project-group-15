/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 114 "model.ump"
// line 215 "model.ump"
public class Category
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Category Attributes
  private int categoryID;
  private String name;

  //Category Associations
  private List<Game> games;
  private Manager manager;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Category(int aCategoryID, String aName, Manager aManager)
  {
    categoryID = aCategoryID;
    name = aName;
    games = new ArrayList<Game>();
    boolean didAddManager = setManager(aManager);
    if (!didAddManager)
    {
      throw new RuntimeException("Unable to create category due to manager. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCategoryID(int aCategoryID)
  {
    boolean wasSet = false;
    categoryID = aCategoryID;
    wasSet = true;
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

  public String getName()
  {
    return name;
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
  public Manager getManager()
  {
    return manager;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGames()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Game addGame(int aGameID, String aTitle, String aDescription, String aCategoryName, double aPrice, int aStock, String aImage, boolean aArchived, boolean aManagerApproval, Employee aEmployee, Wishlist aWishlist, Order aOrder, GameArchive aGameArchive)
  {
    return new Game(aGameID, aTitle, aDescription, aCategoryName, aPrice, aStock, aImage, aArchived, aManagerApproval, aEmployee, aWishlist, aOrder, this, aGameArchive);
  }

  public boolean addGame(Game aGame)
  {
    boolean wasAdded = false;
    if (games.contains(aGame)) { return false; }
    Category existingCategory = aGame.getCategory();
    boolean isNewCategory = existingCategory != null && !this.equals(existingCategory);
    if (isNewCategory)
    {
      aGame.setCategory(this);
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
    //Unable to remove aGame, as it must always have a category
    if (!this.equals(aGame.getCategory()))
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
  public boolean setManager(Manager aManager)
  {
    boolean wasSet = false;
    if (aManager == null)
    {
      return wasSet;
    }

    Manager existingManager = manager;
    manager = aManager;
    if (existingManager != null && !existingManager.equals(aManager))
    {
      existingManager.removeCategory(this);
    }
    manager.addCategory(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=games.size(); i > 0; i--)
    {
      Game aGame = games.get(i - 1);
      aGame.delete();
    }
    Manager placeholderManager = manager;
    this.manager = null;
    if(placeholderManager != null)
    {
      placeholderManager.removeCategory(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "categoryID" + ":" + getCategoryID()+ "," +
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "manager = "+(getManager()!=null?Integer.toHexString(System.identityHashCode(getManager())):"null");
  }
}