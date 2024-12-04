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
  padding: 40px 20px;
  font-family: Arial, sans-serif;
  background-color: #f5f5f5;
  color: #333; 
  min-height: 100vh; 
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

h1 {
  font-size: 2.5rem;
  color: #0040ff; 
  margin-bottom: 20px;
}

.receipt-summary {
  background: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  max-width: 600px;
  width: 100%;
  text-align: left; 
  color: #444;
}

.receipt-summary h2 {
  font-size: 1.5rem;
  color: #333; 
  margin-bottom: 10px;
}

.receipt-summary p {
  font-size: 1rem;
  margin: 10px 0;
}

.receipt-summary ul {
  list-style: none;
  padding: 0;
}

.receipt-summary ul li {
  font-size: 1rem;
  line-height: 1.5;
  margin-bottom: 8px;
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
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.home-button:hover {
  background-color: #002080;
}

.home-button:active {
  background-color: #001a66;
}
</style>

  