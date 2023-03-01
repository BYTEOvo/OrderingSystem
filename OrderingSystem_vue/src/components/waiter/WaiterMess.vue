<template>
  <div>
    <div style="margin: 20px 0; text-align: left; font-size: 20px">
      <h1>系统公告</h1>
    </div>
    <el-collapse class="user_skills" accordion v-model="active">
      <el-collapse-item  class="coll" :name="index" v-for="(item ,index) in notices" :key="item.id">
        <template slot="title">
          <b>{{"公告"}}</b>
          <b>{{item.id}}</b>
          <b>{{"："}}</b>
          <b>{{ item.title }}</b>
          <span style="margin-left: 50px; color: #888">{{ item.time }}</span>
        </template>
        <div style="padding: 0 20px">{{ item.content }}</div>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>
<script>

export default {
  name: 'WaiterMess',
  data () {
    return {
      notices: [],
      active: 0,
      user: {}
    }
  },
  created () {
    this.load()
  },
  methods: {
    load () {
      this.$axios.get('/waiter/bulletins').then((res) => {
        this.notices = res.data.result
      })
    }

  }

}
</script>

<style scoped>

.coll >>> .el-collapse-item__header{
  background-color: transparent;
}
.coll >>> .el-collapse-item__wrap{
  background-color: transparent;
}
.coll >>>.el-collapse-item,.el-collapse-item__wrap{
  border: none;
}
</style>
