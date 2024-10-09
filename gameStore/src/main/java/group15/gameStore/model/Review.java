/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 96 "model.ump"
// line 204 "model.ump"
public class Review
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<int, Review> reviewsByReviewID = new HashMap<int, Review>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Review Attributes
  private int reviewID;
  private Rating rating;
  private String description;

  //Review Associations
  private Game game;
  private Customer customer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Review(int aReviewID, Rating aRating, String aDescription, Game aGame, Customer aCustomer)
  {
    rating = aRating;
    description = aDescription;
    if (!setReviewID(aReviewID))
    {
      throw new RuntimeException("Cannot create due to duplicate reviewID. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create review due to game. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create review due to customer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setReviewID(int aReviewID)
  {
    boolean wasSet = false;
    int anOldReviewID = getReviewID();
    if (anOldReviewID != null && anOldReviewID.equals(aReviewID)) {
      return true;
    }
    if (hasWithReviewID(aReviewID)) {
      return wasSet;
    }
    reviewID = aReviewID;
    wasSet = true;
    if (anOldReviewID != null) {
      reviewsByReviewID.remove(anOldReviewID);
    }
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
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_SetOneToMany */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    if (aGame == null)
    {
      return wasSet;
    }

    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      existingGame.removeReview(this);
    }
    game.addReview(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCustomer(Customer aCustomer)
  {
    boolean wasSet = false;
    if (aCustomer == null)
    {
      return wasSet;
    }

    Customer existingCustomer = customer;
    customer = aCustomer;
    if (existingCustomer != null && !existingCustomer.equals(aCustomer))
    {
      existingCustomer.removeReview(this);
    }
    customer.addReview(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    reviewsByReviewID.remove(getReviewID());
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeReview(this);
    }
    Customer placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removeReview(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "reviewID" + ":" + getReviewID()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "rating" + "=" + (getRating() != null ? !getRating().equals(this)  ? getRating().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null");
  }
}