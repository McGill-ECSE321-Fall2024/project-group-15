import { createRouter, createWebHistory } from 'vue-router'
import Game from '../components/Game.vue'; 
import SearchBar from '../components/SearchBar.vue'; 
import Home from '../components/Home.vue'; 
import Account from '../components/Settings.vue'; 


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
    name: 'Games',
    component: SearchBar, 
  },
  {
    path: '/games/:id',
    name: 'GameDetails',
    component: Game, 
    props: true, // Allow route params as props
  },
  /*{
    path: '/account',
    name: 'Account',
    component: Account,
  }*/,
  ],
}) 

export default router
