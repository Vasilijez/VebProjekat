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


<!-- FORMA -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css" integrity="sha384-r4NyP46KrjDleawBgD5tp8Y7UzmLA05oM1iAEQ17CSuDqnUK2+k9luXQOfXJCJ4I" crossorigin="anonymous">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">

<div class="card">
    
    <!-- todo, sliku ovde -->

    <br>
    
    <div id="app">
      <label> Ime</label>
  <div class="d-flex flex-row mt-2 mb-3 align-items-center gap-1">
    <input class="form-control" type="text" v-model="this.user.ime">      
  </div>

  <label> Prezime</label>
  <div class="d-flex flex-row mt-2 mb-3 align-items-left gap-1">
    <input class="form-control" type="text" v-model="this.user.prezime">  
  </div>
  
  <label> Pol</label>
  <div class="d-flex flex-row mt-2 mb-3 align-items-center gap-1">
    <select v-model="this.user.pol" class="form-select" aria-label="Default select example">
      <option value="MUSKO">Musko</option>
      <option value="ZENSKO">Zensko</option>
    </select>
  </div>

  
  <label>Korisničko ime</label>
  <div class="d-flex flex-row mt-2 mb-3 align-items-center gap-1">
    <input class="form-control" type="text"  v-model="this.user.korisnickoIme">  
  </div>

  <label>Lozinka</label>
  <div class="d-flex flex-row mt-2 mb-3 align-items-center gap-1">
    <input class="form-control" type="text"  v-model="this.user.lozinka">  
  </div>
  
  <!-- todo -->

  <label>Datum rodjenja</label>
  <div class="d-flex flex-row mt-2 mb-5 align-items-center gap-1">
    <input v-model="this.user.datum_rodjenja" type="date" id="birthday" name="birthday">
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
<!-- FORMA -->


</template>

<script>
import axios from "axios";
export default {
  name: "ProfilView",
  data: function () {
    return {
      uloga: "neovlascen_pristup",
      user:  {
        korisnickoIme: "",
        ime: "",
        prezime: "",
        p: "",
        datum_rodjenja: "",
        uloga: "",
        lozinka: "",
      },
    };
  },
   mounted: function () {
    axios // uloga
            .get('http://localhost:8081/api/vratiUlogu/', {withCredentials: true})
            .then((res) => {
                this.uloga = res.data
           })
            .catch((err) =>{ // todo neovlascen pristup
                console.log(err)
            })
    
    axios
      .get("http://localhost:8081/api/profil", {withCredentials:'include'})
      .then((res) => {
        console.log(res.data);
        this.user = res.data;
        this.user.datum_rodjenja = this.user.datum_rodjenja.substring(0,10)
      })
      .catch((err) =>{
        console.log(err)
        alert("Neispravna izmena")
      })
   },
   methods: {
        cancel: function() {
            this.user = this.resUser
            window.location.reload(); 
        },
        save: function() {
            axios
            .post("http://localhost:8081/api/izmeni-profil", this.user, {withCredentials:'include'})
            .then(res => {
                console.log(res)
                alert("Uspesno ste izmenili podatke!")
            })
            .catch(error => {
                console.log(error)
                alert("Neispravan unos!")
            });
        }
   }
}


</script>

