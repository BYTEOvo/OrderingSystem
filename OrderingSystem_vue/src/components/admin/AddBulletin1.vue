<template>
    <container>
        <div style="margin: 20px 0;"></div>
        <el-input type="textarea" :rows="10" placeholder="请输入员工公告内容" v-model="textarea" maxlength="100" show-word-limit>
        </el-input>
                <el-button type="primary" ><i class="el-icon-plus el-icon--left"> 发布</i></el-button>
    </container>
</template>

<script>

export default {
  name: 'AddBulletin1',
  data () {
    return {
      text: '',
      textarea: ''
    }
  }
}
</script>
