<template>
  <div class="list-item" style="display: flex">
    <el-table :data="tableData" stripe v-loading="loading" style="width: 100%" max-height="500">
      <!--    <el-table :span-method="spanMethod" :key="id" :data="tableData" border="1px" stripe v-loading="loading" style="width: 100%" max-height="500">-->
      <el-table-column prop="table" label="桌号" width="120" />
      <el-table-column prop="dish.name" label="菜名" width="300" />
      <el-table-column prop="dish.price" label="价格" width="150" />
      <el-table-column prop="count" label="数量" width="150" />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button @click="ready(scope.row)" v-if="scope.row.table !== '外卖'" type="text" size="small" >通知上菜</el-button>
          <el-button @click="ready(scope.row)" v-if="scope.row.table === '外卖'" type="text" size="small" >备餐完成</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
import axios from 'axios'
export default {

  name: 'CookOrder2',
  inject: ['reload'],
  data () {
    return {
      tableData: [],
      orderDetail: [],
      tables: [],
      loading: false,
      timer: null
    }
  },
  mounted () {
    this.loadTables()
    this.timer = setInterval(() => {
      setTimeout(this.loadTables, 0)
    }, 1000 * 5)
  },
  beforeDestroy () {
    clearInterval(this.timer)
    this.timer = null
  },
  // created () {
  //   const _this = this
  //   axios.get('/findAll/0/10').then(function (resp) {
  //     _this.tableData = resp.data.result.content
  //     _this.total = resp.data.result.totalElements
  //     _this.loading = false
  //   })
  // },
  created () {
    this.loadTables()
  },
  methods: {
    loadTables () {
      this.$axios.get('/cook/order/unprocessed/tables').then(resp => {
        if (resp && resp.status === 200) {
          this.tables = resp.data.result
          this.loadOrderDetails()
        }
      })
    },
    loadOrderDetails () {
      this.$axios.get('/cook/order/unprocessed').then(resp => {
        if (resp && resp.status === 200) {
          this.orderDetail = resp.data.result
          this.handleData()
        }
      })
    },
    handleData () {
      this.tableData = []
      this.orderDetail.forEach((item, index) => {
        item.forEach(item2 => {
          if (this.tables[index] === 'DELIVERY') {
            item2.table = '外卖'
          } else {
            item2.table = this.tables[index]
          }
          this.tableData.push(item2)
        })
      })
    },
    ready (row) {
      const _this = this
      axios.get('/cook/' + row.id + '/ready')
        .then(res => {
          console.log(res)
          _this.$message({
            showClose: true,
            message: '提交成功！',
            type: 'success'
          })
          this.reload()
        })
    }
  }
}

</script>
<style>

</style>
