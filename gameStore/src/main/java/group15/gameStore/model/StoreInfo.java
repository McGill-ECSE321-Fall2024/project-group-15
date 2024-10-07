/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 77 "model.ump"
// line 168 "model.ump"
public class StoreInfo
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<int, StoreInfo> storeinfosByStoreInfoID = new HashMap<int, StoreInfo>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //StoreInfo Attributes
  private int storeInfoID;
  private String storePolicies;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public StoreInfo(int aStoreInfoID, String aStorePolicies)
  {
    storePolicies = aStorePolicies;
    if (!setStoreInfoID(aStoreInfoID))
    {
      throw new RuntimeException("Cannot create due to duplicate storeInfoID. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStoreInfoID(int aStoreInfoID)
  {
    boolean wasSet = false;
    int anOldStoreInfoID = getStoreInfoID();
    if (anOldStoreInfoID != null && anOldStoreInfoID.equals(aStoreInfoID)) {
      return true;
    }
    if (hasWithStoreInfoID(aStoreInfoID)) {
      return wasSet;
    }
    storeInfoID = aStoreInfoID;
    wasSet = true;
    if (anOldStoreInfoID != null) {
      storeinfosByStoreInfoID.remove(anOldStoreInfoID);
    }
    storeinfosByStoreInfoID.put(aStoreInfoID, this);
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
  /* Code from template attribute_GetUnique */
  public static StoreInfo getWithStoreInfoID(int aStoreInfoID)
  {
    return storeinfosByStoreInfoID.get(aStoreInfoID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithStoreInfoID(int aStoreInfoID)
  {
    return getWithStoreInfoID(aStoreInfoID) != null;
  }

  public String getStorePolicies()
  {
    return storePolicies;
  }

  public void delete()
  {
    storeinfosByStoreInfoID.remove(getStoreInfoID());
  }


  public String toString()
  {
    return super.toString() + "["+
            "storeInfoID" + ":" + getStoreInfoID()+ "," +
            "storePolicies" + ":" + getStorePolicies()+ "]";
  }
}