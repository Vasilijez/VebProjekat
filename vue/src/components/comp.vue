
    <template>
        
                <form @submit.prevent="handleSubmit">
           <!-- <customInput v-model="email" :label="emailLabel"/> v-bind: je isto sto i :--> 
           <customInput 
                v-for="(input,i) in inputs" 
                v-bind:key="i"

                v-model="input.value" 
                v-bind:label="input.label"
                v-bind:type ="input.type"
           
           /> <!-- znaci custom input dobija label podatke od parenta a to je comp, jer su custom inputu potrebni -->
                <button>Log In</button>
    </form>


    </template>

    <script>
    import customInput from "../components/CustomInputComp.vue"
    export default {
    name: "comp",
    components: {customInput},
    data: function() {
        return {
            inputs: [
                {
                    label: 'Email', 
                    value: '', 
                    type: 'email',
                },
                {
                    label: 'Password', 
                    value: '', 
                    type: 'password',
                }
            ],
            email: "",
            password: "",
            title: "Login Form",
            emailLabel: 'Email',
            passwordLabel: 'Password'
};
    },
    //<p v-for="(str, i) in inputs" v-bind:key="str">{{ str }} </p> <!-- dobra je stvar koristiti key, pomaze kod bugova posle, unique reference for each paragraph-->
    methods: {
        handleSubmit() {
            console.log(this.inputs[0].value)
        }
    }
    }
        
    </script>


    <style>

    input {
        margin: 10px;
        color:  blue;
        display: block;
    }

    </style>