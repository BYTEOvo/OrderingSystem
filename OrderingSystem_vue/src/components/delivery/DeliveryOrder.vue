<template>
  <div class="list-item" style="display: flex">
    <el-table :data="tableData" stripe v-loading="loading" style="width: 100%" max-height="500" :row-class-name="rowClassName">
      <el-table-column type="expand">
        <template v-slot="scope">
          <el-table :data="details[scope.row.rowId-1]">
            <el-table-column prop="dish.name" label="菜品名" width="300"/>
            <el-table-column prop="count" label="数量" width="300"/>
            <el-table-column prop="dish.price" label="单价" width="300"/>
          </el-table>
        </template>
      </el-table-column>
      <!--    <el-table :span-method="spanMethod" :key="id" :data="tableData" border="1px" stripe v-loading="loading" style="width: 100%" max-height="500">-->
      <el-table-column prop="rowId" label="订单号" width="120" />
      <el-table-column prop="address" label="用户地址" width="300" />
      <el-table-column prop="user.username" label="用户名" width="150" />
      <el-table-column prop="phoneNumber" label="电话号码" width="150" />
      <el-table-column prop="price" label="价格" width="150" />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button @click="ready(scope.row)" type="text" size="small" >接单</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'DeliverOrder',
  inject: ['reload'],
  data () {
    return {
      tableData: [],
      loading: false,
      timer: null,
      details: []
    }
  },
  mounted () {
    this.loadOrders()
    this.timer = setInterval(() => {
      setTimeout(this.loadOrders, 0)
    }, 1000 * 5)
  },
  beforeDestroy () {
    clearInterval(this.timer)
    this.timer = null
  },
  created () {
    this.loadOrders()
  },
  methods: {
    rowClassName ({row, rowIndex}) {
      row.rowId = rowIndex + 1
    },
    loadOrders () {
      // 调取可以配送的订单
      this.$axios.get('/delivery/order/receive').then(resp => {
        if (resp && resp.status === 200) {
          if (this.tableData.toString() !== resp.data.result.toString()) {
            this.tableData = resp.data.result
            this.loadDetails()
          }
        }
      })
    },
    ready (row) {
      // 接受此订单
      this.$confirm('确认通知吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const _this = this
        // 发送接收到订单信息
        var username = window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
        axios.get('/delivery/order/' + row.id + '/send/' + username)
          .then(res => {
            console.log(res)
            _this.$message({
              showClose: true,
              message: '成功',
              type: 'success'
            })
            this.reload()
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    loadDetails () {
      this.$axios.get('/delivery/order/details').then(resp => {
        if (resp && resp.status === 200) {
          this.details = resp.data.result
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
