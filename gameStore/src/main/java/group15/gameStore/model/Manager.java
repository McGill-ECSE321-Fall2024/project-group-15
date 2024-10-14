/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package group15.gameStore.model;

import jakarta.persistence.*;
import java.util.*;

// line 63 "model.ump"
// line 162 "model.ump"
@Entity
public class Manager extends Employee
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Manager Associations
  @OneToMany
  @JoinTable(
    name = "manager_promotion", // Custom join table name
    joinColumns = @JoinColumn(name = "managerID"), // Join column in the Customer entity
    inverseJoinColumns = @JoinColumn(name = "promotionID") // Join column in the Order entity
  )
  private List<Promotion> promotions;

  // Hibernate default constructor
  @SuppressWarnings("unused")
  private Manager() {
  }

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Manager(String aUsername, String aPassword, String aEmail, boolean aIsActive, boolean aIsManager)
  {
    super(aUsername, aPassword, aEmail, aIsActive, aIsManager);
    promotions = new ArrayList<Promotion>();
  }

  //------------------------
  // INTERFACE
  //------------------------
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
  /* Code from template association_GetMany_specialization */
  public Game getGame_Game(int index)
  {
    Game aGame = (Game)super.getGame(index);
    return aGame;
  }

  /* required for Java 7. */
  @SuppressWarnings("unchecked")
  public List<Game> getGames_Game()
  {
    List<? extends Game> newGames = super.getGames();
    return (List<Game>)newGames;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPromotions()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addPromotion(Promotion aPromotion)
  {
    boolean wasAdded = false;
    if (promotions.contains(aPromotion)) { return false; }
    promotions.add(aPromotion);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePromotion(Promotion aPromotion)
  {
    boolean wasRemoved = false;
    if (promotions.contains(aPromotion))
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
  /* Code from template association_set_specialization_reqSuperCode */  /* Code from template association_MinimumNumberOfMethod_specialization */
  public static int minimumNumberOfGames_Game()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany_specialization */
  public boolean removeGame(Game aGame)
  {
    boolean wasRemoved = false;
    // List<Game> games = getGames_Game();
    if (getGames().contains(aGame))
    {
      wasRemoved = super.removeGame(aGame);
    }
    return wasRemoved;
  }

  public void delete()
  {
    promotions.clear();
    clear_games();
    super.delete();
  }

}