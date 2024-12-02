<template>
  <div class="nav-bar">
    <nav>
      <a href="/" class="nav-link">Sign Out</a>
      <a href="/home" class="nav-link">Home</a>
      <a href="/games" class="nav-link">Games</a>
      <a href="/categories" class="nav-link">Categories</a>
      <a href="/account" class="nav-link">Account</a>
      <a href="/staff" class="nav-link">Staff</a>
      <a href="/cart" class="nav-link">Cart</a>
      <a href="/wishlist" class="nav-link">Wishlist</a> <!-- Added Wishlist -->
    </nav>
  </div>
</template>

<script>
export default {
  name: "Navbar",
};
</script>

<style scoped>
.nav-bar {
  background: #2c3e50;
  padding: 10px 20px;
  display: flex;
  justify-content: center;
  position: fixed;
  width: 100%;
  top: 0;
  z-index: 10;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  height: 60px;
}

.nav-link {
  color: white;
  text-decoration: none;
  font-size: 18px;
  margin: 0 15px;
  padding: 8px 15px;
  border-radius: 5px;
  transition: background-color 0.3s ease, color 0.3s ease;
}

.nav-link:hover {
  background-color: #1abc9c;
  color: white;
}

.nav-link:active {
  background-color: #16a085;
}

@media (max-width: 768px) {
  .nav-bar {
    flex-direction: column;
    padding: 15px;
  }

  .nav-link {
    margin: 5px 0;
  }
}
</style>
