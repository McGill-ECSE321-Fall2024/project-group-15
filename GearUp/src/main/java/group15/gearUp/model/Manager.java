/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package group15.gearUp.model;

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
  public Manager() {
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
  public List<Equipment> getEquipments_Equipment()
  {
    List<? extends Equipment> newEquipments = super.getEquipments();
    return (List<Equipment>)newEquipments;
  }
  
  /* Code from template association_set_specialization_reqSuperCode */  /* Code from template association_MinimumNumberOfMethod_specialization */
  public static int minimumNumberOfEquipments_Equipment()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany_specialization */
  public boolean removeEquipment(Equipment aEquipment)
  {
    boolean wasRemoved = false;
    // List<Equipment> Equipments = getEquipments_Equipment();
    if (getEquipments().contains(aEquipment))
    {
      wasRemoved = super.removeEquipment(aEquipment);
    }
    return wasRemoved;
  }

  public void delete()
  {
    clear_Equipments();
    super.delete();
  }

}