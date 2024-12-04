package group15.gameStore.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import group15.gameStore.model.Category;

public class CategoryDto {

    //Category Attributes
    private int categoryID;
    private String name;

    private List<GameDto> games;

    @SuppressWarnings("unused")
    public CategoryDto(){
    }

    //Constructor
    public CategoryDto(Category categoryDto){
        this.categoryID = categoryDto.getCategoryID();
        this.name = categoryDto.getName();
        this.games = categoryDto.getGames().stream().map(GameDto::new).collect(Collectors.toList());
    }

    public CategoryDto(String name){
        this.name = name;
        this.games = new ArrayList<GameDto>();;
    }

    //Generated Getters and Setters
    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GameDto> getGames() {
        return games;
    }

    public void setGames(List<GameDto> games) {
        this.games = games;
    }

    
}
