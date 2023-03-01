<template>
  <div>
    <div style="margin: 20px 20px; text-align: left; font-size: 20px">
      <h1>系统公告</h1>
    </div>
    <el-collapse style="margin-left:3%" accordion v-model="active">
      <el-collapse-item  :name="index" v-for="(item ,index) in notices" :key="item.id">
        <template slot="title">
          <span style="color: #888">{{ item.time }}</span>
          <div  style="margin-left: 3%;">
            <b>{{"公告"}}</b>
            <b>{{item.id}}</b>
            <b>{{"："}}</b>
            <b>{{ item.title }}</b>
          </div>
        </template>
        <div style="padding: 0 20px">{{ item.content }}</div>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
export default {
  name: 'DeliverMess',
  data () {
    return {
      notices: [
        {
          id: 1,
          title: 'hello',
          time: '2004',
          content: 'hhhhhhhhhh'
        },
        {
          id: 2,
          title: 'hello',
          time: '2004',
          content: 'hhhhhhhhhh'
        }

      ],
      active: 0,
      user: {}
    }
  },
  created () {
    this.loadMsg()
  },
  methods: {
    loadMsg () {
      this.$axios.get('/delivery/bulletins').then((res) => {
        if (res && res.data.code === 200) {
          this.notices = res.data.result
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
