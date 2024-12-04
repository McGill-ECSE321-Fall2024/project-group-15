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
  }

  //------------------------
  // INTERFACE
  //------------------------
  
  /* required for Java 7. */
  @SuppressWarnings("unchecked")
  public List<Game> getGames_Game()
  {
    List<? extends Game> newGames = super.getGames();
    return (List<Game>)newGames;
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
    clear_games();
    super.delete();
  }

}