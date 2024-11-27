<template>
    <div class="receipt-page">
      <h1>Thank You for Your Purchase!</h1>
      <div class="receipt-summary">
        <h2>Order Summary</h2>
        <p>Order Number: <strong>{{ orderData.orderNumber }}</strong></p>
        <ul>
          <li v-for="(game, index) in orderData.cartItems" :key="index">
            {{ game.title }} (x{{ game.quantity }}) - ${{ (game.price * game.quantity).toFixed(2) }}
          </li>
        </ul>
        <p><strong>Total: ${{ orderData.totalPrice.toFixed(2) }}</strong></p>
      </div>
      <button @click="goHome" class="home-button">Return to Home</button>
    </div>
  </template>
  
  <script>
  export default {
    name: "Receipt",
    data() {
      return {
        orderData: {},
      };
    },
    created() {
      const storedOrder = JSON.parse(sessionStorage.getItem("orderData") || "{}");
      if (!storedOrder.cartItems) {
        alert("No order data found.");
        this.$router.push("/home");
      } else {
        this.orderData = storedOrder;
      }
    },
    methods: {
      goHome() {
        this.$router.push("/home");
      },
    },
  };
  </script>
  
  <style scoped>
  .receipt-page {
    text-align: center;
    padding: 20px;
    font-family: Arial, sans-serif;
    background-color: #f5f5f5;
  }
  
  .receipt-summary {
    background: #fff;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    max-width: 600px;
    margin: 20px auto;
    text-align: left;
  }
  
  ul {
    list-style: none;
    padding: 0;
  }
  
  .home-button {
    margin-top: 20px;
    padding: 10px 20px;
    background-color: #0040ff;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
  }
  
  .home-button:hover {
    background-color: #002080;
  }
  </style>
  