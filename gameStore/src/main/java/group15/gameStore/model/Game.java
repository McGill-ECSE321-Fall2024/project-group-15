/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 23 "model.ump"
// line 152 "model.ump"
public class Game
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Attributes
  private int gameID;
  private String title;
  private String description;
  private String categoryName;
  private double price;
  private int stock;
  private String image;
  private boolean archived;
  private boolean managerApproval;

  //Game Associations
  private Employee employee;
  private Wishlist wishlist;
  private Order order;
  private List<Cart> carts;
  private List<Reviews> reviews;
  private Category category;
  private GameArchive gameArchive;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game(int aGameID, String aTitle, String aDescription, String aCategoryName, double aPrice, int aStock, String aImage, boolean aArchived, boolean aManagerApproval, Employee aEmployee, Wishlist aWishlist, Order aOrder, Category aCategory, GameArchive aGameArchive)
  {
    gameID = aGameID;
    title = aTitle;
    description = aDescription;
    categoryName = aCategoryName;
    price = aPrice;
    stock = aStock;
    image = aImage;
    archived = aArchived;
    managerApproval = aManagerApproval;
    boolean didAddEmployee = setEmployee(aEmployee);
    if (!didAddEmployee)
    {
      throw new RuntimeException("Unable to create game due to employee. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddWishlist = setWishlist(aWishlist);
    if (!didAddWishlist)
    {
      throw new RuntimeException("Unable to create game due to wishlist. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddOrder = setOrder(aOrder);
    if (!didAddOrder)
    {
      throw new RuntimeException("Unable to create game due to order. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    carts = new ArrayList<Cart>();
    reviews = new ArrayList<Reviews>();
    boolean didAddCategory = setCategory(aCategory);
    if (!didAddCategory)
    {
      throw new RuntimeException("Unable to create game due to category. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddGameArchive = setGameArchive(aGameArchive);
    if (!didAddGameArchive)
    {
      throw new RuntimeException("Unable to create game due to gameArchive. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setGameID(int aGameID)
  {
    boolean wasSet = false;
    gameID = aGameID;
    wasSet = true;
    return wasSet;
  }

  public boolean setTitle(String aTitle)
  {
    boolean wasSet = false;
    title = aTitle;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setCategoryName(String aCategoryName)
  {
    boolean wasSet = false;
    categoryName = aCategoryName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPrice(double aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setStock(int aStock)
  {
    boolean wasSet = false;
    stock = aStock;
    wasSet = true;
    return wasSet;
  }

  public boolean setImage(String aImage)
  {
    boolean wasSet = false;
    image = aImage;
    wasSet = true;
    return wasSet;
  }

  public boolean setArchived(boolean aArchived)
  {
    boolean wasSet = false;
    archived = aArchived;
    wasSet = true;
    return wasSet;
  }

  public boolean setManagerApproval(boolean aManagerApproval)
  {
    boolean wasSet = false;
    managerApproval = aManagerApproval;
    wasSet = true;
    return wasSet;
  }

  public int getGameID()
  {
    return gameID;
  }

  public String getTitle()
  {
    return title;
  }

  public String getDescription()
  {
    return description;
  }

  public String getCategoryName()
  {
    return categoryName;
  }

  public double getPrice()
  {
    return price;
  }

  public int getStock()
  {
    return stock;
  }

  public String getImage()
  {
    return image;
  }

  public boolean getArchived()
  {
    return archived;
  }

  public boolean getManagerApproval()
  {
    return managerApproval;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isArchived()
  {
    return archived;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isManagerApproval()
  {
    return managerApproval;
  }
  /* Code from template association_GetOne */
  public Employee getEmployee()
  {
    return employee;
  }
  /* Code from template association_GetOne */
  public Wishlist getWishlist()
  {
    return wishlist;
  }
  /* Code from template association_GetOne */
  public Order getOrder()
  {
    return order;
  }
  /* Code from template association_GetMany */
  public Cart getCart(int index)
  {
    Cart aCart = carts.get(index);
    return aCart;
  }

  public List<Cart> getCarts()
  {
    List<Cart> newCarts = Collections.unmodifiableList(carts);
    return newCarts;
  }

  public int numberOfCarts()
  {
    int number = carts.size();
    return number;
  }

  public boolean hasCarts()
  {
    boolean has = carts.size() > 0;
    return has;
  }

  public int indexOfCart(Cart aCart)
  {
    int index = carts.indexOf(aCart);
    return index;
  }
  /* Code from template association_GetMany */
  public Reviews getReview(int index)
  {
    Reviews aReview = reviews.get(index);
    return aReview;
  }

  public List<Reviews> getReviews()
  {
    List<Reviews> newReviews = Collections.unmodifiableList(reviews);
    return newReviews;
  }

  public int numberOfReviews()
  {
    int number = reviews.size();
    return number;
  }

  public boolean hasReviews()
  {
    boolean has = reviews.size() > 0;
    return has;
  }

  public int indexOfReview(Reviews aReview)
  {
    int index = reviews.indexOf(aReview);
    return index;
  }
  /* Code from template association_GetOne */
  public Category getCategory()
  {
    return category;
  }
  /* Code from template association_GetOne */
  public GameArchive getGameArchive()
  {
    return gameArchive;
  }
  /* Code from template association_SetOneToMany */
  public boolean setEmployee(Employee aEmployee)
  {
    boolean wasSet = false;
    if (aEmployee == null)
    {
      return wasSet;
    }

    Employee existingEmployee = employee;
    employee = aEmployee;
    if (existingEmployee != null && !existingEmployee.equals(aEmployee))
    {
      existingEmployee.removeGame(this);
    }
    employee.addGame(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setWishlist(Wishlist aWishlist)
  {
    boolean wasSet = false;
    if (aWishlist == null)
    {
      return wasSet;
    }

    Wishlist existingWishlist = wishlist;
    wishlist = aWishlist;
    if (existingWishlist != null && !existingWishlist.equals(aWishlist))
    {
      existingWishlist.removeGame(this);
    }
    wishlist.addGame(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setOrder(Order aOrder)
  {
    boolean wasSet = false;
    if (aOrder == null)
    {
      return wasSet;
    }

    Order existingOrder = order;
    order = aOrder;
    if (existingOrder != null && !existingOrder.equals(aOrder))
    {
      existingOrder.removeGame(this);
    }
    order.addGame(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCarts()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addCart(Cart aCart)
  {
    boolean wasAdded = false;
    if (carts.contains(aCart)) { return false; }
    carts.add(aCart);
    if (aCart.indexOfGame(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aCart.addGame(this);
      if (!wasAdded)
      {
        carts.remove(aCart);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeCart(Cart aCart)
  {
    boolean wasRemoved = false;
    if (!carts.contains(aCart))
    {
      return wasRemoved;
    }

    int oldIndex = carts.indexOf(aCart);
    carts.remove(oldIndex);
    if (aCart.indexOfGame(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aCart.removeGame(this);
      if (!wasRemoved)
      {
        carts.add(oldIndex,aCart);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCartAt(Cart aCart, int index)
  {  
    boolean wasAdded = false;
    if(addCart(aCart))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCarts()) { index = numberOfCarts() - 1; }
      carts.remove(aCart);
      carts.add(index, aCart);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCartAt(Cart aCart, int index)
  {
    boolean wasAdded = false;
    if(carts.contains(aCart))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCarts()) { index = numberOfCarts() - 1; }
      carts.remove(aCart);
      carts.add(index, aCart);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCartAt(aCart, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReviews()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Reviews addReview(int aReviewID, rating aRating, String aDescription, Customer aCustomerDetails, Customer aCustomer)
  {
    return new Reviews(aReviewID, aRating, aDescription, aCustomerDetails, this, aCustomer);
  }

  public boolean addReview(Reviews aReview)
  {
    boolean wasAdded = false;
    if (reviews.contains(aReview)) { return false; }
    Game existingGame = aReview.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);
    if (isNewGame)
    {
      aReview.setGame(this);
    }
    else
    {
      reviews.add(aReview);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeReview(Reviews aReview)
  {
    boolean wasRemoved = false;
    //Unable to remove aReview, as it must always have a game
    if (!this.equals(aReview.getGame()))
    {
      reviews.remove(aReview);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addReviewAt(Reviews aReview, int index)
  {  
    boolean wasAdded = false;
    if(addReview(aReview))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReviews()) { index = numberOfReviews() - 1; }
      reviews.remove(aReview);
      reviews.add(index, aReview);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveReviewAt(Reviews aReview, int index)
  {
    boolean wasAdded = false;
    if(reviews.contains(aReview))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReviews()) { index = numberOfReviews() - 1; }
      reviews.remove(aReview);
      reviews.add(index, aReview);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addReviewAt(aReview, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCategory(Category aCategory)
  {
    boolean wasSet = false;
    if (aCategory == null)
    {
      return wasSet;
    }

    Category existingCategory = category;
    category = aCategory;
    if (existingCategory != null && !existingCategory.equals(aCategory))
    {
      existingCategory.removeGame(this);
    }
    category.addGame(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setGameArchive(GameArchive aGameArchive)
  {
    boolean wasSet = false;
    if (aGameArchive == null)
    {
      return wasSet;
    }

    GameArchive existingGameArchive = gameArchive;
    gameArchive = aGameArchive;
    if (existingGameArchive != null && !existingGameArchive.equals(aGameArchive))
    {
      existingGameArchive.removeGame(this);
    }
    gameArchive.addGame(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Employee placeholderEmployee = employee;
    this.employee = null;
    if(placeholderEmployee != null)
    {
      placeholderEmployee.removeGame(this);
    }
    Wishlist placeholderWishlist = wishlist;
    this.wishlist = null;
    if(placeholderWishlist != null)
    {
      placeholderWishlist.removeGame(this);
    }
    Order placeholderOrder = order;
    this.order = null;
    if(placeholderOrder != null)
    {
      placeholderOrder.removeGame(this);
    }
    ArrayList<Cart> copyOfCarts = new ArrayList<Cart>(carts);
    carts.clear();
    for(Cart aCart : copyOfCarts)
    {
      aCart.removeGame(this);
    }
    for(int i=reviews.size(); i > 0; i--)
    {
      Reviews aReview = reviews.get(i - 1);
      aReview.delete();
    }
    Category placeholderCategory = category;
    this.category = null;
    if(placeholderCategory != null)
    {
      placeholderCategory.removeGame(this);
    }
    GameArchive placeholderGameArchive = gameArchive;
    this.gameArchive = null;
    if(placeholderGameArchive != null)
    {
      placeholderGameArchive.removeGame(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "gameID" + ":" + getGameID()+ "," +
            "title" + ":" + getTitle()+ "," +
            "description" + ":" + getDescription()+ "," +
            "categoryName" + ":" + getCategoryName()+ "," +
            "price" + ":" + getPrice()+ "," +
            "stock" + ":" + getStock()+ "," +
            "image" + ":" + getImage()+ "," +
            "archived" + ":" + getArchived()+ "," +
            "managerApproval" + ":" + getManagerApproval()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "employee = "+(getEmployee()!=null?Integer.toHexString(System.identityHashCode(getEmployee())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "wishlist = "+(getWishlist()!=null?Integer.toHexString(System.identityHashCode(getWishlist())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "order = "+(getOrder()!=null?Integer.toHexString(System.identityHashCode(getOrder())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "category = "+(getCategory()!=null?Integer.toHexString(System.identityHashCode(getCategory())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "gameArchive = "+(getGameArchive()!=null?Integer.toHexString(System.identityHashCode(getGameArchive())):"null");
  }
}