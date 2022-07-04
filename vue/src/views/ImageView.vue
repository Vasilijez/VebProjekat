<template>

<link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css"
    />
<div v-for="pic in pics">
      <img :src="getImgUrl(pic)" v-bind:alt="pic">
   </div>

  



</template>




<script>
import axios from "axios";
//<img v-bind:src="require('../assets/giros.jpg')" />
import $ from 'jquery';
export default {
    name: "ImageView"
}
   
</script>

