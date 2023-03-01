<template>
  <div>
    <el-container >
      <el-aside style="width: 200px ;border-radius: 20px;margin-top:10% ; margin-left:10px;">
        <el-menu style="background-color: rgb(255,255,255,0.8);border-radius: 20px;" default-active="/cook/main" @select="menuClick" active-text-color="rgb(2, 0, 105)">
          <el-menu-item index="/cook/main" style="border-radius: 20px">
            <i class="el-icon-menu"></i>
            <el-badge  class='item'>
              <span >个人主页</span>
            </el-badge>
          </el-menu-item>
          <el-menu-item index="/cook/mess" style="border-radius: 20px">
            <i class="el-icon-menu"></i>
            <el-badge :is-dot='flag1' class='item'>
              <span   @click='flag1=false'>我的公告</span>
            </el-badge>
          </el-menu-item>

          <el-menu-item index="/cook/order" style="border-radius: 20px">
            <i class="el-icon-menu"></i>
            <el-badge :is-dot='flag2' class='item'>
              <span   @click='flag2=false'>订单管理</span>
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
  </div>
</template>

<script>

export default {
  name: 'CookIndex',
  data () {
    return {
      flag1: true,
      flag2: true
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
  background-image: url(../../assets/120.jpg);
  background-size: cover;
}
</style>
