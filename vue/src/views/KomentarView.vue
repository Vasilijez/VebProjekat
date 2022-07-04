
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

<div class="card">
               
              <div class="row">
                  
                  <div class="col-1">
                  </div>
                  
                  <div class="col-10">
                      
                      <div class="comment-box ml-2">
                          
                          <h4>Dodaj komentar</h4>
                          
                          <div class="rating"> 
                              <input v-model="this.komentar.ocena" type="radio" name="rating" value="5" id="5"><label for="5">☆</label>
                              <input v-model="this.komentar.ocena" type="radio" name="rating" value="4" id="4"><label for="4">☆</label> 
                              <input v-model="this.komentar.ocena" type="radio" name="rating" value="3" id="3"><label for="3">☆</label>
                              <input v-model="this.komentar.ocena" type="radio" name="rating" value="2" id="2"><label for="2">☆</label>
                              <input v-model="this.komentar.ocena" type="radio" name="rating" value="1" id="1"><label for="1">☆</label>
                          </div>
                          
                          <div class="comment-area">
                              
                              <textarea v-model="this.komentar.tekst" class="form-control" placeholder="Podelite Vaše iskustvo..." rows="4"></textarea>
                          
                          </div>
                          
                          <div class="comment-btns mt-3">
                              
                              <div class="row">
                                  
                                  <div class="col-6">
                                      
                                      <div class="pull-left">
                                      
                                      <button @click="posalji()" class="btn btn-success btn-sm">Pošalji</button>      
                                        <div class="divider"> </div> 
                                      <button class="btn btn-success send btn-sm">Odustani <i class="fa fa-long-arrow-left ml-2"></i></button>      
                                

                                      </div>
                                  
                                  </div>
                                  
                                  
                              
                              </div>
                          
                          </div>
                      
                      
                      </div>
                  
                  </div>
              
              
              </div>
    
          </div>


</template>
<script>
import axios from "axios";
export default {
    name: 'DostavljacView',
    data: function() {
        return {
            uloga: "neovlascen_pristup",
            komentar: {
                restoran: { 
                    naziv: ""
                },
                tekst: "",
                ocena: 0
            }
        }
    },
    mounted: function() {
        this.komentar.restoran.naziv = this.$route.query.nazivRestorana;
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
        posalji: function() {
          console.log(this.komentar.ocena, this.komentar.tekst)
        axios
            .post('http://localhost:8081/api/dodajKomentar/' + this.$route.query.id, this.komentar, {withCredentials: true})
            .then(res => {
                console.log(res)
                this.$router.push("/restoran?id=" + this.$route.query.nazivRestorana);
            })
            .catch(error => {
                console.log(error)
            }); 
        }
    }
}
</script>


<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Manrope:wght@200&display=swap');

html,
body {
    height: 100%
}

body {
    display: grid;
   place-items: center;
    font-family: 'Manrope', sans-serif;
   background: red;
    
}

.card {
    position: relative;
    display: flex;
    flex-direction: column;
    min-width: 0;
    padding: 20px;
    width: 450px;
    word-wrap: break-word;
    background-color: #fff;
    background-clip: border-box;
    border-radius: 6px;
    
}

.comment-box{
    
    padding:5px;
}



.comment-area textarea{
   resize: none; 
        border: 1px solid #ad9f9f;
}


.form-control:focus {
    color: #495057;
    background-color: #fff;
    border-color: #ffffff;
    outline: 0;
    box-shadow: 0 0 0 1px rgb(255, 0, 0) !important;
}

.send {
    color: #fff;
    background-color: #ff0000;
    border-color: #ff0000;
}

.send:hover {
    color: #fff;
    background-color: #f50202;
    border-color: #f50202;
}


.rating {
 display: flex;
        margin-top: -10px;
    flex-direction: row-reverse;
    margin-left: -4px;
        float: left;
}

.rating>input {
    display: none
}

.rating>label {
        position: relative;
    width: 19px;
    font-size: 25px;
    color: #ff0000;
    cursor: pointer;
}

.rating>label::before {
    content: "\2605";
    position: absolute;
    opacity: 0
}

.rating>label:hover:before,
.rating>label:hover~label:before {
    opacity: 1 !important
}

.rating>input:checked~label:before {
    opacity: 1
}

.rating:hover>input:checked~label:before {
    opacity: 0.4
}

.divider{
    width:5px;
    height:auto;
    display:inline-block;
}

</style >