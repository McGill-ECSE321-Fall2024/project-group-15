/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package group15.gameStore.model;

import jakarta.persistence.*;
import java.util.*;

// line 100 "model.ump"
// line 186 "model.ump"
@Entity
public class Review
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, Review> reviewsByReviewID = new HashMap<Integer, Review>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Review Attributes
  @Id
  @GeneratedValue
  private int reviewID;
  private Rating rating;
  private String description;

  //Review Associations
  @OneToOne
  // @JoinTable(
  //   name = "review_game", // Custom join table name
  //   joinColumns = @JoinColumn(name = "reviewID"), // Join column in the Customer entity
  //   inverseJoinColumns = @JoinColumn(name = "gameID") // Join column in the Order entity
  // )
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Review(Rating aRating, String aDescription, Game aGame)
  {
    rating = aRating;
    description = aDescription;
    if (!setReviewID(reviewID))
    {
      throw new RuntimeException("Cannot create due to duplicate reviewID. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    if (!setGame(aGame))
    {
      throw new RuntimeException("Unable to create Review due to aGame. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setReviewID(int aReviewID)
  {
    boolean wasSet = false;
    int anOldReviewID = getReviewID();
    if (anOldReviewID == aReviewID) {
      return true;
    }
    if (hasWithReviewID(aReviewID)) {
      return wasSet;
    }
    reviewID = aReviewID;
    wasSet = true;
    reviewsByReviewID.remove(anOldReviewID);
    reviewsByReviewID.put(aReviewID, this);
    return wasSet;
  }

  public boolean setRating(Rating aRating)
  {
    boolean wasSet = false;
    rating = aRating;
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

  public int getReviewID()
  {
    return reviewID;
  }
  /* Code from template attribute_GetUnique */
  public static Review getWithReviewID(int aReviewID)
  {
    return reviewsByReviewID.get(aReviewID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithReviewID(int aReviewID)
  {
    return getWithReviewID(aReviewID) != null;
  }

  public Rating getRating()
  {
    return rating;
  }

  public String getDescription()
  {
    return description;
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
    reviewsByReviewID.remove(getReviewID());
    game = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "reviewID" + ":" + getReviewID()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "rating" + "=" + (getRating() != null ? !getRating().equals(this)  ? getRating().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}