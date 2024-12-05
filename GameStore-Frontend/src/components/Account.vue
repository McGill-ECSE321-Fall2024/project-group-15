<template>
  <div class="account-page">
    <header>
      <h1>Account Settings</h1>
      <button class="review-history-button" @click="fetchPaymentHistory">Review Payment History</button>
    </header>

    <div class="form-container">
      <!-- Current User Info Section -->
      <div class="form-section">
        <h2>Current User Info</h2>
        <p><strong>Username:</strong> {{ currentUsername }}</p>
        <p><strong>Email:</strong> {{ currentEmail }}</p>
      </div>

      <!-- Update Password Section -->
      <!-- Existing password update form -->
      <div class="form-section">
        <h2>Update Password</h2>
        <form @submit.prevent="handlePasswordChange">
          <div class="input-group">
            <label for="current-password">Current Password</label>
            <div class="input-with-toggle">
              <input
                :type="showCurrentPassword ? 'text' : 'password'"
                v-model="currentPassword"
                id="current-password"
                placeholder="Enter your current password"
                required
              />
              <button type="button" @click="toggleVisibility('showCurrentPassword')">
                {{ showCurrentPassword ? 'Hide' : 'Show' }}
              </button>
            </div>
          </div>

          <div class="input-group">
            <label for="new-password">New Password</label>
            <input
              type="password"
              v-model="newPassword"
              id="new-password"
              placeholder="Enter your new password"
              required
            />
          </div>

          <div class="input-group">
            <label for="confirm-password">Confirm New Password</label>
            <input
              type="password"
              v-model="confirmPassword"
              id="confirm-password"
              placeholder="Confirm your new password"
              required
            />
          </div>

          <button type="submit" class="submit-button">Update Password</button>
          <p v-if="passwordErrorMessage" class="error-message">{{ passwordErrorMessage }}</p>
          <p v-if="passwordSuccessMessage" class="success-message">{{ passwordSuccessMessage }}</p>
        </form>
      </div>

      <!-- Update Email Section -->
      <!-- Existing email update form -->
      <div class="form-section">
        <h2>Update Email</h2>
        <form @submit.prevent="handleEmailChange">
          <div class="input-group">
            <label for="current-email">Current Email</label>
            <input
              type="email"
              v-model="currentEmail"
              id="current-email"
              placeholder="Enter your current email"
              required
            />
          </div>

          <div class="input-group">
            <label for="new-email">New Email</label>
            <input
              type="email"
              v-model="newEmail"
              id="new-email"
              placeholder="Enter your new email"
              required
            />
          </div>

          <div class="input-group">
            <label for="confirm-email">Confirm New Email</label>
            <input
              type="email"
              v-model="confirmEmail"
              id="confirm-email"
              placeholder="Confirm your new email"
              required
            />
          </div>

          <button type="submit" class="submit-button">Update Email</button>
          <p v-if="emailErrorMessage" class="error-message">{{ emailErrorMessage }}</p>
          <p v-if="emailSuccessMessage" class="success-message">{{ emailSuccessMessage }}</p>
        </form>
      </div>

      <!-- Update Username Section -->
      <!-- Existing username update form -->
      <div class="form-section">
        <h2>Update Username</h2>
        <form @submit.prevent="handleUsernameChange">
          <div class="input-group">
            <label for="current-username">Current Username</label>
            <input
              type="text"
              v-model="currentUsername"
              id="current-username"
              placeholder="Enter your current username"
              required
            />
          </div>

          <div class="input-group">
            <label for="new-username">New Username</label>
            <input
              type="text"
              v-model="newUsername"
              id="new-username"
              placeholder="Enter your new username"
              required
            />
          </div>

          <div class="input-group">
            <label for="confirm-username">Confirm New Username</label>
            <input
              type="text"
              v-model="confirmUsername"
              id="confirm-username"
              placeholder="Confirm your new username"
              required
            />
          </div>

          <button type="submit" class="submit-button">Update Username</button>
          <p v-if="usernameErrorMessage" class="error-message">{{ usernameErrorMessage }}</p>
          <p v-if="usernameSuccessMessage" class="success-message">{{ usernameSuccessMessage }}</p>
        </form>
      </div>
    </div>

    <!-- Order History Modal -->
    <div v-if="showOrderHistory" class="modal-overlay">
        <div class="modal">
          <header>
            <h2>Order History</h2>
            <button class="close-button" @click="showOrderHistory = false">Close</button>
          </header>
          <div class="modal-content">
            <div class="input-group">
              <label for="userId">Enter User ID:</label>
              <input
                type="number"
                v-model="userID"
                id="userId"
                placeholder="Enter your user ID"
              />
              <button @click="fetchOrderHistory" class="submit-button">Fetch Orders</button>
            </div>
            <ul v-if="orders.length">
              <li v-for="order in orders" :key="order.orderID" class="order-item">
                <p><strong>Order Number:</strong> {{ order.orderNumber }}</p>
                <p><strong>Status:</strong> {{ order.orderStatus }}</p>
                <p><strong>Price:</strong> ${{ order.price.toFixed(2) }}</p>
                <button @click="returnOrder(order.orderID)" class="return-button">Return</button>
              </li>
            </ul>
            <p v-else>No orders found for this user ID.</p>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      // Password fields
      currentPassword: "",
      newPassword: "",
      confirmPassword: "",
      showCurrentPassword: false,
      passwordErrorMessage: "",
      passwordSuccessMessage: "",

      // Email fields
      currentEmail: "email@mail.com", // Default current email
      newEmail: "",
      confirmEmail: "",
      emailErrorMessage: "",
      emailSuccessMessage: "",

      // Username fields
      currentUsername: "ManagerUsername", // Default current username
      newUsername: "",
      confirmUsername: "",
      usernameErrorMessage: "",
      usernameSuccessMessage: "",

      // Payment History
      showOrderHistory: false,
      username: "",
      orders: [],
    };
  },
  methods: {
    toggleVisibility(field) {
      this[field] = !this[field];
    },
    handlePasswordChange() {
      if (this.newPassword !== this.confirmPassword) {
        this.passwordErrorMessage = "New passwords don't match.";
        this.passwordSuccessMessage = "";
        return;
      }

      if (this.currentPassword === "password123") {
        this.passwordSuccessMessage = "Your password has been successfully changed!";
        this.passwordErrorMessage = "";
        this.resetPasswordFields();
      } else {
        this.passwordErrorMessage = "Current password is incorrect.";
        this.passwordSuccessMessage = "";
      }
    },
    handleEmailChange() {
      if (this.newEmail !== this.confirmEmail) {
        this.emailErrorMessage = "New emails don't match.";
        this.emailSuccessMessage = "";
        return;
      }

      if (this.currentEmail === "email@mail.com") {
        this.emailSuccessMessage = "Your email has been successfully changed!";
        this.emailErrorMessage = "";
        this.currentEmail = this.newEmail; // Update the current email on success
        this.resetEmailFields();
      } else {
        this.emailErrorMessage = "Current email is incorrect.";
        this.emailSuccessMessage = "";
      }
    },
    handleUsernameChange() {
      if (this.newUsername !== this.confirmUsername) {
        this.usernameErrorMessage = "New usernames don't match.";
        this.usernameSuccessMessage = "";
        return;
      }

      if (this.currentUsername === "ManagerUsername") {
        this.usernameSuccessMessage = "Your username has been successfully changed!";
        this.usernameErrorMessage = "";
        this.currentUsername = this.newUsername; // Update the current username on success
        this.resetUsernameFields();
      } else {
        this.usernameErrorMessage = "Current username is incorrect.";
        this.usernameSuccessMessage = "";
      }
    },
    resetPasswordFields() {
      this.currentPassword = "";
      this.newPassword = "";
      this.confirmPassword = "";
    },
    resetEmailFields() {
      this.newEmail = "";
      this.confirmEmail = "";
    },
    resetUsernameFields() {
      this.newUsername = "";
      this.confirmUsername = "";
    },
    
    fetchPaymentHistory() {
      this.showOrderHistory = true;
    },

    async fetchOrderHistory() {
      if (!this.userID) {
        alert("Please enter a valid user ID.");
        return;
      }
      try {
        const response = await axios.get(`/orders?userID=${this.userID}`);
        this.orders = response.data;
      } catch (error) {
        console.error("Error fetching orders:", error);
        alert("Failed to fetch order history.");
      }
    },

    async returnOrder(orderID) {
      if (!confirm("Are you sure you want to return this order?")) {
        return;
      }
      try {
        await axios.post(`/orders/${orderID}/return`);
        alert("Order returned successfully!");
        this.orders = this.orders.filter((order) => order.orderID !== orderID);
      } catch (error) {
        console.error("Error returning order:", error);
        alert("Failed to return the order.");
      }
    },
  },
};
</script>

