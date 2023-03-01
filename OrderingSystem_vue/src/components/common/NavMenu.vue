<template>
  <div class="background">
    <el-menu :default-active="'/index'" router mode="horizontal" background-color="rgba(255, 255, 0, 0)" text-color="#222"
             active-text-color="rgb(2, 0, 105)" style="
        width: 100%;
        margin-left: 0%;
        min-width: 1300px;
        height: 50%;
        background-image: linear-gradient(to top, rgba(255, 255, 0, 0), rgb(165, 165, 165));
      " class="animate__animated animate__slideInDown">
      <el-menu-item v-for="(item, i) in navList" :key="i" :index="item.name" style="border-radius: 20px">
        {{ item.navItem }}
      </el-menu-item>
      <el-dropdown @command="handleCommand" style="float: right">
        <div class="header-right" style="font-size: 15px; color: rgb(2, 0, 105); padding: 20px">
          <i class="el-icon-user"></i>
          <span>个人信息</span>
        </div>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item   icon="el-icon-s-fold" command="a">修改信息</el-dropdown-item>
          <el-dropdown-item   icon="el-icon-switch-button" command="b">退出系统</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>

      <div class="title" style="
      position: absolute;
      padding-top: 15px;
      right: 43%;
      font-size: 30px;
      font-weight: bold;
      color: rgb(2, 0, 105)
    ">
        <i class="el-icon-fork-spoon"></i>
        <span class="t">红烧码铃薯餐厅</span>
        <i class="el-icon-location"></i>
      </div>
    </el-menu>
  </div>
</template>

<script>

export default {
  name: 'NavMenu',
  data () {
    return {
      navList: [],
      role: ''
    }
  },
  created: function () {
    this.getRole()
  },
  methods: {
    logout () {
      var _this = this
      this.$axios.get('/logout').then(resp => {
        if (resp.data.code === 200) {
          // 前后端状态保持一致
          _this.$store.commit('logout')
          this.$alert('登出成功', '提示', {
            confirmButtonText: '确定'
          })
          _this.$router.replace('/login')
        }
      })
    },
    getRole () {
      var username = window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
      var url = 'user/' + username + '/role'
      this.$axios.get(url).then(resp => {
        if (resp && resp.status === 200) {
          this.role = resp.data.result.role
          if (this.role === 'admin') {
            this.navList.push({name: '/admin/index', navItem: '管理中心'})
          } else if (this.role === 'cook') {
            this.navList.push({name: '/cook/index', navItem: '个人中心'})
          } else if (this.role === 'waiter') {
            this.navList.push({name: '/waiter/index', navItem: '个人中心'})
          } else if (this.role === 'delivery') {
            this.navList.push({name: '/delivery/index', navItem: '个人中心'})
          } else {
            this.navList.push({name: '/menu', navItem: '菜单'})
            this.navList.push({name: '/customer/index', navItem: '个人中心'})
          }
        }
      })
    },
    handleClose (done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => {})
    },
    handleCommand (command) {
      if (command === 'a') {
        this.$router.push('/' + this.role + '/info')
      } else {
        this.logout()
      }
    }
  }
}
</script>

<style scoped>
a {
  text-decoration: none;
}

span {
  pointer-events: none;
}
/*    @media screen and (min-width: 840px) {*/
/*        #background {*/
/*            position: absolute;*/
/*            left: 0;*/
/*            top: 0;*/
/*            width: 100%;*/
/*            height: 100%;*/
/*            background-image: url(https://img0.baidu.com/it/u=706369925,2175503806&fm=253&fmt=auto&app=120&f=JPEG?w=1280&h=800);*/
/*            background-size: cover;*/
/*            z-index: -1;*/
/*        }*/
/*}*/
.t{
  font-family: myfont2;
}
/* .dropdown>>>.el-dropdown-menu
.el-dropdown-menu-item{
  background-color: aqua;
} */
</style>
