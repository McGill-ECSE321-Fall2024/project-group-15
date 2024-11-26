<template>
  <nav class="navbar">
    <div class="navbar-container">
      <!-- Logo -->
      <router-link to="/" class="navbar-logo">GameStore</router-link>
      
      <!-- Navigation Links -->
      <div class="navbar-links">
        <router-link to="/" class="nav-link">Home</router-link>
        <router-link to="/games" class="nav-link">Games</router-link>
        <router-link to="/about" class="nav-link">About</router-link>
        <router-link to="/cart" class="nav-link">Cart</router-link>
      </div>
    </div>
  </nav>
</template>

<style>
/* Styles for the navigation bar */
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.5rem 1rem;
  background-color: #333;
  color: white;
}

.navbar-container {
  display: flex;
  align-items: center;
  width: 100%;
}

.navbar-logo {
  font-size: 1.5rem;
  font-weight: bold;
  color: white;
  text-decoration: none;
  margin-right: 1rem;
}

.navbar-links {
  display: flex;
  gap: 1rem;
}

.nav-link {
  color: white;
  text-decoration: none;
  font-size: 1rem;
  transition: color 0.3s ease;
}

.nav-link:hover {
  color: #ff9800;
}
</style>
