<template>
  <div class="game-list-page">
    <NavBar />
    <div class="header-actions">
      <button class="add-game-button" @click="addGame">Add Game</button>
      <SearchBar class="search-bar" @search="searchGame" />
    </div>

    <!-- Featured Games Section -->
    <div class="featured-games-section">
      <h2 class="centered-header">Featured Games (5-Star Rated)</h2>
      <div class="game-cards featured">
        <div v-for="game in featuredGames" :key="game.id" class="game-card">
          <img :src="game.image" alt="Game Image" class="game-image" />
          <h3 @click="goToGameDetails(game.id)">{{ game.title }}</h3>
          <p>${{ game.price.toFixed(2) }}</p>
          <button @click="addToCart(game)">Add to Cart</button>
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
import axios from "./axios";

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
          image: "https://store-images.s-microsoft.com/image/apps.20648.69531514236615003.534d4f71-03cb-4592-929a-b00a7de28b58.54adf0c7-6e6f-4d36-b639-503087c6fab2?q=90&w=177&h=177",
          rating: "FIVE_STAR",
          category: "Action"
        },
        {
          id: 2,
          title: "Red Dead Redemption 2",
          price: 59.99,
          image: "https://upload.wikimedia.org/wikipedia/en/thumb/4/44/Red_Dead_Redemption_II.jpg/220px-Red_Dead_Redemption_II.jpg",
          rating: "FIVE_STAR",
          category: "RPG"
        },
        {
          id: 3,
          title: "Cyberpunk 2077",
          price: 49.99,
          image: "https://image.api.playstation.com/vulcan/ap/rnd/202111/3013/6bAF2VVEamgKclalI0oBnoAe.jpg",       
          rating: "FOUR_STAR",
          category: "Adventure"
        },
        {
          id: 4,
          title: "Assassin's Creed Odyssey",
          price: 29.99,
          image: "https://i.imgur.com/txaFxgA.jpg",
          rating: "FIVE_STAR",
          category: "Strategy"
        },
        {
          id: 5,
          title: "Minecraft",
          price: 26.95,
          image: "https://www.minecraft.net/content/dam/minecraftnet/games/minecraft/key-art/Homepage_Discover-our-games_MC-Vanilla-KeyArt_864x864.jpg",
          rating: "THREE_STAR",
          category: "Action"
        },
        {
        id: 6,
        title: "God of War",
        price: 59.99,
        image: "https://upload.wikimedia.org/wikipedia/en/a/a7/God_of_War_4_cover.jpg",
        rating: "FIVE_STAR",
        category: "Action"
        },
        {
        id: 7,
        title: "The Last of Us Part II",
        price: 49.99,
        image: "https://upload.wikimedia.org/wikipedia/en/thumb/4/4f/TLOU_P2_Box_Art_2.png/220px-TLOU_P2_Box_Art_2.png",
        rating: "FIVE_STAR",
        category: "Action"
        },
        {
        id: 8,
        title: "Horizon Zero Dawn",
        price: 49.99,
        image: "https://upload.wikimedia.org/wikipedia/en/thumb/9/93/Horizon_Zero_Dawn.jpg/220px-Horizon_Zero_Dawn.jpg",
        rating: "FOUR_STAR",
        category: "Adventure"
       }
      ],
      filteredGames: [],
      categories: ["Action", "RPG", "Adventure", "Strategy"],
      selectedCategory: "",
      cart: [],
      featuredGames: [], // Array for featured 5-star games
    };
  },
  created() {
    const savedCart = localStorage.getItem('cart');
    if (savedCart) {
      this.cart = JSON.parse(savedCart);
    }
    this.filterByCategory();
    this.setFeaturedGames();
  },
  methods: {
    // Filter by Category
    filterByCategory() {
      if (this.selectedCategory) {
        this.filteredGames = this.games.filter(
          (game) => game.category === this.selectedCategory
        );
      } else {
        this.filteredGames = [...this.games];
      }
    },

    // Set the featured games (5-star rated games)
    setFeaturedGames() {
      this.featuredGames = this.games.filter(game => game.rating === "FIVE_STAR");
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

/* Featured Games Section */
.featured-games-section {
  background-color: gold;
  padding: 20px;
  border-radius: 10px;
  margin-top: 40px;
}

.featured-games-section h2 {
  font-size: 1.8rem;
  font-weight: bold;
  text-align: center;
  color: #333;
}

.featured .game-card {
  border: 2px solid gold;
  background-color: #fff8e1;
}

.featured .game-card img {
  border-radius: 8px;
}

.featured .game-card h3 {
  color: #000000;
}

</style>
