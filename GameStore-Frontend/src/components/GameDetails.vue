<template>
  <div class="game-details-page">
    <NavBar />
    <div class="game-details">
      <img :src="game.image" alt="Game Image" class="game-image" />
      <h2>{{ game.title }}</h2>
      <p>{{ game.description }}</p>
      <p><strong>Category:</strong> {{ game.category }}</p>
      <p><strong>Price:</strong> ${{ game.price.toFixed(2) }}</p>
      <p><strong>Rating:</strong> {{ game.rating }}</p>
      <button @click="addToCart(game)">Add to Cart</button>
    </div>
  </div>
</template>

<script>
import NavBar from "./NavBar.vue";

export default {
  name: "GameDetails",
  components: { NavBar },
  data() {
    return {
      game: null,  // To store the game details
      cart: []
    };
  },
  created() {
    const gameId = this.$route.params.id;
    this.fetchGameDetails(gameId);
    const savedCart = localStorage.getItem('cart');
    if (savedCart) {
      this.cart = JSON.parse(savedCart);
    }
  },
  methods: {
    fetchGameDetails(gameId) {
      // Static list of games for demonstration purposes
      const games = [
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
        }
      ];
      // Find the game that matches the gameId
      this.game = games.find(game => game.id === parseInt(gameId));
    },
    addToCart(game) {
      // Check if the game is already in the cart
      const existingGame = this.cart.find(item => item.id === game.id);
      
      if (existingGame) {
        // If game already in the cart, increment quantity
        existingGame.quantity += 1;
      } else {
        // If game not in the cart, add it with quantity 1
        this.cart.push({ ...game, quantity: 1 });
      }
  
      // Update the cart in localStorage
      localStorage.setItem('cart', JSON.stringify(this.cart));
  
      // Provide user feedback
      alert(`${game.title} has been added to your cart! Quantity: ${existingGame ? existingGame.quantity : 1}`);
    }
  }
};
</script>

<style scoped>
.game-details-page {
  padding: 20px;
  background-color: #f5f5f5;
  margin-top: 60px; /* Added margin to avoid nav bar overlap */
}

.game-details {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

.game-image {
  max-width: 300px;
  height: auto;
  object-fit: cover;
  border-radius: 10px;
}

h2 {
  font-size: 2rem;
  font-weight: bold;
  margin-top: 20px;
}

p {
  font-size: 1.2rem;
  margin: 10px 0;
}

button {
  margin-top: 20px;
  background-color: #007bff;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}
</style>
