/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

import java.sql.Date;

// line 95 "model.ump"
// line 197 "model.ump"
@Entity
public class Cart
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Cart Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int cartID;

  @Id
  private double totalPrice;

  //Cart Associations
  @OneToOne
  private Customer customer;
  @ManyToMany
  private List<Game> games;
  @OneToOne
  private Promotion promotion;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Cart(int aCartID, double aTotalPrice, Customer aCustomer, Promotion aPromotion)
  {
    cartID = aCartID;
    totalPrice = aTotalPrice;
    if (aCustomer == null || aCustomer.getCart() != null)
    {
      throw new RuntimeException("Unable to create Cart due to aCustomer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    customer = aCustomer;
    games = new ArrayList<Game>();
    if (aPromotion == null || aPromotion.getCart() != null)
    {
      throw new RuntimeException("Unable to create Cart due to aPromotion. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    promotion = aPromotion;
  }

  public Cart(int aCartID, double aTotalPrice, String aUsernameForCustomer, String aPasswordForCustomer, String aEmailForCustomer, int aCustomerIDForCustomer, String aAddressForCustomer, String aPhoneNumberForCustomer, Wishlist aWishListForCustomer, PurchaseHistory aPurchaseHistoryForCustomer, PaymentInfo aSavedPaymentInfoForCustomer, int aPromotionIDForPromotion, String aPromotionCodeForPromotion, double aDiscountPercentageForPromotion, Date aValidUntilForPromotion, Manager aManagerForPromotion, StoreInfo aStoreInfoForPromotion)
  {
    cartID = aCartID;
    totalPrice = aTotalPrice;
    customer = new Customer(aUsernameForCustomer, aPasswordForCustomer, aEmailForCustomer, aCustomerIDForCustomer, aAddressForCustomer, aPhoneNumberForCustomer, aWishListForCustomer, aPurchaseHistoryForCustomer, aSavedPaymentInfoForCustomer, this);
    games = new ArrayList<Game>();
    promotion = new Promotion(aPromotionIDForPromotion, aPromotionCodeForPromotion, aDiscountPercentageForPromotion, aValidUntilForPromotion, this, aManagerForPromotion, aStoreInfoForPromotion);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCartID(int aCartID)
  {
    boolean wasSet = false;
    cartID = aCartID;
    wasSet = true;
    return wasSet;
  }

  public boolean setTotalPrice(double aTotalPrice)
  {
    boolean wasSet = false;
    totalPrice = aTotalPrice;
    wasSet = true;
    return wasSet;
  }

  public int getCartID()
  {
    return cartID;
  }

  public double getTotalPrice()
  {
    return totalPrice;
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
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
  /* Code from template association_GetOne */
  public Promotion getPromotion()
  {
    return promotion;
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
    if (aGame.indexOfCart(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aGame.addCart(this);
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
    if (aGame.indexOfCart(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aGame.removeCart(this);
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
    Customer existingCustomer = customer;
    customer = null;
    if (existingCustomer != null)
    {
      existingCustomer.delete();
    }
    ArrayList<Game> copyOfGames = new ArrayList<Game>(games);
    games.clear();
    for(Game aGame : copyOfGames)
    {
      aGame.removeCart(this);
    }
    Promotion existingPromotion = promotion;
    promotion = null;
    if (existingPromotion != null)
    {
      existingPromotion.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "cartID" + ":" + getCartID()+ "," +
            "totalPrice" + ":" + getTotalPrice()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "promotion = "+(getPromotion()!=null?Integer.toHexString(System.identityHashCode(getPromotion())):"null");
  }
}