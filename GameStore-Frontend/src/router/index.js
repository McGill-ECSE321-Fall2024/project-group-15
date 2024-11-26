import { createRouter, createWebHistory } from 'vue-router'
import Game from '../components/GameDetails.vue'; 
import SearchBar from '../components/SearchBar.vue'; 
import GameList from "@/components/GameList.vue";
import Home from '../components/Home.vue'; 
import Account from '../components/Settings.vue'; 
import AddGame from '@/components/AddGame.vue';
import Category from '../components/Category.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
    path: '/',
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
  }
  /*{
    path: '/account',
    name: 'Account',
    component: Account,
  }*/
],
})

export default router
