<template>
    <div class="auth-page">
      <div class="auth-container">
        <h1>{{ isSignIn ? 'Sign In' : 'Create Account' }}</h1>
        <form @submit.prevent="handleSubmit">
          <div class="input-group">
            <label for="email">Email</label>
            <input
              type="email"
              id="email"
              v-model="email"
              placeholder="Enter your email"
              required
            />
          </div>
          <div class="input-group">
            <label for="password">Password</label>
            <input
              type="password"
              id="password"
              v-model="password"
              placeholder="Enter your password"
              required
            />
          </div>
          <div v-if="!isSignIn" class="input-group">
            <label for="confirmPassword">Confirm Password</label>
            <input
              type="password"
              id="confirmPassword"
              v-model="confirmPassword"
              placeholder="Confirm your password"
              required
            />
          </div>
          <button type="submit">{{ isSignIn ? 'Sign In' : 'Create Account' }}</button>
          <div class="auth-switch">
            <p @click="toggleAuthMode" class="switch-link">
              {{ isSignIn ? 'Create an Account' : 'Already have an account? Sign In' }}
            </p>
            <p @click="handleForgotPassword" class="forgot-password-link">Forgot password?</p>
          </div>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: "Login",
    data() {
      return {
        isSignIn: true, // Tracks whether the user is signing in or creating an account
        email: "",
        password: "",
        confirmPassword: "", // Only used for account creation
      };
    },
    methods: {
      handleSubmit() {
        if (this.isSignIn) {
          this.signIn();
        } else {
          this.createAccount();
        }
      },
      signIn() {
        console.log("Signing in with", this.email, this.password);
        // Add logic to sign in the user, such as an API request
        this.$router.push('/home');
      },
      createAccount() {
        if (this.password !== this.confirmPassword) {
          alert("Passwords do not match.");
          return;
        }
        console.log("Creating account with", this.email, this.password);
        // Add logic to create a new account, such as an API request
        this.$router.push('/home');
      },
      toggleAuthMode() {
        this.isSignIn = !this.isSignIn; // Toggle between sign in and create account
      },
      handleForgotPassword() {
        alert("Redirecting to password recovery page...");
        this.$router.push('/forgot-password'); // Example route for password recovery page
      },
    },
  };
  </script>
  
  <style scoped>
  .auth-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh; 
  background-color: #e9ecef; 
  margin: 0;
  }

  .auth-container {
    max-width: 400px;
    margin: 50px auto;
    padding: 20px;
    background-color: #f5f5f5;
    border-radius: 8px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    text-align: center;
  }
  
  h1 {
    text-align: center;
    margin-bottom: 20px;
    color: black;
  }
  
  .input-group {
    margin-bottom: 15px;
  }
  
  .input-group label {
    display: block;
    font-size: 14px;
    color: #555;
  }
  
  .input-group input {
    width: 100%;
    padding: 10px;
    margin-top: 5px;
    border-radius: 5px;
    border: 1px solid #ddd;
  }
  
  button {
    width: 100%;
    padding: 10px;
    background-color: #0040ff;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
  }
  
  button:hover {
    background-color: #0033cc;
  }
  
  .auth-switch {
    text-align: center;
    margin-top: 15px;
  }
  
  .switch-link {
    color: #0040ff;
    cursor: pointer;
    text-decoration: underline;
  }
  
  .switch-link:hover {
    color: #0033cc;
  }
  
  .forgot-password-link {
    color: #0040ff;
    cursor: pointer;
    text-decoration: underline;
  }
  
  .forgot-password-link:hover {
    color: #0033cc;
  }
  </style>
  