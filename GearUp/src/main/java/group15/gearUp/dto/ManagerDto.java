package group15.gearUp.dto;

import group15.gearUp.model.Employee;
import group15.gearUp.model.Manager;

public class ManagerDto extends EmployeeDto {

    //private Employee employee;

    @SuppressWarnings("unused")
    public ManagerDto(){
    }

    //Constructor
    //public ManagerDto(Manager manager, Employee employee){
    public ManagerDto(Manager manager){
        super(manager);
        //this.employee = employee;

    }

    //public ManagerDto(String aUsername, String aPassword, String aEmail, boolean aIsActive, boolean aIsManager, Employee employee){
    public ManagerDto(String username, String password, String email, boolean isActive, boolean isManager){
        super(username, password, email, isActive, isManager);
        //this.employee = employee;
    }

    /*public Employee getEmployee() {
        return employee;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }*/

    
}
