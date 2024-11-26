<script setup>
import { RouterLink, RouterView } from 'vue-router';
</script>

<template>
  <div id="app">
    <!-- Conditionally render NavBar based on the current route -->
    <NavBar v-if="showNavBar" />
    <router-view></router-view> <!-- This will display the content of the current route -->
  </div>
</template>

<script>
import NavBar from './components/NavBar.vue';

export default {
  name: 'App',
  components: {
    NavBar,
  },
  computed: {
    showNavBar() {
      // The NavBar will not show if the current route is the login page
      return this.$route.name !== 'Login';
    },
  },
};
</script>

<style>

<style scoped>
header {
  background-color: #f8f9fa;
  padding: 1rem;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

nav {
  display: flex;
  gap: 1rem;
  justify-content: center;
}

.nav-link {
  text-decoration: none;
  color: #007bff;
  font-weight: bold;
  transition: color 0.3s ease;
}

.nav-link:hover {
  color: #0056b3;
}

.nav-link.router-link-exact-active {
  text-decoration: underline;
  color: #0056b3;
}

main {
  padding: 2rem;
}
</style>
