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

<link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css"
    />


<br>

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



    <div v-if="this.uloga === 'Admin'">
    <br>
    <button type="button" class="btn btn-secondary btn-block me-4" @click="this.$router.push('/kreiraj-menadzera')">Kreiraj menadzera</button>
    <button type="button" class="btn btn-secondary btn-block me-4" @click="this.$router.push('/kreiraj-dostavljaca')">Kreiraj dostavljaca</button>
    <button type="button" class="btn btn-secondary btn-block me-4" @click="this.$router.push('/kreiraj-restoran')">Kreiraj restoran</button>
    <button type="button" class="btn btn-danger btn-block me-4" @click="this.$router.push('/obrisi-restoran')">Obrisi restoran</button>
    <button type="button" class="btn btn-primary btn-block me-4" @click="this.$router.push('/izaberi-menadzera')">Izaberi menadžera</button>
    </div>



</template>




<script>
import axios from "axios";
import $ from 'jquery';
export default {
    name: "HomeView",
    data: function () {
        return {
            uloga: "neovlascen_pristup",
            restorani: []
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
    
    $(document).ready(function() {
  $(".search").keyup(function () {
    var searchTerm = $(".search").val();
    var listItem = $('.results tbody').children('tr');
    var searchSplit = searchTerm.replace(/ /g, "'):containsi('")
    
  $.extend($.expr[':'], {'containsi': function(elem, i, match, array){
        return (elem.textContent || elem.innerText || '').toLowerCase().indexOf((match[3] || "").toLowerCase()) >= 0;
    }
  });
    
  $(".results tbody tr").not(":containsi('" + searchSplit + "')").each(function(e){
    $(this).attr('visible','false');
  });

  $(".results tbody tr:containsi('" + searchSplit + "')").each(function(e){
    $(this).attr('visible','true');
  });

  var jobCount = $('.results tbody tr[visible="true"]').length;
    $('.counter').text(jobCount + ' item');

  if(jobCount == '0') {$('.no-result').show();}
    else {$('.no-result').hide();}
		  });
});

    axios
      .get("http://localhost:8081/api/restorani")
      .then((res) => {
        console.log(res.data)
        this.restorani = res.data
      })
      .catch((err) =>{ // todo neovlascen pristup
        console.log(err)
      })

    
    },
    methods: {
        seeMore: function (naziv) { // ovde dobije employee, ali ima delova koda kao sto je employeesComp koji barata sa employee a ne znam odakle mu
          this.$router.push("/restoran?id=" + naziv); // idemo preusmeravamo se momentalno na pomenuti link i vidi formata, pravimo query id link
        },
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