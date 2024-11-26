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
          <div class="form-actions">
            <button type="submit" class="submit-button">Add Game</button>
            <button type="button" class="cancel-button" @click="cancel">Cancel</button>
          </div>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  import NavBar from "./NavBar.vue";
  import axios from "axios";
  
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
      };
    },
    methods: {
      async submitGame() {
        const gameData = {
          title: this.title,
          description: this.description,
          price: this.price,
          stock: this.stock,
          image: this.image,
        };
  
        try {
          await axios.post("http://localhost:8080/game", gameData);
          alert("Game added successfully!");
          this.$router.push("/games"); // Redirect to the list of games
        } catch (error) {
          console.error("Error adding game:", error);
          alert("Failed to add game. Please try again.");
        }
      },
      cancel() {
        this.$router.push("/games"); // Redirect back to the games page
      },
    },
  };
  </script>
  
  <style scoped>
  /* Same styling as before */
  .add-game-page {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 20px;
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
  .form-group textarea {
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
  </style>
  