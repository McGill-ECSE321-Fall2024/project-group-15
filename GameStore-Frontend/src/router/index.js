import { createRouter, createWebHistory } from 'vue-router';
import Login from '../components/Login.vue'; // Login page for the homepage
import Home from '../components/Home.vue'; // Home page after login
import Game from '../components/Game.vue'; // Game details page
import SearchBar from '../components/SearchBar.vue'; // Search bar page for games
import Cart from '../components/Cart.vue'; // Cart page
import Account from '../components/Account.vue'; // Account page

const routes = [
  {
    path: '/',
    name: 'Login', // Set the login page as the homepage
    component: Login,
  },
  {
    path: '/home', // The home page after login
    name: 'Home',
    component: Home,
  },
  {
    path: '/games',
    name: 'Games',
    component: SearchBar,
  },
  {
    path: '/games/:id',
    name: 'GameDetails',
    component: Game,
    props: true, // Allow route params as props
  },
  {
    path: '/cart',
    name: 'Cart',
    component: Cart,
  },
  {
    path: '/account',
    name: 'Account',
    component: Account,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
