<template>
    <div class="cart-container">
      <h1 class="cart-title">Your Shopping Cart</h1>
  
      <!-- Empty Cart Message -->
      <div v-if="!cartItems.length" class="empty-cart">
        <p>Your cart is empty. <router-link to="/products">Shop now</router-link>.</p>
      </div>
  
      <!-- Cart Items -->
      <div v-else class="cart-items">
        <CartItem
          v-for="item in cartItems"
          :key="item.id"
          :item="item"
          @remove="removeItem(item.id)"
          @updateQuantity="updateQuantity"
        />
      </div>
  
      <!-- Cart Summary -->
      <CartSummary
        :total="cartTotal"
        :tax="tax"
        :discount="discount"
        @checkout="checkout"
      />
    </div>
  </template>
  
  <script>
  import { computed } from "vue";
  import { useCartStore } from "@/stores/cartStore";
  import CartItem from "@/components/CartItem.vue";
  import CartSummary from "@/components/CartSummary.vue";
  
  export default {
    name: "Cart",
    components: {
      CartItem,
      CartSummary,
    },
    setup() {
      const cartStore = useCartStore();
  
      // Access cart items from the store
      const cartItems = computed(() => cartStore.items);
  
      // Derived cart values
      const cartTotal = computed(() => cartStore.total);
      const tax = computed(() => cartStore.calculateTax());
      const discount = computed(() => cartStore.calculateDiscount());
  
      // Store actions
      const removeItem = (id) => {
        if (confirm("Are you sure you want to remove this item?")) {
          cartStore.removeItem(id);
        }
      };
  
      const updateQuantity = (id, quantity) => {
        cartStore.updateItemQuantity(id, quantity);
      };
  
      const checkout = () => {
        cartStore.checkout();
        alert("Checkout complete!"); // You can replace this with a redirect to a confirmation page
      };
  
      return {
        cartItems,
        cartTotal,
        tax,
        discount,
        removeItem,
        updateQuantity,
        checkout,
      };
    },
  };
  </script>
  
  <style scoped>
  .cart-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
  }
  
  .cart-title {
    font-size: 1.5rem;
    text-align: center;
    margin-bottom: 20px;
  }
  
  .empty-cart {
    text-align: center;
    margin-top: 50px;
  }
  
  .cart-items {
    margin-bottom: 20px;
  }
  </style>
  