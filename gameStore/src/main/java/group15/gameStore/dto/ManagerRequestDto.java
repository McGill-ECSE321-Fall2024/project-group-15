package group15.gameStore.dto;

import jakarta.validation.constraints.NotBlank;
import group15.gameStore.model.Employee;
import group15.gameStore.model.Manager;
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
    private Manager manager;
    private Employee employee;

    public ManagerRequestDto(String username, String password, String email, boolean isActive, boolean isManager, Employee employee) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isActive = isActive;
        this.isManager = isManager;
        this.manager = new Manager(username, password, email, isActive, isManager);
        this.employee = employee;
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
}
