<template>
  <el-container>
    <el-aside width="20%" >
      <SideMenu2></SideMenu2>
    </el-aside>
    <el-main class="el_main"  style="height: calc(100vh ); ">
      <router-view></router-view>
    </el-main>
  </el-container>
</template>

<script>
import SideMenu2 from './SideMenu2.vue'
export default {
  name: 'Aside',
  components: { SideMenu2 }
}
</script>

<style>
.el_main{
  background-color:rgba(255, 255, 255,0.8);
  border-top-right-radius: 40px;
  border-bottom-right-radius: 40px;

}
</style>
