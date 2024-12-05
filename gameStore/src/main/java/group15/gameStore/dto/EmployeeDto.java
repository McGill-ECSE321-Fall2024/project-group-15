package group15.gameStore.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import group15.gameStore.model.Employee;

public class EmployeeDto extends PersonDto {

    //Employee Attributes
    public boolean isActive;
    public boolean isManager;

    public List<CategoryDto> categories;
    public List<OrderDto> orders;
    public List<GameDto> games;
    public List<WishlistDto> wishlists;

    @SuppressWarnings("unused")
    public EmployeeDto(){
    }

    //Constructor
    public EmployeeDto(Employee employee) {
        super(employee);
        this.isActive = employee.getIsActive();
        this.isManager = employee.getIsManager();
        this.categories = employee.getCategories().stream().map(CategoryDto::new).collect(Collectors.toList());
        this.orders = employee.getOrders().stream().map(OrderDto::new).collect(Collectors.toList());
        this.games = employee.getGames().stream().map(GameDto::new).collect(Collectors.toList());
        this.wishlists = employee.getWishlists().stream().map(WishlistDto::new).collect(Collectors.toList());
    }

    public EmployeeDto(String username, String password, String email, boolean isActive, boolean isManager)
    {
      super(username, password, email);
      this.isActive = isActive;
      this.isManager = isManager;
      this.categories = new ArrayList<CategoryDto>();
      this.orders = new ArrayList<OrderDto>();
      this.games = new ArrayList<GameDto>();
      this.wishlists = new ArrayList<WishlistDto>();
    }

    //Generated Getters and Setters
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean isManager) {
        this.isManager = isManager;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }

    public List<OrderDto> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDto> orders) {
        this.orders = orders;
    }

    public List<GameDto> getGames() {
        return games;
    }

    public void setGames(List<GameDto> games) {
        this.games = games;
    }

    public List<WishlistDto> getWishlists() {
        return wishlists;
    }

    public void setWishlists(List<WishlistDto> wishlists) {
        this.wishlists = wishlists;
    }

    
}
