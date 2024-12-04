<template>
  <div class="game-list-page">
    <NavBar />
    <div class="header-actions">
      <SearchBar class="search-bar" @search="searchGame" />
    </div>

    <!-- Featured Games Section -->
    <div class="featured-games-section">
      <h2 class="centered-header">Featured Games (5-Star Rated)</h2>
      <div class="game-cards featured">
        <div v-for="game in featuredGames" :key="game.id" class="game-card">
          <img :src="game.image" alt="Game Image" class="game-image" />
          <h3 @click="goToGameDetails(game.id)">{{ game.title }}</h3>
          <div class="price">
            <p v-if="game.promoPrice">
              <span class="original-price">${{ game.price.toFixed(2) }}</span>
              <span class="promo-price">${{ game.promoPrice.toFixed(2) }}</span>
            </p>
            <p v-else>
              ${{ game.price.toFixed(2) }}
            </p>
          </div>
          <button @click="addToCart(game)">Add to Cart</button>
          <button @click="addToWishlist(game)">Add to Wishlist</button>
        </div>
      </div>
    </div>

    <!-- Category Games Section -->
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
          <div class="price">
            <p v-if="game.promoPrice">
              <span class="original-price">${{ game.price.toFixed(2) }}</span>
              <span class="promo-price">${{ game.promoPrice.toFixed(2) }}</span>
            </p>
            <p v-else>
              <div v-if="game && game.price !== undefined">
              ${{ game.price.toFixed(2) }}
              </div>
            </p>
          </div>
          <button @click="addToCart(game)">Add to Cart</button>
          <button @click="addToWishlist(game)">Add to Wishlist</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "./axios";
import NavBar from "./NavBar.vue";
import SearchBar from "./SearchBar.vue";

export default {
  name: "GameList",
  components: { NavBar, SearchBar },
  data() {
    return {
      games: [],
      filteredGames: [],
      categories: [],
      selectedCategory: "",
      cart: [],
      wishlist: [],
      featuredGames: [],
    };
  },

   // Fetch categories
   async fetchCategories() {
      try {
        const response = await axiosClient.get("/categories");
        this.categories = response.data || [];
      } catch (error) {
        console.error("Error fetching categories:", error);
      }
    },
    
  async created() {
    try {
      const savedCart = localStorage.getItem("cart");
      const savedWishlist = localStorage.getItem("wishlist");
      if (savedCart) this.cart = JSON.parse(savedCart);
      if (savedWishlist) this.wishlist = JSON.parse(savedWishlist);

      // Fetch games from backend
      const response = await axios.get("/games");
      this.games = response.data;
      this.filterByCategory();
      this.setFeaturedGames();
    } catch (error) {
      console.error("Error fetching games:", error);
    }
  },
  methods: {
    filterByCategory() {
      this.filteredGames = this.selectedCategory
        ? this.games.filter((game) => game.category === this.selectedCategory)
        : [...this.games];
    },
    setFeaturedGames() {
      this.featuredGames = this.games.filter(
        (game) => game.rating === "FIVE_STAR"
      );
    },
    addToCart(game) {
      const existingGame = this.cart.find((item) => item.id === game.id);
      if (existingGame) existingGame.quantity += 1;
      else this.cart.push({ ...game, quantity: 1 });
      localStorage.setItem("cart", JSON.stringify(this.cart));
      alert(`${game.title} has been added to your cart!`);
    },
    addToWishlist(game) {
      const existingGame = this.wishlist.find((item) => item.id === game.id);
      if (!existingGame) {
        this.wishlist.push(game);
        localStorage.setItem("wishlist", JSON.stringify(this.wishlist));
        alert(`${game.title} has been added to your wishlist!`);
      } else {
        alert(`${game.title} is already in your wishlist.`);
      }
    },
    searchGame(query) {
      this.$router.push({ path: "/search-results", query: { query } });
    },
    goToGameDetails(gameId) {
      this.$router.push(`/games/${gameId}`);
    },
  },
};
</script>

<style scoped>
.game-list-page {
  padding: 20px;
  background-color: #f5f5f5;
  color: #333; /* Default text color */
}

.header-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 30px;
  margin-bottom: 20px;
}

.search-bar {
  margin-left: auto;
}

.game-cards {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: center;
}

.game-card {
  width: 200px;
  border: 1px solid black; /* Black outline added here */
  border-radius: 10px;
  padding: 10px;
  text-align: center;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  color: #333; /* Ensures text inside cards is visible */
}

.game-card img {
  max-width: 100%;
  height: 150px;
  object-fit: cover;
  border-radius: 5px;
}

.game-card h3,
.game-card p {
  color: #333; /* Dark text for headings and paragraphs inside cards */
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

.featured-games-section {
  background-color: gold;
  padding: 20px;
  border-radius: 10px;
  color: #333; /* Text color for featured section */
}

.featured-games-section h3,
.featured-games-section p {
  color: #333; /* Ensure headings and text in the featured section are visible */
}

.centered-header {
  text-align: center;
  margin-bottom: 20px;
  color: #333; /* Dark color for centered headers */
}

.filters {
  text-align: center;
  margin-bottom: 20px;
}

.filters select {
  padding: 8px;
  font-size: 16px;
  border-radius: 5px;
  border: 1px solid #ccc;
  color: #333; /* Text color for dropdowns */
}

.wishlist {
  margin-top: 30px;
}

.wishlist h3 {
  text-align: center;
  color: #333; /* Dark text for wishlist headings */
}

.price {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 10px;
}

.original-price {
  text-decoration: line-through;
  color: red;
  font-size: 14px;
}

.promo-price {
  color: green;
  font-size: 16px;
  font-weight: bold;
}

</style>
