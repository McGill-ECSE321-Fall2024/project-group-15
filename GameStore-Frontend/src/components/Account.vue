<template>
    <div class="account-page">
      <h1>Account Settings</h1>
      <p>Welcome to your account settings. You can change your password here.</p>
  
      <div class="password-change-form">
        <h2>Change Password</h2>
        <form @submit.prevent="handlePasswordChange">
          <!-- Current Password Field -->
          <div class="input-group">
            <label for="current-password">Current Password</label>
            <div class="password-wrapper">
              <input
                :type="showCurrentPassword ? 'text' : 'password'"
                v-model="currentPassword"
                id="current-password"
                placeholder="Enter your current password"
                required
              />
              <button type="button" @click="toggleShowCurrentPassword" class="show-password-btn">
                {{ showCurrentPassword ? 'Hide' : 'Show' }}
              </button>
            </div>
          </div>
  
          <!-- New Password Field -->
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
  
          <!-- Confirm New Password Field -->
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
  
          <button type="submit" class="submit-button">Change Password</button>
  
          <!-- Error/Success Messages -->
          <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
          <p v-if="successMessage" class="success-message">{{ successMessage }}</p>
        </form>
      </div>
    </div>
  </template>
  
  <style scoped>
  .account-page {
    padding: 20px;
    font-family: Arial, sans-serif;
    background-color: #f5f5f5;
    margin-top: 60px; /* Ensures content is positioned below the navbar */
  }
  
  h1 {
    color: #0040ff;
    margin-bottom: 10px;
  }
  
  .password-change-form {
    background-color: white;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    max-width: 500px;
    margin: auto;
  }
  
  h2 {
    font-size: 24px;
    margin-bottom: 15px;
    color: #0040ff;
  }
  
  .input-group {
    margin-bottom: 15px;
  }
  
  .input-group label {
    display: block;
    font-size: 14px;
    color: #333;
  }
  
  .input-group input {
    width: 100%;
    padding: 10px;
    font-size: 14px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
  }
  
  .password-wrapper {
    display: flex;
    align-items: center;
  }
  
  .show-password-btn {
    background: none;
    border: none;
    color: #0040ff;
    cursor: pointer;
    font-size: 14px;
    margin-left: 10px;
    padding: 0;
    display: inline-flex; /* Ensures the button is properly aligned */
    justify-content: center;
    align-items: center;
  }
  
  .submit-button {
    width: 100%;
    padding: 10px;
    background-color: #0040ff;
    color: white;
    border: none;
    border-radius: 4px;
    font-size: 16px;
    cursor: pointer;
    transition: background-color 0.3s;
  }
  
  .submit-button:hover {
    background-color: #002080;
  }
  
  .error-message {
    color: red;
    text-align: center;
    font-size: 14px;
    margin-top: 15px;
  }
  
  .success-message {
    color: green;
    text-align: center;
    font-size: 14px;
    margin-top: 15px;
  }
  </style>
  
  <script>
  export default {
    name: 'Account',
    data() {
      return {
        currentPassword: '',
        newPassword: '',
        confirmPassword: '',
        showCurrentPassword: false, // Controls visibility of current password
        errorMessage: '',
        successMessage: '',
      };
    },
    methods: {
      // Toggle visibility of the current password
      toggleShowCurrentPassword() {
        this.showCurrentPassword = !this.showCurrentPassword;
      },
      handlePasswordChange() {
        // Simple validation for password matching
        if (this.newPassword !== this.confirmPassword) {
          this.errorMessage = "New passwords don't match.";
          this.successMessage = '';
          return;
        }
  
        // Assume a simple check for the current password (replace with actual logic)
        if (this.currentPassword === "password123") { // Just an example condition
          // Simulate a password change process
          this.successMessage = 'Your password has been successfully changed!';
          this.errorMessage = '';
  
          // Reset the form fields after successful password change
          this.currentPassword = '';
          this.newPassword = '';
          this.confirmPassword = '';
        } else {
          this.errorMessage = 'Current password is incorrect.';
          this.successMessage = '';
        }
      },
    },
  };
  </script>
  