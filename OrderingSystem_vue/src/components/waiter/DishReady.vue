<template>
  <div class="user_skills">
    <el-table :data="dishes.filter(item => item.order.tables.name !== 'DELIVERY')" title="84">
      <el-table-column property="dish.name" label="菜品名" width="150"></el-table-column>
      <el-table-column property="order.tables.name" label="桌号" ></el-table-column>
      <el-table-column property="count" label="数量" ></el-table-column>
      <el-table-column  label="操作" >
        <template #default="scope">
          <el-button type="success" icon="el-icon-check" circle @click="changeState(scope.row)"></el-button>
        </template>

      </el-table-column>
    </el-table>

  </div>

</template>

<script>
export default {
  name: 'DishReady',
  inject: ['reload'],
  data () {
    return {
      loading: false,
      dishes: [],
      tables: [],
      timer: null
    }
  },
  created () {
    this.loadReadyDishes()
  },
  mounted () {
    this.loadReadyDishes()
    this.timer = setInterval(() => {
      setTimeout(this.loadReadyDishes, 0)
    }, 1000 * 5)
  },
  beforeDestroy () {
    clearInterval(this.timer)
    this.timer = null
  },
  methods: {
    // 此表单根据数据库中菜品的状态：列举所有未上菜的菜品信息。当服务员去后厨上菜后，点击确认上菜即可改变状态。
    changeState (row) {
      var _this = this
      this.$axios.get('/waiter/dish/ready/' + row.id + '/serve').then(resp => {
        if (resp && resp.data.code === 200) {
          _this.loadReadyDishes()
          _this.$message({
            showClose: true,
            message: '上菜成功！',
            type: 'success'
          })
        }
      })
      this.reload()
      // 同时删除该条目
    },
    loadReadyDishes () {
      var _this = this
      this.$axios.get('/waiter/dishes/ready').then(resp => {
        if (resp && resp.data.code === 200) {
          _this.dishes = resp.data.result
        }
      })
    }
  }
}
</script>

<style scoped>

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
