<template>

</template>

<script>


import axios from 'axios';
export default {
    name: "OdjaviSeView",
    data: function() {
        return {
            uloga: "neovlascen_pristup",
            user: {
                korisnickoIme: "",
                lozinka: ""
            },
        }
    },
    mounted: function() {
        axios // uloga
            .get('http://localhost:8081/api/vratiUlogu/', {withCredentials: true})
            .then((res) => {
                this.uloga = res.data
           })
            .catch((err) =>{ // todo neovlascen pristup
                console.log(err)
            })
        axios // logout
            .get('http://localhost:8081/api/logout', {withCredentials: true})
            .then((res) => {
                this.$router.push("/login")
           })
            .catch((err) =>{ // todo neovlascen pristup
                alert("Bezuspesno!")
            })
    }
};    

</script>