<template>


<div class="card">
    

    <!-- todo, sliku ovde -->

    <div id="app">
  <div class="d-flex flex-row mt-2 mb-2 align-items-center gap-2">
    <label>Naziv:</label>
    <input class="form-control" type="text" v-model="this.artikal.naziv" :disabled="!isEditing"
           :class="{view: !isEditing}" >      
  </div>
  
  <div class="d-flex flex-row mt-2 mb-2 align-items-center gap-2">
    <label>Cena:</label>
    <input class="form-control" type="text" v-model="this.artikal.cena" :disabled="!isEditing"
           :class="{view: !isEditing}">  
  </div>
  
  <div class="d-flex flex-row mt-2 mb-2 align-items-center gap-2">
    <label>Tip:</label>
    <input class="form-control" type="text" v-model="this.artikal.tip" :disabled="true"
           :class="{view: !isEditing}">  
  </div>
  
  <div class="d-flex flex-row mt-2 align-items-center gap-2">
    <label>Opis:</label>
    <input class="form-control" type="text"  v-model="this.artikal.opis" :disabled="!isEditing"
           :class="{view: !isEditing}">  
  </div>
  
  
  <!-- todo -->
  <br>
  <input type="file" class="form-control" id="customFile" />
  
  
  <div class="mt-3 mb-3">
    <button class="btn btn-sm btn-success" @click="isEditing = !isEditing" v-if="!isEditing">
    Edit
  </button>
  <button class="btn btn-sm btn-success me-1" @click="save()" v-else-if="isEditing">
  Save
  </button>
  
  <button class="btn btn-sm btn-danger" v-if="isEditing" @click="isEditing = false">Cancel</button>
  </div>
</div>
</div>

</template>

<script>
import axios from "axios";
export default {
  name: "ArtikalView",
  data: function () {
    return {
      artikal: {
        naziv: "",
        cena: 0,
        tip: "",
        kolicina: 1,
        opis: "",
        slika: ""
      },
      isEditing: false,
  };
  },
   mounted: function() {
        
          axios
            .get('http://localhost:8081/api/artikal/' + this.$route.query.id, {withCredentials: true})
            .then((res) => {
                this.artikal = res.data
           })
            .catch((err) =>{ // todo neovlascen pristup
                console.log(err)
            })

            

    },
   methods: {
        cancel: function() {
            this.$router.push("/menadzer");
        },
        save: function() {
            console.log(this.artikal)
            axios
            .put('http://localhost:8081/api/restoran/izmeni-artikal/' + this.$route.query.id, this.artikal, {withCredentials: true})
            .then(res => {
                console.log(res)
            })
            .catch(error => {
                console.log(error)
                
            });
        }
   }
}
</script>

<style>
.view {
  border-color: transparent;
  background-color: initial !important;
  color: initial
}

.form-control:focus{
   outline: none;
   box-shadow: none;
}

.mt-5{
    margin-top:10px;
}

.mb-5{
    margin-bottom:20px;
}

body{
  height:100vh;
  background-color:#eee;
  display:flex;
  justify-content:center;
  align-items:center;
}

.card{
    width:700px;
    background-color:#fff;
    padding:10px;
    border-radius:10px;
    border: none;
}

.btn:focus{
    outline: none;
    box-shadow: none;
}

</style>