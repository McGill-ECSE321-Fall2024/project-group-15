package group15.gameStore.RequestDto;

public class PersonRequestDto {
     //Person Attributes
     private String username;
     private String password;
     private String email;
 
   @SuppressWarnings("unused")
  private PersonRequestDto(){
  } 

  public PersonRequestDto(String aUsername, String aPassword, String aEmail){
    this.username = aUsername;
    this.password = aPassword;
    this.email = aEmail;
 }



  //Generated Getters and Setters
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
