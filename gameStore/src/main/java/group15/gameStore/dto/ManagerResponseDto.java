package group15.gameStore.dto;

import group15.gameStore.model.Manager;

public class ManagerResponseDto {
    private String username;
    private String email;
    private boolean isActive;
    private boolean isManager;

    //Default constructor
    protected ManagerResponseDto() {}

    public ManagerResponseDto(Manager manager) {
        this.username = manager.getUsername();
        this.email = manager.getEmail();
        this.isActive = manager.getIsActive();
        this.isManager = manager.getIsManager();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public boolean getIsManager() {
        return isManager;
    }
}
