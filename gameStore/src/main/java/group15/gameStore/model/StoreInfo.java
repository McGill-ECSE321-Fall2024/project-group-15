/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;
import java.sql.Date;

// line 78 "model.ump"
// line 184 "model.ump"
public class StoreInfo
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //StoreInfo Attributes
  private int storeInfoID;
  private String storePolicies;

  //StoreInfo Associations
  private List<Promotion> promotions;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public StoreInfo(int aStoreInfoID, String aStorePolicies)
  {
    storeInfoID = aStoreInfoID;
    storePolicies = aStorePolicies;
    promotions = new ArrayList<Promotion>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStoreInfoID(int aStoreInfoID)
  {
    boolean wasSet = false;
    storeInfoID = aStoreInfoID;
    wasSet = true;
    return wasSet;
  }

  public boolean setStorePolicies(String aStorePolicies)
  {
    boolean wasSet = false;
    storePolicies = aStorePolicies;
    wasSet = true;
    return wasSet;
  }

  public int getStoreInfoID()
  {
    return storeInfoID;
  }

  public String getStorePolicies()
  {
    return storePolicies;
  }
  /* Code from template association_GetMany */
  public Promotion getPromotion(int index)
  {
    Promotion aPromotion = promotions.get(index);
    return aPromotion;
  }

  public List<Promotion> getPromotions()
  {
    List<Promotion> newPromotions = Collections.unmodifiableList(promotions);
    return newPromotions;
  }

  public int numberOfPromotions()
  {
    int number = promotions.size();
    return number;
  }

  public boolean hasPromotions()
  {
    boolean has = promotions.size() > 0;
    return has;
  }

  public int indexOfPromotion(Promotion aPromotion)
  {
    int index = promotions.indexOf(aPromotion);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPromotions()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Promotion addPromotion(int aPromotionID, String aPromotionCode, double aDiscountPercentage, Date aValidUntil, Cart aCart, Manager aManager)
  {
    return new Promotion(aPromotionID, aPromotionCode, aDiscountPercentage, aValidUntil, aCart, aManager, this);
  }

  public boolean addPromotion(Promotion aPromotion)
  {
    boolean wasAdded = false;
    if (promotions.contains(aPromotion)) { return false; }
    StoreInfo existingStoreInfo = aPromotion.getStoreInfo();
    boolean isNewStoreInfo = existingStoreInfo != null && !this.equals(existingStoreInfo);
    if (isNewStoreInfo)
    {
      aPromotion.setStoreInfo(this);
    }
    else
    {
      promotions.add(aPromotion);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePromotion(Promotion aPromotion)
  {
    boolean wasRemoved = false;
    //Unable to remove aPromotion, as it must always have a storeInfo
    if (!this.equals(aPromotion.getStoreInfo()))
    {
      promotions.remove(aPromotion);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPromotionAt(Promotion aPromotion, int index)
  {  
    boolean wasAdded = false;
    if(addPromotion(aPromotion))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPromotions()) { index = numberOfPromotions() - 1; }
      promotions.remove(aPromotion);
      promotions.add(index, aPromotion);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePromotionAt(Promotion aPromotion, int index)
  {
    boolean wasAdded = false;
    if(promotions.contains(aPromotion))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPromotions()) { index = numberOfPromotions() - 1; }
      promotions.remove(aPromotion);
      promotions.add(index, aPromotion);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPromotionAt(aPromotion, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=promotions.size(); i > 0; i--)
    {
      Promotion aPromotion = promotions.get(i - 1);
      aPromotion.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "storeInfoID" + ":" + getStoreInfoID()+ "," +
            "storePolicies" + ":" + getStorePolicies()+ "]";
  }
}