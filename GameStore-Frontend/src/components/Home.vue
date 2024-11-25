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
    align-items: center;
    justify-content: center;
    min-height: 100vh;
    background-color: #f5f5f5;
    font-family: Arial, sans-serif;
    text-align: center;
    padding: 20px;
  }
  
  .welcome-section {
    max-width: 800px;
    background: white;
    padding: 40px;
    border-radius: 10px;
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  }
  
  .welcome-section h1 {
    font-size: 2.5rem;
    color: #0040ff;
    margin-bottom: 20px;
  }
  
  .welcome-section p {
    font-size: 1.2rem;
    color: #555;
    margin-bottom: 30px;
  }
  </style>
  