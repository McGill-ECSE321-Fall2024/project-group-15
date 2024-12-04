<template>
    <div class="staff-page">
      <NavBar />
      <div v-if="!isVerified" class="verification-section">
        <h1>Staff Access</h1>
        <p>Please enter your Employee ID to access this page:</p>
        <input
            type="number"
            v-model="employeeId"
            placeholder="Enter your Employee ID"
            required
        />
        <button @click="verifyEmployee" class="verify-button">Verify</button>
        <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
        </div>

    <div v-else class="content-section">
      <div class="header-actions">
        <button class="add-game-button" @click="addGame">Add Game</button>
        </div>
      <div class="management-container">
        <!-- Category Management Section -->
        <div class="category-section">
          <h2>Category Management</h2>
          <div class="search-section">
            <input
              type="text"
              v-model="categorySearchQuery"
              placeholder="Search for games by title..."
            />
            <button @click="searchGameForCategory" class="search-button">Search</button>
          </div>
  
          <!-- Game Search Result for Category -->
          <div v-if="selectedGameForCategory" class="game-search-result">
            <h3>{{ selectedGameForCategory.title }}</h3>
            <img :src="selectedGameForCategory.image" alt="Game Image" class="game-image" />
            <select v-model="selectedCategory" class="dropdown">
              <option value="">Select Category</option>
              <option v-for="category in categories" :key="category.categoryID" :value="category">
                {{ category.name }}
              </option>
            </select>
            <button @click="assignCategory" class="submit-button">Assign to Category</button>
          </div>
  
          <!-- Add Category Section -->
          <div class="add-category-section">
            <button @click="toggleAddCategory" class="add-category-button">Add Category</button>
            <div v-if="showAddCategoryInput" class="add-category-form">
              <input
                type="text"
                v-model="newCategory"
                placeholder="Enter category name..."
              />
              <button @click="addCategory" class="submit-button">Submit</button>
            </div>
          </div>
  
          <!-- Delete Category Button -->
          <div class="centered-buttons">
  <button @click="toggleDeleteCategory" class="delete-category-button">Delete Category</button>
        </div>

        <!-- Delete Category Section -->
        <div v-if="showDeleteCategorySection" class="delete-promotion-section">
        <h3>Delete Category</h3>
        <input
            type="number"
            v-model="deleteCategoryId"
            placeholder="Enter category ID..."
        />
        <button @click="deleteCategory" class="submit-button">Delete</button>
        </div>

        <div class="view-all-categories">
        <!-- Button to toggle category list -->
        <div class="view-categories-section">
        <button @click="toggleViewCategories" class="view-categories-button">
            {{ showAllCategories ? "Hide All Categories" : "View All Categories" }}
        </button>
        </div>

       <!-- Category List -->
        <div v-if="showAllCategories" class="categories-list">
            <h3>All Categories</h3>
            <ul>
            <li v-for="category in categories" :key="category.id" class="category-item">
                <span><strong>ID:</strong> {{ category.categoryID }}</span> | 
                <span><strong>Name:</strong> {{ category.name }}</span>
            </li>
            </ul>
        </div>
        </div>
        </div>
  
        <!-- Promotion Management Section -->
        <div class="promotion-section">
          <h2>Promotion Management</h2>
          <div class="centered-buttons">
            <button @click="toggleAddPromotion" class="add-promotion-button">Add Promotion</button>
            </div>
          <!-- Add Promotion Section -->
            <div v-if="showAddPromotionInput" class="add-promotion-section">
                <h3>Add a Promotion</h3>
                <div class="add-promotion-form">
                <input
                    type="text"
                    v-model="newPromotionCode"
                    placeholder="Enter promotion code..."
                    class="promotion-input"
                />
                <input
                    type="number"
                    v-model="newPromotionDiscount"
                    placeholder="Enter discount percentage..."
                    class="promotion-input"
                />
                <input
                    type="date"
                    v-model="newPromotionDateValidUntil"
                    class="promotion-input"
                />

                <!-- Search for a Game -->
                <div class="game-search-section">
                    <input
                    type="text"
                    v-model="promotionSearchQuery"
                    placeholder="Search for a game by title..."
                    class="game-search-input"
                    />
                    <button @click="searchGameForPromotion" class="search-button">Search</button>
                </div>

                <br>

                <!-- Game Search Result -->
                <div v-if="selectedGameForPromotion" class="game-search-result">
                    <h3>Selected Game: {{ selectedGameForPromotion.title }}</h3>
                    <img :src="selectedGameForPromotion.image" alt="Game Image" class="game-image" />
                </div>

                <button @click="addPromotion" class="submit-button">Submit Promotion</button>
                </div>
            </div>
  
          <!-- Delete Promotion Button -->
            <div class="centered-buttons">
            <button @click="toggleDeletePromotion" class="delete-promotion-button">Delete Promotion</button>
            </div>
            <!-- Delete Promotion Section -->
            <div v-if="showDeletePromotionSection" class="delete-promotion-section">
            <h3>Delete Promotion</h3>
            <input
                type="text"
                v-model="deletePromotionCode"
                placeholder="Enter promotion code..."
            />
            <button @click="deletePromotion" class="submit-button">Delete</button>
            </div>
            <div class="view-all-promotions">
            <!-- Button to toggle promotions list -->
            <div class="view-promotions-section">
                <button @click="toggleViewPromotions" class="view-promotions-button">
                {{ showAllPromotions ? "Hide All Promotions" : "View All Promotions" }}
                </button>
            </div>

            <!-- Promotions List -->
            <div v-if="showAllPromotions" class="promotions-list">
                <h3>All Promotions</h3>
                <ul>
                <li v-for="promotion in promotions" :key="promotion.promotionCode" class="promotion-item">
                    <span><strong>Code:</strong> {{ promotion.promotionCode }}</span> | 
                    <span><strong>Discount:</strong> {{ promotion.discountPercentage }}%</span> | 
                    <span><strong>Valid Until:</strong> {{ promotion.dateValidUntil }}</span>
                </li>
                </ul>
            </div>
            </div>
        </div>
      
    </div>
  
      <div class="sub-nav-bar">
        <a href="/reviews" class="sub-nav-link">Access Reviews</a>
        <a href="/staff/employees" class="sub-nav-link">Manage Employees</a>
        </div>
    </div>
    </div>
  </template>  
  

