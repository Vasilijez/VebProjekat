<template>
</template>
<script>
import axios from 'axios';
export default {
    name: "LoginView",
    data: function() {
        return {
            uloga: "neovlascen_pristup",
        }
    },
    methods: {
        ucitana() {
            console.log(this.uloga, 1)
                if (this.uloga === 'Admin') {
                    this.$router.push("/admin") 
                } else if (this.uloga === 'Menadzer') {
                    this.$router.push("/menadzer")
                } else if (this.uloga === 'Kupac') {
                    this.$router.push("/kupac")
                } else if (this.uloga === 'Dostavljac') {
                    this.$router.push("/dostavljac")
                } else {
                    this.$router.push("/login")
                }  
        },
    },
    mounted: function() {
        axios // uloga
            .get('http://localhost:8081/api/vratiUlogu/', {withCredentials: true})
            .then((res) => {
                this.uloga = res.data
                this.ucitana()
           })
            .catch((err) =>{ // todo neovlascen pristup
                console.log(err)
            })
    }
};    
</script>
