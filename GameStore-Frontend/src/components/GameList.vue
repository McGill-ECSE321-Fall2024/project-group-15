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
          <h3 @click="goToGameDetails(game.id)">{{ game.title }}</h3>
          <p>${{ game.price.toFixed(2) }}</p>
          <button @click="addToCart(game)">Add to Cart</button>
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
      games: [
        {
          id: 1,
          title: "The Witcher 3: Wild Hunt",
          price: 39.99,
          image: "https://upload.wikimedia.org/wikipedia/commons/1/1c/The_Witcher_3_Wild_Hunt_Logo.svg",
          rating: "FIVE_STAR",
          category: "Action"
        },
        {
          id: 2,
          title: "Red Dead Redemption 2",
          price: 59.99,
          image: "https://upload.wikimedia.org/wikipedia/commons/3/3f/Red_Dead_Redemption_2_logo.svg",
          rating: "FIVE_STAR",
          category: "RPG"
        },
        {
          id: 3,
          title: "Cyberpunk 2077",
          price: 49.99,
          image: "https://upload.wikimedia.org/wikipedia/commons/a/a1/Cyberpunk_2077_logo.svg",
          rating: "FOUR_STAR",
          category: "Adventure"
        },
        {
          id: 4,
          title: "Assassin's Creed Odyssey",
          price: 29.99,
          image: "https://upload.wikimedia.org/wikipedia/commons/f/f9/Assassin%27s_Creed_Odyssey_Logo.svg",
          rating: "FIVE_STAR",
          category: "Strategy"
        },
        {
          id: 5,
          title: "Minecraft",
          price: 26.95,
          image: "https://images.app.goo.gl/iAtmrBFPtHCZfyBD7",
          rating: "THREE_STAR",
          category: "Action"
        }
      ],
      filteredGames: [],
      categories: ["Action", "RPG", "Adventure", "Strategy"],
      selectedCategory: "",
      cart: [],
    };
  },
  created() {
    const savedCart = localStorage.getItem('cart');
    if (savedCart) {
      this.cart = JSON.parse(savedCart);
    }
    this.filterByCategory();
  },
  methods: {
    filterByCategory() {
      if (this.selectedCategory) {
        this.filteredGames = this.games.filter(
          (game) => game.category === this.selectedCategory
        );
      } else {
        this.filteredGames = [...this.games];
      }
    },
    searchGame(query) {
      this.$router.push({ path: "/search-results", query: { query } });
    },
    addGame() {
      this.$router.push("/games/add-game");
    },
    addToCart(game) {
      const existingGame = this.cart.find(item => item.id === game.id);
      if (existingGame) {
        existingGame.quantity += 1;
      } else {
        this.cart.push({ ...game, quantity: 1 });
      }
      localStorage.setItem('cart', JSON.stringify(this.cart));
      alert(`${game.title} has been added to your cart!`);
    },
    goToGameDetails(gameId) {
      this.$router.push(`/games/${gameId}`);
    }
  }
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
  cursor: pointer;
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

.add-to-cart-button {
  background-color: #007bff;
  color: white;
  padding: 8px 16px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.add-to-cart-button:hover {
  background-color: #0056b3;
}
</style>
