<template>
  <div class="search-bar">
    <input
      type="text"
      v-model="searchQuery"
      placeholder="Search for game by title..."
      @keyup.enter="searchGame"
    />
    <button @click="searchGame">Search</button>
  </div>
</template>

<script>
import axios from "./axios";

export default {
  data() {
    return {
      searchQuery: "",
    };
  },
  methods: {
    async searchGame() {
      if (!this.searchQuery.trim()) {
        alert("Please enter a game title to search.");
        return;
      }

      try {
        // Fetch game details by title
        const response = await axios.get(`/game/title/${this.searchQuery.trim()}`);
        const game = response.data;

        // Redirect to the game details page
        if (game && game.gameID) {
          this.$router.push(`/games/${game.id}`);
        } else {
          alert("Game not found. Please try a different title.");
        }
      } catch (error) {
        console.error("Error searching for game:", error.response || error);
        alert("Game not found. Please try again.");
      }
    },
  },
};
</script>

<style scoped>
.search-bar {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 20px;
}

input {
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
  flex-grow: 1;
}

button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #0056b3;
}
</style>
