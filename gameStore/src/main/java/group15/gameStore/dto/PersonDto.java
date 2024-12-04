package group15.gameStore.dto;

import group15.gameStore.model.Person;

public class PersonDto{

    //Person Attributes
    public int userID;
    public String username;
    public String password;
    public String email;

  @SuppressWarnings("unused")
    public PersonDto(){
    } 

//Constructor
 public PersonDto(Person personDto){
    this.userID = personDto.getUserID();
    this.username = personDto.getUsername();
    this.password = personDto.getPassword();
    this.email = personDto.getEmail();
 }

 public PersonDto(String username, String password, String email){
    this.username = username;
    this.password = password;
    this.email = email;
 }

 //Generated Getters and Setters
 public int getUserID() {
    return userID; 
    }

public void setUserID(int userID) {
    this.userID = userID;
    }

public String getUsername() {
    return username;
    }

public void setUsername(String username) {
    this.username = username;
    }

public String getEmail() {
    return email;
    }

public void setEmail(String email) {
    this.email = email;
    }

public String getPassword() {
    return password;
    }

public void setPassword(String password) {
    this.password = password;
    }
}