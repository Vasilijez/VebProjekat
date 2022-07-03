<template>

<section>
    <button @click="this.$router.push('/admin')">Admin Dashboard</button>
    <form @submit.prevent="handleSubmit">
        <label>Naziv</label>
        <input placeholder='"Fast Food Koliba"' maxlength = "25" required="true" type="text" v-model="this.restoran.naziv"/>
        <label>Tip restorana</label>
        <input placeholder='"Meksicki"' maxlength = "25" required="true" type="password" v-model="this.restoran.tipRestorana"/>
        <label>Adresa</label>
        <input placeholder='"Pere Cetkovica 30"' maxlength = "25" required="true" type="text" v-model="this.restoran.lokacija.adresa"/>
        <label>Geografska sirina</label>
        <input placeholder='"45.15"' maxlength = "25" required="true" type="text" v-model="this.restoran.lokacija.sirina"/>
        <label>Geografska duzina</label>
        <input placeholder='"45.15"' maxlength = "25" required="true" type="text" v-model="this.restoran.lokacija.duzina"/>
        <br>
        <br>
        <button>Kreiraj restoran</button>
    </form>
    <b v-if="this.errMsg === true">Neispravni podaci!</b>
</section>


</template>



<script>

import axios from 'axios';
export default {
    name: "KreirajRestoran",
    data: function() {
        return {
            restoran: {
                naziv: "",
                tipRestorana: "",
                lokacija: {
                    adresa: "",
                    sirina: 0,
                    duzina: 0
                }
            },
            errMsg: false
        }
    },

    methods: {  
        handleSubmit: function() {
            console.log(this.restoran)
            axios
            .post("http://localhost:8081/api/dodaj-restoran", this.restoran, {withCredentials: true})
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
