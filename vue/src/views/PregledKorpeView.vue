<template>

<link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css"
    />

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


<br>
<h4>Korpa</h4>
<table class="table table-hover table-bordered results">
  <thead>
    <tr>
      <th>#</th>
      <th class="col-md-2 col-xs-3">Naziv artikla</th>
      <th class="col-md-2 col-xs-3">Cena artikla</th>
      <th class="col-md-2 col-xs-3">Kolicina artikla</th>
      <th class="col-md-2 col-xs-3">Izbaci iz korpe</th>
      <th class="col-md-2 col-xs-3">Status</th>
    </tr>
    <tr class="warning no-result">
      <td colspan="4"><i class="fa fa-warning"></i> No result</td>
    </tr>
  </thead>
  <tbody>
    <tr  v-for="(artikal,i) in this.mojaKorpa.artikli" :key="i">
            <th scope="row">{{i}}</th>
            <td>{{artikal.naziv}}</td>
            <td>{{artikal.cena}}</td>
            <td>{{artikal.kolicina}}</td>
            <td><button @click="deleteArtikal(artikal.naziv)" type="button" class="btn btn-danger"><i class="bi bi-trash text-light"></i> Obriši</button></td>
   
     </tr>
  </tbody>
</table>
<b>Ukupna cena: </b><label>{{this.mojaKorpa.ukupnaCena}}</label>
<br>
<br>
<td><button @click="poruci()" type="button" class="btn btn-success"><i class="bi bi-check-circle-fill text-light"></i> Poruči</button></td>
 
</template>
<script>
//<td><button @click="dlete" type="button" class="btn btn-danger"><i class="bi bi-trash text-light"></i> Obriši</button></td>
      
import axios from "axios";
export default {
    name: 'PregledKorpe',
    data: function() {
        return {
            mojaKorpa: {
              artikli: [
                  {
                      naziv: "",
                      cena: 0,
                      kolicina: 0
                  }
              ],
              ukupnaCena: 0
          }
        }
    },
    mounted: function() {
        // ovde kao callback funkciju odmah poziva i pravi employee
       
        axios
            .get('http://localhost:8081/api/pregledKorpe/', {withCredentials: true})
            .then((res) => {
                console.log(res.data)
                this.mojaKorpa = res.data
           })
            .catch((err) =>{ // todo neovlascen pristup
                console.log(err)
            })
    },
    methods: {
      deleteArtikal: function(naziv) {
          axios
            .delete('http://localhost:8081/api/izbaciIzKorpeNazivomArtikla/' + naziv, {withCredentials: true})
            .then((res) => {
                console.log(res.data)
                window.location.reload();
           })
            .catch((err) =>{ // todo neovlascen pristup
                console.log(err)
            })
      },
       poruci: function() {
          axios
            .get('http://localhost:8081/api/submitKorpe/', {withCredentials: true})
            .then((res) => {
                console.log(res.data)
                 this.$router.push("/kupac");
           })
            .catch((err) =>{ // todo neovlascen pristup
                console.log(err)
                alert("nesto je poslo po zlu")
            })
      }

    }
    
}
</script>
