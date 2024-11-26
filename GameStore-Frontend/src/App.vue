<script setup>
import { RouterView } from 'vue-router';
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

<style scoped>
html,
body {
  margin: 0;
  padding: 0;
  height: 100%;
  width: 100%;
  overflow: hidden; 
}

#app {
  display: flex;
  flex-direction: column;
  height: 100vh; 
  width: 100vw; 
  margin: 0;
  padding: 0;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter, 
.fade-leave-to {
  opacity: 0;
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
