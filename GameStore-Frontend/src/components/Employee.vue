<template>
    <div class="employee-management-page">
        <NavBar />
        <div v-if="!isVerified" class="verification-section">
        <h1>Employee Management Access</h1>
        <p>Please enter your Manager ID to access this page:</p>
        <input
            type="number"
            v-model="employeeId"
            placeholder="Enter your Manager ID"
            required
        />
        <button @click="verifyManager" class="verify-button">Verify</button>
        <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
        </div>
        <div v-else>
      <h1>Employee Management</h1>
  
      <div class="centered-management-container">
    <!-- Employee and Manager Sections -->
    <div class="management-container">
        <!-- Add Employee Section -->
        <div class="employee-section">
        <h2>Add Employee</h2>
        <form @submit.prevent="addEmployee">
            <input type="text" v-model="newEmployee.username" placeholder="Enter username" required />
            <input type="password" v-model="newEmployee.password" placeholder="Enter password" required />
            <input type="email" v-model="newEmployee.email" placeholder="Enter email" required />
            <label>
            <span>Is Active</span>
            <input type="checkbox" v-model="newEmployee.isActive" />
            </label>
            <button type="submit" class="submit-button">Add Employee</button>
        </form>
        </div>

        <!-- Add Manager Section -->
        <div class="manager-section">
        <h2>Add Manager</h2>
        <form @submit.prevent="addManager">
            <input type="text" v-model="newManager.username" placeholder="Enter username" required />
            <input type="password" v-model="newManager.password" placeholder="Enter password" required />
            <input type="email" v-model="newManager.email" placeholder="Enter email" required />
            <label>
            <span>Is Active</span>
            <input type="checkbox" v-model="newManager.isActive" />
            </label>
            <button type="submit" class="submit-button">Add Manager</button>
        </form>
        </div>
    </div>

    <!-- Update and Delete Employee Sections -->
    <div class="management-container">
        <!-- Update Employee Section -->
        <div class="employee-section">
        <h2>Update Employee</h2>
        <form @submit.prevent="updateEmployeeById">
            <input type="number" v-model="employeeToUpdateId" placeholder="Enter Employee ID" required />
            <button @click="fetchEmployeeById" type="button" class="confirm-button">Confirm ID</button>
            <div v-if="selectedEmployee.username">
            <input type="text" v-model="selectedEmployee.username" placeholder="Enter username" required />
            <input type="password" v-model="selectedEmployee.password" placeholder="Enter password" required />
            <input type="email" v-model="selectedEmployee.email" placeholder="Enter email" required />
            <label>
                <span>Is Active</span>
                <input type="checkbox" v-model="selectedEmployee.isActive" />
            </label>
            <div class="button-container">
            <button type="submit" class="submit-button">Update Employee</button>
            </div>
        </div>
        </form>
        </div>

        <!-- Delete Employee Section -->
        <div class="employee-section">
        <h2>Delete Employee</h2>
        <form @submit.prevent="deleteEmployeeById">
            <input type="number" v-model="employeeToDeleteId" placeholder="Enter Employee ID" required />
            <button type="submit" class="delete-button">Delete Employee</button>
        </form>
        </div>
    </div>

    <!-- Employee List -->
    <div class="employee-list">
    <h2>All Employees</h2>
    <div class="button-container">
        <button @click="toggleViewEmployees">
        {{ showAllEmployees ? "Hide All Employees" : "View All Employees" }}
        </button>
    </div>

    <ul v-if="showAllEmployees && employees.length > 0">
        <li v-for="employee in employees" :key="employee.id">
        ID: {{ employee.userID }} | Username: {{ employee.username || "No username" }} | 
        {{ employee.isManager ? "Manager" : "Staff" }} | ({{ employee.isActive ? "Active" : "Not Active" }})
        </li>
    </ul>

    <p v-if="showAllEmployees && employees.length === 0">
        No employees found in the system.
    </p>
    </div>
    </div>
    </div>
    </div>
  </template>

