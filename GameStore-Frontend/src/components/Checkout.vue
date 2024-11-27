<template>
    <div class="checkout-page">
      <h1>Checkout</h1>
      <div class="order-summary">
        <h2>Your Order:</h2>
        <div class="order-items">
          <div v-for="(game, index) in cartItems" :key="index" class="order-item">
            <img :src="game.image" alt="Game Image" class="item-image" />
            <div class="item-details">
              <h3>{{ game.title }}</h3>
              <p>Quantity: {{ game.quantity }}</p>
              <p>Price: ${{ (game.price * game.quantity).toFixed(2) }}</p>
            </div>
          </div>
        </div>
        <div class="total">
          <strong>Total: ${{ totalPrice.toFixed(2) }}</strong>
        </div>
      </div>
  
      <button class="proceed-button" @click="unlockPaymentForm">Proceed</button>
  
      <div v-if="isPaymentFormUnlocked" class="payment-form">
        <h3>Select Payment Method</h3>
        <div>
          <label>
            <input type="radio" v-model="paymentMethod" value="existing" /> Existing Payment Option
          </label>
          <label>
            <input type="radio" v-model="paymentMethod" value="new" /> New Payment Option
          </label>
        </div>
  
        <div v-if="paymentMethod === 'existing'">
          <h4>Choose your existing payment option</h4>
          <select v-model="selectedPaymentOption">
            <option v-for="(option, index) in paymentOptions" :key="index" :value="option">
              {{ option }}
            </option>
          </select>
        </div>
  
        <div v-if="paymentMethod === 'new'">
          <h4>Enter New Payment Information</h4>
          <div class="input-group">
            <label for="new-card-number">Card Number</label>
            <input type="text" v-model="newCardNumber" id="new-card-number" placeholder="Enter card number" />
          </div>
          <div class="input-group">
            <label for="new-card-expiry">Expiry Date</label>
            <input type="text" v-model="newCardExpiry" id="new-card-expiry" placeholder="MM/YY" />
          </div>
          <div class="input-group">
            <label for="new-card-cvc">CVC</label>
            <input type="text" v-model="newCardCvc" id="new-card-cvc" placeholder="CVC" />
          </div>
          <div class="input-group">
            <label for="address">Address</label>
            <input type="text" v-model="address" id="address" placeholder="Enter your address" />
          </div>
        </div>
  
        <button class="submit-payment" @click="submitPayment">Submit Payment</button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: "Checkout",
    data() {
      return {
        cartItems: [],
        totalPrice: 0,
        isPaymentFormUnlocked: false,
        paymentMethod: null,
        selectedPaymentOption: "",
        paymentOptions: ["Visa **** 1234", "Mastercard **** 5678"],
        newCardNumber: "",
        newCardExpiry: "",
        newCardCvc: "",
        address: "",
      };
    },
    created() {
      const storedCart = JSON.parse(sessionStorage.getItem("cart") || "[]");
      if (storedCart.length === 0) {
        alert("Your cart is empty!");
        this.$router.push("/cart");
        return;
      }
      this.cartItems = storedCart;
      this.totalPrice = this.cartItems.reduce(
        (total, game) => total + game.price * game.quantity,
        0
      );
    },
    methods: {
      unlockPaymentForm() {
        this.isPaymentFormUnlocked = true;
      },
      submitPayment() {
        if (this.paymentMethod === "new") {
          if (!this.newCardNumber || !this.newCardExpiry || !this.newCardCvc || !this.address) {
            alert("Please fill all the fields for the new payment option.");
            return;
          }
        }
  
        if (this.paymentMethod === "existing" && !this.selectedPaymentOption) {
          alert("Please select an existing payment option.");
          return;
        }
  
        const orderData = {
          cartItems: this.cartItems,
          totalPrice: this.totalPrice,
          orderNumber: Math.floor(Math.random() * 1000000), // Generate random order number
        };
  
        sessionStorage.setItem("orderData", JSON.stringify(orderData));
        sessionStorage.removeItem("cart"); // Clear cart
        this.$router.push("/receipt");
      },
    },
  };
  </script>
  
  <style scoped>
.checkout-page {
  padding: 20px;
  margin-top: 60px;
  font-family: Arial, sans-serif;
  background-color: #f5f5f5;
  color: #333;
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 100vh; 
}

h1 {
  font-size: 2.5rem;
  color: #0040ff;
  margin-bottom: 20px;
  text-align: center;
}

.order-summary {
  background: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  width: 100%;
}

.order-items {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-bottom: 20px;
}

.order-item {
  display: flex;
  align-items: center;
  gap: 15px;
  background: #f9f9f9;
  padding: 15px;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.item-image {
  width: 100px;
  height: 100px;
  object-fit: cover;
  border-radius: 5px;
}

.item-details {
  flex: 1;
}

.item-details h3 {
  font-size: 1.2rem;
  margin-bottom: 5px;
}

.total {
  font-size: 1.5rem;
  font-weight: bold;
  text-align: right;
  color: #333;
}

.proceed-button {
  padding: 10px 20px;
  background-color: #0040ff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  margin-top: 20px;
}

.proceed-button:hover {
  background-color: #002080;
}

.payment-form {
  margin-top: 30px;
  max-width: 800px;
  width: 100%;
  text-align: center;
}

.input-group {
  margin-bottom: 15px;
  text-align: left;
}

.input-group label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
}

.input-group input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.submit-payment {
  padding: 10px 20px;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

.submit-payment:hover {
  background-color: #218838;
}
</style>

  