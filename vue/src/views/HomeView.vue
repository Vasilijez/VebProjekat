<template>
  <h1>{{ role }}</h1>

  <a type="button" href="/employees" class="btn">Show employee</a>

</template>

<script>
export default {
  name: "HomeView",
  data: function() {
        return {
            role: ""
        }
    },
  mounted: function() {
    role = this.$role
    console.log(this.$role)
  }  
};
</script>

<style>
h1 {
  color: #6d4caf;
}
</style>
