<template>
    <div>
      <h2>Search Results for "{{ searchQuery }}"</h2>
      <div v-if="filteredGames.length > 0">
        <div v-for="game in filteredGames" :key="game.id" class="game-card">
          <img :src="game.image" alt="Game Image" />
          <h3>{{ game.title }}</h3>
          <p>${{ game.price.toFixed(2) }}</p>
        </div>
      </div>
      <div v-else>
        <p>No results found.</p>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        games: [
          { id: 1, title: "Game One", price: 29.99, image: "https://via.placeholder.com/200", category: "Action" },
          { id: 2, title: "Game Two", price: 19.99, image: "https://via.placeholder.com/200", category: "RPG" },
          { id: 3, title: "Game Three", price: 49.99, image: "https://via.placeholder.com/200", category: "Adventure" },
          { id: 4, title: "Game Four", price: 39.99, image: "https://via.placeholder.com/200", category: "Strategy" },
          { id: 5, title: "Game Five", price: 59.99, image: "https://via.placeholder.com/200", category: "Action" }
        ],
        searchQuery: this.$route.query.query || "",
        filteredGames: []
      };
    },
    mounted() {
      this.filterGames();
    },
    watch: {
      "$route.query.query": function (newQuery) {
        this.searchQuery = newQuery;
        this.filterGames();
      }
    },
    methods: {
      filterGames() {
        this.filteredGames = this.games.filter(game =>
          game.title.toLowerCase().includes(this.searchQuery.toLowerCase())
        );
      }
    }
  };
  </script>
  
  
  <style scoped>
  .search-results-page {
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
  
  .centered-header {
    text-align: center;
    font-size: 1.5rem;
    font-weight: bold;
    color: black;
    margin: 20px 0;
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
    cursor: pointer;
  }
  
  .game-card p {
    font-size: 1rem;
    color: #333;
  }
  
  .no-results {
    text-align: center;
    font-size: 1.2rem;
    color: #999;
  }
  </style>
  