<template>
  <tr>
    <td>{{ employee.firstName }}</td>
    <td>{{ employee.lastName }}</td>
    <td>{{ employee.position }}</td>
    <td>
      <button class="btnSeeMore" v-on:click="seeMore">See more</button> <!-- ovde smo zapravo napravili u komponenti odmah i dugme, i posle u employees iterirali kroz employeesComp, prakticno spakovali ceo red u tabeli -->
    </td>
    <td>
      <button class="delete" v-on:click="deleteEmployee">
        Delete
      </button>
    </td>
    <td>
      <button class="delete" v-on:click="editEmployee">
        Edit
      </button>
    </td>
  </tr>
</template>

<script>
export default {
  name: "EmployeeComp",
  // latest: u ovaj props kazemo da ce nam neko kao osobinu u templete-u navesti ovaj comp i dodelice nam employee-a, kao sto je i uradjeno u employees view, i sada mozemo da operiramo nad employee objektom
  props: ["employee"], // neko nasledjivanje sa roditelja na dete, props je property - moguce da je ovo definisanje objekta nad kojim sve ovo radimo
  methods: {
    seeMore: function () { // on click seeMore se pozove funkcija seeMore
      this.$router.push("/employee?id=" + this.employee.id); // ovo nam potrebno za ono gore, kada se klikne na dugme da se pushuje odnosno pocnemo preusmeravati na odredjeni link
    },
    deleteEmployee: function() {
      fetch("http://localhost:8081/api/employees/" + this.employee.id, {
        method: "DELETE", // ovo naglasavamo ukoliko se na istom url-u nalaze vise razlicitih metoda
      }).then((res) => {
        if (res.ok) {
          window.location.reload();
        }
      });
    },
    editEmployee: function(){
          this.$router.push("/edit-employee?id=" + this.employee.id);
    }
  },
};
/* fetch("http://localhost:8081/api/employees/" + this.employee.id,{
      method: "PUT",
      }).then((res) => {
          if(res.ok){
            this.$router.push("http://localhost:8081/api/employees/");
          }

      });
*/
</script>


<style>
</style>
