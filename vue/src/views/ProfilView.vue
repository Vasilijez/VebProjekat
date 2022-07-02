<template>

<div>
    <label for="example-datepicker">Choose a date</label>
    <b-form-datepicker id="example-datepicker" v-model="value" class="mb-2"></b-form-datepicker>
    <p>Value: '{{ value }}'</p>
  </div>


<div class="container">
    <h1>Vaš profil</h1>
  	<hr>
	<div class="row">
      <!-- left column -->
      
      <!-- edit form column -->
      <div class="col-md-9 personal-info">
        <h3>Lični podaci</h3>
        
        <form class="form-horizontal" role="form">
          <div class="form-group">
            <label class="col-lg-3 control-label">Ime:</label>
            <div class="col-lg-8">
              <input class="form-control" required="true" maxlength = "20" type="text" v-model="this.user.ime">
            </div>
          </div>
          <div class="form-group">
            <label class="col-lg-3 control-label">Prezime:</label>
            <div class="col-lg-8">
              <input class="form-control" required="true" maxlength = "20" type="text" v-model="this.user.prezime">
            </div>
          </div>
          <div class="form-group">
            <label class="col-md-3 control-label">Korisničko ime:</label>
            <div class="col-md-8">
              <input class="form-control" required="true" maxlength = "20" type="text" v-model="this.user.korisnickoIme">
            </div>
          </div>
          <div class="form-group">
            <label class="col-md-3 control-label">Lozinka:</label>
            <div class="col-md-8">
              <input class="form-control" required="true" maxlength = "20" type="password" v-model="this.lozinka">
            </div>
          </div>
          <div class="form-group">
            <label class="col-md-3 control-label"></label>
            <div class="col-md-8">
              <input @click="save()" type="button" class="btn btn-primary" value="Save Changes">
              <span></span>
              <input @click="cancel()" type="button" class="btn btn-default" value="Cancel">
            </div>
          </div>
          <!-- todo datum i pol -->
        </form>
      </div>
  </div>
</div>
<hr>





    <div>
      <table>
        <tr>
            <th>Korisnicko ime</th>
            <td>{{ this.user.korisnickoIme }}</td>
            <td><button v-on:click="izmeniKorisnickoIme">Izmeni</button></td>
        </tr>
        <tr>
            <th>Lozinka</th>
            <td>••••••••</td>
            <td><button v-on:click="izmeniLozinku">Izmeni</button></td>
        </tr>
        <tr>
            <th>Ime</th>
            <td>{{ this.user.ime }}</td>
            <td><button v-on:click="izmeniIme">Izmeni</button></td>
        </tr>
        <tr>
            <th>Prezime</th>
            <td>{{ this.user.prezime }}</td>
            <td><button v-on:click="izmeniPrezime">Izmeni</button></td>
        </tr>
        <tr>
            <th>Pol</th>
            <td>{{ this.user.p }}</td>
            <td><button v-on:click="izmeniPol">Izmeni</button></td>
        </tr>
        <tr>
            <th>Datum rodjenja</th>
            <td>{{ this.user.datum_rodjenja }}</td>
            <td><button v-on:click="izmeniPol">Izmeni</button></td>
        </tr>
      </table>
    </div>

</template>

<script>
import axios from "axios";
export default {
  name: "ProfilView",
  data: function () {
    return {
      user: {} /* {
        korisnickoIme: '',
        lozinka: 'safsa',
        ime: '',
        prezime: '',
        p: '',
        datum_rodjenja: '',
      }*/,
      resUser: '',
      lozinka: "12345678",
      value: '',
      tmp: "2000-04-04",
      n: ''
    };
  },
   mounted: function () {
    axios
      .get("http://localhost:8081/api/profil", {withCredentials:'include'})
      .then((res) => {
        console.log(res.data)
        
        this.user = JSON.parse(JSON.stringify(res.data));
        console.log(this.user)
        this.resUser = JSON.parse(JSON.stringify(res.data));
        console.log(this.resUser.datum_rodjenja)
      })
      .catch((err) =>{
        console.log(err)
      })
   },
   methods: {
        cancel: function() {
            this.user = this.resUser
            window.location.reload(); 
        },
        save: function() {
            //this.user.datum_rodjenja = this.user.datum_rodjenja+"T22:00:00";
            console.log(this.user)
            /*axios
            .put("http://localhost:8081/api/izmeni-profil", this.user)
            .then(res => {
                console.log(res)
                
            })
            .catch(error => {
                console.log(error)
                
            });*/
            
            console.log(this.user)
            n = {korisnickoIme: this.user.korisnickoIme,
        lozinka: this.user.lozinka,
        ime: this.user.ime,
        prezime: this.user.prezime,
        p: this.user.p,
        datum_rodjenja: this.user.datum_rodjenja}
        
            fetch('http://localhost:8081/api/izmeni-profil', {
        method: "PUT",
        headers: {
          Accept: "application/json",
          "Content-type": "application/json",
        },
        body: JSON.stringify(n),
      })
        .then((response) => response.json)
        .then((data) => {
          console.log("Success : " + data);
        })
        .catch((error) => {
          console.error("Error:", error);
        });
        }
   }
}


</script>

