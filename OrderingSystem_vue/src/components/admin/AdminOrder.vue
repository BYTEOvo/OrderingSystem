<template>
  <div class="user_skills" style="">
    <el-table :data="currentTable"  v-loading="loading" style="width: 100%;"
              ref="refTable" max-height="500" @expand-change="loadEachDetail" :row-key="getRowKeys">
      <el-table-column type="expand" style="background: rgba(255,255,255,0);">
        <template v-slot="scope">
          <el-table :data="details[scope.row.id-1]" v-loading="childLoading">
            <el-table-column prop="dish.name" label="菜品名" width="300"/>
            <el-table-column prop="count" label="数量" width="300"/>
            <el-table-column prop="state" label="状态" width="300"/>
          </el-table>
        </template>
      </el-table-column>
      <el-table-column prop="id" label="桌号"  />
      <el-table-column prop="user.username" label="用户名"  />
      <el-table-column prop="createdTime" label="订单生成时间"  />
      <el-table-column prop="payTime" label="订单完成时间"  />
      <el-table-column prop="price" label="价格" />
      <el-table-column prop="state" label="状态" />
    </el-table>
    <el-pagination style="width:80%;bottom:10vh;position:fixed" background layout="prev, pager, next" :page-size="10" :total="total" @current-change="changePage" :current-page.sync="CurrentPage"/>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Display',
  data () {
    return {
      total: 0,
      tableData: [],
      loading: true,
      details: [],
      orderDetail: [],
      currentTable: [],
      CurrentPage: 1,
      childLoading: true
    }
  },
  created () {
    this.loadOrder()
    this.loadDetails()
  },
  methods: {
    AliPay (row) {
      axios.post('/create', row).then(resp => {
        if (resp && resp.status === 200) {
          console.log(row)
          this.userInfo = resp.data.result
          window.localStorage.removeItem('callbackHTML')
          window.localStorage.setItem('callbackHTML', resp.data.result)
          // 读取本地保存的html数据，使用新窗口打开
          var newWin = window.open('')
          newWin.document.write(localStorage.getItem('callbackHTML'))
          // 关闭输出流
          newWin.document.close()
        }
      })
    },
    loadOrder () {
      const _this = this
      axios.get('/manageOrder/findAll').then(function (resp) {
        _this.tableData = resp.data.result
        _this.currentTable = _this.tableData.slice((_this.CurrentPage - 1) * 10, _this.CurrentPage * 10)
        _this.total = resp.data.result.length
        _this.loading = false
      })
    },
    getRowKeys (row) {
      return row.id
    },
    // deleteData (row) {
    //   this.$confirm('此操作将永久删除该条目, 是否继续?', '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning'
    //   }).then(() => {
    //     const _this = this
    //     axios.delete('/deleteById/' + row.id)
    //       .then(res => {
    //         console.log(res)
    //         _this.$message({
    //           showClose: true,
    //           message: '修改成功',
    //           type: 'success'
    //         })
    //         window.location.reload()
    //         // 删除账单接口未写
    //       })
    //   }).catch(() => {
    //     this.$message({
    //       type: 'info',
    //       message: '已取消删除'
    //     })
    //   })
    // },
    // editData (row) {
    //   this.$router.push({
    //     path: '/editdish',
    //     query: {
    //       name: row.name,
    //       author: row.author,
    //       id: row.id,
    //       isaccessible: row.isaccessible,
    //       quantity: row.quantity
    //     }
    //   })
    // },
    changePage () {
      const _this = this
      _this.loading = true
      _this.currentTable = _this.tableData.slice((_this.CurrentPage - 1) * 10, _this.CurrentPage * 10)
      console.log(this.CurrentPage)
      _this.loading = false
    },
    loadDetails () {
      this.$axios.get('/order/details').then(resp => {
        if (resp && resp.status === 200) {
          this.details = resp.data.result
        }
      })
      // 需要返回订单的桌号，未完成
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
/*::v-deep .el-table__expanded-cell {*/
/*  background-color: transparent !important;*/
/*}*/

/*::v-deep .el-table th{*/
/*  background-color: transparent;*/
/*}*/
/*::v-deep .el-table tr{*/
/*  background-color: transparent;*/
/*}*/
/*::v-deep .el-table td {*/
/*  background-color: transparent;*/
/*}*/
/*!*表头*!*/
/*::v-deep .el-table__header th {*/
/*  padding: 0;*/
/*  background: transparent;*/
/*  line-height: 30px;*/
/*}*/
.user_skills >>> .el-table--fit{
  padding: 10px;
}
.user_skills >>> .el-table, .el-table__expanded-cell {
  background-color: transparent;
}

.user_skills >>> .el-table tr {
  background-color: transparent!important;
}
.user_skills >>> .el-table th {
  background-color: transparent!important;
}
.user_skills >>> .el-table--enable-row-transition .el-table__body td, .el-table .cell{
  background-color: transparent;
}
.el-table::before {
  left: 0;
  bottom: 0;
  width: 100%;
  height: 0px;
}
</style>
