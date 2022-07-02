<template>
  <!--<nav>
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link>
  </nav>-->
  <router-view/>
</template>

<script>
import { onMounted } from 'vue';

export default {

  mounted() {
    role: 'ka'    
  }
};
</script>


