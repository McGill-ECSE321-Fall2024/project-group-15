import { createRouter, createWebHistory } from 'vue-router'
import Login from '../components/Login.vue'; // Login page for the homepage
import Game from '../components/GameDetails.vue'; 
import SearchBar from '../components/SearchBar.vue'; 
import GameList from "@/components/GameList.vue";
import Home from '../components/Home.vue'; 
import Account from '../components/Settings.vue'; 
import AddGame from '@/components/AddGame.vue';
import Category from '../components/Category.vue'
import Cart from '../components/Cart.vue'; // Cart page


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
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
