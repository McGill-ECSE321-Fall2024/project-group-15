<template>
    <div class="game-list-page">
      <NavBar />
      <div class="header-actions">
        <button class="add-game-button" @click="addGame">Add Game</button>
        <SearchBar class="search-bar" @search="searchGame" />
      </div>
  
      <div class="category-games">
        <h2 class="centered-header">
          {{ selectedCategory ? selectedCategory : "All" }} Games
        </h2>
        <div class="filters">
          <select v-model="selectedCategory" @change="filterByCategory">
            <option value="">All Categories</option>
            <option v-for="category in categories" :key="category" :value="category">
              {{ category }}
            </option>
          </select>
        </div>
        <div class="game-cards">
          <div v-for="game in filteredGames" :key="game.id" class="game-card">
            <img :src="game.image" alt="Game Image" class="game-image" />
            <h3>{{ game.title }}</h3>
            <p>${{ game.price.toFixed(2) }}</p>
          </div>
        </div>
      </div>
  
      <div class="five-star-games">
        <h2 class="centered-header">FIVE STAR Games</h2>
        <div class="game-cards">
          <div v-for="game in fiveStarGames" :key="game.id" class="game-card">
            <img :src="game.image" alt="Game Image" class="game-image" />
            <h3>{{ game.title }}</h3>
            <p>${{ game.price.toFixed(2) }}</p>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import NavBar from "./NavBar.vue";
  import SearchBar from "./SearchBar.vue";
  import axios from "axios";
  
  export default {
    name: "GameList",
    components: { NavBar, SearchBar },
    data() {
      return {
        games: [],
        fiveStarGames: [],
        filteredGames: [],
        categories: [],
        selectedCategory: "",
      };
    },
    async created() {
      await this.fetchGames();
      this.filterByCategory();
    },
    methods: {
      async fetchGames() {
        try {
          const response = await axios.get("/games");
          this.games = response.data;
          this.fiveStarGames = this.games.filter((game) => game.rating === "FIVE_STAR");
          this.categories = Array.from(new Set(this.games.map((game) => game.category)));
          this.filteredGames = [...this.games];
        } catch (error) {
          console.error("Error fetching games:", error);
        }
      },
      filterByCategory() {
        if (this.selectedCategory) {
          this.filteredGames = this.games.filter(
            (game) => game.category === this.selectedCategory
          );
        } else {
          this.filteredGames = [...this.games];
        }
      },
      async searchGame(query) {
        if (!query.trim()) {
          alert("Please enter a valid search query.");
          return;
        }
        try {
          const response = await axios.get(`/game/title/${query}`);
          this.filteredGames = [response.data];
        } catch (error) {
          console.error("Error searching for game:", error);
          alert("Game not found.");
        }
      },
      addGame() {
        this.$router.push("/games/add-game");
      },
    },
  };
  </script>
    
    <style scoped>
    .game-list-page {
      padding: 20px;
      background-color: #f5f5f5;
    }
    
    .header-actions {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-top: 20px;
      margin-bottom: 20px;
    }

    .add-game-container {
     flex-shrink: 0; 
    display: flex;
    align-items: center;
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
    
    .search-bar {
      margin-left: auto;
    }
    
    .category-header {
      display: flex;
      flex-direction: column;
      align-items: center;
      margin-bottom: 20px;
    }
    
    .category-header h2 {
      font-size: 1.5rem;
      font-weight: bold;
      color: black;
      margin-bottom: 10px;
    }
    
    .category-header select {
      padding: 10px;
      font-size: 16px;
      border-radius: 5px;
      border: 1px solid #ccc;
    }
    
    .game-cards {
      display: flex;
      flex-wrap: wrap;
      gap: 20px;
      justify-content: center;
    }
    
    .game-card {
      width: 200px;
      border: 1px solid #ccc;
      border-radius: 10px;
      padding: 10px;
      text-align: center;
      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    }
    
    .game-card img {
      max-width: 100%;
      height: 150px;
      object-fit: cover;
      border-radius: 5px;
    }
    
    .game-card h3 {
      font-size: 1.1rem;
      margin: 10px 0;
      color: black;
    }
    
    .game-card p {
      font-size: 1rem;
      color: #333;
    }
    
    .centered-header {
      text-align: center;
      font-size: 1.5rem;
      font-weight: bold;
      color: black;
      margin: 20px 0;
    }
    </style>
    
    