/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 105 "model.ump"
// line 190 "model.ump"
public class Category
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<int, Category> categorysByCategoryID = new HashMap<int, Category>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Category Attributes
  private int categoryID;
  private String name;

  //Category Associations
  private List<Game> games;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Category(int aCategoryID, String aName)
  {
    name = aName;
    if (!setCategoryID(aCategoryID))
    {
      throw new RuntimeException("Cannot create due to duplicate categoryID. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    games = new ArrayList<Game>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCategoryID(int aCategoryID)
  {
    boolean wasSet = false;
    int anOldCategoryID = getCategoryID();
    if (anOldCategoryID != null && anOldCategoryID.equals(aCategoryID)) {
      return true;
    }
    if (hasWithCategoryID(aCategoryID)) {
      return wasSet;
    }
    categoryID = aCategoryID;
    wasSet = true;
    if (anOldCategoryID != null) {
      categorysByCategoryID.remove(anOldCategoryID);
    }
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
  /* Code from template association_AddManyToManyMethod */
  public boolean addGame(Game aGame)
  {
    boolean wasAdded = false;
    if (games.contains(aGame)) { return false; }
    games.add(aGame);
    if (aGame.indexOfCategory(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aGame.addCategory(this);
      if (!wasAdded)
      {
        games.remove(aGame);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeGame(Game aGame)
  {
    boolean wasRemoved = false;
    if (!games.contains(aGame))
    {
      return wasRemoved;
    }

    int oldIndex = games.indexOf(aGame);
    games.remove(oldIndex);
    if (aGame.indexOfCategory(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aGame.removeCategory(this);
      if (!wasRemoved)
      {
        games.add(oldIndex,aGame);
      }
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
    categorysByCategoryID.remove(getCategoryID());
    ArrayList<Game> copyOfGames = new ArrayList<Game>(games);
    games.clear();
    for(Game aGame : copyOfGames)
    {
      if (aGame.numberOfCategories() <= Game.minimumNumberOfCategories())
      {
        aGame.delete();
      }
      else
      {
        aGame.removeCategory(this);
      }
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "categoryID" + ":" + getCategoryID()+ "," +
            "name" + ":" + getName()+ "]";
  }
}