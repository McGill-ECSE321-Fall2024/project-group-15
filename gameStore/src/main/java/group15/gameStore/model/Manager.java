/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;
import java.sql.Date;

// line 57 "model.ump"
// line 169 "model.ump"
public class Manager extends Employee
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Manager Attributes
  private int managerID;

  //Manager Associations
  private List<Promotion> promotions;
  private List<Category> categories;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Manager(String aUsername, String aPassword, String aEmail, int aEmployeeID, boolean aStatus, int aManagerID)
  {
    super(aUsername, aPassword, aEmail, aEmployeeID, aStatus);
    managerID = aManagerID;
    promotions = new ArrayList<Promotion>();
    categories = new ArrayList<Category>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setManagerID(int aManagerID)
  {
    boolean wasSet = false;
    managerID = aManagerID;
    wasSet = true;
    return wasSet;
  }

  public int getManagerID()
  {
    return managerID;
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
  /* Code from template association_GetMany */
  public Category getCategory(int index)
  {
    Category aCategory = categories.get(index);
    return aCategory;
  }

  public List<Category> getCategories()
  {
    List<Category> newCategories = Collections.unmodifiableList(categories);
    return newCategories;
  }

  public int numberOfCategories()
  {
    int number = categories.size();
    return number;
  }

  public boolean hasCategories()
  {
    boolean has = categories.size() > 0;
    return has;
  }

  public int indexOfCategory(Category aCategory)
  {
    int index = categories.indexOf(aCategory);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPromotions()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Promotion addPromotion(int aPromotionID, String aPromotionCode, double aDiscountPercentage, Date aValidUntil, Cart aCart, StoreInfo aStoreInfo)
  {
    return new Promotion(aPromotionID, aPromotionCode, aDiscountPercentage, aValidUntil, aCart, this, aStoreInfo);
  }

  public boolean addPromotion(Promotion aPromotion)
  {
    boolean wasAdded = false;
    if (promotions.contains(aPromotion)) { return false; }
    Manager existingManager = aPromotion.getManager();
    boolean isNewManager = existingManager != null && !this.equals(existingManager);
    if (isNewManager)
    {
      aPromotion.setManager(this);
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
    //Unable to remove aPromotion, as it must always have a manager
    if (!this.equals(aPromotion.getManager()))
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCategories()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Category addCategory(int aCategoryID, String aName)
  {
    return new Category(aCategoryID, aName, this);
  }

  public boolean addCategory(Category aCategory)
  {
    boolean wasAdded = false;
    if (categories.contains(aCategory)) { return false; }
    Manager existingManager = aCategory.getManager();
    boolean isNewManager = existingManager != null && !this.equals(existingManager);
    if (isNewManager)
    {
      aCategory.setManager(this);
    }
    else
    {
      categories.add(aCategory);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCategory(Category aCategory)
  {
    boolean wasRemoved = false;
    //Unable to remove aCategory, as it must always have a manager
    if (!this.equals(aCategory.getManager()))
    {
      categories.remove(aCategory);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCategoryAt(Category aCategory, int index)
  {  
    boolean wasAdded = false;
    if(addCategory(aCategory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCategories()) { index = numberOfCategories() - 1; }
      categories.remove(aCategory);
      categories.add(index, aCategory);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCategoryAt(Category aCategory, int index)
  {
    boolean wasAdded = false;
    if(categories.contains(aCategory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCategories()) { index = numberOfCategories() - 1; }
      categories.remove(aCategory);
      categories.add(index, aCategory);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCategoryAt(aCategory, index);
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
    for(int i=categories.size(); i > 0; i--)
    {
      Category aCategory = categories.get(i - 1);
      aCategory.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "managerID" + ":" + getManagerID()+ "]";
  }
}