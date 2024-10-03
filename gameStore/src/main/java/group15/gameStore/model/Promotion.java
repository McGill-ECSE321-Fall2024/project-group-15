/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.sql.Date;

// line 85 "model.ump"
// line 190 "model.ump"
public class Promotion
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Promotion Attributes
  private int promotionID;
  private String promotionCode;
  private double discountPercentage;
  private Date validUntil;

  //Promotion Associations
  private Cart cart;
  private Manager manager;
  private StoreInfo storeInfo;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Promotion(int aPromotionID, String aPromotionCode, double aDiscountPercentage, Date aValidUntil, Cart aCart, Manager aManager, StoreInfo aStoreInfo)
  {
    promotionID = aPromotionID;
    promotionCode = aPromotionCode;
    discountPercentage = aDiscountPercentage;
    validUntil = aValidUntil;
    if (aCart == null || aCart.getPromotion() != null)
    {
      throw new RuntimeException("Unable to create Promotion due to aCart. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    cart = aCart;
    boolean didAddManager = setManager(aManager);
    if (!didAddManager)
    {
      throw new RuntimeException("Unable to create promotion due to manager. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddStoreInfo = setStoreInfo(aStoreInfo);
    if (!didAddStoreInfo)
    {
      throw new RuntimeException("Unable to create promotion due to storeInfo. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Promotion(int aPromotionID, String aPromotionCode, double aDiscountPercentage, Date aValidUntil, int aCartIDForCart, double aTotalPriceForCart, Customer aCustomerForCart, Manager aManager, StoreInfo aStoreInfo)
  {
    promotionID = aPromotionID;
    promotionCode = aPromotionCode;
    discountPercentage = aDiscountPercentage;
    validUntil = aValidUntil;
    cart = new Cart(aCartIDForCart, aTotalPriceForCart, aCustomerForCart, this);
    boolean didAddManager = setManager(aManager);
    if (!didAddManager)
    {
      throw new RuntimeException("Unable to create promotion due to manager. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddStoreInfo = setStoreInfo(aStoreInfo);
    if (!didAddStoreInfo)
    {
      throw new RuntimeException("Unable to create promotion due to storeInfo. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPromotionID(int aPromotionID)
  {
    boolean wasSet = false;
    promotionID = aPromotionID;
    wasSet = true;
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
  public Cart getCart()
  {
    return cart;
  }
  /* Code from template association_GetOne */
  public Manager getManager()
  {
    return manager;
  }
  /* Code from template association_GetOne */
  public StoreInfo getStoreInfo()
  {
    return storeInfo;
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
  /* Code from template association_SetOneToMany */
  public boolean setStoreInfo(StoreInfo aStoreInfo)
  {
    boolean wasSet = false;
    if (aStoreInfo == null)
    {
      return wasSet;
    }

    StoreInfo existingStoreInfo = storeInfo;
    storeInfo = aStoreInfo;
    if (existingStoreInfo != null && !existingStoreInfo.equals(aStoreInfo))
    {
      existingStoreInfo.removePromotion(this);
    }
    storeInfo.addPromotion(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Cart existingCart = cart;
    cart = null;
    if (existingCart != null)
    {
      existingCart.delete();
    }
    Manager placeholderManager = manager;
    this.manager = null;
    if(placeholderManager != null)
    {
      placeholderManager.removePromotion(this);
    }
    StoreInfo placeholderStoreInfo = storeInfo;
    this.storeInfo = null;
    if(placeholderStoreInfo != null)
    {
      placeholderStoreInfo.removePromotion(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "promotionID" + ":" + getPromotionID()+ "," +
            "promotionCode" + ":" + getPromotionCode()+ "," +
            "discountPercentage" + ":" + getDiscountPercentage()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "validUntil" + "=" + (getValidUntil() != null ? !getValidUntil().equals(this)  ? getValidUntil().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "cart = "+(getCart()!=null?Integer.toHexString(System.identityHashCode(getCart())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "manager = "+(getManager()!=null?Integer.toHexString(System.identityHashCode(getManager())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "storeInfo = "+(getStoreInfo()!=null?Integer.toHexString(System.identityHashCode(getStoreInfo())):"null");
  }
}