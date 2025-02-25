/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package group15.gearUp.model;

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
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int promotionID;
  private String promotionCode;
  private double discountPercentage;
  private Date validUntil;

  //Promotion Associations
  @OneToOne()
  private Equipment equipment;

  // Hibernate default constructor
  @SuppressWarnings("unused")
  public Promotion() {
  }

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Promotion(String aPromotionCode, double aDiscountPercentage, Date aValidUntil, Equipment aEquipment)
  {
    promotionCode = aPromotionCode;
    discountPercentage = aDiscountPercentage;
    validUntil = aValidUntil;
    if (!setPromotionID(promotionID))
    {
      throw new RuntimeException("Cannot create due to duplicate promotionID. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    if (!setEquipment(aEquipment))
    {
      throw new RuntimeException("Unable to create Promotion due to aEquipment. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
    promotionsByPromotionID.remove(getPromotionID());
    equipment = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "promotionID" + ":" + getPromotionID()+ "," +
            "promotionCode" + ":" + getPromotionCode()+ "," +
            "discountPercentage" + ":" + getDiscountPercentage()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "validUntil" + "=" + (getValidUntil() != null ? !getValidUntil().equals(this)  ? getValidUntil().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "Equipment = "+(getEquipment()!=null?Integer.toHexString(System.identityHashCode(getEquipment())):"null");
  }
}