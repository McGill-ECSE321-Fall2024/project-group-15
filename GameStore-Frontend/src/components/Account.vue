  <template>
    <div class="account-page">
      <h1>Account Settings</h1>
      <p style="color: black;">Welcome to your account settings. You can change your password, email, and username here.</p>
  
      <div class="password-change-form">
        <h2>Update Password</h2>
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
  
          <button type="submit" class="submit-button">Update Password</button>
  
          <!-- Error/Success Messages -->
          <p v-if="passwordErrorMessage" class="error-message">{{ passwordErrorMessage }}</p>
          <p v-if="passwordSuccessMessage" class="success-message">{{ passwordSuccessMessage }}</p>
        </form>
      </div>
      
      <!-- Space between divs -->
      <br>

      <div class="email-change-form">
        <h2>Update Email</h2>
        <form @submit.prevent="handleEmailChange">
          <!-- Current Email Field -->
          <div class="input-group">
            <label for="current-email">Current Email</label>
            <div class="email-wrapper">
              <input
                type="email"
                v-model="currentEmail"
                id="current-email"
                placeholder="Enter your current email"
                required
              />
            </div>
          </div>
  
          <!-- New Email Field -->
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
  
          <!-- Confirm New Email Field -->
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
  
          <!-- Error/Success Messages -->
          <p v-if="emailErrorMessage" class="error-message">{{ emailErrorMessage }}</p>
          <p v-if="emailSuccessMessage" class="success-message">{{ emailSuccessMessage }}</p>
        </form>
      </div>

      <!-- Space between divs -->
      <br>

      <div class="username-change-form">
        <h2>Update Username</h2>
        <form @submit.prevent="handleUsernameChange">
          <!-- Current Username Field -->
          <div class="input-group">
            <label for="current-username">Current Username</label>
            <div class="username-wrapper">
              <input
                type="Username"
                v-model="currentUsername"
                id="current-username"
                placeholder="Enter your current username"
                required
              />
            </div>
          </div>
  
          <!-- New Username Field -->
          <div class="input-group">
            <label for="new-username">New Username</label>
            <input
              type="username"
              v-model="newUsername"
              id="new-username"
              placeholder="Enter your new username"
              required
            />
          </div>
  
          <!-- Confirm New Username Field -->
          <div class="input-group">
            <label for="confirm-username">Confirm New Username</label>
            <input
              type="username"
              v-model="confirmUsername"
              id="confirm-username"
              placeholder="Confirm your new username"
              required
            />
          </div>
  
          <button type="submit" class="submit-button">Update Username</button>
  
          <!-- Error/Success Messages -->
          <p v-if="usernameErrorMessage" class="error-message">{{ usernameErrorMessage }}</p>
          <p v-if="usernameSuccessMessage" class="success-message">{{ usernameSuccessMessage }}</p>
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
  
  .email-change-form {
    background-color: white;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    max-width: 500px;
    margin: auto;
  }

  .username-change-form {
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

  .email-wrapper {
    display: flex;
    align-items: center;
  }

  .username-wrapper {
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
    import axios from "axios";
  export default {

    /*const axiosClient = axios.create({
      // NOTE: it's baseURL, not baseUrl
      baseURL: "http://localhost:5173/account"
    });*/
    
    name: 'Account',
    data() {
      return {
        currentPassword: '',
        newPassword: '',
        confirmPassword: '',
        showCurrentPassword: false, // Controls visibility of current password
        passwordErrorMessage: '',
        passwordSuccessMessage: '',

        currentEmail: '',
        newEmail: '',
        confirmEmail: '',
        emailErrorMessage: '',
        emailSuccessMessage: '',

        currentUsername: '',
        newUsername: '',
        confirmUsername: '',
        usernameErrorMessage: '',
        usernameSuccessMessage: '',
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
          this.passwordErrorMessage = "New passwords don't match.";
          this.passwordSuccessMessage = '';
          return;
        }
  
        // Assume a simple check for the current password (replace with actual logic)
        if (this.currentPassword === "password123") { // Just an example condition
          // Simulate a password change process
          this.passwordSuccessMessage = 'Your password has been successfully changed!';
          this.passwordErrorMessage = '';
  
          // Reset the form fields after successful password change
          this.currentPassword = '';
          this.newPassword = '';
          this.confirmPassword = '';
        } else {
          this.passwordErrorMessage = 'Current password is incorrect.';
          this.passwordSuccessMessage = '';
        }
      },

      handleEmailChange() {
        // Simple validation for email matching
        if (this.newEmail !== this.confirmEmail) {
          this.emailErrorMessage = "New emails don't match.";
          this.emailSuccessMessage = '';
          return;
        }
  
        // Assume a simple check for the current email (replace with actual logic)
        if (this.currentEmail === "email@mail.com") { // Just an example condition
          // Simulate a email change process
          this.emailSuccessMessage = 'Your email has been successfully changed!';
          this.emailErrorMessage = '';
  
          // Reset the form fields after successful password change
          this.currentEmail = '';
          this.newEmail = '';
          this.confirmEmail = '';
        } else {
          this.emailErrorMessage = 'Current email is incorrect.';
          this.emailSuccessMessage = '';
        }
      },

      handleUsernameChange() {
        // Simple validation for email matching
        if (this.newUsername !== this.confirmUsername) {
          this.usernameErrorMessage = "New usernames don't match.";
          this.usernameSuccessMessage = '';
          return;
        }
  
        // Assume a simple check for the current username (replace with actual logic)
        if (this.currentUsername === "ManagerUsername") { // Just an example condition
          // Simulate a username change process
          this.usernameSuccessMessage = 'Your username has been successfully changed!';
          this.usernameErrorMessage = '';
  
          // Reset the form fields after successful password change
          this.currentUsername = '';
          this.newUsername = '';
          this.confirmUsername = '';
        } else {
          this.usernameErrorMessage = 'Current username is incorrect.';
          this.usernameSuccessMessage = '';
        }
      },

    },
  };
  </script>
  