<template>


<link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css"
    />

<br>  
<br>  
<br>  
<br>  
<br>  
<br>  
<br>  
<br>  

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
<h4>Restoran</h4>
<table class="table table-hover table-bordered results">
  <thead>
    <tr>
      <th class="col-md-2 col-xs-3">Naziv restorana</th>
      <th class="col-md-2 col-xs-3">Tip restorana</th>
      <th class="col-md-2 col-xs-3">Adresa</th>
      <th class="col-md-2 col-xs-3">Geografska širina</th>
      <th class="col-md-2 col-xs-3">Geografska dužina</th>
      <th class="col-md-2 col-xs-3">Status</th>
      <th class="col-md-2 col-xs-3">Prosečna ocena</th>
    </tr>
    <tr class="warning no-result">
      <td colspan="4"><i class="fa fa-warning"></i> No result</td>
    </tr>
  </thead>
  <tbody>
    <tr>
            <td>{{restoran.naziv}}</td>
            <td>{{restoran.tipRestorana}}</td>
            <td>{{restoran.lokacija.adresa}}</td>
            <td>{{restoran.lokacija.sirina}}</td>
            <td>{{restoran.lokacija.duzina}}</td>
            <td>{{restoran.status}}</td>
            <td>{{restoran.prosecnaOcena}}</td>
    </tr>
  </tbody>
</table>

<br>
<h4>Artikli</h4>
<table class="table table-hover table-bordered results">
  <thead>
    <tr>
      <th>#</th>
      <th class="col-md-2 col-xs-3">Naziv artikla</th>
      <th class="col-md-2 col-xs-3">Cena</th>
      <th class="col-md-2 col-xs-3">Tip artikla</th>
      <th class="col-md-2 col-xs-3">Kolicina</th>
      <th class="col-md-2 col-xs-3">Opis</th>      
      <th class="col-md-2 col-xs-3">Slika</th>
      <th v-if="restoran.status == 'RADI' && this.uloga === 'Kupac'" class="col-md-2 col-xs-3">Poruci</th>

    </tr>
    <tr class="warning no-result">
      <td colspan="4"><i class="fa fa-warning"></i> No result</td>
    </tr>
  </thead>
  <tbody>
    <tr  v-for="(artikal,i) in this.restoran.artikli" :key="i">
            <th scope="row">{{i}}</th>
            <td>{{artikal.naziv}}</td>
            <td>{{artikal.cena}}</td>
            <td>{{artikal.tip}}</td>
            <td>{{artikal.kolicina}}</td>
            <td>{{artikal.opis}}</td>
            <td><img v-bind:src="require('../assets/' + artikal.id + '.jpg')" style="width:100px;height:100px;"/></td>
            <td v-if="restoran.status == 'RADI' && this.uloga === 'Kupac'"><button @click="poruci(artikal.id)" type="button" class="btn btn-primary"><i class="bi bi-check-circle-fill text-light"></i> Poruči</button></td>
     </tr>
  </tbody>
</table>

<br>
<h4>Komentari</h4>
<table class="table table-hover table-bordered results">
  <thead>
    <tr>
      <th class="col-md-1 col-xs">#</th>
      <th class="col-md-2 col-xs-3">Tekst komentara</th>
      <th class="col-md-2 col-xs-3">Ocena komentara</th>
    </tr>
    <tr class="warning no-result">
      <td colspan="4"><i class="fa fa-warning"></i> No result</td>
    </tr>
  </thead>
  <tbody>
    <tr  v-for="(komentar,i) in this.restoran.komentari" :key="i">
            <th scope="row">{{i}}</th>
            <td>{{komentar.tekst_komentara}}</td>
            <td>{{komentar.ocena}}</td>
     </tr>
  </tbody>
</table>

</template>
<script>
//<td><img v-bind:src="require('../assets/' + artikal.id + '.jpg')" style="width:100px;height:100px;"/></td>
            
// <td><img :src="'http://localhost:8081/get/image/'+artikal.naziv+'.jpg'" v-bind:alt="pic" style="width:100px;height:100px;"></td>
import axios from "axios";
export default {
    name: 'RestoranView',
    data: function() {
        return {
            restoran: { lokacija: { adresa: "", sirina: "", duzina: ""}}, // pravi gresku morao sam dodati
        }
    },
    mounted: function() {
        // ovde kao callback funkciju odmah poziva i pravi employee
        console.log('http://localhost:8081/api/restoran/' + this.$route.query.id)
        axios
            .get('http://localhost:8081/api/restoran/' + this.$route.query.id)
            .then((res) => {
                console.log(res.data)
                this.restoran = res.data
            })
            .catch((err) =>{ // todo neovlascen pristup
                console.log(err)
            })
    },
    methods: {
        poruci: function(artikal_id) {
            axios
            .get('http://localhost:8081/api/dodajUKorpuNazivomRestorana/' + this.restoran.naziv + '/' + artikal_id, {withCredentials:'include'})
            .then((res) => {
                alert(res.data)
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
</style>