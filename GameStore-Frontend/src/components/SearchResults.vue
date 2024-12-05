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
import axios from "./axios";

export default {
  data() {
    return {
      games: [],
      searchQuery: this.$route.query.query || "",
      filteredGames: []
    };
  },
  mounted() {
    this.fetchGames();
  },
  watch: {
    "$route.query.query": function (newQuery) {
      this.searchQuery = newQuery;
      this.filterGames();
    }
  },
  methods: {
    async fetchGames() {
      try {
        const response = await axios.get("/games");
        this.games = response.data || [];
        this.filterGames();
      } catch (error) {
        console.error("Error fetching games:", error);
      }
    },
    filterGames() {
      this.filteredGames = this.games.filter(game =>
        game.title.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
    searchGame(query) {
      const matchingGame = this.games.find(game =>
        game.title.toLowerCase().includes(query.toLowerCase())
      );
      if (matchingGame) {
        this.$router.push(`/games/${matchingGame.id}`);
      } else {
        // If no match, redirect to an empty results page or a 'not found' page
        this.$router.push({ path: "/search-results", query: { query } });
      }
    }
  }
};
</script>


<style scoped>
.search-results-page {
  padding: 20px;
  background-color: #f5f5f5;
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
}

.game-card p {
  font-size: 1rem;
  color: #333;
}

button {
  margin-top: 10px;
  background-color: #007bff;
  color: white;
  padding: 8px 16px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

.no-results {
  text-align: center;
  font-size: 1.2rem;
  color: #999;
}
</style>
