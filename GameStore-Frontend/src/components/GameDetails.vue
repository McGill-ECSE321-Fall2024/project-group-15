<template>
    <div class="game-details-page">
      <NavBar />
      <div class="search-bar-container">
        <input
          type="text"
          v-model="searchQuery"
          @input="searchGame"
          placeholder="Search for games by name..."
          class="search-bar"
        />
      </div>
      <div class="game-details-container">
        <div class="game-image-section">
          <img :src="game.image" alt="Game Image" class="game-image" />
        </div>
        <div class="game-info-section">
          <h1 class="game-title">{{ game.title }}</h1>
          <p class="game-description">{{ game.description }}</p>
          <p class="game-price">Price: ${{ game.price.toFixed(2) }}</p>
          <p class="game-stock">In Stock: {{ game.stock }}</p>
          <div class="game-actions">
            <button @click="editGame">Edit Game</button>
            <button @click="deleteGame">Delete Game</button>
          </div>
        </div>
      </div>
    </div>
</template>
  
<script>
    import NavBar from "./NavBar.vue";
    import axios from "./axios";
  
    export default {
        name: "GameDetailsPage",
        components: { NavBar },
        data() {
            return {
            game: {}, // Holds the game details
            searchQuery: "", // Search input
        };
    },
    created() {
      this.getGame(); // Fetch the game when the page is loaded
    },
    methods: {
      async getGame() {
        const gameId = this.$route.params.id; // Assume the game ID is passed via route
        try {
          const response = await axios.get(`http://localhost:8080/game/${gameId}`);
          this.game = response.data;
        } catch (error) {
          console.error("Error fetching game details:", error);
        }
      },
      async searchGame() {
        if (!this.searchQuery.trim()) return;
  
        try {
          const response = await axios.get(
            `http://localhost:8080/game/title/${this.searchQuery}`
          );
          this.game = response.data;
        } catch (error) {
          console.error("Error searching for game:", error);
          alert("Game not found. Please try another search.");
        }
      },
      editGame() {
        this.$router.push({ name: "EditGame", params: { id: this.game.id } });
      },
      async deleteGame() {
        try {
          await axios.delete(`http://localhost:8080/game/${this.game.id}`);
          alert("Game deleted successfully!");
          this.$router.push("/games"); // Redirect to the list of games after deletion
        } catch (error) {
          console.error("Error deleting game:", error);
          alert("Failed to delete the game. Please try again.");
        }
      },
    },
  };
  </script>
  
  <style scoped>
  body {
    font-family: Arial, sans-serif;
    background-color: #f5f5f5;
    margin: 0;
    padding: 0;
  }
  
  .game-details-page {
    padding: 20px;
    margin: auto;
    max-width: 1200px;
    background: #fff;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  
  .search-bar-container {
    margin: 20px 0;
    width: 100%;
    display: flex;
    justify-content: center;
  }
  
  .search-bar {
    width: 80%;
    padding: 10px;
    font-size: 1rem;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
  }
  
  .game-details-container {
    display: flex;
    align-items: center;
    gap: 20px;
    margin-top: 20px;
  }
  
  .game-image-section {
    flex: 1;
    text-align: center;
  }
  
  .game-image {
    width: 300px;
    height: 300px;
    object-fit: cover;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  }
  
  .game-info-section {
    flex: 2;
    display: flex;
    flex-direction: column;
    gap: 15px;
  }
  
  .game-title {
    font-size: 2rem;
    font-weight: bold;
    color: #333;
    margin: 0;
  }
  
  .game-description {
    font-size: 1.2rem;
    color: #555;
  }
  
  .game-price,
  .game-stock {
    font-size: 1.2rem;
    font-weight: bold;
    color: #0040ff;
  }
  
  .game-actions {
    display: flex;
    gap: 10px;
    margin-top: 20px;
  }
  
  button {
    padding: 10px 20px;
    font-size: 1rem;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }
  
  button:first-child {
    background-color: #0040ff;
    color: white;
  }
  
  button:first-child:hover {
    background-color: #002080;
  }
  
  button:last-child {
    background-color: #ff4d4d;
    color: white;
  }
  
  button:last-child:hover {
    background-color: #b30000;
  }
  </style>
  