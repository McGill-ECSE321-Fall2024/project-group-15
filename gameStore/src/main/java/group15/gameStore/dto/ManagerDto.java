package group15.gameStore.dto;

import group15.gameStore.model.Manager;

public class ManagerDto extends EmployeeDto {

    @SuppressWarnings("unused")
    private ManagerDto(){
    }

    //Constructor
    public ManagerDto(Manager manager){
        super(manager);
    }

    public ManagerDto(String aUsername, String aPassword, String aEmail, boolean aIsActive, boolean aIsManager){
        super(aUsername, aPassword, aEmail, aIsActive, aIsManager);
    }

    
}
