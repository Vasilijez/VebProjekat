<template>

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


<link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css"
    />


<br>
<h4>Moj restoran</h4>
<table class="table table-hover table-bordered results">
  <thead>
    <tr>
      <th class="col-md-2 col-xs-3">Naziv restorana</th>
      <th class="col-md-2 col-xs-3">Tip restorana</th>
      <th class="col-md-2 col-xs-3">Adresa</th>
      <th class="col-md-2 col-xs-3">Geografska širina</th>
      <th class="col-md-2 col-xs-3">Geografska dužina</th>
    </tr>
    <tr class="warning no-result">
      <td colspan="4"><i class="fa fa-warning"></i> No result</td>
    </tr>
  </thead>
  <tbody>
    <tr>
            <td>{{menadzer.restoran.naziv}}</td>
            <td>{{menadzer.restoran.tipRestorana}}</td>
            <td>{{menadzer.restoran.lokacija.adresa}}</td>
            <td>{{menadzer.restoran.lokacija.sirina}}</td>
            <td>{{menadzer.restoran.lokacija.duzina}}</td>
    </tr>
  </tbody>
</table>

<br>
<h4>Porudzbine</h4>
<table class="table table-hover table-bordered results">
  <thead>
    <tr>
      <th>#</th>
      <th class="col-md-2 col-xs-3">Datum porudzbine</th>
      <th class="col-md-2 col-xs-3">Vreme</th>
      <th class="col-md-2 col-xs-3">Cena</th>
      <th class="col-md-2 col-xs-3">Status</th>
      <th class="col-md-2 col-xs-3">Izmeni status</th>
    </tr>
    <tr class="warning no-result">
      <td colspan="4"><i class="fa fa-warning"></i> No result</td>
    </tr>
  </thead>
  <tbody>
    <tr  v-for="(porudzbina,i) in porudzbine" :key="i">
            <th scope="row">{{i}}</th>
            <td>{{porudzbina.datum_vreme.substring(0,10)}}</td>
            <td>{{porudzbina.datum_vreme.substring(11,19)}}</td>
            <td>{{porudzbina.cena}}</td>
            <td>{{porudzbina.status}}</td>
            <td>
              <div v-if="porudzbina.status == 'obrada'" class="dropdown">
                <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">
                  Izaberi
                </a>

                <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                  <li><a class="dropdown-item" @click="promenaUpripremi(porudzbina.id)">U pripremi</a></li>
                </ul>
              </div>
              <div v-else-if="porudzbina.status == 'u_pripremi'" class="dropdown">
                <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">
                  Izaberi
                </a>

                <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                  <li><a class="dropdown-item" @click="promenaUcekaDostavljaca(porudzbina.id)">Čeka dostavljača</a></li>
                </ul>
              </div>
              <div v-else class="dropdown">
                <i>Nemoguće promeniti status</i>
              </div>
            </td>
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
      <th class="col-md-2 col-xs-3">Obriši</th>
      <th class="col-md-2 col-xs-3">Izmeni</th>
      
    </tr>
    <tr class="warning no-result">
      <td colspan="4"><i class="fa fa-warning"></i> No result</td>
    </tr>
  </thead>
  <tbody>
    <tr  v-for="(artikal,i) in menadzer.restoran.artikli" :key="i">
            <th scope="row">{{i}}</th>
            <td>{{artikal.naziv}}</td>
            <td>{{artikal.cena}}</td>
            <td>{{artikal.tip}}</td>
            <td>{{artikal.kolicina}}</td>
            <td>{{artikal.opis}}</td>
            <td><img v-bind:src="require('../assets/' + artikal.id + '.jpg')" style="width:100px;height:100px;"/></td>
            <td><button @click="deleteEmployee(artikal.id)" type="button" class="btn btn-danger"><i class="bi bi-trash text-light"></i> Obriši</button></td>
            <td><button @click="this.$router.push('/artikal?id=' + artikal.id)" type="button" class="btn btn-secondary"><i class="bi bi-check-circle-fill text-light"></i> Izmeni</button></td>
     
     </tr>
  </tbody>
</table>

<td><button @click="this.$router.push('/artikal-dodaj')" type="button" class="btn btn-primary"><i class="bi bi-check-circle-fill text-light"></i> Dodaj artikal</button></td>

<br>  
</template>


<script>
// <td><button @click="this.$router.push('/porudzbina?id=' + porudzbina.id)" type="button" class="btn btn-secondary"><i class="bi bi-check-circle-fill text-light"></i> Izmeni</button></td>
     
import axios from "axios";
export default {
    name: 'MenadzerView',
    data: function() {
        return {
          uloga: "neovlascen_pristup",
            menadzer: {
                restoran: { 
                    naziv: "neovlascen_pristup", 
                    tipRestorana: "", 
                    artikli: [], 
                    lokacija: { 
                        adresa: "", 
                        sirina: "", 
                        duzina: ""}
                    }, 
                    porudzbinaList: [{
                        artikli: [],
                        restoran: {},
                        datum_vreme: "",
                        cena: 0,
                        status: ""
                    }]
            },
            porudzbine: [{
                        artikli: [],
                        restoran: {},
                        datum_vreme: "",
                        cena: 0,
                        status: ""
                    }],
            restoran: { lokacija: { adresa: "", sirina: "", duzina: ""}}, // pravi gresku morao sam dodati
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
        axios
            .get('http://localhost:8081/api/menadzer/', {withCredentials: true})
            .then((res) => {
                console.log(res.data)
                this.menadzer = res.data
                console.log(this.menadzer.restoran.naziv)
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
        deleteEmployee: function(naziv) {
          //this.restoran_naziv.naziv = naziv;
          //console.log(this.restoran_naziv)
        axios
            .get('http://localhost:8081/api/restoran/obrisi-artikal/' + naziv, {withCredentials: true})
            .then(res => {
                console.log(res)
                window.location.reload();
            })
            .catch(error => {
                console.log(error)
            }); 
        },
        promenaUpripremi: function(id) {
          //this.restoran_naziv.naziv = naziv;
          console.log(id)
        axios
            .get('http://localhost:8081/api/porudzbinaUPripremi/' + id, {withCredentials: true})
            .then(res => {
                console.log(res)
                window.location.reload();
            })
            .catch(error => {
                console.log(error)
            }); 
        },
        promenaUcekaDostavljaca: function(id) {
          //this.restoran_naziv.naziv = naziv;
          console.log(id)
        axios
            .get('http://localhost:8081/api/porudzbinaCekaDostavljaca/' + id, {withCredentials: true})
            .then(res => {
                console.log(res)
                window.location.reload();
            })
            .catch(error => {
                console.log(error)
            }); 
        },
      
    }
    
}
</script>
