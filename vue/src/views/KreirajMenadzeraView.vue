<template>

<section>
    <button @click="this.$router.push('/admin')">Admin Dashboard</button>
    <form @submit.prevent="handleSubmit">
        <label>Korisnicko ime</label>
        <input placeholder='"pera123"' maxlength = "25" required="true" type="text" v-model="this.user.korisnickoIme"/>
        <label>Lozinka</label>
        <input placeholder='"petarPetrovic123!"' maxlength = "25" required="true" type="password" v-model="this.user.lozinka"/>
        <label>Ime</label>
        <input placeholder='"Petar"' maxlength = "25" required="true" type="text" v-model="this.user.ime"/>
        <label>Prezime</label>
        <input placeholder='"Petrovic"' maxlength = "25" required="true" type="text" v-model="this.user.prezime"/>
        <label>Datum rodjenja</label>
        <input type = "date" v-model="this.user.datum_rodjenja"/> 
        <label>Pol</label>
        <select name="pol" required="true" v-model="this.user.p">
            <option value="0">Muski</option>
            <option value="1">Zenski</option>
        </select>
        <br>
        <br>
        <button>Kreiraj menadzera</button>
    </form>
    <b v-if="this.errMsg === true">Neispravni podaci!</b>
</section>


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
                p: null,
                datum_rodjenja: "", 
                uloga: 0
                //restoran
            },
            errMsg: false
        }
    },

    methods: {  
        handleSubmit: function() {
            console.log(this.user)
            this.user.datum_rodjenja = this.user.datum_rodjenja+"T22:00:00";
            axios
            .post("http://localhost:8081/api/dodaj-menadzera", this.user, {withCredentials: true})
            .then(res => {
                console.log(res)
                this.$router.push("/admin")
            })
            .catch(error => {
                console.log(error)
                this.errMsg = true;
            }); 
        }
    }
};    
</script>
