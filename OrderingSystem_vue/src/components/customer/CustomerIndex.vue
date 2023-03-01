<template>

  <el-container
    style="width: 98%;margin-left: 1%;margin-top: 10px;border-radius: 20px;background-color: rgba(255, 255, 255, 0.719) ;height: 700px;">
    <el-aside style="width: 200px ;border-radius: 40px">
      <!--<el-menu default-active="/dashboard" @select="menuClick" >-->
      <el-menu default-active="/customer/collect" @select="menuClick">
        <el-menu-item index="/customer/collect" style="border-radius: 20px">
          <i class="el-icon-menu"></i>
          <span slot="title">收藏菜品</span>
        </el-menu-item>
        <el-menu-item index="/customer/info" style="border-radius: 20px">
          <i class="el-icon-menu"></i>
          <span slot="title">个人信息修改</span>
        </el-menu-item>
        <!--        <el-menu-item index="/admin/user" style="border-radius: 20px">-->
        <!--          <i class="el-icon-menu"></i>-->
        <!--          <span slot="title">用户信息管理</span>-->
        <!--        </el-menu-item>-->
        <el-menu-item index="/customer/order" style="border-radius: 20px">
          <i class="el-icon-menu"></i>
          <span slot="title">订单信息管理</span>
        </el-menu-item>
        <!--        <el-menu-item index="/admin/data" style="border-radius: 20px">-->
        <!--          <i class="el-icon-menu"></i>-->
        <!--          <span slot="title">查看经营数据</span>-->
        <!--        </el-menu-item>-->
        <el-menu-item index="/customer/reviews" style="border-radius: 20px">
          <i class="el-icon-menu"></i>
          <span slot="title">查看所有评价</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-main>
      <router-view />
    </el-main>
  </el-container>

</template>

<script>
// eslint-disable-next-line import/no-duplicates

// eslint-disable-next-line import/no-duplicates

export default {
  name: 'UserIndex',
  methods: {
    menuClick (index) {
      this.$router.push(index)
    }
  }
}
</script>

<style scoped>
.el-menu-item.is-active {
  border-radius: 12px;
  background-color: #00278191 !important;
  color: #fff;
}
</style>
