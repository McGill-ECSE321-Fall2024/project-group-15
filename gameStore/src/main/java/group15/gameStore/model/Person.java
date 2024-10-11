/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package group15.gameStore.model;

import jakarta.persistence.*;
import java.util.*;

// line 2 "model.ump"
// line 127 "model.ump"
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
// design decision: to create table for the bottom level classes
public class Person
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, Person> personsByUserID = new HashMap<Integer, Person>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Person Attributes
  @Id
  private int userID;
  private String username;
  private String password;
  private String email;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Person(int aUserID, String aUsername, String aPassword, String aEmail)
  {
    username = aUsername;
    password = aPassword;
    email = aEmail;
    if (!setUserID(aUserID))
    {
      throw new RuntimeException("Cannot create due to duplicate userID. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUserID(int aUserID)
  {
    boolean wasSet = false;
    int anOldUserID = getUserID();
    if (anOldUserID == aUserID) {
      return true;
    }
    if (hasWithUserID(aUserID)) {
      return wasSet;
    }
    userID = aUserID;
    wasSet = true;
    personsByUserID.remove(anOldUserID);
    personsByUserID.put(aUserID, this);
    return wasSet;
  }

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    username = aUsername;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public int getUserID()
  {
    return userID;
  }
  /* Code from template attribute_GetUnique */
  public static Person getWithUserID(int aUserID)
  {
    return personsByUserID.get(aUserID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithUserID(int aUserID)
  {
    return getWithUserID(aUserID) != null;
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }

  public String getEmail()
  {
    return email;
  }

  public void delete()
  {
    personsByUserID.remove(getUserID());
  }


  public String toString()
  {
    return super.toString() + "["+
            "userID" + ":" + getUserID()+ "," +
            "username" + ":" + getUsername()+ "," +
            "password" + ":" + getPassword()+ "," +
            "email" + ":" + getEmail()+ "]";
  }
}