<script>
import NavBar from "./NavBar.vue";
import axiosClient from "./axios";

export default {
  name: "EmployeeManagement",
  data() {
    return {
        isVerified: false, 
        employeeId: null, 
        errorMessage: "",
      
        employees: [], // List of all employees
        showAllEmployees: false,
      newEmployee: {
        username: "",
        password: "",
        email: "",
        isActive: true,
        isManager: false,
      },

      newManager: {
        username: "",
        password: "",
        email: "",
        isActive: true,
      },

      selectedEmployee: {
        userID: 0,
        username: "",
        password: "",
        email: "",
        isActive: true,
        isManager: false,
      }, // Employee selected for update
      employeeToUpdateId: null,
      employeeToDeleteId: null,
    };
  },
  created() {
    this.fetchEmployees();
  },
  methods: {

    toggleViewEmployees() {
      this.showAllEmployees = !this.showAllEmployees;
    },

    async verifyManager() {
      if (!this.employeeId) {
        this.errorMessage = "Please enter a valid Employee ID.";
        return;
      }

      try {
        const response = await axiosClient.get(`/employee/id/${this.employeeId}`);
        const employee = response.data;

        if (employee.isManager) {
          this.isVerified = true;
          this.errorMessage = "";
        } else {
          this.errorMessage = "Access denied. You are not a manager.";
        }
      } catch (error) {
        if (error.response && error.response.status === 404) {
          this.errorMessage = "Please enter a valid Employee ID.";
        } else {
          this.errorMessage = "An error occurred. Please try again.";
          console.error("Error verifying manager:", error.response || error);
        }
        this.isVerified = false;
      }
    },
    
    async fetchEmployees() {
      try {
        const response = await axiosClient.get("/employees");
        this.employees = Array.isArray(response.data) ? response.data : [];
      } catch (error) {
        console.error("Error fetching employees:", error.response || error);
        alert("Failed to load employees.");
      }
    },

    async fetchEmployeeById() {
        if (!this.employeeToUpdateId) {
            alert("Please enter a valid Employee ID.");
            return;
        }

        try {
            const response = await axiosClient.get(`/employee/id/${this.employeeToUpdateId}`);
            // Populate form with employee data
            this.selectedEmployee = { 
              userID: response.data.userID,
              username: response.data.username,
              password: response.data.password,
              email: response.data.email,
              isActive: !!response.data.isActive,  // Ensure boolean
              isManager: !!response.data.isManager // Ensure boolean
            };
        } catch (error) {
            console.error("Error fetching employee by ID:", error.response || error);
            alert(
                error.response?.data?.message ||
                "An error occurred while trying to fetch the employee."
            );
        }
    },

    async addEmployee() {
      try {
        if (this.newEmployee.isManager === true) {
          const response = await axiosClient.post("/manager", this.newEmployee);
          this.employees.push(response.data); // Add new employee to the list
        }
        else {
          const response = await axiosClient.post("/employee", this.newEmployee);
          this.employees.push(response.data); // Add new employee to the list
        }
        this.resetNewEmployee();
        alert("Employee added successfully!");
      } catch (error) {
        console.error("Error adding employee:", error.response || error);
        alert("Failed to add employee.");
      }
    },

    async addManager() {
      try {
        const response = await axiosClient.post("/manager", this.newManager);
        this.employees.push(response.data);
        this.resetNewManager();
        alert("Manager added successfully!");
      } catch (error) {
        console.error("Error adding manager:", error.response || error);
        alert("Failed to add manager.");
      }
    },

    async updateEmployeeById() {
        if (!this.selectedEmployee) {
            alert("No employee selected for update.");
            return;
        }
        try {

            if (this.selectedEmployee.isManager === true) {
              await axiosClient.put(`/manager/${this.selectedEmployee.userID}`,this.selectedEmployee);
            }
            else {
              await axiosClient.put(`/employee/update/${this.selectedEmployee.userID}`,this.selectedEmployee);
            }
            alert("Employee updated successfully.");
            this.selectedEmployee.username = ""; // Clear form
        } catch (error) {
            console.error("Error updating employee:", error.response || error);
            alert(
                error.response?.data?.message ||
                "An error occurred while trying to update the employee."
            );
        }
    },   
    async deleteEmployeeById() {
        if (!this.employeeToDeleteId) {
            alert("Please enter a valid Employee ID.");
            return;
        }

        try {
            await axiosClient.delete(`/employee/delete/${this.employeeToDeleteId}`);
            this.employees = this.employees.filter(
                (employee) => employee.id !== parseInt(this.employeeToDeleteId)
            );
            alert("Employee deleted successfully.");
            this.employeeToDeleteId = null; // Reset input
        } catch (error) {
            console.error("Error deleting employee:", error.response || error);
            alert(
                error.response?.data?.message ||
                "An error occurred while trying to delete the employee."
            );
        }
    },

    resetNewEmployee() {
      this.newEmployee = {
        password: "",
        email: "",
        username: "",
        isManager: false,
        isActive: false,
      };
    },

    resetNewManager() {
      this.newManager = {
        username: "",
        password: "",
        email: "",
        isManager: true,
        isActive: true,
      };
    },
  },
};
</script>

