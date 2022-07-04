<template>

<link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css"
    />

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
<h4>Porudzbine</h4>
<table class="table table-hover table-bordered results">
  <thead>
    <tr>
      <th>#</th>
      <th class="col-md-2 col-xs-3">Naziv restorana</th>
      <th class="col-md-2 col-xs-3">Datum porudzbine</th>
      <th class="col-md-2 col-xs-3">Vreme</th>
      <th class="col-md-2 col-xs-3">Cena</th>
      <th class="col-md-2 col-xs-3">Status</th>
      <th class="col-md-2 col-xs-3">Komentar</th>
    </tr>
    <tr class="warning no-result">
      <td colspan="4"><i class="fa fa-warning"></i> No result</td>
    </tr>
  </thead>
  <tbody>
    <tr  v-for="(porudzbina,i) in porudzbine" :key="i">
            <th scope="row">{{i}}</th>
            <td>{{porudzbina.restoran.naziv}}</td>
            <td>{{porudzbina.datum_vreme.substring(0,10)}}</td>
            <td>{{porudzbina.datum_vreme.substring(11,19)}}</td>
            <td>{{porudzbina.cena}}</td>
            <td>{{porudzbina.status}}</td>
            <td>
              <div v-if="porudzbina.status == 'dostavljena'" class="dropdown">
                <button  class="btn btn-success " @click="ostaviKomentar(porudzbina.id, porudzbina.restoran.naziv)" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">
                  Ostavi
                </button>
              </div>
              <div v-else>
                <i>Nemoguće ostaviti komentar</i>
              </div>
            </td>
    </tr>
  </tbody>
</table>

<b>Tip kupca: </b>
<br>
<b>Broj poena: </b>
  
</template>
<script>
import axios from "axios";
export default {
    name: 'KupacView',
    data: function() {
        return {
            uloga: "neovlascen_pristup",
            porudzbine: [{
                        artikli: [],
                        restoran: {},
                        datum_vreme: "",
                        cena: 0,
                        status: ""
                    }]
        }
    },
    mounted: function() {
        // ovde kao callback funkciju odmah poziva i pravi employee
       
        axios
            .get('http://localhost:8081/api/porudzbine/', {withCredentials: true})
            .then((res) => {
                console.log(res.data)
                this.porudzbine = res.data
           })
            .catch((err) =>{ // todo neovlascen pristup
                console.log(err)
            })
        
        axios // uloga
            .get('http://localhost:8081/api/vratiUlogu/', {withCredentials: true})
            .then((res) => {
                this.uloga = res.data
           })
            .catch((err) =>{ // todo neovlascen pristup
                console.log(err)
            })
    },
  methods: {
        ostaviKomentar: function(id, nazivRestorana) {
            this.$router.push("/komentar?id=" + id + "&nazivRestorana=" + nazivRestorana);
      }
  }    
}
</script>
