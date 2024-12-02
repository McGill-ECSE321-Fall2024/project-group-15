<template>
    <div class="employee-management-page">
      <h1>Employee Management</h1>
  
     <!-- Add Employee Section -->
    <div class="employee-section">
    <h2>Add Employee</h2>
    <form @submit.prevent="addEmployee">
        <input
        type="text"
        v-model="newEmployee.username"
        placeholder="Enter username"
        required
        />
        <input
        type="password"
        v-model="newEmployee.password"
        placeholder="Enter password"
        required
        />
        <input
        type="email"
        v-model="newEmployee.email"
        placeholder="Enter email"
        required
        />
        <label>
        <span>Is a Manager</span>
        <input type="checkbox" v-model="newEmployee.isManager" />
        </label>
        <label>
        <span>Is Active</span>
        <input type="checkbox" v-model="newEmployee.isActive" />
        </label>
        <button type="submit" class="submit-button">Add Employee</button>
    </form>
    </div>

    <!-- Update Employee Section -->
    <div class="employee-section">
    <h2>Update Employee</h2>
    <div>
        <select v-model="selectedEmployeeId" required>
        <option disabled value="">Select an Employee</option>
        <option v-for="employee in employees" :key="employee.id" :value="employee.id">
            {{ employee.username }}
        </option>
        </select>
        <button @click="confirmSelection" class="confirm-button">Confirm</button>
    </div>

    <form @submit.prevent="updateEmployee" v-if="selectedEmployee">
        <input
        type="text"
        v-model="selectedEmployee.username"
        placeholder="Enter username"
        required
        />
        <input
        type="password"
        v-model="selectedEmployee.password"
        placeholder="Enter password"
        required
        />
        <input
        type="email"
        v-model="selectedEmployee.email"
        placeholder="Enter email"
        required
        />
        <label>
        <span>Is a Manager</span>
        <input type="checkbox" v-model="selectedEmployee.isManager" />
        </label>
        <label>
        <span>Is Active</span>
        <input type="checkbox" v-model="selectedEmployee.isActive" />
        </label>
        <button type="submit" class="submit-button">Update Employee</button>
    </form>

    <p v-else>Please select an employee and click Confirm to update.</p>
    </div>




  
      <!-- Delete Employee Section -->
      <div class="employee-section">
        <h2>Delete Employee</h2>
        <select v-model="employeeToDelete" required>
          <option v-for="employee in employees" :key="employee.id" :value="employee.id">
            {{ employee.username }} - 
            {{ employee.isManager ? "Manager" : "Staff" }} 
          </option>
        </select>
        <button @click="deleteEmployee" class="delete-button">Delete Employee</button>
      </div>
  
      <!-- Employee List -->
      <div class="employee-list">
        <h2>All Employees</h2>
        <ul v-if="employees.length > 0">
            <li v-for="employee in employees" :key="employee.id">
             {{ employee.username || "No username" }} - 
            {{ employee.isManager ? "Manager" : "Staff" }} - ({{ employee.isActive ? "Active" : "Not Active" }})
            </li>
        </ul>
        <p v-else>No employees found in the system.</p>
        </div>
    </div>
  </template>

<script>
import axiosClient from "./axios";

export default {
  name: "EmployeeManagement",
  data() {
    return {
      employees: [], // List of all employees
      newEmployee: {
        name: "",
        email: "",
        username: "",
        isManager: false,
        isActive: false,
      },
      selectedEmployee: null, // Employee selected for update
      employeeToDelete: null, // Employee ID to delete
    };
  },
  created() {
    this.fetchEmployees();
  },
  methods: {
    async fetchEmployees() {
      try {
        const response = await axiosClient.get("/employees");
        this.employees = Array.isArray(response.data) ? response.data : [];
      } catch (error) {
        console.error("Error fetching employees:", error.response || error);
        alert("Failed to load employees.");
      }
    },
    async addEmployee() {
      try {
        const response = await axiosClient.post("/employee", this.newEmployee);
        this.employees.push(response.data); // Add new employee to the list
        this.resetNewEmployee();
        alert("Employee added successfully!");
      } catch (error) {
        console.error("Error adding employee:", error.response || error);
        alert("Failed to add employee.");
      }
    },
    confirmSelection() {
    // Convert both values to strings for comparison consistency
    const employee = this.employees.find(
        (emp) => String(emp.id) === String(this.selectedEmployeeId)
    );

    if (employee) {
        this.selectedEmployee = JSON.parse(JSON.stringify(employee)); // Deep copy to avoid mutation
    } else {
        alert("Please select a valid employee.");
    }
    },

  async updateEmployee() {
    if (!this.selectedEmployee) {
      alert("Please select an employee to update.");
      return;
    }
    try {
      await axiosClient.put(`/employee/update/${this.selectedEmployee.id}`, this.selectedEmployee);
      alert("Employee updated successfully!");
      // Refresh employees list after update
      this.fetchEmployees();
    } catch (error) {
      console.error("Error updating employee:", error.response || error);
      alert("Failed to update employee.");
    }
  },
    async deleteEmployee() {
      if (!this.employeeToDelete) {
        alert("Please select an employee to delete.");
        return;
      }
      try {
        await axiosClient.delete(`/api/employee/${this.employeeToDelete}`);
        this.employees = this.employees.filter(
          (employee) => employee.id !== this.employeeToDelete
        );
        this.employeeToDelete = null;
        alert("Employee deleted successfully!");
      } catch (error) {
        console.error("Error deleting employee:", error.response || error);
        alert("Failed to delete employee.");
      }
    },
    resetNewEmployee() {
      this.newEmployee = {
        name: "",
        email: "",
        username: "",
        isManager: false,
      };
    },
  },
};
</script>

<style scoped>
.employee-management-page {
  padding: 70px;
  background-color: #f5f5f5;
  font-family: Arial, sans-serif;
}

h1, h2 {
  color: #333;
  text-align: center;
}

.employee-section {
  background-color: #fff;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.15);
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
}

button:hover {
  background-color: #0056b3;
}

.delete-button {
  background-color: #dc3545;
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

</style>

