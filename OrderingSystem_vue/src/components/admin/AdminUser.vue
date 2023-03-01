<template>
  <div class="AdminUser">

    <div class="user_skills" style="width:100%">
      <el-button type="primary" plain style="width: 100%; background: transparent" @click="addUser()" >添加员工</el-button>
      <el-table :data="currentData" v-loading="loading1" style="width: 100%"   >
        <el-table-column prop="id" label="Id"  />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="password" label="密码"  />
        <el-table-column prop="role" label="角色" />
        <el-table-column fixed="right" label="操作" v-if="hidden" key="slot">
          <template  #default="scope" >
            <el-button @click="deleteData(scope.row)" type="text" size="small" >删除</el-button>
            <el-button  type="text" size="small" @click="dialogVisible1=true,currentUser=scope.row">修改密码</el-button>
            <el-dialog
            top="30vh"
            append-to-body
              title="提示"
              center
              :visible.sync="dialogVisible1"
              width="30%"
              :before-close="handleClose">
              您正在对{{currentUser.username}}的密码进行修改
              <el-input v-model="input" placeholder="请输入新密码"  auto-complete="off" show-password></el-input>
              <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible1 = false">取 消</el-button>
              <el-button type="primary" @click="editData(currentUser)">确定</el-button>
              </span>
            </el-dialog>
          </template>
        </el-table-column>

        <!--      <el-table-column prop="quantity" label="库存数量" width="120" />-->
        <!--      <el-table-column fixed="right" label="操作" width="120">-->
        <!--        <template #default="scope">-->
        <!--          <el-button @click="deleteData(scope.row)" type="text" size="small" >删除</el-button>-->
        <!--          <el-button @click="editData(scope.row)" type="text" size="small" >编辑</el-button>-->
        <!--        </template>-->
        <!--      </el-table-column>-->
      </el-table>

      <el-pagination  style="width:80%;bottom:10vh;position:fixed" background layout="prev, pager, next" :page-size="10" :total="total" :current-page="currentPage" @currentChange="changePage" />

    </div>
    <!-- <div>
      <el-dropdown class="manageStaff;"  @command="handleCommand">
      <span class="el-dropdown-link">
        员工管理
        <i class="el-icon-arrow-down el-icon--right"></i>
      </span>
        <el-dropdown-menu slot="dropdown">

          <el-dropdown-item command="b">增添新员工</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>

    </div> -->
    <el-dialog
      append-to-body
      title="提示"
      center
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <el-form class="login-container" label-position="left"
               label-width="0px" v-loading="loading">
        <h3 class="login_title">员工注册</h3>
        <el-form-item>
          <el-input type="text" v-model="loginForm.username"
                    auto-complete="off" placeholder="账号"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input type="password" v-model="loginForm.password"
                    auto-complete="off" placeholder="密码"></el-input>
        </el-form-item>
        <el-select style="width:100%;margin-bottom: 20px;" v-model="loginForm.role" placeholder="请选择员工职业">
      <el-option label="服务员" value="waiter"></el-option>
      <el-option label="厨师" value="cook"></el-option>
      <el-option label="管理员" value="admin"></el-option>
    </el-select>

        <!-- <el-form-item>
          <el-input type="text" v-model=""
                    auto-complete="off" placeholder="职位"></el-input>
        </el-form-item> -->
        <el-form-item>
          <el-input type="text" v-model="loginForm.salary"
                    auto-complete="off" placeholder="薪水"></el-input>
        </el-form-item>
        <el-form-item style="width: 100%">

          <el-button type="primary" style="width: 100%;background: #505458;border: none" @click="register">注册新员工</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
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
      currentData: [],
      loading1: true,
      currentPage: 1,
      dialogVisible: false,
      checked: true,
      loginForm: {
        username: '',
        password: '',
        role: '',
        salary: 0
      },
      loading: false,
      hidden: true,
      dialogVisible1: false,
      input: '',
      currentUser: {}
    }
  },
  created () {
    const _this = this
    axios.get('/manageUser/findAll/0/10').then(function (resp) {
      // _this.tableData = resp.data.result.content
      resp.data.result.content.forEach(item => {
        if (item.role === 'admin') {
          _this.tableData.push(item)
          // console.log(item)
        }
      })
      // console.log(resp.data.result.content)
      resp.data.result.content.forEach(item => {
        if (item.role === 'waiter') { _this.tableData.push(item) }
      })
      resp.data.result.content.forEach(item => {
        if (item.role === 'cook') { _this.tableData.push(item) }
      })
      resp.data.result.content.forEach(item => {
        if (item.role === 'customer') { _this.tableData.push(item) }
      })
      // console.log(_this.currentPage)
      _this.currentData = _this.tableData.slice((_this.currentPage - 1) * 10, _this.currentPage * 10)
      _this.total = resp.data.result.totalElements
      _this.loading1 = false
    })
  },
  methods: {
    addUser () {
      this.addNew()
    },
    deleteData (row) { // 删除某个员工
      this.$confirm('此操作将永久删除该条目, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const _this = this
        axios.delete('ManageUser/delete/' + row.id)
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
      console.log(row)
    },
    editData (row) {
      this.$confirm('确定修改该员工密码吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
      // 编辑某一个用户信息
        row.password = this.input
        console.log(row)
        this.$axios.post('/user/info/editPassWord', row)
        window.location.reload()
      })
    },

    register () { // 添加新员工
      var _this = this
      this.$axios
        .post('/employee/register', _this.loginForm)
        .then(resp => {
          if (resp.data.code === 200) {
            this.$alert('注册成功', '提示', {
              confirmButtonText: '确定'
            })
            _this.$router.replace('/login')
          } else {
            this.$alert(resp.data.message, '提示', {
              confirmButtonText: '确定'
            })
          }
        })
        .catch(failResponse => {})
      window.location.reload()
    },
    show () {
      this.hidden = !this.hidden
    },
    addNew () {
      this.dialogVisible = true
      console.log(2)
    },
    handleClose (done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => {})
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
    changePage (currentPage) {
      const _this = this
      _this.loading1 = true
      axios.get('/findAll/' + currentPage + '/10').then(function (resp) {
        _this.tableData = resp.data.result.content
        _this.total = resp.data.result.totalElements
        _this.loading1 = false
      })
    }
  }
}

</script>

<style scoped>
.AdminUser{
  cursor: pointer;
  display: flex;
}
.pagination{
  position:fixed;

  bottom: 20px;
  /* height:40px; */
  /* width:100%; */

}
.AdminUser{
  background-color:rgba(255, 255, 255,0);
}
/*.method1{*/
/*  visibility: hidden;*/
/*}*/

/*以下为修改表单透明度*/
.user_skills >>> .el-table--fit{

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
.table /deep/ .el-table th {
  background-color: transparent!important;
}

.table /deep/ .el-table tr {
  background-color: transparent!important;
}
.table /deep/  .el-table--enable-row-transition .el-table__body td, .el-table .cell{
  background-color: transparent;
}
/*去除底部白线*/
.el-table::before {
left: 0;
  bottom: 0;
  width: 100%;
  height: 0px;
}

/*结束*/
.el-dropdown-link {
  cursor: pointer;
  display: flex;
  font-style: italic;
  font-size: 30px;
  color: #3377aa;
  height: 40px;
  align-items: center;
  /*position:fixed;*/
  /*top:80px;*/
  /*right:20px;*/
}
</style>
