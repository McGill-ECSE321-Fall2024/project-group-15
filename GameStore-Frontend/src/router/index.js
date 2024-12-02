import { createRouter, createWebHistory } from 'vue-router';
import Login from '../components/Login.vue'; // Login page for the homepage
import Game from '../components/GameDetails.vue'; 
import SearchResults from '../components/SearchResults.vue'; // Add this import for the search results page
import GameList from "@/components/GameList.vue";
import Home from '../components/Home.vue'; 
import Account from '../components/Account.vue'; 
import AddGame from '@/components/AddGame.vue';
import Category from '../components/Category.vue';
import Cart from '../components/Cart.vue'; // Cart page
import Checkout from "@/components/Checkout.vue";
import Receipt from "@/components/Receipt.vue";
import Staff from "@/components/Staff.vue";
import Employee from '@/components/Employee.vue';


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
    name: 'GameList',
    component: GameList,
  },
  {
    path: '/games/:id',
    name: 'GameDetails',
    component: Game,
    props: true, // Allow route params as props
  },
  {
    path: '/games/add-game',
    name: 'AddGame',
    component: AddGame, 
  },
  {
    path: '/categories',
    name: 'Category',
    component: Category,
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
  // New route for Search Results
  {
    path: '/search-results',
    name: 'SearchResults',
    component: SearchResults,
  },

  {
    path: "/checkout",
    name: "Checkout",
    component: Checkout,
  },

  {
    path: '/receipt',
    name: 'Receipt',
    component: Receipt,
  },

  {
    path: "/staff",
    name: "Staff",
    component: Staff,
  },

  {
    path: "/staff/employees",
    name: "ManageEmployees",
    component: Employee,
  }

];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
