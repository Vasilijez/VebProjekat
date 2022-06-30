<template>
    <h1>{{ this.role }}</h1>
    <form @submit.prevent="handleSubmit">
        <label>Korisnicko ime</label>
        <input type="text" v-model="this.user.korisnickoIme"/>
        <label>Lozinka</label>
        <input type="password" v-model="this.user.lozinka"/>
        <button>Log In</button>
    </form>
</template>

<script>
export default {
    name: "LoginView",
    data: function() {
        return {
            user: {
                korisnickoIme: "",
                lozinka: ""
            }
        }
    },

    methods: {
        handleSubmit: function() {
            fetch('http://localhost:8081/api/login', {
                method: "POST",
                headers: {
                Accept: "application/json",
                "Content-type": "application/json",
                },
                body: JSON.stringify(this.user)
            })
                .then(response => response.json())
                .then(data => {
                    console.log("Success:", data); 
                    this.role = data.uloga
                   /* if (uloga==="Menadzer") {
                        this.$router.push("/edit-employee?id=" + employee.id);
                    }*/
                    }
                )
                .catch((error) => { // todo resiti preko exception-a
                console.error("Error:", error);
                });
        }
    }
};    
</script>
