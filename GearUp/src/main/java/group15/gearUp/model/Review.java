/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package group15.gearUp.model;

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
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int reviewID;
  private Rating rating;
  private String description;

  //Review Associations
  @ManyToOne
  // @JoinTable(
  //   name = "review_Equipment", // Custom join table name
  //   joinColumns = @JoinColumn(name = "reviewID"), // Join column in the Customer entity
  //   inverseJoinColumns = @JoinColumn(name = "EquipmentID") // Join column in the Order entity
  // )
  private Equipment equipment;

  @ManyToOne
  private Customer customer;

  // Hibernate default constructor
  @SuppressWarnings("unused")
  public Review() {
  }

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Review(Rating aRating, String aDescription, Equipment aEquipment, Customer aCustomer)
  {
    rating = aRating;
    description = aDescription;
    customer = aCustomer;
    if (!setReviewID(reviewID))
    {
      throw new RuntimeException("Cannot create due to duplicate reviewID. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    if (!setEquipment(aEquipment))
    {
      throw new RuntimeException("Unable to create Review due to aEquipment. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  public Customer getCustomer() {
    return customer;
  }
public boolean setCustomer(Customer aCustomer){
  boolean wasSet = false;
    customer = aCustomer;
    wasSet = true;
    return wasSet;
}

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
  public Equipment getEquipment()
  {
    return equipment;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setEquipment(Equipment aNewEquipment)
  {
    boolean wasSet = false;
    if (aNewEquipment != null)
    {
      equipment = aNewEquipment;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    reviewsByReviewID.remove(getReviewID());
    equipment = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "reviewID" + ":" + getReviewID()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "rating" + "=" + (getRating() != null ? !getRating().equals(this)  ? getRating().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "Equipment = "+(getEquipment()!=null?Integer.toHexString(System.identityHashCode(getEquipment())):"null");
  }
}