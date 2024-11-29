<template>
  <div class="category-management-page">
    <NavBar />
    <div class="category-management-container">
      <h1>Manage Categories</h1>
      <div class="search-section">
        <input
          type="text"
          v-model="searchQuery"
          placeholder="Search for games by title..."
          @keyup.enter="searchGame"
        />
        <button @click="searchGame">Search</button>
      </div>

      <!-- Game Search Result -->
      <div v-if="selectedGame" class="game-search-result">
        <h2>{{ selectedGame.title }}</h2>
        <img :src="selectedGame.image" alt="Game Image" class="game-image" />
        <select v-model="selectedCategory">
          <option value="">Select Category</option>
          <option v-for="category in categories" :key="category.id" :value="category.name">
            {{ category.name }}
          </option>
        </select>
        <button @click="assignCategory">Assign to Category</button>
      </div>

      <!-- Add Category Section -->
      <div class="add-category-section">
        <button @click="toggleAddCategory" class="add-category-button">
          Add Category
        </button>
        <div v-if="showAddCategoryInput" class="add-category-form">
          <input
            type="text"
            v-model="newCategory"
            placeholder="Enter category name..."
          />
          <button @click="addCategory">Submit</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from "./NavBar.vue";
import axiosClient from "./axios";



export default {
  name: "CategoryManagement",
  components: {
    NavBar,
  },
  data() {
    return {
      searchQuery: "",
      selectedGame: null,
      selectedCategory: "",
      categories: [], // Categories will be fetched from the backend
      showAddCategoryInput: false,
      newCategory: "",
    };
  },
  created() {
    // Fetch categories when component is created
    this.fetchCategories();
  },
  methods: {
    // Fetch categories from the backend (for future scalability)
    async fetchCategories() {
      try {
        const response = await axiosClient.get("/categories");
        this.categories = response.data; // Set categories data
        if(!this.categories){
            this.categories = [];
        }
      } catch (error) {
        console.error("Error fetching categories:", error);
        alert("Failed to fetch categories. Check the console for more details.");
      }
    },

    // Toggle the visibility of the Add Category input field
    toggleAddCategory() {
      this.showAddCategoryInput = !this.showAddCategoryInput;
    },

    // Add a new category to the system
    async addCategory() {
      try{
        if (!this.newCategory.trim()) {
        alert("Please enter a valid category name.");
        return;
      }

      const categoryNamesSet = new Set((this.categories || []).map(category => category.name));

        // Check if the category already exists
        if (categoryNamesSet.has(this.newCategory.trim())) {
        alert("Category already exists.");
        return;
        }

        await axiosClient.post("/category", { name: this.newCategory.trim() });
        this.categories.push({ name: this.newCategory.trim() }); // Add the new category to the list
        this.newCategory = ""; // Clear the input field
        this.showAddCategoryInput = false;
        alert("Category added successfully!");
      } catch (error) {
        console.error("Error adding category:", error);
        alert("Failed to add category. Please try again.");
      }
    },

    // Search for a game by title
    async searchGame() {
      if (!this.searchQuery.trim()) {
        alert("Please enter a valid search query.");
        return;
      }

      try {
        const response = await axiosClient.get(`/api/game/title/${this.searchQuery}`);
        this.selectedGame = response.data;

        // Fetch categories only when a game is selected
        this.fetchCategories();
      } catch (error) {
        console.error("Error fetching game:", error);
        alert("Game not found.");
        this.selectedGame = null;
      }
    },

    // Assign the selected game to a category
    async assignCategory() {
      if (!this.selectedCategory || !this.selectedGame) {
        alert("Please select a game and a category.");
        return;
      }

      try {
        await axiosClient.post("/api/category/assign", {
          gameId: this.selectedGame.id,
          category: this.selectedCategory,
        });
        alert("Category assigned successfully!");
      } catch (error) {
        console.error("Error assigning category:", error);
        alert("Failed to assign category. Please try again.");
      }
    },
  },
};
</script>

<style scoped>
.category-management-page {
  padding: 20px;
  font-family: Arial, sans-serif;
  background-color: #f5f5f5;
  margin-top: 60px; /* Ensure content starts below navbar (adjust this as per your navbar height) */
  min-height: 100vh; 
}

.category-management-container {
  max-width: 800px;
  margin: 0 auto;
  text-align: center;
}

.search-section {
  margin-bottom: 20px;
}

.search-section input {
  padding: 10px;
  width: 60%;
  font-size: 16px;
}

.search-section button {
  padding: 10px;
  font-size: 16px;
}

.game-search-result {
  margin-top: 20px;
}

.game-search-result img {
  max-width: 200px;
  margin-top: 10px;
}

.add-category-section {
  margin-top: 40px;
}

.add-category-button {
  background-color: #007bff;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.add-category-button:hover {
  background-color: #0056b3;
}

.add-category-form {
  margin-top: 10px;
}

.add-category-form input {
  padding: 10px;
  width: 60%;
  margin-right: 10px;
  font-size: 16px;
}

.add-category-form button {
  padding: 10px 20px;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.add-category-form button:hover {
  background-color: #218838;
}

select {
  margin-top: 10px;
  padding: 10px;
  font-size: 16px;
  border-radius: 5px;
}

button {
  padding: 10px;
  margin-top: 10px;
}
</style>