<style scoped>
.verification-section {
  max-width: 500px;
  margin: auto;
  background-color: white;
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.error-message {
  color: red;
  margin-top: 10px;
  font-size: 14px;
}

.verify-button {
  background-color: #28a745;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
  margin-top: 10px
}

.verify-button:hover {
  background-color: #218838;
}

.centered-management-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 40px; /* Adds spacing between sections */
  padding: 20px;
  margin: auto;
  max-width: 1200px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}

.management-container {
  display: flex;
  justify-content: center;
  gap: 40px;
  margin-top: 20px;
}

.employee-management-page {
  padding: 70px;
  background-color: #f5f5f5;
  font-family: Arial, sans-serif;
}

h1 {
  color: #333;
  text-align: center;
}

h2 {
  font-size: 30px;
    color: #333;
    padding: 20px;
  text-align: center;
}

.employee-section,
.manager-section {
  background-color: #fff;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.15);
  width: 400px;
  margin-bottom: 20px;
}

.employee-section h2 {
  font-size: 1.8rem;
  margin-bottom: 20px;
}

.employee-section p {
  color: #333; 
  font-size: 16px; 
  text-align: center; 
  margin-top: 10px; 
}

form {
  display: flex;
  flex-direction: column;
  gap: 10px;
  align-items: center;
}

input, select, label {
  width: 85%;
  max-width: 400px;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

input:focus, select:focus {
  border-color: #007bff;
  outline: none;
  box-shadow: 0px 0px 5px rgba(0, 123, 255, 0.5);
}

button {
  background-color: #007bff;
  color: white;
  padding: 10px 20px;
  font-size: 16px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  margin-top: 10px;
}

button:hover {
  background-color: #0056b3;
}

.button-container {
  display: flex;
  justify-content: center;
  margin-bottom: 10px;
}

.delete-section {
  display: flex;
  align-items: center;
  gap: 10px; 
}

.delete-button {
  background-color: #dc3545;
  color: white;
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
  margin-left: auto; 
  transition: background-color 0.3s;
}

.delete-button:hover {
  background-color: #a71d2a;
}

.employee-list {
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.15);
  margin-top: 30px;
}

.employee-list h2 {
  margin-bottom: 10px;
}

.employee-list ul {
  list-style: none;
  padding: 0;
}

.employee-list li {
  padding: 5px 0;
  border-bottom: 1px solid #ccc;
  color: #333;
}

.employee-list li:last-child {
  border-bottom: none;
}

label {
  display: flex;
  align-items: center;
  gap: 5px; /* Adds space between text and checkbox */
  font-size: 16px; /* Matches other input field font sizes */
  color: #333; /* Matches the text color */
}

p {
  color: #444; 
}

</style>

