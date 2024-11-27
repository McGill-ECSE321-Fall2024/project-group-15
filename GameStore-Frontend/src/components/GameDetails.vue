<template>
  <div class="game-details-page">
    <NavBar />
    <div class="game-details">
      <!-- Left side: Game Image and Basic Info -->
      <div class="game-info">
        <img :src="game.image" alt="Game Image" class="game-image" />
        <div class="game-info-text">
          <h2>{{ game.title }}</h2>
          <p><strong>Category:</strong> {{ game.category }}</p>
          <p><strong>Price:</strong> ${{ game.price.toFixed(2) }}</p>
          <p><strong>Rating:</strong>
            <span class="star-rating">
              <span v-for="n in maxStars" :key="n" :class="getStarClass(n)">&starf;</span>
            </span>
          </p>
          <button @click="addToCart(game)">Add to Cart</button>
        </div>
      </div>

      <!-- Right side: Game Description (Synopsis) -->
      <div class="game-synopsis">
        <h3>Game Synopsis</h3>
        <p>{{ game.description }}</p>
      </div>
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
      cart: [],
      maxStars: 5, // Maximum number of stars (for a 5-star rating)
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
          category: "Action",
          description: "An action RPG set in a dark fantasy world. Follow Geralt of Rivia in a journey full of mystery, danger, and choices."
        },
        {
          id: 2,
          title: "Red Dead Redemption 2",
          price: 59.99,
          image: "https://upload.wikimedia.org/wikipedia/en/thumb/4/44/Red_Dead_Redemption_II.jpg/220px-Red_Dead_Redemption_II.jpg",
          rating: "FIVE_STAR",
          category: "RPG",
          description: "An epic western game where you explore a vast open world in the final years of the American frontier."
        },
        {
          id: 3,
          title: "Cyberpunk 2077",
          price: 49.99,
          image: "https://image.api.playstation.com/vulcan/ap/rnd/202111/3013/6bAF2VVEamgKclalI0oBnoAe.jpg",
          rating: "FOUR_STAR",
          category: "Adventure",
          description: "An open-world RPG set in the futuristic Night City, filled with high-tech and cybernetic enhancements."
        },
        {
          id: 4,
          title: "Assassin's Creed Odyssey",
          price: 29.99,
          image: "https://i.imgur.com/txaFxgA.jpg",
          rating: "FIVE_STAR",
          category: "Strategy",
          description: "An action-adventure game set in Ancient Greece. Fight, explore, and shape your destiny in a massive world."
        },
        {
          id: 5,
          title: "Minecraft",
          price: 26.95,
          image: "https://www.minecraft.net/content/dam/minecraftnet/games/minecraft/key-art/Homepage_Discover-our-games_MC-Vanilla-KeyArt_864x864.jpg",
          rating: "THREE_STAR",
          category: "Action",
          description: "A sandbox game where you can build, explore, and survive in a world made entirely of blocks."
        },
        {
        id: 6,
        title: "God of War",
        price: 59.99,
        image: "https://upload.wikimedia.org/wikipedia/en/a/a7/God_of_War_4_cover.jpg",
        rating: "FIVE_STAR",
        category: "Action",
        description: "God of War is an action-adventure game set in Norse mythology, where Kratos and his son Atreus face gods and mythical creatures in a journey filled with secrets and epic battles."
        },
        {
        id: 7,
        title: "The Last of Us Part II",
        price: 49.99,
        image: "https://upload.wikimedia.org/wikipedia/en/thumb/4/4f/TLOU_P2_Box_Art_2.png/220px-TLOU_P2_Box_Art_2.png",
        rating: "FIVE_STAR",
        category: "Action",
        description: "In The Last of Us Part II, Ellie seeks revenge in a post-apocalyptic world, facing difficult moral choices as she navigates a brutal, unforgiving environment."
        },
        {
        id: 8,
        title: "Horizon Zero Dawn",
        price: 49.99,
        image: "https://upload.wikimedia.org/wikipedia/en/thumb/9/93/Horizon_Zero_Dawn.jpg/220px-Horizon_Zero_Dawn.jpg",
        rating: "FOUR_STAR",
        category: "Adventure",
        description: "Horizon Zero Dawn follows Aloy, a hunter in a world dominated by robotic creatures. Players explore the ruins of the old world and fight machines in an effort to uncover the truth."
      }

      ];
      // Find the game that matches the gameId
      this.game = games.find(game => game.id === parseInt(gameId));
    },
    getStarClass(starIndex) {
      // Map the rating to the number of filled stars
      const ratingMapping = {
        "FIVE_STAR": 5,
        "FOUR_STAR": 4,
        "THREE_STAR": 3,
        "TWO_STAR": 2,
        "ONE_STAR": 1
      };
      const rating = ratingMapping[this.game.rating];
      return starIndex <= rating ? 'filled-star' : 'empty-star';
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
  justify-content: space-between; /* Added this for parallel layout */
  align-items: flex-start;
  text-align: left;
}

.game-info {
  flex: 1;
  max-width: 50%;
  padding-right: 20px;
}

.game-info-text {
  margin-top: 20px;
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

.game-synopsis {
  flex: 1;
  max-width: 50%;
  padding-left: 20px;
}

.game-synopsis h3 {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 10px;
}

.star-rating {
  font-size: 1.5rem;
  color: #ffcc00; /* Gold color for filled stars */
}

.star-rating .filled-star {
  color: #ffcc00; /* Gold color */
}

.star-rating .empty-star {
  color: #ddd; /* Light grey for empty stars */
}

strong {
  font-weight: bold;
}
</style>