<script>
import NavBar from "./NavBar.vue";
import axiosClient from "./axios";

export default {
  name: "StaffPage",
  components: { NavBar },
  data() {
    return {
        isVerified: false,
        employeeId: null,
        errorMessage: "",

        categories: [],
        promotions: [],

        categorySearchQuery: "",
        promotionSearchQuery: "",

        selectedGameForCategory: null,
        selectedGameForPromotion: null,

        selectedCategory: null,       
        selectedPromotion: "",

        showAddCategoryInput: false,
        newCategory: "",

        showAddPromotionInput: false,
        newPromotionCode: "",
        newPromotionDiscount: null,
        newPromotionDateValidUntil: null,

        showDeleteCategorySection: false,
        showDeletePromotionSection: false,
        showAllCategories: false,
        showAllPromotions: false,

        deleteCategoryId: null,
        deletePromotionCode: "", // For delete promotion input
    };
  },
  created() {
    this.fetchCategories();
    this.fetchPromotions();
  },
  methods: {

    toggleDeleteCategory() {
      this.showDeleteCategorySection = !this.showDeleteCategorySection;
    },

    // Toggle visibility of delete promotion section
    toggleDeletePromotion() {
      this.showDeletePromotionSection = !this.showDeletePromotionSection;
    },

    toggleViewCategories() {
    this.showAllCategories = !this.showAllCategories;
  },

  toggleAddPromotion() {
    this.showAddPromotionInput = !this.showAddPromotionInput;
  },

  toggleViewPromotions() {
    this.showAllPromotions = !this.showAllPromotions;
  },

  async verifyEmployee() {
    if (!this.employeeId) {
        this.errorMessage = "Please enter a valid Employee ID.";
        return;
    }

    try {
        const response = await axiosClient.get(`/employee/id/${this.employeeId}`);
        if (response.data) {
        this.isVerified = true;
        this.errorMessage = "";
        }
    } catch (error) {
        if (error.response && error.response.status === 404) {
        this.errorMessage = "Employee ID not found.";
        this.isVerified = false;
        } else {
        console.error("Error verifying employee:", error.response || error);
        this.errorMessage = "An error occurred. Please try again.";
        this.isVerified = false;
        }
    }
    },

    // Fetch categories
    async fetchCategories() {
      try {
        const response = await axiosClient.get("/categories");
        this.categories = response.data || [];
      } catch (error) {
        console.error("Error fetching categories:", error);
      }
    },

    // Fetch promotions
    async fetchPromotions() {
      try {
        const response = await axiosClient.get("/promotions");
        this.promotions = response.data || [];
      } catch (error) {
        console.error("Error fetching promotions:", error);
      }
    },

    addGame() {
      this.$router.push("/staff/add-game");
    },

    // Search for a game for category
    async searchGameForCategory() {
      try {
        const response = await axiosClient.get(`/game/title/${this.categorySearchQuery}`);
        this.selectedGameForCategory = response.data;
      } catch (error) {
        console.error("Error fetching game:", error);
        alert("Game not found.");
      }
    },

    // Assign game to category
    async assignCategory() {
    if (!this.selectedCategory || !this.selectedGameForCategory) {
      alert("Please select a game and a category.");
      return;
    }
    try {
      console.log("Selected Game:", this.selectedGameForCategory);
      console.log(this.selectedCategory.name)
      console.log(this.selectedCategory, this.selectedGameForCategory.gameID);
      await axiosClient.post(`/category/assign/${this.selectedGameForCategory.gameID}`, this.selectedCategory);
      alert("Game assigned to category!");
    } catch (error) {
      console.error("Error assigning category:", error);
      alert("Failed to assign game to category. Please try again.");
    }
  },

    // Add category
    toggleAddCategory() {
      this.showAddCategoryInput = !this.showAddCategoryInput;
    },
    async addCategory() {
      if (!this.newCategory.trim()) {
        alert("Please enter a valid category name.");
        return;
      }
      try {
        await axiosClient.post("/category", { name: this.newCategory.trim() });
        this.categories.push({ name: this.newCategory.trim() });
        this.newCategory = "";
        this.showAddCategoryInput = false;
        alert("Category added!");
      } catch (error) {
        console.error("Error adding category:", error);
      }
    },

    // Search for a game for promotion
    async searchGameForPromotion() {
    if (!this.promotionSearchQuery.trim()) {
      alert("Please enter a game title to search.");
      return;
    }
    try {
      const response = await axiosClient.get(`/game/title/${this.promotionSearchQuery.trim()}`);
      this.selectedGameForPromotion = response.data;
    } catch (error) {
      console.error("Error searching for game:", error.response || error);
      alert("Game not found.");
    }
  },

    // Assign game to promotion
    async assignPromotion() {
      if (!this.selectedPromotion || !this.selectedGameForPromotion) {
        alert("Please select a game and a promotion.");
        return;
      }
      try {
        await axiosClient.post("/promotion", {
          promotionCode: this.selectedPromotion,
          gameId: this.selectedGameForPromotion.gameID,
        });
        alert("Game assigned to promotion!");
      } catch (error) {
        console.error("Error assigning promotion:", error);
      }
    },

   

    async addPromotion() {
    if (
      !this.newPromotionCode.trim() ||
      !this.newPromotionDiscount ||
      !this.newPromotionDateValidUntil ||
      !this.selectedGameForPromotion
    ) {
      alert("Please complete all fields and select a game.");
      return;
    }

    try {
      await axiosClient.post("/promotion", {
        promotionCode: this.newPromotionCode.trim(),
        discountPercentage: this.newPromotionDiscount,
        validUntil: this.newPromotionDateValidUntil,
        gameId: this.selectedGameForPromotion.gameID, // Pass the selected game ID
      });

      // Update local promotions list
      this.promotions.push({
        promotionCode: this.newPromotionCode.trim(),
        discountPercentage: this.newPromotionDiscount,
        dateValidUntil: this.newPromotionDateValidUntil,
      });

      // Reset fields
      this.newPromotionCode = "";
      this.newPromotionDiscount = null;
      this.newPromotionDateValidUntil = null;
      this.selectedGameForPromotion = null;

      alert("Promotion added successfully!");
    } catch (error) {
      console.error("Error adding promotion:", error.response || error);
      alert("Failed to add promotion. Please try again.");
    }
  },

    // Delete a category by ID
    async deleteCategory() {
      if (!this.deleteCategoryId) {
        alert("Please enter a valid category ID.");
        return;
      }
      try {
        await axiosClient.delete(`/category/${this.deleteCategoryId}`);
        this.categories = this.categories.filter(
          (category) => category.id !== this.deleteCategoryId
        );
        alert("Category deleted successfully.");
        this.deleteCategoryId = null; // Reset input
      } catch (error) {
        console.error("Error deleting category:", error);
        alert("Failed to delete category.");
      }
    },

    // Delete a promotion
    async deletePromotion() {
      if (!this.deletePromotionCode.trim()) {
        alert("Please enter a valid promotion code.");
        return;
      }
      try {
        await axiosClient.delete(`/promotion/${this.deletePromotionCode.trim()}`);
        this.promotions = this.promotions.filter(
          (promotion) => promotion.promotionCode !== this.deletePromotionCode.trim()
        );
        alert("Promotion deleted successfully.");
        this.deletePromotionCode = ""; // Reset input
      } catch (error) {
        console.error("Error deleting promotion:", error);
        alert("Failed to delete promotion.");
      }
    },
  },
};
</script>

