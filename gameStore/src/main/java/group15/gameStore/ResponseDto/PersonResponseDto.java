package main.java.group15.gameStore.dto;

import group15.gameStore.model.Person;

public class PersonResponseDto{

    //Person Attributes
    private int userID;
    private String username;
    private String email;

  @SuppressWarnings("unused")
 private PersonResponseDto(){
 } 

 public PersonResponseDto(Person personDto){
    this.userID = personDto.getUserID();
    this.username = personDto.getUsername();
    this.email = personDto.getEmail();
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
 
}