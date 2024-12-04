<template>
  <div class="home-page">
    <NavBar />
    <div class="welcome-section">
      <h1>Welcome to the Game Store</h1>
      <p>Discover amazing games, explore new releases, and shop for your favorites.</p>
      <SearchBar />
    </div>
  </div>
</template>

<script>
import NavBar from "./NavBar.vue";
import SearchBar from "./SearchBar.vue";

export default {
  name: "Home",
  components: {
    NavBar,
    SearchBar,
  },
};
</script>

<style scoped>
.home-page {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh; /* Ensures it fills the entire viewport */
  width: 100%;
  background-color: #f5f5f5;
  font-family: Arial, sans-serif;
  text-align: center;
  margin: 0;
}

.welcome-section {
  max-width: 800px;
  background: linear-gradient(to right, #ffffff, #f0f0f5);
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.welcome-section h1 {
  font-size: 2.5rem;
  font-weight: bold;
  color: #0040ff;
  margin-bottom: 20px;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
}

.welcome-section p {
  font-size: 1.2rem;
  color: #333;
  margin-bottom: 30px;
  line-height: 1.6;
}

.welcome-section button {
  padding: 10px 20px;
  font-size: 1rem;
  background-color: #0040ff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.welcome-section button:hover {
  background-color: #002080;
}

/* Ensure navbar does not interfere with alignment */
.home-page > .nav-bar {
  width: 100%;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 10;
}
</style>
