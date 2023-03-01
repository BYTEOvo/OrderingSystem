<template>
    <container>
        <el-upload class="upload-demo" style="margin: 20px 0;" drag action="https://jsonplaceholder.typicode.com/posts/"
            multiple>
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
        </el-upload>
        <el-button type="primary" style="margin-top: 10px;"><i class="el-icon-plus el-icon--left"> 发布</i></el-button>
    </container>
</template>

<script>

export default {
  name: 'AddBulletin2'

}
</script>
