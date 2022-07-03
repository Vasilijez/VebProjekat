<template>
<div>
    <h2 style="text-align:center">Employee Profile</h2>
    <div class="card">
        <img alt="User" src="../assets/user.png"/>
        <h1> {{ employee.firstName }} </h1>
        <h1> {{ employee.lastName }} </h1>
        <p class="title">{{employee.position}}</p>
        <br>
    </div>
</div>
</template>
<script>
//import axios from "axios";
export default {
    name: 'EmployeeView',
    data: function() {
        return {
            employee: {}, // bice da se ovde generise employee kog svi koriste u aplikaciji kao objekat
        }
    },
    mounted: function() {
        /*axios
            .get("http://localhost:8081/api/employees/" + this.$route.query.id)
            .then((res) => {
                this.employee = res.data
            })
            .catch((err) => {
                console.log(err)
            })*/

        // ovde kao callback funkciju odmah poziva i pravi employee
         // redirekcija
        let role = localStorage.getItem('role')
        if ("\""+'Admin'+"\"" === role) {
            console.log("radi!")
            this.$router.push("/admin")
        } else if ("\""+'Menadzer'+"\"" === role) {
            console.log("radi!")
            this.$router.push("/menadzer")
        }

       fetch('http://localhost:8081/api/employees/' + this.$route.query.id) // vue spremi cist id, a mi ga preko query-ja generisemo u url baru
            .then(response => response.json())
            .then(data => {console.log("Success:", data); this.employee = data})
            .catch((error) => {
                console.error("Error:", error);
            });
    
       

    }
}
</script>
