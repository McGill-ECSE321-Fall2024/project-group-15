/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package group15.gameStore.model;

import jakarta.persistence.*;
import java.util.*;
import java.sql.Date;

// line 87 "model.ump"
// line 180 "model.ump"
@Entity
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
  @Id
  @GeneratedValue
  private int promotionID;
  private String promotionCode;
  private double discountPercentage;
  private Date validUntil;

  //Promotion Associations
  @OneToMany
  @JoinTable(
    name = "promotion_game", // Custom join table name
    joinColumns = @JoinColumn(name = "promotionID"), // Join column in the Customer entity
    inverseJoinColumns = @JoinColumn(name = "gameID") // Join column in the Order entity
  )
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Promotion(String aPromotionCode, double aDiscountPercentage, Date aValidUntil, Game aGame)
  {
    promotionCode = aPromotionCode;
    discountPercentage = aDiscountPercentage;
    validUntil = aValidUntil;
    if (!setPromotionID(promotionID))
    {
      throw new RuntimeException("Cannot create due to duplicate promotionID. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    if (!setGame(aGame))
    {
      throw new RuntimeException("Unable to create Promotion due to aGame. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setGame(Game aNewGame)
  {
    boolean wasSet = false;
    if (aNewGame != null)
    {
      game = aNewGame;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    promotionsByPromotionID.remove(getPromotionID());
    game = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "promotionID" + ":" + getPromotionID()+ "," +
            "promotionCode" + ":" + getPromotionCode()+ "," +
            "discountPercentage" + ":" + getDiscountPercentage()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "validUntil" + "=" + (getValidUntil() != null ? !getValidUntil().equals(this)  ? getValidUntil().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}