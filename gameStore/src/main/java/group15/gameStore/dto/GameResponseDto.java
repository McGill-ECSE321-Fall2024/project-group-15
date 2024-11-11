package group15.gameStore.dto;

import java.sql.Date;

import group15.gameStore.model.Game;
import group15.gameStore.model.Manager;

public class GameResponseDto {
    private int gameID;
    private String title;
    private String description;
    private double price;
    private int stock;
    private String image;
    private Date archivedDate;
    private boolean isApproved;
    private Manager manager;

    //Default constructor
    protected GameResponseDto() {}

    public GameResponseDto(Game game) {
        this.gameID = game.getGameID();
        this.title = game.getTitle();
        this.description = game.getDescription();
        this.price = game.getPrice();
        this.stock = game.getStock();
        this.image = game.getImage();
        this.archivedDate = game.getArchivedDate();
        this.isApproved = game.getIsApproved();
        this.manager = game.getManager();
    }

    public int getGameID() {
        return gameID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    
    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getImage() {
        return image;
    }

    public Date getArchivedDate() {
        return archivedDate;
    }

    public boolean getIsApproved() {
        return isApproved;
    }

    public Manager getManager() {
        return manager;
    }
}
