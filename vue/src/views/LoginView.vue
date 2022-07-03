<template>


    <button @click="this.$router.push('/home')">Pocetna strana</button>
    <form @submit.prevent="handleSubmit">
        <label>Korisnicko ime</label>
        <input type="text" v-model="this.user.korisnickoIme"/>
        <label>Lozinka</label>
        <input type="password" v-model="this.user.lozinka"/>
        <button @click="this.errMsg = false">Log In</button>
        <button class="reg-button" @click="this.$router.push('/register')">Register</button>
    </form>
    <b color="red" v-if="this.errMsg === true">Neispravni podaci</b>
    
</template>

<script>
import axios from 'axios';
export default {
    name: "LoginView",
    data: function() {
        return {
            user: {
                korisnickoIme: "",
                lozinka: ""
            },
            errMsg: false
        }
    },

    methods: {  
        handleSubmit: function() {
            axios
            .post("http://localhost:8081/api/login", this.user, {withCredentials:'include'})
            .then(res => {
                if (res.data.uloga === 'Admin') {
                    this.$router.push("/admin") // bolje mozda redirektovati na /profil
                } else if (res.data.uloga === 'Menadzer') {
                    this.$router.push("/menadzer")
                } else if (res.data.uloga === 'Kupac') {
                    this.$router.push("/kupac")
                } else if (res.data.uloga === 'Dostavljac') {
                    this.$router.push("/dostavljac")
                }
            })
            .catch(error => {
                this.errMsg = true;
            }); 
        }
    }
};    
</script>
