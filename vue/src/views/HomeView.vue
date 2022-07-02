<template>

  <a type="button" href="/employees" class="btn">Show employee</a>
  <h1>{{this.role}}</h1>
</template>

<script>
import LoginViewVue from './LoginView.vue';

export default {
  name: "HomeView",
  data: function() {
        return {
            
        }
    }  
    
};
</script>

<style>
h1 {
  color: #6d4caf;
}
</style>
