<template>
  <div class="AdminUser">
    <el-table :data="tableData" stripe v-loading="loading" style="width: 100%"
              ref="refTable" max-height="1000" @expand-change="loadEachDetail" :row-key="getRowKeys" :row-class-name="rowClassName">
      <el-table-column type="expand">
        <template v-slot="scope">
          <el-table :data="details[scope.row.id-1]" v-loading="childLoading">
            <el-table-column prop="dish.name" label="菜品名" width="300"/>
            <el-table-column prop="count" label="数量" width="300"/>
            <el-table-column prop="state" label="状态" width="300"/>
          </el-table>
        </template>
      </el-table-column>
      <el-table-column prop="id" label="Id" width="120" />
      <el-table-column label="订单创建时间" width="300" prop="createdTime"/>
      <el-table-column prop="payTime" label="订单支付时间" width="300" />
      <el-table-column prop="state" label="订单状态" width="300" />
      <el-table-column prop="price" label="总价格" width="150" />
    </el-table>
    <el-pagination background layout="prev, pager, next" :page-size="10" :total="total" @currentChange="changePage" />
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'CustomerOrder',
  data () {
    return {
      total: 0,
      tableData: [],
      loading: true,
      details: [],
      orderDetail: [],
      childLoading: true
    }
  },
  created () {
    this.loadOrder()
    this.loadDetails()
  },
  methods: {
    loadOrder () {
      var username = window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
      const _this = this
      _this.loading = true
      axios.get('/order/' + username + '/history').then(function (resp) {
        _this.tableData = resp.data.result
        _this.loading = false
      })
    },
    rowClassName ({row, rowIndex}) {
      row.id = rowIndex + 1
    },
    getRowKeys (row) {
      return row.id
    },
    changePage (currentPage) {
      const _this = this
      _this.loading = true
      axios.get('/findAll/' + currentPage + '/10').then(function (resp) {
        _this.tableData = resp.data.result
        _this.loading = false
      })
    },
    loadDetails () {
      var username = window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
      this.$axios.get('/order/' + username + '/details').then(resp => {
        if (resp && resp.status === 200) {
          this.details = resp.data.result
        }
      })
    },
    loadEachDetail (row) {
      this.orderDetail = []
      this.orderDetail = this.details[row.id - 1]
      this.childLoading = false
    }
  }
}
</script>

<style scoped>

</style>
