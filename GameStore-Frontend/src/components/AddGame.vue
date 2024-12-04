<template>
  <div class="add-game-page">
    <NavBar />
    <div class="add-game-form-container">
      <h1>Add a New Game</h1>
      <form @submit.prevent="submitGame">
        <div class="form-group">
          <label for="title">Title</label>
          <input type="text" id="title" v-model="title" required />
        </div>
        <div class="form-group">
          <label for="description">Description</label>
          <textarea id="description" v-model="description" required></textarea>
        </div>
        <div class="form-group">
          <label for="price">Price</label>
          <input type="number" id="price" v-model="price" step="0.01" min="0" required />
        </div>
        <div class="form-group">
          <label for="stock">Stock</label>
          <input type="number" id="stock" v-model="stock" min="0" required />
        </div>
        <div class="form-group">
          <label for="image">Image URL</label>
          <input type="url" id="image" v-model="image" required />
        </div>
        <div class="form-group">
        <label for="managerId">Manager ID</label>
        <div class="manager-input-container">
          <input
            type="number"
            id="managerId"
            v-model="managerId"
            min="0"
            required
          />
          <button
            type="button"
            class="confirm-button"
            @click="verifyManagerId"
          >
            Confirm
          </button>
        </div>
        <p v-if="managerErrorMessage" class="error-message">{{ managerErrorMessage }}</p>
      </div>
        <div class="form-group">
          <label>
            <span>Is Approved</span>
            <input type="checkbox" v-model="isApproved" />
          </label>
        </div>
        <div class="form-actions">
          <button type="submit" class="submit-button">Add Game</button>
          <button type="button" class="cancel-button" @click="cancel">Cancel</button>
        </div>
      </form>
      <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
    </div>
  </div>
</template>

<script>
import NavBar from "./NavBar.vue";
import axios from "./axios";

export default {
  name: "AddGame",
  components: {
    NavBar,
  },
  data() {
    return {
      title: "",
      description: "",
      price: 0,
      stock: 0,
      image: "",
      managerId: null,
      isApproved: false,
      managerErrorMessage: "",
      isManagerConfirmed: false,
      errorMessage: "",
    };
  },
  methods: {
    async verifyManagerId() {
      if (!this.managerId) {
        this.managerErrorMessage = "Please enter a valid Manager ID.";
        return;
      }

      try {
        const response = await axios.get(`/employee/id/${this.managerId}`);
        const manager = response.data;

        if (manager.isManager) {
          this.managerErrorMessage = "";
          this.isManagerConfirmed = true;
          alert("Manager ID confirmed.");
        } else {
          this.managerErrorMessage = "The entered ID does not belong to a manager.";
          this.isManagerConfirmed = false;
        }
      } catch (error) {
        if (error.response && error.response.status === 404) {
          this.managerErrorMessage = "No employee found with this ID.";
        } else {
          this.managerErrorMessage = "An error occurred while verifying the manager.";
        }
        this.isManagerConfirmed = false;
      }
    },
    
    async submitGame() {
      if (!this.isManagerConfirmed) {
      alert("Please confirm a valid Manager ID before submitting.");
      return;
    }

      const gameData = {
        title: this.title,
        description: this.description,
        price: this.price,
        stock: this.stock,
        image: this.image,
        managerId: this.managerId,
        isApproved: this.isApproved,
      };

      try {
        const response = await axios.post("/game", gameData);
        if (response.status === 201) {
          alert("Game added successfully!");
          this.$router.push("/games");
        }
      } catch (error) {
        console.error("Error adding game:", error);
        if (error.response && error.response.data) {
          this.errorMessage = `Failed to add game: ${error.response.data.message || "Unknown error"}`;
        } else {
          this.errorMessage = "Failed to add game. Please try again.";
        }
      }
    },
    cancel() {
      this.$router.push("/games");
    },
  },
};
</script>

<style scoped>
/* Styling for add game page */
.add-game-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 50px;
  font-family: Arial, sans-serif;
  background-color: #f5f5f5;
  min-height: 100vh;
}

.add-game-form-container {
  background: #fff;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  max-width: 600px;
  width: 100%;
  text-align: center;
}

.add-game-form-container h1 {
  margin-bottom: 20px;
  font-size: 1.8rem;
  color: #333;
}

.form-group {
  margin-bottom: 15px;
  text-align: left;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  color: #333;
}

.form-group input,
.form-group textarea,
.form-group select {
  width: 100%;
  padding: 10px;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.form-group textarea {
  resize: vertical;
  height: 100px;
}

.form-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.submit-button {
  background-color: #28a745;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.submit-button:hover {
  background-color: #218838;
}

.cancel-button {
  background-color: #dc3545;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.cancel-button:hover {
  background-color: #c82333;
}

.error-message {
  margin-top: 20px;
  color: red;
  font-size: 1rem;
}

.manager-input-container {
  display: flex;
  align-items: center;
  gap: 10px;
}

.confirm-button {
  background-color:#28a745;
  color: white;
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.confirm-button:hover {
  background-color: #218838;
}
</style>
