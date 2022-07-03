<template>
    <label>     
        {{ label }}
        <input type="text" v-model="label"/>   
    </label>

</template>

<script>
export default {
name: "customInput",
props: ['label', 'modelValue'], // modelValue jer ne prosledjuje direktno osobinu? ovde su svi podaci koje dobijamo i koji su nam prosledjeni od comp
data: function() {
    return {
        input: ""
    }
},
computed: {
    inputValue: {
        get() {
            return this.modelValue
        },
        set(nekaVrednost) {
            this.$emit('update:modelValue', nekaVrednost) // emit od emituje, update je event
        }
    }
}
}
</script>

<style></style>