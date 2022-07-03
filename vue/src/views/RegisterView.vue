<template>


    <button @click="this.$router.push('/home')">Pocetna strana</button>
    <form @submit.prevent="handleSubmit">
        <label>Korisnicko ime</label>
        <input placeholder='"pera123"' required="true" type="text" v-model="this.user.korisnickoIme"/>
        <label>Lozinka</label>
        <input placeholder='"petarPetrovic123!"' required="true" type="password" v-model="this.user.lozinka"/>
        <label>Ime</label>
        <input placeholder='"Petar"' required="true" type="text" v-model="this.user.ime"/>
        <label>Prezime</label>
        <input placeholder='"Petrovic"' required="true" type="text" v-model="this.user.prezime"/>
        <label>Datum rodjenja</label>
        <input type = "date" v-model="user.datum_rodjenja"/> 
        <label>Pol</label>
        <select name="pol" required="true" v-model="this.user.pol">
            <option value="0">Muski</option>
            <option value="1">Zenski</option>
        </select>
        <br>
        <br>
        <button>Registruj se</button>
    </form>
    <b v-if="this.errMsg === true">Neispravni podaci!</b>

</template>



<script>

import axios from 'axios';
export default {
    name: "RegisterView",
    data: function() {
        return {
            user: {
                korisnickoIme: "",
                lozinka: "",
                ime: "",
                prezime: "",
                pol: "",
                datum_rodjenja: "", 
            },
            errMsg: false
        }
    },

    methods: {  
        handleSubmit: function() {
            this.user.datum_rodjenja = this.user.datum_rodjenja+"T22:00:00";
            axios
            .post("http://localhost:8081/api/register", this.user)
            .then(res => {
                console.log(res)
                this.$router.push("/kupac")
            })
            .catch(error => {
                console.log(error)
                this.errMsg = true;
            }); 
        }
    }
};    
</script>
