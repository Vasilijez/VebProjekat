<template>


<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand">Odnesi</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="/">Početna</a>
        </li>
        <li class="nav-item">
          <a v-if="this.uloga === 'neovlascen_pristup'" class="nav-link active" aria-current="page" href="/login">Uloguj se</a>
        </li>
        <li  v-if="this.uloga !== 'neovlascen_pristup'" class="nav-item">
          <a class="nav-link active" aria-current="page" href="/odjavi-se">Odjavi se</a>
        </li>
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="/profil">Profil</a>
        </li>
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="/dashboard">Dashboard</a>
        </li>
      </ul>
    </div>
  </div>
</nav>

<br>


<!-- Bootstrap 5 CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css" integrity="sha384-r4NyP46KrjDleawBgD5tp8Y7UzmLA05oM1iAEQ17CSuDqnUK2+k9luXQOfXJCJ4I" crossorigin="anonymous">
<!-- Font Awesome CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">



<div class="card">
    

    <!-- todo, sliku ovde -->

    <br>
    
    <div id="app">
      <label> Naziv</label>
  <div class="d-flex flex-row mt-2 mb-3 align-items-center gap-1">
    <input class="form-control" type="text" v-model="this.artikal.naziv"  
           :class="{view: !isEditing}" >      
  </div>

  <label>Cena</label>
  <div class="d-flex flex-row mt-2 mb-3 align-items-left gap-1">

    <input class="form-control" type="text" v-model="this.artikal.cena" 
           :class="{view: !isEditing}">  
  </div>
  
  <label>Tip</label>
  <div class="d-flex flex-row mt-2 mb-3 align-items-center gap-1">
    <select v-model="this.artikal.tip" class="form-select" aria-label="Default select example">
      <option value="jelo">Jelo</option>
      <option value="pice">Pice</option>
    </select>
  </div>

  
  <label>Opis</label>
  <div class="d-flex flex-row mt-2 mb-3 align-items-center gap-1">
    <input class="form-control" type="text"  v-model="this.artikal.opis" 
           :class="{view: !isEditing}">  
  </div>
  
  
  <!-- todo -->

  <label>Slika-upload(todo)</label>
  <div class="d-flex flex-row mt-2 mb-3 align-items-center gap-1">
    <input type="file" class="form-control" id="customFile" />
  </div>
  
  <div class="mt-3 mb-3">
    <button @click="save()" type="button" class="btn btn-labeled btn-success mr-3"> 
      <span class="btn-label"><i class="fa fa-check"></i></span> Izmeni 
    </button>
    <button @click="cancel()" type="button" class="btn btn-labeled btn-danger"> 
      <span class="btn-label"><i class="fa fa-remove"></i></span> Otkaži
    </button> 
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
      uloga: "neovlascen_pristup",
      artikal: {
        naziv: "",
        cena: 0,
        tip: "",
        kolicina: 1,
        opis: "",
        slika: ""
      },
  };
  },
   mounted: function() {
          axios // uloga
            .get('http://localhost:8081/api/vratiUlogu/', {withCredentials: true})
            .then((res) => {
                this.uloga = res.data
           })
            .catch((err) =>{ // todo neovlascen pristup
                console.log(err)
            })

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
            this.$router.push("/dashboard");
        },
        save: function() {
            console.log(this.artikal)
            axios
            .put('http://localhost:8081/api/restoran/izmeni-artikal/' + this.$route.query.id, this.artikal, {withCredentials: true})
            .then(res => {
                console.log(res)
                this.$router.push("/dashboard");
            })
            .catch(error => {
              alert("Neispravna izmena!")
            });
        }
   }
}
</script>
