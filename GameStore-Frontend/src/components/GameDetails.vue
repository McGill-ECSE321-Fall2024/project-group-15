<template>
  <div class="game-details-page">
    <NavBar />
    <div class="content-container">
      <div v-if="game" class="game-details">
        <!-- Left side: Game Image and Basic Info -->
        <div class="game-info">
          <img :src="game.image" alt="Game Image" class="game-image" />
          <div class="game-info-text">
            <h2>{{ game.title }}</h2>
            <p><strong>Price:</strong> ${{ game.price.toFixed(2) }}</p>

            <button @click="addToCart(game)">Add to Cart</button>
            <button @click="toggleWishlist(game)">
              {{ isInWishlist(game) ? 'Remove from Wishlist' : 'Add to Wishlist' }}
            </button>
          </div>
        </div>

        <!-- Right side: Game Description (Synopsis) -->
        <div class="game-synopsis">
          <h3>Game Synopsis</h3>
          <p>{{ game.description }}</p>
        </div>
      </div>
      <div v-else class="loading-message">
        <p>Loading game details...</p>
      </div>
    </div>
  </div>
</template>


<script>
import axios from "./axios";
import NavBar from "./NavBar.vue";

export default {
  name: "GameDetailsPage",
  components: { NavBar },
  data() {
    return {
      game: null, // To hold the fetched game details
      cart: [],
      wishlist: [],
    };
  },
  async created() {
    try {
      const gameId = this.$route.params.id; // Get game ID from the URL
      const response = await axios.get(`/game/${gameId}`); // Fetch game details from the backend
      this.game = response.data;

      // Load cart and wishlist from local storage
      const savedCart = localStorage.getItem("cart");
      const savedWishlist = localStorage.getItem("wishlist");
      if (savedCart) this.cart = JSON.parse(savedCart);
      if (savedWishlist) this.wishlist = JSON.parse(savedWishlist);
    } catch (error) {
      console.error("Error fetching game details:", error);
      alert("Failed to load game details. Please try again later.");
    }
  },
  methods: {
    addToCart(game) {
      const existingGame = this.cart.find((item) => item.id === game.id);
      if (existingGame) existingGame.quantity += 1;
      else this.cart.push({ ...game, quantity: 1 });

      localStorage.setItem("cart", JSON.stringify(this.cart));
      alert(`${game.title} has been added to your cart!`);
    },
    toggleWishlist(game) {
      const index = this.wishlist.findIndex((item) => item.id === game.id);
      if (index !== -1) {
        // Remove from wishlist if it exists
        this.wishlist.splice(index, 1);
        alert(`${game.title} has been removed from your wishlist.`);
      } else {
        // Add to wishlist
        this.wishlist.push(game);
        alert(`${game.title} has been added to your wishlist.`);
      }
      localStorage.setItem("wishlist", JSON.stringify(this.wishlist));
    },
    isInWishlist(game) {
      return this.wishlist.some((item) => item.id === game.id);
    },
  },
};
</script>

<style scoped>
.game-details-page {
  padding-top: 60px; /* Add space for NavBar height */
  background-color: #f5f5f5;
  min-height: 100vh; /* Ensures full-screen height */
  box-sizing: border-box; /* Include padding in height/width calculations */
}

.content-container {
  padding: 20px;
}

.game-details {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 20px;
}

.game-info {
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 40%;
}

.game-info-text {
  text-align: center;
  margin-top: 10px;
}

.game-image {
  width: 100%;
  max-width: 300px;
  height: auto;
  border-radius: 10px;
  object-fit: cover;
}

.game-synopsis {
  flex: 1;
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

button {
  margin-top: 10px;
  background-color: #007bff;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #0056b3;
}

.loading-message {
  text-align: center;
  font-size: 18px;
  color: #333;
  margin-top: 50px;
}
</style>
