<template>

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

<div class="form-group pull-right">
    <input type="text" class="search form-control" placeholder="Koga tražite?">
</div>
<span class="counter pull-right"></span>
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
    <tr  v-for="(korisnik,i) in korisnici" :key="i">
            <th scope="row">{{i}}</th>
            <td>{{korisnik.korisnickoIme}}</td>
            <td>{{korisnik.ime}}</td>
            <td>{{korisnik.prezime}}</td>
            <td>{{korisnik.p}}</td>
            <td>{{korisnik.uloga}}</td>
            <td>{{korisnik.datum_rodjenja.substring(0,10)}}</td>
    </tr>
  </tbody>
</table>


    
    <br>
    <button type="button" class="btn btn-secondary btn-block me-4" @click="this.$router.push('/kreiraj-menadzera')">Kreiraj menadzera</button>

    <button type="button" class="btn btn-secondary btn-block me-4" @click="this.$router.push('/kreiraj-dostavljaca')">Kreiraj dostavljaca</button>

    <button type="button" class="btn btn-secondary btn-block me-4" @click="this.$router.push('/kreiraj-restoran')">Kreiraj restoran</button>

    <button type="button" class="btn btn-danger btn-block me-4" @click="this.$router.push('/obrisi-restoran')">Obrisi restoran</button>

    <button type="button" class="btn btn-primary btn-block me-4" @click="this.$router.push('/izaberi-menadzera')">Izaberi menadžera</button>




</template>



<script>
import axios from "axios";
import $ from 'jquery';
export default {
    name: "AdminView",
    data: function () {
        return {
            korisnici: []
        };
    },
    mounted: function () {
    
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
      .get("http://localhost:8081/api/admin", {withCredentials:'include'})
      .then((res) => {
        this.korisnici = res.data
      })
      .catch((err) =>{ // todo neovlascen pristup
        console.log(err)
      })

    
    },
    methods: function() {
        
    }

}
   
</script>
