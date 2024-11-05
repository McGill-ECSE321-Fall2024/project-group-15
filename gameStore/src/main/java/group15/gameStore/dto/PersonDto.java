package main.java.group15.gameStore.dto;

import group15.gameStore.model.Person;

public class PersonDto{

    //Person Attributes
    private int userID;
    private String username;
    private String password;
    private String email;

  @SuppressWarnings("unused")
 private PersonDto(){
 } 

 public PersonDto(Person personDto){
    this.userID = personDto.getUserID();
    this.username = personDto.getUsername();
    this.password = personDto.getPassword();
    this.email = personDto.getEmail();
 }

 public PersonDto(String aUsername, String aPassword, String aEmail){
    this.username = aUsername;
    this.password = aPassword;
    this.email = aEmail;
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

public String getPassword() {
    return password;
}

public void setPassword(String password) {
    this.password = password;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}
 
}