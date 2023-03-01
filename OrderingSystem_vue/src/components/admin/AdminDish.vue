<template>
  <div class="AdminDish">
  <el-button type="primary" plain style="width: 100%; background: transparent" @click="addData()" >新增菜品</el-button>

  <el-table :data="currentData"  v-loading="loading" style="width: 100%" max-height="500">
      <el-table-column prop="id" label="Id" width="120" />
      <el-table-column prop="name" label="菜名" width="300" />
      <el-table-column prop="price" label="价格" width="300" />
      <el-table-column prop="description" label="描述" width="240" />
<!--      <el-table-column prop="quantity" label="库存数量" width="120" />-->
      <el-table-column fixed="right" label="操作" width="150">
        <template #default="scope">
          <el-button @click="deleteData(scope.row)" type="text" size="small" >删除</el-button>
          <el-button @click="editData(scope.row)" type="text" size="small" >编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
  <el-pagination  style="width:80%;bottom:10vh;position:fixed" background layout="prev, pager, next" :page-size="10" :total="total" @current-change="changePage" :current-page.sync="CurrentPage"/>

    </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Display',
  data () {
    return {
      total: 0,
      CurrentPage: 1,
      currentData: [],
      tableData: [
        {
          name: '',
          price: '',
          id: '',
          description: ''
          // quantity: ''
        },
        {
          name: '',
          price: '',
          id: '',
          description: ''
          // quantity: ''
        }],
      loading: true
    }
  },
  created () {
    const _this = this
    axios.get('/dishes').then(function (resp) {
      _this.tableData = resp.data.result
      _this.currentData = _this.tableData.slice((_this.CurrentPage - 1) * 10, _this.CurrentPage * 10)
      console.log(_this.CurrentPage)
      console.log(_this.currentData)
      _this.total = resp.data.result.length
      _this.loading = false
    })
  },
  methods: {
    deleteData (row) {
      this.$confirm('此操作将永久删除该条目, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const _this = this
        axios.delete('/dishes/delete/' + row.id)
          .then(res => {
            console.log(res)
            _this.$message({
              showClose: true,
              message: '修改成功',
              type: 'success'
            })
            window.location.reload()
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    editData (row) {
      this.$router.push({
        path: '/editdish',
        query: {
          name: row.name,
          description: row.description,
          id: row.id,
          price: row.price,
          tid: row.dishType.id
        }
      })
    },
    addData () {
      this.$router.push('/adddish')
    },
    changePage () {
      const _this = this
      console.log(_this.CurrentPage)
      _this.loading = true
      _this.currentData = _this.tableData.slice((_this.CurrentPage - 1) * 10, _this.CurrentPage * 10)
      _this.loading = false
    }
  }
}

</script>
<style scoped>
/*以下为修改表单透明度*/
.AdminDish >>> .el-table--fit{

}
.AdminDish >>> .el-table, .el-table__expanded-cell {
  background-color: transparent;
}

.AdminDish >>> .el-table tr {
  background-color: transparent!important;
}
.AdminDish >>> .el-table th {
  background-color: transparent!important;
}
.AdminDish >>> .el-table--enable-row-transition .el-table__body td ,.el-table .cell{
  background-color: transparent;
}

.el-table::before {
  left: 0;
  bottom: 0;
  width: 100%;
  height: 0;
}

</style>
