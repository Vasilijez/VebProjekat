<template>
  <div id="allEmployees">
    <h1>Employees</h1>
    <button v-on:click="addEmployee">Add new employee</button>
    <br /><br />
    <div class="container-employees">
      <table id="employees">
        <tr>
          <th>Ime</th>
          <th>Prezime</th>
          <th>Pozicija</th>
          <th>About</th>
          <th>Delete</th>
          <th>Edit</th>
        </tr>
        <!--<tr v-for="employee in employees" :key="employee.id">
          <td>{{ employee.firstName }}</td>
          <td>{{ employee.lastName }}</td>
          <td>{{ employee.position }}</td>
          <td>
            <button class="btnSeeMore" v-on:click="seeMore(employee)">
              See more
            </button>
          </td>
          <td>
            <button class="delete" v-on:click="deleteEmployee(employee.id)">
              Delete
            </button>
          </td>
        </tr>-->

        <!-- ovde koristimo employee komponentu koju smo dole importovali i mi saljemo komponenti objekte employee -->
        <employees-comp
          v-for="employee in employees"
          :key="employee.id"
          :employee="employee"
        >
        </employees-comp>
      </table>
    </div>
  </div>
</template>

<script>
//import axios from "axios";
import EmployeesComp from "../components/EmployeesComp.vue"; // importujemo da bismo je naveli u export
export default { // sta cemo da prikazemo odnosno da exportujemo
  name: "EmployeesView",
  components: { EmployeesComp }, // koje komponente koristi
  data: function () {
    return {
      employees: [], // vraca listu zaposlenih, evo ovde smo napravili employees listu i dole u callback funkciji dodeljujemo vrednosti, ovde se onda pravi taj objekat employees
    };
  },
  mounted: function () { // mounted je udica za callback funkcije

    //primer axios poziva
    /*axios
      .get("http://localhost:8081/api/employees")
      .then((res) => {
        this.employees = res.data
      })
      .catch((err) =>{
        console.log(err)
      })*/

      fetch('http://localhost:8081/api/employees')
        .then(response => response.json())
        .then(data => {console.log("Success:", data); this.employees = data}) // bitno samo this.emplyees = data
        .catch((error) => {
          console.error("Error:", error);
        })
        ;
  },
  methods: {
    addEmployee: function() {
      this.$router.push("/add-employee"); // napravi (pushuj) url i preusmeri se rutiranjem
    },
    seeMore: function (employee) { // ovde dobije employee, ali ima delova koda kao sto je employeesComp koji barata sa employee a ne znam odakle mu
      this.$router.push("/employee?id=" + employee.id); // idemo preusmeravamo se momentalno na pomenuti link i vidi formata, pravimo query id link
    },
    deleteEmployee: function (id) {
      fetch("http://localhost:8081/api/employees/" + id, {
        method: "DELETE",
      }).then((res) => {
        if (res.ok) {
          window.location.reload(); // osvezi stranicu
        }
      });
    },
    editEmployee: function(employee) {
      this.$router.push("/edit-employee?id=" + employee.id);
    },
  },
};
</script>

<style>
h1 {
  color: #4caf50;
}

h3 {
  color: #4caf50;
}

.container-employees {
  display: flex;
  justify-content: center;
}

#employees th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: left;
  background-color: #4caf50;
  color: white;
}

#employees td,
#employees th {
  border: 1px solid #ddd;
  padding: 8px;
}

#employees tr:nth-child(even) {
  background-color: #f2f2f2;
}

#employees tr:hover {
  background-color: #ddd;
}

.card {
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
  max-width: 300px;
  margin: auto;
  text-align: center;
}

.title {
  color: grey;
  font-size: 18px;
}
</style>

