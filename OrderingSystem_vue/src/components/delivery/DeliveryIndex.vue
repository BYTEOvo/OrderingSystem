<template>
  <el-container >
    <el-aside style="width: 200px ;border-radius: 20px;margin-top:10% ; margin-left:10px;">
      <el-menu style="background-color: rgb(255,255,255,0.8);border-radius: 20px;" default-active="/delivery/main" @select="menuClick" active-text-color="rgb(2, 0, 105)">
        <el-menu-item index="/delivery/main" style="border-radius: 20px">
          <i class="el-icon-menu"></i>
          <el-badge  class='item'>
            <span >个人主页</span>
          </el-badge>
        </el-menu-item>
        <el-menu-item index="/delivery/mess" style="border-radius: 20px">
          <i class="el-icon-menu"></i>
          <el-badge :is-dot='flag1' class='item'>
            <span   @click='flag1=false'>我的公告</span>
          </el-badge>
        </el-menu-item>

        <el-menu-item index="/delivery/order" style="border-radius: 20px">
          <i class="el-icon-menu"></i>
          <el-badge :is-dot='flag2' class='item'>
            <span   @click='flag2=false'>接单中心</span>
          </el-badge>
        </el-menu-item>
        <el-menu-item index="/delivery/my/order" style="border-radius: 20px">
          <i class="el-icon-menu"></i>
          <el-badge :is-dot='flag3' class='item'>
            <span   @click='flag3=false'>我的派单</span>
          </el-badge>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-main >
      <el-container>
        <el-aside style="background-color:white; margin-left: 3%;width: 70%;border-radius: 20px;height:80vh">
          <router-view/>

        </el-aside >
        <el-main class="rightBox" style="background-color: white;border-radius: 20px;margin-left: 1.5%;">
        </el-main>
      </el-container>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: 'DeliveryIndex',
  data () {
    return {
      flag1: true,
      flag2: true,
      flag3: true
    }
  },

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
.rightBox{
  background-image: url(../../assets/delivery1.png);
  background-size: 300px;
}
</style>
