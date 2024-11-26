<template>
    <div class="cart-page">
      <NavBar />
      <div class="cart-container">
        <h1>Your Shopping Cart</h1>
        <div v-if="cartItems.length > 0" class="cart-items">
          <div v-for="(item, index) in cartItems" :key="item.id" class="cart-item">
            <img :src="item.image" alt="Game Image" class="item-image" />
            <div class="item-details">
              <h2>{{ item.title }}</h2>
              <p>Price: ${{ item.price.toFixed(2) }}</p>
              <p>Quantity: {{ item.quantity }}</p>
              <div class="item-actions">
                <button @click="increaseQuantity(index)">+</button>
                <button @click="decreaseQuantity(index)">-</button>
                <button @click="removeItem(index)">Remove</button>
              </div>
            </div>
          </div>
        </div>
        <div v-else>
          <p>Your cart is empty.</p>
        </div>
        <div class="cart-summary" v-if="cartItems.length > 0">
          <h3>Total: ${{ calculateTotal().toFixed(2) }}</h3>
          <div class="action-buttons">
            <button @click="checkout">Checkout</button>
            <button @click="clearCart">Clear Cart</button>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import NavBar from "./NavBar.vue";
  
  export default {
    name: "CartPage",
    components: { NavBar },
    data() {
      return {
        cartItems: [] // Start with an empty cart
      };
    },
    created() {
      this.loadCart();
    },
    methods: {
      loadCart() {
        // Fetch cart items from localStorage or an API
        const savedCart = JSON.parse(localStorage.getItem("cart") || "[]");
        if (savedCart.length > 0) {
          this.cartItems = savedCart;
        }
      },
      addGameToCart(game) {
        // Check if game is already in the cart
        const existingGame = this.cartItems.find(item => item.id === game.id);
        if (existingGame) {
          existingGame.quantity++;
        } else {
          this.cartItems.push({...game, quantity: 1});
        }
        this.updateCart();
      },
      increaseQuantity(index) {
        this.cartItems[index].quantity++;
        this.updateCart();
      },
      decreaseQuantity(index) {
        if (this.cartItems[index].quantity > 1) {
          this.cartItems[index].quantity--;
          this.updateCart();
        }
      },
      removeItem(index) {
        this.cartItems.splice(index, 1);
        this.updateCart();
      },
      updateCart() {
        localStorage.setItem("cart", JSON.stringify(this.cartItems));
      },
      calculateTotal() {
        return this.cartItems.reduce(
          (total, item) => total + item.price * item.quantity,
          0
        );
      },
      checkout() {
        alert("Proceeding to checkout!");
        // You could route to a checkout page or clear the cart here
        localStorage.removeItem("cart");
        this.cartItems = [];
      },
      clearCart() {
        localStorage.removeItem("cart");
        this.cartItems = [];
      }
    }
  };
  </script>
  
  <style scoped>
  .cart-page {
    padding: 20px;
    margin-top: 80px; /* Adjust for fixed NavBar */
    font-family: Arial, sans-serif;
    background-color: #f5f5f5;
  }
  
  .cart-container {
    max-width: 800px;
    margin: auto;
    background: #fff;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  
  .cart-items {
    margin-bottom: 20px;
  }
  
  .cart-item {
    display: flex;
    align-items: center;
    margin-bottom: 15px;
    border-bottom: 1px solid #ddd;
    padding-bottom: 15px;
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
  
  .item-actions button {
    margin: 5px;
    padding: 5px 10px;
    font-size: 14px;
    cursor: pointer;
  }
  
  .cart-summary {
    text-align: center;
    margin-top: 20px;
  }
  
  .action-buttons {
    display: flex;
    justify-content: center;
    gap: 15px;
    margin-top: 10px;
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
  </style>
  