<template>

  <el-container style="width: 96%;margin-left: 2%;margin-top: 10px;background-color:rgba(255, 255, 255,0.9);
  border-radius: 20px 40px 40px 20px;height:90vh;">
    <el-aside style="width: 200px ;border-radius: 20px">
      <el-menu default-active="/admin/index" @select="menuClick"
               background-color="#545c64"
               text-color="#fff"
               active-text-color=" #6EB5E7"
               style="height:100%;border-radius: 20px">
        <el-menu-item index="/dashboard" style="border-radius: 20px">
          <i class="el-icon-menu"></i>
          <span slot="title">网站信息总览</span>
        </el-menu-item>
        <el-menu-item index="/admin/dish" style="border-radius: 20px">
          <i class="el-icon-menu"></i>
          <span slot="title">菜品信息管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/user" style="border-radius: 20px">
          <i class="el-icon-menu"></i>
          <span slot="title">用户信息管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/order" style="border-radius: 20px">
          <i class="el-icon-menu"></i>
          <span slot="title">订单信息管理</span>
        </el-menu-item>
        <el-menu-item index="/pay" style="border-radius: 20px">
          <i class="el-icon-menu"></i>
          <span slot="title">订单结账管理</span>
        </el-menu-item>
        <el-menu-item index="/view/bulletin" style="border-radius: 20px">
          <i class="el-icon-menu"></i>
          <span slot="title">公告信息管理</span>
        </el-menu-item>
        <el-menu-item index="/viewdata" style="border-radius: 20px">
          <i class="el-icon-menu"></i>
          <span slot="title">查看经营数据</span>
        </el-menu-item>

      </el-menu>
    </el-aside>
    <el-main>
<!--      <el-container>-->
<!--        <el-aside style="background-color:white; margin-left: 3%;width: 70%;border-radius: 20px;height:80vh">-->
<!--          <router-view/>-->

<!--        </el-aside >-->
<!--        <el-main class="rightbox" style="background-color: white;border-radius: 20px;margin-left: 3%;">-->

<!--        </el-main>-->
<!--      </el-container>-->
        <router-view/>
    </el-main>
  </el-container>

</template>

<script>

export default {
  name: 'AdminIndex',
  beforeCreate () {
    document.body.setAttribute('style', 'background:#EAECF1')
  },
  methods: {
    menuClick (index) {
      this.$router.push(index)
    }
  }
}
</script>

<style scoped>

.rightbox{
  background-image: url(../../assets/120.jpg);
  background-size: cover;
}

</style>
