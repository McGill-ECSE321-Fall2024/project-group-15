<template>
  <div class="wishlist-page">
    <NavBar />
    <div class="wishlist-container">
      <h1>Your Wishlist</h1>
      <div v-if="wishlistItems.length > 0" class="wishlist-items">
        <div v-for="(item, index) in wishlistItems" :key="item.id" class="wishlist-item">
          <img :src="item.image" alt="Game Image" class="item-image" />
          <div class="item-details">
            <h2>{{ item.title }}</h2>
            <p>Price: ${{ item.price.toFixed(2) }}</p>
            <div class="item-actions">
              <button @click="addToCart(item)">Add to Cart</button>
              <button @click="removeItem(index)">Remove from Wishlist</button>
            </div>
          </div>
        </div>
        <button @click="addAllToCart" class="add-all-to-cart">Add All to Cart</button>
      </div>
      <div v-else>
        <p>Your wishlist is empty.</p>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from "./NavBar.vue";

export default {
  name: "WishlistPage",
  components: { NavBar },
  data() {
    return {
      wishlistItems: [],
      cart: [],
    };
  },
  created() {
    this.loadWishlist();
    const savedCart = localStorage.getItem("cart");
    if (savedCart) {
      this.cart = JSON.parse(savedCart);
    }
  },
  methods: {
    loadWishlist() {
      const savedWishlist = JSON.parse(localStorage.getItem("wishlist") || "[]");
      if (savedWishlist.length > 0) {
        this.wishlistItems = savedWishlist;
      }
    },
    addToCart(item) {
      const existingGame = this.cart.find((cartItem) => cartItem.id === item.id);
      if (existingGame) {
        existingGame.quantity += 1;
      } else {
        this.cart.push({ ...item, quantity: 1 });
      }
      localStorage.setItem("cart", JSON.stringify(this.cart));
      alert(`${item.title} has been added to your cart!`);
    },
    removeItem(index) {
      this.wishlistItems.splice(index, 1);
      localStorage.setItem("wishlist", JSON.stringify(this.wishlistItems));
    },
    addAllToCart() {
      this.wishlistItems.forEach(item => {
        const existingGame = this.cart.find(cartItem => cartItem.id === item.id);
        if (existingGame) {
          existingGame.quantity += 1;
        } else {
          this.cart.push({ ...item, quantity: 1 });
        }
      });
      localStorage.setItem("cart", JSON.stringify(this.cart));
      alert("All items have been added to your cart!");
    },
  },
};
</script>

<style scoped>
.wishlist-page {
  padding: 20px;
  margin-top: 80px; /* Adjust for fixed NavBar */
  font-family: Arial, sans-serif;
  background-color: #f5f5f5;
  color: #333; /* Default text color */
}

.wishlist-container {
  max-width: 800px;
  margin: auto;
  background: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h1 {
  font-size: 2rem;
  color: #333; /* Dark header color */
  margin-bottom: 20px;
  text-align: center;
}

.wishlist-items {
  margin-bottom: 20px;
}

.wishlist-item {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
  border-bottom: 1px solid #ddd;
  padding-bottom: 15px;
  color: #333; /* Ensure text inside items is visible */
}

.item-image {
  width: 100px;
  height: 100px;
  object-fit: cover;
  margin-right: 15px;
}

.item-details {
  flex: 1;
}

.item-details h2 {
  font-size: 1.5rem;
  margin-bottom: 5px;
  color: #333; /* Dark color for game titles */
}

.item-details p {
  font-size: 1rem;
  color: #555; /* Slightly lighter color for prices */
}

.item-actions button {
  margin: 5px;
  padding: 5px 10px;
  font-size: 14px;
  cursor: pointer;
}

button {
  background-color: #0040ff;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  font-size: 14px;
}

button:hover {
  background-color: #002080;
}

button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.add-all-to-cart {
  margin-top: 20px;
  background-color: #28a745;
  font-size: 16px;
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

.add-all-to-cart:hover {
  background-color: #218838;
}

p {
  font-size: 1rem;
  color: #333; /* Ensure paragraphs, like empty messages, are visible */
  text-align: center;
}
</style>
