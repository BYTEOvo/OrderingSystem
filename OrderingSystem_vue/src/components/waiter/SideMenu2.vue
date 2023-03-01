<template>
  <el-menu
    style="height:100vh"
    default-active=1 router
    class="el-menu-vertical-demo"
    background-color="#545c64"
    text-color="#fff"
    active-text-color=" #6EB5E7">
      <el-submenu >
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>个人中心</span>
        </template>

          <el-menu-item style="margin-left:-20px" index="/waiter/info" >修改信息</el-menu-item>
          <el-menu-item style="margin-left:-20px" @click="handleCommand(b)">登出系统</el-menu-item>

      </el-submenu>
<!--
      <el-dropdown @command="handleCommand" style="float: right">
      <div
        class="header-right"
        style="font-size: 14px; color: white; padding: 20px"
      >
        <i class="el-icon-user"></i>
        <span>个人中心</span>
      </div>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item icon="el-icon-s-fold" command="a"
        >修改信息</el-dropdown-item
        >

        <el-dropdown-item icon="el-icon-s-fold" command="b"
        >退出系统</el-dropdown-item
        >
      </el-dropdown-menu>
    </el-dropdown> -->

    <el-menu-item   index='/dish/ready' >
      <i class="el-icon-document"></i>
      <span slot="title">上菜</span>
    </el-menu-item>
    <el-menu-item index='/table'>
      <i class="el-icon-setting"></i>
      <span slot="title">餐桌</span>
    </el-menu-item>
    <el-menu-item index='/waiter/mess'>
      <i class="el-icon-setting"></i>
      <span slot="title">公告</span>
    </el-menu-item>

  </el-menu>
</template>

<script>

export default {
  name: 'SideMenu2',
  methods: {
    handleCommand (command) {
      if (command === 'a') {
        this.$router.push('/waiter/info')
      } else {
        var _this = this
        this.$axios.get('/logout').then((resp) => {
          if (resp.data.code === 200) {
            // 前后端状态保持一致
            _this.$store.commit('logout')
            this.$alert('登出成功', '提示', {
              confirmButtonText: '确定'
            })
            _this.$router.replace('/login')
          }
        })
      }
    }
  }
}
</script>

<style>

</style>
