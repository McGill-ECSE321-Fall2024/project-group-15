package group15.gameStore.dto;

import java.sql.Date;

import group15.gameStore.model.Employee;
import group15.gameStore.model.Game;
import group15.gameStore.model.Manager;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class GameRequestDto {
    @NotBlank(message = "Game title is required")
    private String title;
    @NotBlank(message = "Game description is required")
    private String description;
    @NotNull(message = "Game price is required")
    @Min(value = 0, message = "The price must be non-negative")
    private double price;
    @Min(value = 0, message = "The stock must be non-negative")
    private int stock;
    @NotBlank(message = "Game image is required")
    private String image;
    private Date archivedDate;
    private boolean isApproved;
    @NotNull(message = "Game manager is required")
    private Manager manager;
    private Employee employee;
    private Game game;

    public GameRequestDto(String title, String description, double price, int stock, String image, Date archivedDate, boolean isApproved, Manager manager, Employee employee) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.image = image;
        this.archivedDate = archivedDate;
        this.isApproved = isApproved;
        this.manager = manager;
        this.employee = employee;
        this.game = new Game(title, description, price, stock, image, isApproved, manager);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getArchivedDate() {
        return archivedDate;
    }

    public void setArchivedDate(Date archivedDate) {
        this.archivedDate = archivedDate;
    }

    public boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