<style scoped>
.staff-page {
  padding: 40px;
  background-color: #f5f5f5;
  font-family: Arial, sans-serif;
}

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

.content-section {
  margin-top: 20px;
}

h1, h2 {
  color: #333;
}

.header-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 30px;
  margin-bottom: 20px;
}

.add-game-button {
  background-color: #28a745;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.add-game-button:hover {
  background-color: #218838;
}

.management-container {
  display: flex;
  justify-content: space-between;
  gap: 20px;
  margin-top: 20px;
}

.category-section,
.promotion-section {
  background-color: #fff;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.15);
  flex: 1;
}

h2 {
  font-size: 1.8rem;
  color: #333;
  text-align: center;
  margin-bottom: 20px;
}

.search-section {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.search-button {
  background-color: #16a085;
  color: white;
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.search-button:hover {
  background-color: #13856b;
}

.add-category-section,
.add-promotion-section {
  margin-top: 30px;
  text-align: center;
}

.add-category-button,
.add-promotion-button {
  background-color: #007bff;
  color: white;
  padding: 12px 25px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.add-category-button:hover,
.add-promotion-button:hover {
  background-color: #0056b3;
}

.add-category-form,
.add-promotion-form {
  margin-top: 15px;
}

.centered-buttons {
  display: flex;
  justify-content: center;
  margin: 20px 0; /* Adjust spacing as needed */
}

.delete-category-button,
.delete-promotion-button {
  background-color: #dc3545;
  color: white;
  padding: 12px 25px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.delete-category-button:hover,
.delete-promotion-button:hover {
  background-color: #a71d2a;
}

 h3 {
  font-size: 18px;
  color: #333; /* Dark color for visibility */
  margin-bottom: 5px; /* Add spacing below the heading */
}

input[type="text"],
input[type="number"],
input[type="date"] {
  padding: 10px;
  margin: 5px;
  width: 85%;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
}

input[type="text"]:focus,
input[type="number"]:focus,
input[type="date"]:focus {
  border-color: #007bff;
  outline: none;
  box-shadow: 0px 0px 5px rgba(0, 123, 255, 0.5);
}

.submit-button {
  background-color: #0040ff;
  color: white;
  padding: 12px 25px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.submit-button:hover {
  background-color: #002080;
}

.sub-nav-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: #0040ff;
  padding: 15px;
  display: flex;
  justify-content: center;
  gap: 30px;
}

.sub-nav-link {
  color: white;
  text-decoration: none;
  font-weight: bold;
  transition: color 0.3s;
}

.sub-nav-link:hover {
  color: #ffcc00;
}

.view-categories-section,
.view-promotions-section{
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
}

.view-categories-button,
.view-promotions-button {
  background-color: #007bff;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  margin-top: 0;
}

.view-categories-button:hover,
.view-promotions-button:hover {
  background-color: #0056b3;
}

.categories-list,
.promotions-list {
  margin-top: 20px;
  text-align: left;
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 10px;
  background-color: #f9f9f9;
}

.categories-list h3,
.promotions-list h3 {
  color: black;
  margin-bottom: 10px;
}

.categories-list ul,
.promotions-list ul {
  list-style-type: none;
  padding: 0;
}

.categories-list li,
.promotions-list li {
  padding: 5px 0;
  border-bottom: 1px solid #ccc;
  color: black; /* Ensures all text is black */
}

.categories-list li:last-child,
.promotions-list li:last-child {
  border-bottom: none;
}

.category-item span,
.promotion-item span {
  color: black; /* Ensures each span element is black */
  font-size: 14px;
}

p {
  color: #444; 
}


</style>

