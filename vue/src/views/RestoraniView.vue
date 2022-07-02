<template>
  <div id="allRestorani">
    <h1>Restorani</h1>

    <div class="container-employees">
      <table id="employees">
        <tr>
          <th>Naziv</th>
          <th>Tip restorana</th>
          <th>Adresa</th>
        </tr>

        <!-- ovde koristimo employee komponentu koju smo dole importovali i mi saljemo komponenti objekte employee -->
        <tr v-for="restoran in restorani">
            <td>{{ restoran.naziv }}</td>
            <td>{{ restoran.tipRestorana }}</td>
            <td>{{ restoran.lokacija.adresa }}</td> 
        </tr>
        </table>

        <input v-model="greeting"/>
        {{greeting}}

        <button @click="toggleBox">Show Box</button>
        <div v-if="isVisible" class="box"></div> <!-- ako je ispunje uslov prikazace se element -->
    </div>
  </div>

 <comp/>

</template>

<script>
//import axios from "axios";
import AppVue from "@/App.vue";
import EmployeesComp from "../components/EmployeesComp.vue"; // importujemo da bismo je naveli u export
import comp from "../components/comp.vue"
export default { // sta cemo da prikazemo odnosno da exportujemo
  name: "EmployeesView",
  components: { EmployeesComp, comp }, // koje komponente koristi
  data: function () {
    return {
        isVisible: false,
      restorani: [], // vraca listu zaposlenih, evo ovde smo napravili employees listu i dole u callback funkciji dodeljujemo vrednosti, ovde se onda pravi taj objekat employees
        greeting: ""
   };
  },
  mounted: function () { // mounted je udica za callback funkcije
      fetch('http://localhost:8081/api/restorani')
        .then(response => response.json())
        .then(data => {console.log("Success:", data); this.restorani = data}) // bitno samo this.emplyees = data
        .catch((error) => {
          console.error("Error:", error);
        })
        ;
  },
    methods: {
    toggleBox: function() {
        this.isVisible = !this.isVisible
    }
  }
  
};

</script>



<style> 

.container-employees {
  justify-content: center;
}

.box {
    margin-top: 2cm;
    margin-bottom: 3cm;
    margin-left: 10cm;
    color: black;
}

input {
    margin: 10px;
    color:  blue;
    display: block;
}

</style>