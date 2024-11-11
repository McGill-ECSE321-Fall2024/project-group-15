package group15.gameStore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class ManagerRequestDto {
    @NotBlank(message = "Manager username is required")
    private String username;
    @NotBlank(message = "Manager password is required")
    private String password;
    @NotBlank(message = "Manager email is required")
    @Email(message = "Invalid email address")
    private String email;
    @NotNull(message = "Manager activity is required")
    private boolean isActive;
    @NotNull(message = "Manager status is required")
    private boolean isManager;

    public ManagerRequestDto(String username, String password, String email, boolean isActive, boolean isManager) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isActive = isActive;
        this.isManager = isManager;
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

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsManager() {
        return isManager;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }
}
