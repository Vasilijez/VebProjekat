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

<div class="form-group pull-right">
    <input type="text" class="search form-control" placeholder="Koji restoran tražite?">
</div>
<span class="counter pull-right"></span>
<table class="table table-hover table-bordered results">
  <thead>
    <tr>
      <th>#</th>
      <th class="col-md-2 col-xs-3">Naziv restorana</th>
      <th class="col-md-2 col-xs-3">Tip restorana</th>
      <th class="col-md-2 col-xs-3">Adresa</th>
      <th class="col-md-2 col-xs-3">Geografska širina</th>
      <th class="col-md-2 col-xs-3">Geografska dužina</th>
      <th class="col-md-1 col-xs-3">Više o restoranu</th>
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
            <td><button @click="seeMore(restoran.naziv)" type="button" class="btn btn-secondary"><i class="bi bi-check-circle-fill text-light"></i> Više</button></td>
    </tr>
  </tbody>
</table>

</template>

<script>
//import axios from "axios";
import AppVue from "@/App.vue";
import EmployeesComp from "../components/EmployeesComp.vue"; // importujemo da bismo je naveli u export
import comp from "../components/comp.vue"
export default { // sta cemo da prikazemo odnosno da exportujemo
  name: "EmployeesView",
  components: { EmployeesComp, comp }, // koje komponente koristi
  data: function () {
    return {
        isVisible: false,
      restorani: [], // vraca listu zaposlenih, evo ovde smo napravili employees listu i dole u callback funkciji dodeljujemo vrednosti, ovde se onda pravi taj objekat employees
        greeting: ""
   };
  },
  mounted: function () { // mounted je udica za callback funkcije
      fetch('http://localhost:8081/api/restorani')
        .then(response => response.json())
        .then(data => {console.log("Success:", data); this.restorani = data}) // bitno samo this.emplyees = data
        .catch((error) => {
          console.error("Error:", error);
        })
        ;
  },
    methods: {
    toggleBox: function() {
        this.isVisible = !this.isVisible
    }
  }
  
};

</script>



<style> 

.container-employees {
  justify-content: center;
}

.box {
    margin-top: 2cm;
    margin-bottom: 3cm;
    margin-left: 10cm;
    color: black;
}

input {
    margin: 10px;
    color:  blue;
    display: block;
}

</style>