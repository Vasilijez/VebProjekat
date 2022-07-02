<template>




<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">Navbar</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="#">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">Početna</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">Odjavi se</a>
        </li>
        <li class="nav-item">
          <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
        </li>
      </ul>
    </div>
  </div>
</nav>

<h2>Menadzeri</h2>
<table class="table table-hover table-bordered results">
  <thead>
    <tr>
      <th>#</th>
      <th class="col-md-2 col-xs-5">Korisničko ime</th>
      <th class="col-md-2 col-xs-4">Ime</th>
      <th class="col-md-2 col-xs-3">Prezime</th>
      <th class="col-md-2 col-xs-3">Pol</th>
      <th class="col-md-2 col-xs-3">Uloga</th>
      <th class="col-md-2 col-xs-3">Datum rođenja</th>
    </tr>
    <tr class="warning no-result">
      <td colspan="4"><i class="fa fa-warning"></i> No result</td>
    </tr>
  </thead>
  <tbody>
    <tr  v-for="(korisnik,i) in menadzeri" :key="i">
            
            <th v-if="korisnik.uloga == 'Menadzer'" scope="row">{{i}}</th>
            <td v-if="korisnik.uloga == 'Menadzer'">{{korisnik.korisnickoIme}}</td>
            <td v-if="korisnik.uloga == 'Menadzer'">{{korisnik.ime}}</td>
            <td v-if="korisnik.uloga == 'Menadzer'">{{korisnik.prezime}}</td>
            <td v-if="korisnik.uloga == 'Menadzer'">{{korisnik.p}}</td>
            <td v-if="korisnik.uloga == 'Menadzer'">{{korisnik.uloga}}</td>
            <td v-if="korisnik.uloga == 'Menadzer' ">{{korisnik.datum_rodjenja.substring(0,10)}}</td>
    </tr>
  </tbody>
</table>

<h2>Restorani</h2>
<table class="table table-hover table-bordered results">
  <thead>
    <tr>
      <th>#</th>
      <th class="col-md-2 col-xs-1">Naziv restorana</th>
      <th class="col-md-3 col-xs-5">Tip restorana</th>
      <th class="col-md-3 col-xs-5">Adresa</th>
      <th class="col-md-2 col-xs-3">Geografska širina</th>
      <th class="col-md-2 col-xs-3">Geografska dužina</th>
    </tr>
    <tr class="warning no-result">
      <td colspan="4"><i class="fa fa-warning"></i> No result</td>
    </tr>
  </thead>
  <tbody>
    <tr  v-for="(restoran,i) in restorani" :key="i">
            <th scope="row">{{i}}</th>
            <td>{{restoran.naziv}}</td>
            <td>{{restoran.tipRestorana}}</td>
            <td>{{restoran.lokacija.adresa}}</td>
            <td>{{restoran.lokacija.sirina}}</td>
            <td>{{restoran.lokacija.duzina}}</td>
    </tr>
  </tbody>
</table>

<label for="html">Ponuđeni menadžeri</label>
<select v-model="this.menadzer.naziv" class="form-select form-select mb-3" aria-label=".form-select-lg example">
  <option  v-for="(korisnik,i) in menadzeri" :key="i" :value="korisnik.korisnickoIme">{{korisnik.korisnickoIme}}</option>
</select>
<label for="html">Ponuđeni restorani</label>
<select v-model="this.restoran.naziv" class="form-select form-select mb-3" aria-label=".form-select-lg example">
  <option v-for="(restoran,j) in restorani" :key="j" :value="restoran.naziv">{{restoran.naziv}}</option>
</select>
<input @click = "povezi()" class="btn btn-success" type="submit" value="Potvrdi dodelu">


    <br>
    <br>
    <button type="button" class="btn btn-primary btn-block me-4" @click="this.$router.push('/kreiraj-menadzera')">Kreiraj menadzera</button>
    <br>
    <button type="button" class="btn btn-primary btn-block me-4" @click="this.$router.push('/kreiraj-dostavljaca')">Kreiraj dostavljaca</button>
    <br>
    <button type="button" class="btn btn-primary btn-block me-4" @click="this.$router.push('/kreiraj-restoran')">Kreiraj restoran</button>
    <br>
    <button type="button" class="btn btn-primary btn-block me-4" @click="this.$router.push('/obrisi-restoran')">Obrisi restoran</button>
    <br>
    <button type="button" class="btn btn-primary btn-block me-4" @click="this.$router.push('/izaberi-menadzera')">Izaberi menadžera</button>




</template>



<script>
import axios from "axios";
import $ from 'jquery';
export default {
    name: "IzaberiMenadzera",
    data: function () {
        return {
            menadzeri: [],
            restorani: [],
            menadzer: { naziv: "" },
            restoran: { naziv: "" }
        };
    },
    mounted: function () {
      
    axios
      .get("http://localhost:8081/api/menadzeri-bez-restorana", {withCredentials:'include'})
      .then((res) => {
        console.log(res.data)
        this.menadzeri = res.data
      })
      .catch((err) =>{ // todo neovlascen pristup
        console.log(err)
      })

    axios
      .get("http://localhost:8081/api/restorani-bez-menadzera", {withCredentials:'include'})
      .then((res) => {
        console.log(res.data)
        this.restorani = res.data
      })
      .catch((err) =>{ // todo neovlascen pristup
        console.log(err)
      })

    },
    methods: {
        povezi: function() {
          console.log(this.menadzer.naziv, this.restoran.naziv)
          /*fetch("http://localhost:8081/api/postavi-menadzera/" + this.menadzer.naziv + "/" + this.restoran.naziv, {withCredentials:'include'}, {
            method: "GET",
          }).then((res) => {
            if (res.ok) {
              window.location.reload(); // osvezi stranicu
            }
          });*/
        
        axios
      .get("http://localhost:8081/api/postavi-menadzera/" + this.menadzer.naziv + "/" + this.restoran.naziv, {withCredentials:'include'})
      .then((res) => {
        console.log(res.data)
        window.location.reload();
      })
      .catch((err) =>{ // todo neovlascen pristup
        console.log(err)
      })
        
        }



    }

}
   
</script>

<style>

body{
  padding:20px 20px;
}

.results tr[visible='false'],
.no-result{
  display:none;
}

.results tr[visible='true']{
  display:table-row;
}

.counter{
  padding:8px; 
  color:#ccc;
}

.btn btn-primary {
 margin-right: 4px;
}

/* Pen-specific styles */
html, body { 
  color: #fff;
  height: 100%;
}

/* Pattern styles */
.container {
  background: linear-gradient(
    to right, 
    #ff9e2c 0%, 
    #ff9e2c 50%, 
    #b6701e 50%, 
    #b6701e 100%
  );
  height: 100%;
  width: 100%;
}
</style>