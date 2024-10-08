/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package group15.gameStore.model;
import java.util.*;
import java.sql.Date;

// line 83 "model.ump"
// line 174 "model.ump"
public class Promotion
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, Promotion> promotionsByPromotionID = new HashMap<Integer, Promotion>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Promotion Attributes
  private int promotionID;
  private String promotionCode;
  private double discountPercentage;
  private Date validUntil;

  //Promotion Associations
  private Manager manager;
  private List<Game> games;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Promotion(int aPromotionID, String aPromotionCode, double aDiscountPercentage, Date aValidUntil, Manager aManager)
  {
    promotionCode = aPromotionCode;
    discountPercentage = aDiscountPercentage;
    validUntil = aValidUntil;
    if (!setPromotionID(aPromotionID))
    {
      throw new RuntimeException("Cannot create due to duplicate promotionID. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddManager = setManager(aManager);
    if (!didAddManager)
    {
      throw new RuntimeException("Unable to create promotion due to manager. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    games = new ArrayList<Game>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPromotionID(int aPromotionID)
  {
    boolean wasSet = false;
    int anOldPromotionID = getPromotionID();
    if (anOldPromotionID == aPromotionID) {
      return true;
    }
    if (hasWithPromotionID(aPromotionID)) {
      return wasSet;
    }
    promotionID = aPromotionID;
    wasSet = true;
  
    promotionsByPromotionID.remove(anOldPromotionID);
    promotionsByPromotionID.put(aPromotionID, this);
    return wasSet;
  }

  public boolean setPromotionCode(String aPromotionCode)
  {
    boolean wasSet = false;
    promotionCode = aPromotionCode;
    wasSet = true;
    return wasSet;
  }

  public boolean setDiscountPercentage(double aDiscountPercentage)
  {
    boolean wasSet = false;
    discountPercentage = aDiscountPercentage;
    wasSet = true;
    return wasSet;
  }

  public boolean setValidUntil(Date aValidUntil)
  {
    boolean wasSet = false;
    validUntil = aValidUntil;
    wasSet = true;
    return wasSet;
  }

  public int getPromotionID()
  {
    return promotionID;
  }
  /* Code from template attribute_GetUnique */
  public static Promotion getWithPromotionID(int aPromotionID)
  {
    return promotionsByPromotionID.get(aPromotionID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithPromotionID(int aPromotionID)
  {
    return getWithPromotionID(aPromotionID) != null;
  }

  public String getPromotionCode()
  {
    return promotionCode;
  }

  public double getDiscountPercentage()
  {
    return discountPercentage;
  }

  public Date getValidUntil()
  {
    return validUntil;
  }
  /* Code from template association_GetOne */
  public Manager getManager()
  {
    return manager;
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
      existingManager.removePromotion(this);
    }
    manager.addPromotion(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGames()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addGame(Game aGame)
  {
    boolean wasAdded = false;
    if (games.contains(aGame)) { return false; }
    Promotion existingPromotion = aGame.getPromotion();
    if (existingPromotion == null)
    {
      aGame.setPromotion(this);
    }
    else if (!this.equals(existingPromotion))
    {
      existingPromotion.removeGame(aGame);
      addGame(aGame);
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
    if (games.contains(aGame))
    {
      games.remove(aGame);
      aGame.setPromotion(null);
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
    promotionsByPromotionID.remove(getPromotionID());
    Manager placeholderManager = manager;
    this.manager = null;
    if(placeholderManager != null)
    {
      placeholderManager.removePromotion(this);
    }
    while( !game.isEmpty() )
    {
      Game aGame = game.get(0);
      aGame.setPromotion(null);
      game.remove(aGame);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "promotionID" + ":" + getPromotionID()+ "," +
            "promotionCode" + ":" + getPromotionCode()+ "," +
            "discountPercentage" + ":" + getDiscountPercentage()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "validUntil" + "=" + (getValidUntil() != null ? !getValidUntil().equals(this)  ? getValidUntil().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "manager = "+(getManager()!=null?Integer.toHexString(System.identityHashCode(getManager())):"null");
  }
}