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
<h4>Porudzbine</h4>
<table class="table table-hover table-bordered results">
  <thead>
    <tr>
      <th>#</th>
      <th class="col-md-2 col-xs-3">Naziv restorana</th>
      <th class="col-md-2 col-xs-3">Datum porudzbine</th>
      <th class="col-md-2 col-xs-3">Vreme porudzbine</th>
      <th class="col-md-2 col-xs-3">Cena</th>
      <th class="col-md-2 col-xs-3">Status</th>
    </tr>
    <tr class="warning no-result">
      <td colspan="4"><i class="fa fa-warning"></i> No result</td>
    </tr>
  </thead>
  <tbody>
    <tr  v-for="(porudzbina,i) in this.porudzbine" :key="i">
            <th scope="row">{{i}}</th>
            <td>{{porudzbina.restoran.naziv}}</td>
            <td>{{porudzbina.datum_vreme.substring(0,10)}}</td>
            <td>{{porudzbina.datum_vreme.substring(11,19)}}</td>
            <td>{{porudzbina.cena}}</td>
            <td>{{porudzbina.status}}</td>
     </tr>
  </tbody>
</table>
  
</template>
<script>
import axios from "axios";
export default {
    name: 'KupacView',
    data: function() {
        return {
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
    }
    
}
</script>
