<template>

<div class="hello">
    <form ref="uploadForm" @submit.prevent="submit">
        <input type="file" ref="uploadImage" @change="onImageUpload()" class="form-control" required>
        <input type="button" @click="startUpload" name="Upload" value="Upload"/>
    
    </form>
</div>

</template>




<script>
import axios from "axios";
export default {
    name: "ImageView",
    data: () => ({
        formData: null
    }),
    methods: {
        onImageUpload() {
            let file = this.$refs.uploadImage.files[0];
            this.formData = new FormData();
            this.formData.append("file", file);
        },
        startUpload() {
            axios({
                url: "http://localhost:8081/upload/image",
                method: 'POST',
                data: this.formData,
                headers: {
                    Accept: 'application/json',
                    'Content-Type' : 'multipart/form-data'
                },
            }).then(response => {
                console.log(JSON.stringify(response.data));
            });
        }
    }

}

</script>

