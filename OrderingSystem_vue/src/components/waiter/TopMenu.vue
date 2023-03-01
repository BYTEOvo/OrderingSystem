<template>
  <div class="menuin">

  <el-menu
    class="dishTypes" default-active="1"
    mode="horizontal"
    @select="handleSelect"

    text-color="white"
    background-color="#545c64"
    active-text-color="#6EB5E7"
  >

    <el-menu-item
    style="size:10px"
      v-for="item in dishTypeList.slice().reverse()"
      :key="item.id"
      :index="item.id.toString()"
      :title="item.name">
      <i class="el-icon-food"></i>
      {{item.name}}
    </el-menu-item>
  </el-menu>

</div>
</template>

<script>
export default {
  name: 'TopMenu',
  data () {
    return {
      dishTypeList: [],
      dishTypeId: ''
    }
  },
  created: function () {
    this.getDishTypeList()
  },
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
    },
    handleSelect (key, keyPath) {
      this.dishTypeId = key
      this.$emit('indexSelect')
    },
    getDishTypeList () {
      this.$axios.get('/dishType').then(resp => {
        if (resp && resp.status === 200) {
          this.dishTypeList = resp.data.result
          this.dishTypeList.push({
            id: 1,
            name: '全部'
          })
        }
      })
    }
  }

}
</script>

<style  scoped>
.el-menu-item{

padding-right:7px;
  font-size: small;
}

</style>
