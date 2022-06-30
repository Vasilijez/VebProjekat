<template>
  <tr>
    <td>{{ restoran.naziv }}</td>
    <td>{{ restoran.tipRestorana }}</td>
    <td>{{ restoran.lokacija }}</td> 
    <td>
      <button class="btnSeeMore" v-on:click="seeMore">See more</button> <!-- ovde smo zapravo napravili u komponenti odmah i dugme, i posle u employees iterirali kroz employeesComp, prakticno spakovali ceo red u tabeli -->
    </td>
  </tr>
</template>

<script>
export default {
  name: "RestoraniComp",
  // latest: u ovaj props kazemo da ce nam neko kao osobinu u templete-u navesti ovaj comp i dodelice nam employee-a, kao sto je i uradjeno u employees view, i sada mozemo da operiramo nad employee objektom
  props: ["restoran"], // neko nasledjivanje sa roditelja na dete, props je property - moguce da je ovo definisanje objekta nad kojim sve ovo radimo
  methods: {
    seeMore: function () { // on click seeMore se pozove funkcija seeMore
      this.$router.push("/restoran?id=" + this.restoran.id); // ovo nam potrebno za ono gore, kada se klikne na dugme da se pushuje odnosno pocnemo preusmeravati na odredjeni link
    }
  },
};
</script>


<style>
</style>