<style scoped>
.account-page {
  padding-top: 80px; /* Ensures the header is not cut off */
  font-family: Arial, sans-serif;
  background-color: #f5f5f5;
}
header {
  text-align: center;
  margin-bottom: 20px;
}
h1 {
  color: #0040ff;
}

h2 {
  color: #000000;
}

p {
  color: #000000;
}

label {
  color: #000000;
}

.form-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: center;
}
.form-section {
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  width: 300px;
  flex: 1;
}
.input-group {
  margin-bottom: 15px;
}
.input-with-toggle {
  display: flex;
  align-items: center;
}
input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}
button {
  background: none;
  border: none;
  color: #0040ff;
  cursor: pointer;
}
.submit-button {
  width: 100%;
  background-color: #0040ff;
  color: white;
  border: none;
  padding: 10px;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}
.submit-button:hover {
  background-color: #002080;
}
.error-message {
  color: red;
  text-align: center;
}
.success-message {
  color: green;
  text-align: center;
}

.review-history-button {
  position: absolute;
  top: 80px;
  right: 1000px;
  background-color: #0040ff;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
.review-history-button:hover {
  background-color: #002080;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}
.modal {
  background: white;
  padding: 20px;
  border-radius: 8px;
  width: 400px;
  max-height: 80%;
  overflow-y: auto;
}

.modal header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.close-button {
  background: none;
  border: none;
  color: red;
  font-size: 1.2em;
  cursor: pointer;
}
.delete-button {
  background-color: red;
  color: white;
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.delete-button:hover {
  background-color: darkred;
}

.return-button {
  background-color: red;
  color: white;
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.return-button:hover {
  background-color: darkred;
}

.order-item {
  border-bottom: 1px solid #ddd;
  padding: 10px 0;
}

.order-item:last-child {
  border-bottom: none;
}
</style>
