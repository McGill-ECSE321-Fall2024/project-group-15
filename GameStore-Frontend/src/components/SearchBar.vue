<template>
    <div class="search-bar">
      <input
        type="text"
        v-model="searchQuery"
        placeholder="Search for a game by title..."
        @keyup.enter="searchGame"
      />
      <button @click="searchGame">Search</button>
      <div v-if="searchResult" class="search-result">
        <h3>Search Result</h3>
        <p><strong>Title:</strong> {{ searchResult.title }}</p>
        <p><strong>Description:</strong> {{ searchResult.description }}</p>
        <p><strong>Price:</strong> ${{ searchResult.price }}</p>
        <p><strong>Stock:</strong> {{ searchResult.stock }}</p>
        <img :src="searchResult.image" alt="Game Image" class="game-image" />
      </div>
      <p v-else-if="errorMessage" class="error-message">{{ errorMessage }}</p>
    </div>
</template>
  
<script>
    import axios from "./axios";

    export default {
        data() {
        return {
            searchQuery: "",
            searchResult: null,
            errorMessage: null,
        };
        },
        methods: {
        async searchGame() {
            if (!this.searchQuery.trim()) {
            this.errorMessage = "Please enter a game title to search.";
            this.searchResult = null;
            return;
            }
    
            try {
            const response = await axios.get(`/game/title/${this.searchQuery.trim()}`);
            this.searchResult = response.data;
            this.errorMessage = null;
            } catch (error) {
            this.searchResult = null;
            if (error.response && error.response.status === 404) {
                this.errorMessage = "Game not found.";
            } else {
                this.errorMessage = "An error occurred while searching for the game.";
            }
            }
        },
        },
    };
</script>
  
<style scoped>
  .search-bar {
    margin: 20px;
    text-align: center;
  }
  
  .search-bar input {
    width: 300px;
    padding: 10px;
    margin-right: 10px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 5px;
  }
  
  .search-bar button {
    padding: 10px 20px;
    font-size: 16px;
    color: white;
    background-color: #1abc9c;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  
  .search-bar button:hover {
    background-color: #16a085;
  }
  
  .search-result {
    margin-top: 20px;
    text-align: left;
    display: inline-block;
  }
  
  .error-message {
    color: red;
    margin-top: 10px;
  }
  
  .game-image {
    margin-top: 10px;
    max-width: 300px;
    max-height: 200px;
    border: 1px solid #ccc;
    border-radius: 5px;
  }
</style>
  