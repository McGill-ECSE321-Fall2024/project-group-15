<template>
  <div class="account-page">
    <header>
      <h1>Account Settings</h1>
    </header>

    <div class="form-container">
      <!-- Current User Info Section -->
      <div class="form-section">
        <h2>Current User Info</h2>
        <p><strong>Username:</strong> {{ currentUsername }}</p>
        <p><strong>Email:</strong> {{ currentEmail }}</p>
      </div>

      <!-- Update Password Section -->
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
  </div>
</template>

<script>
export default {
  name: "Account",
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
      currentEmail: localStorage.getItem("email") || "",
      newEmail: "",
      confirmEmail: "",
      emailErrorMessage: "",
      emailSuccessMessage: "",

      // Username fields
      currentUsername: localStorage.getItem("username") || "",
      newUsername: "",
      confirmUsername: "",
      usernameErrorMessage: "",
      usernameSuccessMessage: "",
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

      if (this.currentPassword === localStorage.getItem("password")) {
        this.passwordSuccessMessage = "Your password has been successfully changed!";
        this.passwordErrorMessage = "";
        localStorage.setItem("password", this.newPassword); // Save updated password
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

      if (this.currentEmail === localStorage.getItem("email")) {
        this.emailSuccessMessage = "Your email has been successfully changed!";
        this.emailErrorMessage = "";
        this.currentEmail = this.newEmail; // Update the current email on success
        localStorage.setItem("email", this.newEmail); // Save updated email
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

      if (this.currentUsername === localStorage.getItem("username")) {
        this.usernameSuccessMessage = "Your username has been successfully changed!";
        this.usernameErrorMessage = "";
        this.currentUsername = this.newUsername; // Update the current username on success
        localStorage.setItem("username", this.newUsername); // Save updated username
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
  },
};
</script>


<style scoped>
/* Ensure there's space below the fixed navbar */
.account-page {
  font-family: Arial, sans-serif;
  max-width: 1000px;
  margin: 0 auto;
  padding: 20px;
  margin-top: 80px; /* Adjust the margin-top to ensure content is below navbar */
}

header {
  text-align: center;
  margin-bottom: 30px;
}

.form-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: space-between;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
}

.form-section {
  flex: 1 1 calc(33% - 20px); /* Each section takes up roughly 1/3 of the container */
  margin-bottom: 20px;
  padding: 15px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h2 {
  color: #333;
  margin-bottom: 10px;
}

.input-group {
  margin-bottom: 15px;
}

.input-group label {
  display: block;
  margin-bottom: 5px;
}

.input-group input {
  width: 100%;
  padding: 10px;
  border-radius: 4px;
  border: 1px solid #ccc;
}

.input-with-toggle {
  display: flex;
  align-items: center;
}

.input-with-toggle button {
  background: none;
  border: none;
  color: #007bff;
  cursor: pointer;
  margin-left: 10px;
}

.submit-button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 4px;
  width: 100%;
}

.submit-button:hover {
  background-color: #0056b3;
}

.error-message {
  color: red;
  font-size: 14px;
}

.success-message {
  color: green;
  font-size: 14px;
}
</style>
